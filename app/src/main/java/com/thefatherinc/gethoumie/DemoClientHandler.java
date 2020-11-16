package com.thefatherinc.gethoumie;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class DemoClientHandler extends ChannelInboundHandlerAdapter {
    DemoClient client;
    public DemoClientHandler(DemoClient client) {

        this.client=client;
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);

        //channelReadComplete - основной метод класса. Когджа ChannelInboundHandlerAdapter полностью
        //примет сообщение вызывается метод channelReadComplete. По сути, тут в ctx будет
        //отправляемый с сервера JSON.


    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
        //метод класса возвращает переданные данные. Но, есть отличие от channelReadComplete.
        //Если сообщение большое и в буфер не влезает, то вернется только часть сообщения. Поэтому будет несколько вызовов channelRead, пока не прочитается полностью сообщение из канала.
    }
}
