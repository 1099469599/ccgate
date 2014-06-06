package cn.voicet.callcenter.nts;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

public class NTSThreadClient {
	private static Logger log = Logger.getLogger(NTSThreadClient.class);
	private byte[] databuff = new byte[4096];
	private Socket socket = null;
	public String sServerIP;
	public String sNTSName;// 消息服务器名称
	public String sLocalName;// 本地登录名称
	public String sPassword;// 本地登录密码
	public boolean bRunning;
	private InputStream inputs;
	private OutputStream outputs;
	public int iServerPort;

	public NTSThreadClient(String local, String pwd) {
		sLocalName = local;
		sPassword = pwd;
	}

	private static Runnable createTask(final NTSThreadClient client,
			final String serverName, final String serverIp, final int port) {
		return new Runnable() {
			public void run() {
				client.bRunning = true;
				log.info("通讯线程已经启动");
				tryconnectagain: 
					if (client.Connect(serverName, serverIp, port)) {
					while (client.bRunning) {
						NTSMsg msg = client.RecvMessage();
						if (msg != null) {
							client.OnRecvMessage(msg);
						} else {
							try {
								Thread.sleep(1000 * 5);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							if(client.bRunning)
							{
								break tryconnectagain;
							}
							else
							{
								break;
							}
						}
					}
				} else {
					log.info("链接[" + client.sServerIP + ":"
							+ client.iServerPort + "]失败");
					try {
						Thread.sleep(1000 * 5);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if(client.bRunning)
					{
						break tryconnectagain;
					}
				}
			}
		};
	}

	//
	public void Start(String serverName, String serverIp, int port) {
		// 启动客户端线程
		ExecutorService exec = Executors.newCachedThreadPool();
		exec.execute(createTask(this, serverName, serverIp, port));
	}

	public void End() {
		try {
			this.bRunning = false;
			this.socket.close();
		} catch (Exception e) {

		}

	}

	private boolean Connect(String serverName, String serverIp, int port) {
		sNTSName = serverName;
		sServerIP = serverIp;
		iServerPort = port;
		try {
			socket = new Socket(serverIp, port);
			inputs = socket.getInputStream();
			outputs = socket.getOutputStream();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	private NTSMsg RecvMessage() {
		NTSMsg msg;
		try {
			int len = inputs.read(databuff, 0, 4000);
			if (len > 0) {
				msg = new NTSMsg();
				int packLen = BytesConvert.BytesToInt(databuff, 1);
				msg.msgID = BytesConvert.BytesToInt(databuff, 5);
				msg.from = BytesConvert.BytesToString(databuff, "UTF-8", 9, 6);
				msg.to = BytesConvert.BytesToString(databuff, "UTF-8", 15, 6);
				msg.action = BytesConvert.BytesToInt(databuff, 21);
				msg.channel = BytesConvert.BytesToShort(databuff, 25);
				msg.param = BytesConvert.BytesToShort(databuff, 27);
				System.out.println(packLen);
				if (packLen > 28) {
					msg.body = BytesConvert.BytesToString(databuff, "GBK", 29,
							packLen - 28);
				} else
					msg.body = "";
				return msg;
			}
			return null;
		}
		//
		catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean SendMessage(String to, int action, int channel, int param,
			String body) {
		return SendMessage(sLocalName, to, action, channel, param, body);
	}

	public boolean SendMessage(String from, String to, int action, int channel,
			int param, String body) {
		int len = 0;
		try {
			len = body.getBytes("GBK").length + 1;
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		databuff[0] = (byte) 0xA5;
		BytesConvert.IntToBytes(action, databuff, 5);
		BytesConvert.StringToBytes(from, "UTF-8", databuff, 9);
		BytesConvert.StringToBytes(to, "UTF-8", databuff, 15);
		BytesConvert.IntToBytes(action, databuff, 21);
		BytesConvert.ShortToBytes((short) channel, databuff, 25);
		BytesConvert.ShortToBytes((short) param, databuff, 27);
		BytesConvert.StringToBytes(body, "GBK", databuff, 29);
		BytesConvert.IntToBytes(len + 28, databuff, 1);
		System.out.println("len:" + len);
		try {
			outputs.write(databuff, 0, len + 29);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	//
	public class NTSMsg {
		public NTSMsg() {

		}

		public int msgID;
		public String from;
		public String to;
		public int action;
		public short channel;
		public short param;
		public String body;
	}

	protected void OnRecvMessage(NTSMsg msg) {
		log.info("From:" + msg.from + ",To:" + msg.to + ",action:" + msg.action
				+ ",channel:" + msg.channel + ",param:" + msg.param
				+ ",content:" + msg.body);
		switch (msg.action) {
		case 1: {
			SendMessage(sLocalName, "NTS100", 100, -1, -1, "PWD=" + sPassword
					+ ";");
		}
			break;
		case 110: {
			log.info("帐号:" + sLocalName + "登录服务器:" + sNTSName + "成功");
		}
			break;
		case 111: {
			log.info("帐号:" + sLocalName + "登录服务器:" + sNTSName + "失败，原因["
					+ msg.body + "]");
		}
			break;
		case 1000:// 心跳包
		{
			log.info("接收到心跳包");
			SendMessage("AGT100", "NTS100", 1000, -1, -1, "");
		}
			break;
		}

	}

}
