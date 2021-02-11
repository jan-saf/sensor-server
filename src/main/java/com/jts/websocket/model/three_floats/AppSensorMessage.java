package com.jts.websocket.model.three_floats;

import com.jts.data.Operation;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AppSensorMessage {

    private Vector vector;
    private Operation operation;

    @Data
    @AllArgsConstructor
    public class Vector {
        private float x;
        private float y;
        private float z;
    }
}
