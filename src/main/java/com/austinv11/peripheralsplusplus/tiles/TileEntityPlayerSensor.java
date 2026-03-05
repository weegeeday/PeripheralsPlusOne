package com.austinv11.peripheralsplusplus.tiles;

import com.austinv11.peripheralsplusplus.reference.Config;
import com.austinv11.peripheralsplusplus.utils.IPlusPlusPeripheral;
import com.austinv11.peripheralsplusplus.utils.Util;
import dan200.computercraft.api.lua.IArguments;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.turtle.ITurtleAccess;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.HashMap;
import java.util.List;

public class TileEntityPlayerSensor extends BlockEntity implements IPlusPlusPeripheral.HasPeripheral {

private final HashMap<IComputerAccess, Boolean> computers = new HashMap<>();
private ITurtleAccess turtle;

public TileEntityPlayerSensor(BlockPos pos, BlockState state) {
super(com.austinv11.peripheralsplusplus.init.ModTileEntities.PLAYER_SENSOR.get(), pos, state);
}

public TileEntityPlayerSensor(ITurtleAccess turtle) {
super(com.austinv11.peripheralsplusplus.init.ModTileEntities.PLAYER_SENSOR.get(), turtle.getPosition(), turtle.getLevel().getBlockState(turtle.getPosition()));
this.turtle = turtle;
}

public void tickAsTurtle() {
if (turtle != null)
}
public final Object[] getNearbyPlayers(IArguments args) throws LuaException {
if (!Config.enablePlayerSensor)
throw new LuaException("Player sensors have been disabled");
if (!Config.additionalMethods)
throw new LuaException("Additional methods for player sensors have been disabled");

double range = args.count() > 0 ? args.getDouble(0) : Config.sensorRange;
BlockPos pos = getBlockPos();
AABB box = new AABB(pos.getX() - range, pos.getY() - range, pos.getZ() - range,
pos.getX() + range, pos.getY() + range, pos.getZ() + range);

List<Player> nearby = getLevel().getEntitiesOfClass(Player.class, box);
HashMap<Integer, HashMap<String, Object>> returnVal = new HashMap<>();
int i = 1;
for (Player player : nearby) {
// Simpler distance calculation
double dx = player.getX() - pos.getX();
double dy = player.getY() - pos.getY();
double dz = player.getZ() - pos.getZ();
double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);
HashMap<String, Object> table = new HashMap<>();
table.put("player", player.getName().getString());
table.put("distance", distance);
returnVal.put(i++, table);
}
return new Object[]{returnVal};
}

public final Object[] getAllPlayers(IArguments args) throws LuaException {
if (!Config.enablePlayerSensor)
throw new LuaException("Player sensors have been disabled");
if (!Config.additionalMethods)
throw new LuaException("Additional methods for player sensors have been disabled");

boolean inWorld = args.count() > 0 && args.getBoolean(0);
HashMap<Integer, String> map = new HashMap<>();
int i = 1;
for (String p : Util.getPlayers(inWorld ? getLevel() : null)) {
map.put(i++, p);
}
return new Object[]{map};
}
public void blockActivated(String player) {
for (IComputerAccess computer : computers.keySet())
computer.queueEvent("player", new Object[]{player});
}
    private final IPeripheral peripheral = new IPeripheral() {
        @Override
        public String getType() { return "playerSensor"; }

        @Override
        public void attach(IComputerAccess computer) {
            computers.put(computer, true);
        }

        @Override
        public void detach(IComputerAccess computer) {
            computers.remove(computer);
        }

        @Override
        public boolean equals(IPeripheral other) { return other == TileEntityPlayerSensor.this; }

        @LuaFunction
        public final Object[] getNearbyPlayers(IArguments args) throws LuaException {
            return TileEntityPlayerSensor.this.getNearbyPlayers(args);
        }

        @LuaFunction
        public final Object[] getAllPlayers(IArguments args) throws LuaException {
            return TileEntityPlayerSensor.this.getAllPlayers(args);
        }

    };

    @Override
    public IPeripheral getModPeripheral() { return peripheral; }

}
