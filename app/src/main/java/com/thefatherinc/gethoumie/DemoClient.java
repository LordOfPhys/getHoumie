package com.thefatherinc.gethoumie;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetSocketAddress;
import java.util.concurrent.RejectedExecutionException;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

public class DemoClient {

    public Bootstrap b;
    public Context context;
    public EventLoopGroup workerGroup;
    public String host = "193.124.47.83"; //IP сервер
    public int port = 11111; //порт сервера
    public ChannelFuture f;
    String method;

    public DemoClient(String method) {

        workerGroup = new NioEventLoopGroup();
        this.method = method;
        //init();
    }

    public void init()
    {
        EventLoopGroup group = new NioEventLoopGroup();
        try{
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
            if (method.equals("authorization")) {
                test();
            }
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
    };

    public void authoraized() {
        String mData;
        mData = "{\"admin_panel\":\"true\", \"admin_pass\":\"Ulf5Wm3BtE9NJLvZ?|A9\",\"func\":\"getCountSocket\"}";//in.nextLine();
        f.channel().writeAndFlush(Unpooled.copiedBuffer(mData, CharsetUtil.UTF_8));
    }

    public void test() {
        String mData;
        mData = "{\"admin_panel\":\"true\",\"func\":\"test\"} ";//in.nextLine();
        f.channel().writeAndFlush(Unpooled.copiedBuffer(mData, CharsetUtil.UTF_8));
    }
}
