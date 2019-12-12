package cn.gov.wuxi.websocket;

import javax.websocket.*;

@ClientEndpoint
public class MyClient {
	    @OnOpen
	    public void onOpen(Session session){
	    	System.out.println("请求成功建立连接！！！");
	    }
	    @OnMessage
	    public void onMessage(String message) {
	        System.out.println("Client onMessage: " + message);
	    }
	 
	    @OnClose
	    public void onClose() {
			System.out.println("连接关闭！！！");
	    }
}
