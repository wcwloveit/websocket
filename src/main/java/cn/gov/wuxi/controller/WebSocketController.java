package cn.gov.wuxi.controller;
import cn.gov.wuxi.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
/**
 * Author:王春伟
 * Date:2019/9/5
 * 版权归王春伟本人所有，如有盗版，必按刑法处置
 **/
@RestController
@CrossOrigin(origins = "*",maxAge = 36000)
public class WebSocketController {

    @Autowired
    private WebSocketService webSocketService;

    @PostMapping("/test")
    public void pushMessage() throws IOException {
        webSocketService.start();
        webSocketService.session.getBasicRemote().sendText("testSMZX");
    }
}
