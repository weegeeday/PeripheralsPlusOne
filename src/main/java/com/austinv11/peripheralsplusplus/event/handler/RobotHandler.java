package com.austinv11.peripheralsplusplus.event.handler;

import com.austinv11.peripheralsplusplus.network.RobotEventPacket;
import com.austinv11.peripheralsplusplus.reference.Config;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.TickEvent;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.concurrent.CopyOnWriteArrayList;

public class RobotHandler {

private Robot robot;
public static CopyOnWriteArrayList<RobotOperation> operationList = new CopyOnWriteArrayList<>();

public RobotHandler() {
try {
robot = new Robot();
} catch (AWTException e) {
e.printStackTrace();
}
}

@SubscribeEvent
public void onClientTick(TickEvent.ClientTickEvent event) {
doOperations();
}

public void doOperations() {
if (robot == null) return;
for (RobotOperation op : operationList) {
operationList.remove(op);
try {
if (op.isKeyOp()) {
if (op.isDown())
robot.keyPress(op.getKeyCode());
else
robot.keyRelease(op.getKeyCode());
} else {
if (op.isDown())
robot.mousePress(op.getKeyCode());
else
robot.mouseRelease(op.getKeyCode());
}
} catch (Exception e) {
// Ignore
}
}
}

public static class RobotOperation {
private final int keyCode;
private final boolean isDown;
private final boolean isKeyOp;

public RobotOperation(int keyCode, boolean isDown, boolean isKeyOp) {
this.keyCode = keyCode;
this.isDown = isDown;
this.isKeyOp = isKeyOp;
}

public int getKeyCode() { return keyCode; }
public boolean isDown() { return isDown; }
public boolean isKeyOp() { return isKeyOp; }
}
}
