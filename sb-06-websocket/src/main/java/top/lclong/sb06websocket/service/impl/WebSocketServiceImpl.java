package top.lclong.sb06websocket.service.impl;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;
import top.lclong.sb06websocket.pojo.Message;
import top.lclong.sb06websocket.service.WebSocketService;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: zlatanlong
 * @Date: 2020/9/29 15:52
 */
@ServerEndpoint("/webSocket/{sid}")
@Service
public class WebSocketServiceImpl implements WebSocketService {
    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static AtomicInteger onlineNum = new AtomicInteger();

    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的WebSocketServer对象。
     */
    private static ConcurrentHashMap<String, Session> sessionPools = new ConcurrentHashMap<>();

    private String username;

    private Session session;

    @Override
    public void sendMessage(Session session, String message) throws IOException {
        if (session != null) {
            synchronized (session) {
                System.out.println("发送数据：" + message);
                session.getBasicRemote().sendText(message);
            }
        }
    }

    //给指定用户发送信息
    @Override
    public void sendInfo(String userName, String message) {
        Session session = sessionPools.get(userName);
        try {
            sendMessage(session, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 建立连接成功调用
    @Override
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "sid") String userName) {
        this.username = userName;
        this.session = session;
        sessionPools.put(userName, session);
        addOnlineCount();
        System.out.println(userName + "加入webSocket！当前人数为" + onlineNum);
        try {
            sendMessage(session, "欢迎" + userName + "加入连接！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 关闭连接时调用
    @Override
    @OnClose
    public void onClose(@PathParam(value = "sid") String userName) {
        sessionPools.remove(userName);
        subOnlineCount();
        System.out.println(userName + "断开webSocket连接！当前人数为" + onlineNum);
    }

    /**
     * 接收到客户端消息，并广播出去
     *
     * @param message
     */
    @Override
    @OnMessage
    public void onMessage(String message) {
        Message data = JSON.parseObject(message, Message.class);
        String txt = this.username + "找" + data.getToUserId() +" 说："+ data.getContentText();
        System.out.println(message);
        for (Session session : sessionPools.values()) {
            try {
                sendMessage(session, txt);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 错误时调用
    @Override
    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("发生错误");
        throwable.printStackTrace();
    }

    public static void addOnlineCount() {
        onlineNum.incrementAndGet();
    }

    public static void subOnlineCount() {
        onlineNum.decrementAndGet();
    }


}
