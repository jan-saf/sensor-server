package com.jts.websocket.model.three_floats;

import com.google.gson.Gson;

import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AppSensorMessageEncoder implements Encoder.Text<AppSensorMessage> {

    private static Gson gson = new Gson();

    public AppSensorMessageEncoder() {
        super();
        log.debug("New AppSensorMessageEncoder created");
    }

    @Override
    public String encode(AppSensorMessage gyroscopeDataEncoder) {
        String json = gson.toJson(gyroscopeDataEncoder);
        return json;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
        log.debug("AppSensorMessageEncoder Init");
    }

    @Override
    public void destroy() {
        log.debug("AppSensorMessageEncoder Destroy");
    }
}
