package com.jts.websocket.model.simple_message;

import com.google.gson.Gson;

import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleMessageEncoder  implements Encoder.Text<SimpleMessage> {

    private static Gson gson = new Gson();

    public SimpleMessageEncoder() {
        super();
        log.debug("New SimpleMessageEncoder created");
    }

    @Override
    public String encode(SimpleMessage gyroscopeDataEncoder) {
        String json = gson.toJson(gyroscopeDataEncoder);
        return json;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
        log.debug("SimpleMessageEncoder Init");
    }

    @Override
    public void destroy() {
        log.debug("SimpleMessageEncoder Destroy");
    }
}
