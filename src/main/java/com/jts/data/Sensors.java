package com.jts.data;

import javax.websocket.Session;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

public class Sensors {

    @Getter
    private static final Map<Session, SensorData> sensors = new HashMap<>();

    public static void addSensor(Session session) {
        sensors.put(session, new SensorData(0, 0, 0, Operation.NONE));
    }

    public static void updateSensor(Session session, float x, float y, float z, Operation operation) {
        sensors.get(session).updateData(x, y, z, operation);
    }

    public static void removeSensor(Session session) {
        sensors.remove(session);
    }
}
