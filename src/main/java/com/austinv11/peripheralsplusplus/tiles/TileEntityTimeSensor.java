package com.austinv11.peripheralsplusplus.tiles;

import com.austinv11.peripheralsplusplus.reference.Config;
import com.austinv11.peripheralsplusplus.utils.IPlusPlusPeripheral;
import dan200.computercraft.api.lua.IArguments;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class TileEntityTimeSensor extends BlockEntity implements IPlusPlusPeripheral {

private long timerStart = -1;

public TileEntityTimeSensor(BlockPos pos, BlockState state) {
super(com.austinv11.peripheralsplusplus.init.ModTileEntities.TIME_SENSOR.get(), pos, state);
}

@Override
public String getType() {
return "timeSensor";
}

@LuaFunction
public final Object[] getDate(IArguments args) throws LuaException {
if (!Config.enableTimeSensor)
throw new LuaException("Time Sensors have been disabled!");
String timeStamp = new SimpleDateFormat("yyyy@MM@dd@HH@mm@ss").format(new Date());
HashMap<String, Integer> map = new HashMap<>();
String[] split = timeStamp.split("@");
map.put("year", Integer.valueOf(split[0]));
map.put("month", Integer.valueOf(split[1]));
map.put("day", Integer.valueOf(split[2]));
map.put("hour", Integer.valueOf(split[3]));
map.put("minute", Integer.valueOf(split[4]));
map.put("second", Integer.valueOf(split[5]));
return new Object[]{map};
}

@LuaFunction
public final Object[] getTime(IArguments args) throws LuaException {
if (!Config.enableTimeSensor)
throw new LuaException("Time Sensors have been disabled!");
return new Object[]{System.currentTimeMillis()};
}

@LuaFunction
public final void startTimer(IArguments args) throws LuaException {
if (!Config.enableTimeSensor)
throw new LuaException("Time Sensors have been disabled!");
timerStart = System.currentTimeMillis();
}

@LuaFunction
public final Object[] stopTimer(IArguments args) throws LuaException {
if (!Config.enableTimeSensor)
throw new LuaException("Time Sensors have been disabled!");
long time = timerStart < 0 ? 0 : System.currentTimeMillis() - timerStart;
timerStart = -1;
return new Object[]{time};
}

@Override
public boolean equals(IPeripheral other) {
return other == this;
}
}
