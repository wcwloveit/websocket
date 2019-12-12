package cn.gov.wuxi.websocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
/** 
 * WebSocket服务端示例 
 */
@Component
//访问服务端的url地址
@ServerEndpoint(value = "/websocket/{userName}")
public class WebSocketServer {
	private static int onlineCount = 0;
	private static ConcurrentHashMap<String, WebSocketServer> webSocketSet = new ConcurrentHashMap<>();
	//与某个客户端的连接会话，需要通过它来给客户端发送数据
	private Session session;
	private static Logger log = LoggerFactory.getLogger(WebSocketServer.class);
	//用来标识客户端的(如是java客户端还是web客户端)
	private String userName = "";

	/**
	 * 连接建立成功调用的方法*/
	@OnOpen
	public void onOpen(@PathParam(value = "userName") String userName, Session session){
		this.session = session;
		this.userName = userName;//接收到发送消息的人员编号
		if (webSocketSet.containsKey("webClient")){
			webSocketSet.remove("webClient");
			webSocketSet.put(userName, this);     //加入set中
		}else {
			webSocketSet.put(userName, this);     //加入set中
		}
		addOnlineCount(+1);           //在线数加1
		log.info("用户"+userName+"加入！当前在线人数为" + getOnlineCount());
		try {
			sendMessage("连接成功",this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 连接关闭调用的方法
	 */
	@OnClose
	public void onClose() {
		webSocketSet.remove(this.userName);  //从set中删除
		addOnlineCount(-1);           //在线数减1
		log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
	}

	/**
	 * 收到客户端消息后调用的方法
	 * @param message 客户端发送过来的消息*/
	@OnMessage
	public void onMessage(String message){
		System.out.println("陈湘军");
		System.out.println(message);
		log.info("=============================================================================");
		if(message.startsWith("webMessage")){
			log.info("===============================webMessage============================");
			try {
				webSocketSet.forEach((userName,webSocket) -> {
					if ("javaClient".equals(userName)){
						try {
							int index = message.indexOf(":");
							String resultMessage = message.substring(index+1);
							System.out.println("将消息发送给张怀北:"+resultMessage);
							sendMessage(resultMessage,webSocket);
						} catch (IOException e){
							e.printStackTrace();
						}
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if (message.startsWith("javaMessage:")){
			log.info("===============================javaMessage============================");
			webSocketSet.forEach((userName,webSocket) -> {if ("webClient".equals(userName)) {
				try {
					int index = message.indexOf(":");
					String resultMessage = message.substring(index+1);
					sendMessage(resultMessage,webSocket);
				} catch (IOException e) {

					e.printStackTrace();
				}
			}
			});
		} 

		System.out.println("来自客户端的消息:" + message);
	}
	/**
	 * @param session
	 * @param error
	 */
	@OnError
	public void onError(Session session, Throwable error) {
		log.error("发生错误");
		error.printStackTrace();
	}
	/**
	 * 发送消息的具体方法
	 * @param message
	 * @throws IOException
	 */
	public void sendMessage(String message,WebSocketServer webSocketServer) throws IOException {
		webSocketServer.session.getBasicRemote().sendText(message);
	}

	/**
	 * 发送信息给指定ID用户，如果用户不在线则返回不在线信息给自己
	 * @param message
	 * @throws IOException
	 */
	public void sendtoUser(String message,String sendUserUserName) throws IOException {
		WebSocketServer webSocketServer = webSocketSet.get(sendUserUserName);
		if (webSocketServer != null) {
			webSocketServer.sendMessage(message,webSocketServer);
		} else {
			log.info("当前用户不在线："+sendUserUserName);
		}
	}

	/**
	 * 发送信息给所有人
	 * @param message
	 * @throws IOException
	 */
	public void sendtoAll(String message) throws IOException {
		for (String key : webSocketSet.keySet()) {
			try {
				webSocketSet.get(key).sendMessage(message,this);
			} catch (IOException e){
				e.printStackTrace();
			}
		}
	}


	public static synchronized int getOnlineCount() {
		return onlineCount;
	}

	public static synchronized void addOnlineCount(int count) {

		WebSocketServer.onlineCount = WebSocketServer.onlineCount + count;
	}

}

