package com.example.demo.decoder;

import java.util.List;

import org.springframework.stereotype.Component;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TestDecoder extends ByteToMessageDecoder {
    private int DATA_LENGTH = 2048;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < DATA_LENGTH) {
            return;
        }

        out.add(in.readBytes(DATA_LENGTH));
    }
}