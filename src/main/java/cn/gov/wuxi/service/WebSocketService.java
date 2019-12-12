package cn.gov.wuxi.service;
import cn.gov.wuxi.websocket.MyClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.net.URI;
/**
 * Author:王春伟
 * Date:2019/9/5
 * 版权归王春伟本人所有，如有盗版，必按刑法处置
 **/
@Service
public class WebSocketService {
    private ObjectMapper objectMapper = new ObjectMapper();
    private static String uri = "ws://localhost:8888/websocket/javaClient";
    public static Session session;
    public void start() {
        WebSocketContainer container = null;
        try {
            container = ContainerProvider.getWebSocketContainer();
        } catch (Exception ex) {
            System.out.println("error" + ex);
        }
        try {
            URI r = URI.create(uri);
            session = container.connectToServer(MyClient.class, r);
        } catch (Exception e) {

        }
    }
}
