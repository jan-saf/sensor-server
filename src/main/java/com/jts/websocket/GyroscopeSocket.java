package com.jts.websocket;

import com.jts.data.Sensors;
import com.jts.websocket.model.simple_message.SimpleMessage;
import com.jts.websocket.model.simple_message.SimpleMessageDecoder;
import com.jts.websocket.model.simple_message.SimpleMessageEncoder;
import com.jts.websocket.model.three_floats.AppSensorMessage;
import com.jts.websocket.model.three_floats.AppSensorMessageDecoder;
import com.jts.websocket.model.three_floats.AppSensorMessageEncoder;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;

@ServerEndpoint(
    value = "/gyroscope",
    decoders = {AppSensorMessageDecoder.class, SimpleMessageDecoder.class},
    encoders = {AppSensorMessageEncoder.class, SimpleMessageEncoder.class}
)
@Slf4j
public class GyroscopeSocket {

    private Session session;

    public GyroscopeSocket() {
        log.debug("New GyroscopeSocket created");
    }

    @OnOpen
    public void onOpen(Session session) throws IOException {
        this.session = session;
        Sensors.addSensor(session);
        broadcast(new SimpleMessage("Connected!"));
        log.info("Session {} opened", session.getId());
    }

    @OnMessage
    public void onMessage(Session session, AppSensorMessage data) throws IOException {
        Sensors.updateSensor(session, data.getVector().getX(), data.getVector().getY(), data.getVector().getZ(), data.getOperation());
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        Sensors.removeSensor(session);
        session.close();
        log.info("Session {} closed", session.getId());
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        throwable.printStackTrace();
        throw new RuntimeException(throwable);
    }

    private void broadcast(AppSensorMessage message) {
        try {
            session.getBasicRemote().sendObject(message);
        } catch (IOException | EncodeException e) {
            e.printStackTrace();
        }
    }

    private void broadcast(SimpleMessage message) {
        try {
            session.getBasicRemote().sendObject(message);
        } catch (IOException | EncodeException e) {
            e.printStackTrace();
        }
    }
}
