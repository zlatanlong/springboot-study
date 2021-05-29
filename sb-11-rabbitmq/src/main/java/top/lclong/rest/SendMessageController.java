package top.lclong.rest;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @Author: zlatanlong
 * @Date: 2021/5/28 19:52
 */
@RestController
public class SendMessageController {
    private final RabbitTemplate rabbitTemplate;  //使用RabbitTemplate,这提供了接收/发送等等方法

    public SendMessageController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("/sendDirectMessage")
    public String sendDirectMessage() throws InterruptedException {
        new Thread(()->{
        for (int i = 0; i < 100000; i++) {
            rabbitTemplate.convertAndSend("TestDirectExchange", "TestDirectRouting", i);
            System.out.println(i);
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        }).start();
        //将消息携带绑定键值：TestDirectRouting 发送到交换机TestDirectExchange
        return "ok";
    }
}
