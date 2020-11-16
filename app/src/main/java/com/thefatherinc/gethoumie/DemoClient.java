package com.thefatherinc.gethoumie;

import android.content.Context;

import java.util.concurrent.RejectedExecutionException;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class DemoClient {

    public Bootstrap b;
    public Context context;
    public EventLoopGroup workerGroup;
    public String host = "193.124.47.83"; //IP сервер
    public int port = 10000; //порт сервера
    public ChannelFuture f;

    public DemoClient(Context context) {

        this.context=context;
        workerGroup = new NioEventLoopGroup();

        //init();
    }

    public void init()
    {
        //--
        Runnable runnable = new Runnable() {
            public void run() {
                try {
                    //Здесь происходит настройка портов и привязка к ним
                    b = new Bootstrap(); // (1)
                    b.group(workerGroup); // (2)
                    b.channel(NioSocketChannel.class); // (3)
                    b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
                    b.handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            //тут вызывается обработчик канала
                            ch.pipeline().addLast(new DemoClientHandler(DemoClient.this));
                        }
                    });

                    // Start the client.
                    f = b.connect(host, port).sync(); // (5)

                    // Wait until the connection is closed.
                    f.channel().closeFuture().sync();
                } catch (InterruptedException e) {
                    e.printStackTrace();

                }
                catch (RejectedExecutionException e) {
                    e.printStackTrace();

                }
                finally {
                    workerGroup.shutdownGracefully();
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

}
