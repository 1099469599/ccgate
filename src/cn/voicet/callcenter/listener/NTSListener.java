package cn.voicet.callcenter.listener;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import cn.voicet.callcenter.nts.NTSThreadClient;

public class NTSListener implements ServletContextListener {

	private NTSThreadClient ntsThread;
	
	public void contextDestroyed(ServletContextEvent event) {
		if(null!=ntsThread){
			System.out.println("ntsThread interrupt");
			ntsThread.End();
		}
	}

	public void contextInitialized(ServletContextEvent event) {
		if(null==ntsThread){
			String xmlPath = this.getClass().getClassLoader().getResource("/").getPath()+"appconfig.xml";
			System.out.println("xmlPath:"+xmlPath);
			File file = new File(xmlPath);
			SAXReader reader = new SAXReader();
			Document doc = null;
			try {
				doc = reader.read(file);// 读取XML文件
				Element root = doc.getRootElement();
				System.out.println("rootEle:"+root);
				String local = root.attributeValue("local");
				String password = root.attributeValue("password");
				String nts = root.attributeValue("nts");
				String serverip = root.attributeValue("serverip");
				int port = Integer.parseInt(root.attributeValue("port"));
				//
				ntsThread = new NTSThreadClient(local, password);
				ntsThread.Start(nts, serverip, port);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		ServletContext application = event.getServletContext();
		application.setAttribute("nts", ntsThread);
	}

}
