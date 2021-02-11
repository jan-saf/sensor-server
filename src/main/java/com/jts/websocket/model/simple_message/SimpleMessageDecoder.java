package com.jts.websocket.model.simple_message;

import com.google.gson.Gson;

import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleMessageDecoder implements Decoder.Text<SimpleMessage> {

    private static Gson gson = new Gson();

    public SimpleMessageDecoder() {
        super();
        log.debug("New SimpleMessageDecoder created");
    }

    @Override
    public SimpleMessage decode(String s) {
        SimpleMessage message = gson.fromJson(s, SimpleMessage.class);
        return message;
    }

    @Override
    public boolean willDecode(String s) {
        return (s != null);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
        log.debug("SimpleMessageDecoder Init");
    }

    @Override
    public void destroy() {
        log.debug("SimpleMessageDecoder Destroy");
    }
}
