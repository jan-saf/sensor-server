package com.jts.websocket.model.three_floats;

import com.google.gson.Gson;

import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AppSensorMessageDecoder implements Decoder.Text<AppSensorMessage> {

    private static Gson gson = new Gson();

    public AppSensorMessageDecoder() {
        super();
        log.debug("New AppSensorMessageDecoder created");
    }

    @Override
    public AppSensorMessage decode(String s) {
        AppSensorMessage message = gson.fromJson(s, AppSensorMessage.class);
        return message;
    }

    @Override
    public boolean willDecode(String s) {
        return (s != null);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
        log.debug("AppSensorMessageDecoder Init");
    }

    @Override
    public void destroy() {
        log.debug("AppSensorMessageDecoder Destroy");
    }
}
