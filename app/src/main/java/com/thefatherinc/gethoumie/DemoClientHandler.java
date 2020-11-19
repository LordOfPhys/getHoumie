package com.thefatherinc.gethoumie;

import android.util.Log;

import org.json.JSONException;

import io.netty.buffer.ByteBuf;
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
    public void channelActive(ChannelHandlerContext channelHandlerContext) {
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        String answer = "";
        String jsonText = received; //Здесь лежит JSON
        org.json.JSONObject parsedObject = new org.json.JSONObject(jsonText); //Здесь парсим.
        Log.d("channelReadComplete" , received);
        while (parsedObject.keys().hasNext()) {
            answer += parsedObject.keys().next();
        }
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
