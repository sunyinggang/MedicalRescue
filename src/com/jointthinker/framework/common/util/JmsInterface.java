package com.jointthinker.framework.common.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class JmsInterface {
	private static Session session;
	private static MessageProducer msgPro;
	private static Connection conn=null;
	private static Context context;
	
	public JmsInterface(){
		try {
			if (conn==null){
				Properties props = new Properties();
				InputStream in = JmsInterface.class.getResourceAsStream("/jndi.properties"); 
				props.load(in);
				context = new InitialContext(props);
				ConnectionFactory connFactory = (ConnectionFactory)context.lookup("/ConnectionFactory");
				Queue bizQueue = (Queue)context.lookup("/queue/bizQueue");
				conn = connFactory.createConnection();
				session = conn.createSession(false, Session.DUPS_OK_ACKNOWLEDGE);
				MessageConsumer msgCon = session.createConsumer(bizQueue);
				/*//监控项topic
				Topic commonTopic = (Topic)context.lookup("/topic/commonTopic");
				MessageProducer msgPro = session.createProducer(commonTopic);
				msgPro.setDisableMessageID(true);
				msgPro.setDisableMessageTimestamp(true);
				msgPro.setDeliveryMode(DeliveryMode.NON_PERSISTENT);*/
				//监控项queue
				Queue commonQueue = (Queue)context.lookup("/queue/commonQueue");
				msgPro = session.createProducer(commonQueue);
				msgPro.setDisableMessageID(true);
				msgPro.setDisableMessageTimestamp(true);
				msgPro.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
				conn.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public void SendJmsMessage(String messageStr,String devId){
//		CommonMsgTread commonMsg = new CommonMsgTread(session,msgPro);
//		commonMsg.setMessageStr(messageStr);
//		commonMsg.setDevId(devId);
//		commonMsg.setConn(conn);
//		commonMsg.start();	
		TextMessage tm;
		try {
			tm = session.createTextMessage(messageStr);
			tm.setStringProperty("agentId", devId);
			tm.setStringProperty("_HQ_LVQ_NAME", devId);
			msgPro.send(tm);
			conn.close();
			conn=null;
			context.close();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		try {
			Properties props = new Properties();
			InputStream in = JmsInterface.class.getResourceAsStream("/jndi.properties"); 
			props.load(in);
			Context context = new InitialContext(props);
			ConnectionFactory connFactory = (ConnectionFactory)context.lookup("/ConnectionFactory");
			Queue bizQueue = (Queue)context.lookup("/queue/bizQueue");
			Connection conn = connFactory.createConnection();
			Session session = conn.createSession(false, Session.DUPS_OK_ACKNOWLEDGE);
			MessageConsumer msgCon = session.createConsumer(bizQueue);
			/*//监控项topic
			Topic commonTopic = (Topic)context.lookup("/topic/commonTopic");
			MessageProducer msgPro = session.createProducer(commonTopic);
			msgPro.setDisableMessageID(true);
			msgPro.setDisableMessageTimestamp(true);
			msgPro.setDeliveryMode(DeliveryMode.NON_PERSISTENT);*/
			//监控项queue
			Queue commonQueue = (Queue)context.lookup("/queue/commonQueue");
			MessageProducer msgPro = session.createProducer(commonQueue);
			msgPro.setDisableMessageID(true);
			msgPro.setDisableMessageTimestamp(true);
			msgPro.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			conn.start();
			
			//conn.setExceptionListener(new MyExceptionListener(connFactory,conn,session,msgCon,msgPro));
			
			CommonMsgTread commonMsg = new CommonMsgTread(session,msgPro);
			commonMsg.start();
			
			//TextMessage tm = null;
			
			File fsend = new File("d:\\receive.txt");
			if(!fsend.exists()){
				fsend.createNewFile();
			}
			FileWriter frsend = new FileWriter(fsend);
			final BufferedWriter bwsend = new BufferedWriter(frsend);
			//String text = "";
			/*while(true){
				tm = (TextMessage)msgCon.receive(1000);
				if(tm!=null){
					text = tm.getText();
					//System.out.println(tm.getStringProperty("agentEvent")+"\n"+text);
					bwsend.write(text);
					bwsend.newLine();
					bwsend.flush();
				}else{
					System.out.println("no............");
				}
				Thread.sleep(1000);
			}*/
			
			msgCon.setMessageListener(new MessageListener(){
				@Override
				public void onMessage(Message msg) {
					try {
						TextMessage tm  = (TextMessage)msg;
						if(tm!=null){
							String text = tm.getText();
							System.out.println(tm.getStringProperty("agentEvent")+"\n"+text);
							bwsend.write(text);
							bwsend.newLine();
							bwsend.flush();
						}else{
							System.out.println("no............");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

/*class MyExceptionListener implements ExceptionListener{
	private ConnectionFactory connFactory;
	private Connection conn;
	private Session session;
	private MessageConsumer msgCon;
	private MessageProducer msgPro;
	
	public MyExceptionListener(ConnectionFactory connFactory, Connection conn,Session session,MessageConsumer msgCon,MessageProducer msgPro){
		this.connFactory = connFactory;
		this.conn = conn;
		this.session = session;
		this.msgCon = msgCon;
		this.msgPro = msgPro;
	}

	public void onException(JMSException e) {
		try {
			System.out.println("Handle BsmServer Connection Error.................................");
			if(conn!=null){
				conn.close();
			}
			conn = connFactory.createConnection();
			conn.setExceptionListener(this);
		} catch (JMSException e1) {
			e1.printStackTrace();
		}
	}
	
}*/

class CommonMsgTread extends Thread{
	
	private Session session;
	private MessageProducer msgPro;
	private String messageStr;
	private String devId;
	private Connection conn;
	
	public CommonMsgTread(Session session, MessageProducer msgPro) {
		this.session = session;
		this.msgPro = msgPro;
	}
	@Override
	public void run(){
		TextMessage tm;
		try {
			tm = session.createTextMessage(messageStr);
			tm.setStringProperty("agentId", devId);
			tm.setStringProperty("_HQ_LVQ_NAME", devId);
			msgPro.send(tm);
			conn.close();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//	public void run(){
//		try {
//			Random rand = new Random(); 
//			String randVal = "3";
//			while(true){
//				TextMessage tm = (TextMessage)session.createTextMessage(readAgentContent());
//				//randVal = rand.nextInt(5)+"";
//				System.out.println(randVal+"?????????????????????????????????????????????????");
//				tm.setStringProperty("agentId", randVal);
//				tm.setStringProperty("_HQ_LVQ_NAME", randVal);
//				msgPro.send(tm);
//				Thread.sleep(15000);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	 private String readAgentContent() throws IOException {
		  File file = new File("D:\\workspace\\bsm_agent\\agent.xml");
		  BufferedReader bf = new BufferedReader(new FileReader(file));
		  String content = "";
		  StringBuilder sb = new StringBuilder();
		  
		  while(content != null){
			  content = bf.readLine();
			  if(content == null){
				  break;
			  }
			  sb.append(content.trim()+"\n");
		  }
		  bf.close();
		  return sb.toString();
	 }
	public String getMessageStr() {
		return messageStr;
	}
	public void setMessageStr(String messageStr) {
		this.messageStr = messageStr;
	}
	public String getDevId() {
		return devId;
	}
	public void setDevId(String devId) {
		this.devId = devId;
	}
	public Session getSession() {
		return session;
	}
	public void setSession(Session session) {
		this.session = session;
	}
	public MessageProducer getMsgPro() {
		return msgPro;
	}
	public void setMsgPro(MessageProducer msgPro) {
		this.msgPro = msgPro;
	}
	public Connection getConn() {
		return conn;
	}
	public void setConn(Connection conn) {
		this.conn = conn;
	}
}

