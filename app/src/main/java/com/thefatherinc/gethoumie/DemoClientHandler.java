package com.thefatherinc.gethoumie;

import android.util.Log;

import org.json.JSONObject;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class DemoClientHandler extends ChannelInboundHandlerAdapter {
    public String received = "";
    DemoClient client;
    public DemoClientHandler(DemoClient client) {

        this.client=client;
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

        //channelReadComplete - основной метод класса. Когджа ChannelInboundHandlerAdapter полностью
        //примет сообщение вызывается метод channelReadComplete. По сути, тут в ctx будет
        //отправляемый с сервера JSON.
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf inBuffer = (ByteBuf) msg;
        received += inBuffer.toString(CharsetUtil.UTF_8);
    }
}
