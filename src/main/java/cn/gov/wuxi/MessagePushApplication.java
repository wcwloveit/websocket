package cn.gov.wuxi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * Author:王春伟
 * Date:2019/9/5
 * 版权归王春伟本人所有，如有盗版，必按刑法处置
 **/
@SpringBootApplication(scanBasePackages = "cn.gov.wuxi")
public class MessagePushApplication {
    protected static Logger logger = LoggerFactory.getLogger(MessagePushApplication.class);
    public static void main(String[] args){
        SpringApplication.run(MessagePushApplication.class,args);
        logger.info("SpringBoot Start Success !!!");
    }
}
