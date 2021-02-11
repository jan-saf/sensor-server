package com.jts.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SensorData {

    private float x;
    private float y;
    private float z;
    private Operation operation;

    public void updateData(float x, float y, float z, Operation operation) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.operation = operation;
    }

}
