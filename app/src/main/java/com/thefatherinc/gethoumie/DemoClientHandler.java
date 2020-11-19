package com.thefatherinc.gethoumie;

import android.util.Log;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.HashMap;
import java.util.Map;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.json.JsonObjectDecoder;
import io.netty.util.CharsetUtil;

public class DemoClientHandler extends ChannelInboundHandlerAdapter {
    public String received = "";
    DemoClient client;
    public DemoClientHandler(DemoClient client) {

        this.client=client;
    }

    @Override
    public void channelActive(ChannelHandlerContext channelHandlerContext) {
        Log.d("test", "test");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        String answer = "";
        String jsonText = received; //Здесь лежит JSON
        org.json.JSONObject parsedObject = new org.json.JSONObject(jsonText); //Здесь парсим.
        HashMap hashmap = new HashMap<HashMap, HashMap>();
        try {
            hashmap.putAll((Map) new JSONParser().parse(parsedObject.toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Object[] keys = hashmap.keySet().toArray();
        for (int i = 0; i < keys.length; ++i) {
            answer += hashmap.get(keys[i]);
        }
        Log.d("answer", answer);
        //channelReadComplete - основной метод класса. Когджа ChannelInboundHandlerAdapter полностью
        //примет сообщение вызывается метод channelReadComplete. По сути, тут в ctx будет
        //отправляемый с сервера JSON.
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf inBuffer = (ByteBuf) msg;
        received += inBuffer.toString(CharsetUtil.UTF_8);
        Log.d("answer1", received);
    }
}