package cn.voicet.callcenter.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import net.sf.json.JSONObject;
import cn.voicet.callcenter.nts.NTSThreadClient;

/**
 * http://voicet.oicp.net:8000/ccgate/sendMessage?to=ivr100&action=2700&body=testsss
 * @author Administrator
 *
 */
public class SendMessage extends HttpServlet {

	private static Logger log = Logger.getLogger(SendMessage.class);
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String from;	//default thread own
		String to;		//not null
		int msgId;		//random int number
		int action;		//命令号 not null
		int channel;	//通道号 default -1
		int param;		//default -1
		String body;	//default ''
		//取得Application对象   
        ServletContext application=this.getServletContext();
        NTSThreadClient nsClient = (NTSThreadClient) application.getAttribute("nts");
        //
        if(null != request.getParameter("from"))
        {
        	from = request.getParameter("from");
        }
        else
        {
        	from = nsClient.sLocalName;
        }
        to = request.getParameter("to");
        if(null != request.getParameter("msgId"))
        {
        	msgId = Integer.parseInt(request.getParameter("msgId"));
        }
        else
        {
        	msgId = (int) Math.random();
        }
        action = Integer.parseInt(request.getParameter("action"));
        if(null!=request.getParameter("channel"))
        {
        	channel = Integer.parseInt(request.getParameter("channel"));
        }
        else
        {
        	channel = -1;
        }
        if(null!=request.getParameter("param"))
        {
        	param = Integer.parseInt(request.getParameter("param"));
        }
        else
        {
        	param = -1;
        }
        if(null!=request.getParameter("body"))
        {
        	body = request.getParameter("body");
        }
        else
        {
        	body = "";
        }
		//
        log.info("from:"+from+", to:"+to+", msgId:"+msgId+", action:"+action+", channel:"+channel+", param:"+param+", body:"+body);
        
        nsClient.SendMessage(from, to, action, channel, param, body);
        JSONObject json = new JSONObject();
        json.put("from", from);
        json.put("to", to);
        json.put("msgId", msgId);
        json.put("action", action);
        json.put("channel", channel);
        json.put("param", param);
        json.put("body", body);
        out.println(json);
		out.flush();
		out.close();
	}

}
