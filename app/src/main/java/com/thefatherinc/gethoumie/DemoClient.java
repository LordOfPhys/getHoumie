package com.thefatherinc.gethoumie;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;


public class DemoClient implements Runnable {

    public Bootstrap b;
    public Context context;
    public EventLoopGroup workerGroup;
    public String host = "193.124.47.83"; //IP сервер
    public int port = 11111; //порт сервера
    public ChannelFuture f;
    String method;

    public DemoClient(Context context) {
        this.context = context;
        workerGroup = new NioEventLoopGroup();
        //init();
    }

    public void run() {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap clientBootstrap = new Bootstrap();
            DemoClientHandler demoClientHandler = new DemoClientHandler(DemoClient.this);

            clientBootstrap.group(group);
            clientBootstrap.channel(NioSocketChannel.class);
            clientBootstrap.remoteAddress(new InetSocketAddress("193.124.47.83", 11111));
            clientBootstrap.handler(new ChannelInitializer<SocketChannel>() {
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    //                 socketChannel.pipeline().addLast(new TimeDecoder());
                    socketChannel.pipeline().addLast(demoClientHandler);
                }
            });
            f = clientBootstrap.connect().sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                group.shutdownGracefully().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(String func, String[] message) throws JSONException {
        if (func.equals("registration_use_email")) {
            registration_use_email(message[0], message[1], message[2], message[3], message[4]);
        } else {
            String mData;
            mData = "{\"admin_panel\":\"true\",\"func\":\"test\"} ";//in.nextLine();
            f.channel().writeAndFlush(Unpooled.copiedBuffer(mData, CharsetUtil.UTF_8));
        }
    }

    public void registration_use_email(String user_email, String user_pass, String user_name, String user_birthday, String user_gender) throws JSONException {
        //Пункт 2. На сервер отправляется функция getCountSocket. На самом деле может быть отправлена любая функция
        //первой (например при регистрации). на данный момент именно авторизация.
        JSONObject jsonObjectParams =new JSONObject();
        jsonObjectParams.put("func", "createAccount");
        jsonObjectParams.put("user_email", user_email);
        jsonObjectParams.put("user_pass", user_pass);
        jsonObjectParams.put("user_name", user_name);
        jsonObjectParams.put("user_family", "ТестовФамилия@test.ru");
        jsonObjectParams.put("user_patronymic", "ТестовичОтчество");
        jsonObjectParams.put("user_birthday", user_birthday);
        jsonObjectParams.put("user_gender", user_gender);
        jsonObjectParams.put("device_id", "39df2bbfbec08eea");
        jsonObjectParams.put("device_model", "SM-G955F");
        jsonObjectParams.put("user_language", "ru");
        jsonObjectParams.put("display_language", "русский");
        jsonObjectParams.put("v", "1.0");
        f.channel().writeAndFlush(Unpooled.copiedBuffer(jsonObjectParams.toString(), CharsetUtil.UTF_8));
    }

    public void test() {
        String mData;
        mData = "{\"admin_panel\":\"true\",\"func\":\"test\"} ";//in.nextLine();
        f.channel().writeAndFlush(Unpooled.copiedBuffer(mData, CharsetUtil.UTF_8));
    }
}