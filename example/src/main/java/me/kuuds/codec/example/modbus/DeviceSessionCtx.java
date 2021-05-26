package me.kuuds.codec.example.modbus;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DeviceSessionCtx {

    Map<String, SubDeviceSessionCtx> subDeviceSessionCtxMap = new ConcurrentHashMap<>();

    String deviceSn;
    String productKey;

}
