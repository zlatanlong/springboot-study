package top.lclong.service;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import java.io.IOException;

/**
 * @Author: zlatanlong
 * @Date: 2020/9/29 16:16
 */
public interface WebSocketService {

    void sendMessage(Session session, String message) throws IOException;

    //给指定用户发送信息
    void sendInfo(String userName, String message);

    // 建立连接成功调用
    @OnOpen
    void onOpen(Session session, @PathParam(value = "sid") String userName);

    //关闭连接时调用
    @OnClose
    void onClose(@PathParam(value = "sid") String userName);

    //收到客户端信息
    @OnMessage
    void onMessage(String message);

    //错误时调用
    @OnError
    void onError(Session session, Throwable throwable);
}
