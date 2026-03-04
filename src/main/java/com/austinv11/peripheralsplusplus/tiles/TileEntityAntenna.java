package com.austinv11.peripheralsplusplus.tiles;

import com.austinv11.peripheralsplusplus.PeripheralsPlusPlus;
import com.austinv11.peripheralsplusplus.items.ItemSmartHelmet;
import com.austinv11.peripheralsplusplus.lua.LuaObjectEntityControl;
import com.austinv11.peripheralsplusplus.lua.LuaObjectHUD;
import com.austinv11.peripheralsplusplus.network.ScaleRequestPacket;
import com.austinv11.peripheralsplusplus.reference.Config;
import com.austinv11.peripheralsplusplus.utils.IPlusPlusPeripheral;
import com.austinv11.peripheralsplusplus.utils.Util;
import dan200.computercraft.api.lua.IArguments;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.*;

public class TileEntityAntenna extends BlockEntity implements IPlusPlusPeripheral {

public HashMap<IComputerAccess, Boolean> computers = new HashMap<>();
private final HashMap<Integer, LuaObjectHUD> huds = new HashMap<>();
public static final HashMap<UUID, TileEntityAntenna> ANTENNA_REGISTRY = new HashMap<>();
public UUID identifier;
public String label;
private volatile List<Entity> associatedEntities = new ArrayList<>();

public TileEntityAntenna(BlockPos pos, BlockState state) {
super(null, pos, state);
identifier = UUID.randomUUID();
while (ANTENNA_REGISTRY.containsKey(identifier))
identifier = UUID.randomUUID();
}

@Override
public void load(CompoundTag tag) {
super.load(tag);
if (tag.contains("identifier"))
identifier = UUID.fromString(tag.getString("identifier"));
if (tag.contains("label"))
label = tag.getString("label");
}

@Override
protected void saveAdditional(CompoundTag tag) {
super.saveAdditional(tag);
tag.putString("identifier", identifier.toString());
if (label != null)
tag.putString("label", label);
}

public static void serverTick(net.minecraft.world.level.Level level, BlockPos pos, BlockState state, TileEntityAntenna self) {
if (!TileEntityAntenna.ANTENNA_REGISTRY.containsKey(self.identifier))
TileEntityAntenna.ANTENNA_REGISTRY.put(self.identifier, self);
}

@Override
public void setRemoved() {
super.setRemoved();
ANTENNA_REGISTRY.remove(identifier);
}

@Override
public String getType() {
return "antenna";
}

@LuaFunction
public final Object[] getPlayers(IArguments args) throws LuaException {
if (!Config.enableSmartHelmet)
throw new LuaException("Smart Helmets have been disabled");
synchronized (this) {
List<String> playerNames = new ArrayList<>();
for (Player player : getPlayersWearingSmartHelmets()) {
for (ItemStack itemStack : player.getArmorSlots()) {
if (itemStack.getItem() instanceof ItemSmartHelmet) {
CompoundTag nbt = itemStack.getTag();
if (nbt != null && nbt.contains("identifier") &&
identifier.equals(UUID.fromString(nbt.getString("identifier"))))
playerNames.add(player.getName().getString());
}
}
}
return new Object[]{Util.arrayToMap(playerNames.toArray())};
}
}

@LuaFunction(mainThread = true)
public final Object[] getHUD(IArguments args) throws LuaException {
if (!Config.enableSmartHelmet)
throw new LuaException("Smart Helmets have been disabled");
String playerName = args.getString(0);
Player player = Util.getPlayer(playerName);
if (player == null)
return new Object[]{null};
LuaObjectHUD obj = new LuaObjectHUD(playerName, identifier);
// TODO: re-implement scale request for 1.20.1 networking
return new Object[]{obj};
}

@LuaFunction
public final void setLabel(IArguments args) throws LuaException {
this.label = args.getString(0);
setChanged();
}

@LuaFunction
public final Object[] getLabel(IArguments args) {
return new Object[]{this.label};
}

@LuaFunction
public final Object[] getInfectedEntities(IArguments args) throws LuaException {
if (!Config.enableNanoBots)
throw new LuaException("Nano bots have been disabled");
HashMap<Integer, String> entities = new HashMap<>();
for (int i = 0; i < associatedEntities.size(); i++)
entities.put(i + 1, associatedEntities.get(i).getUUID().toString());
return new Object[]{entities};
}

@LuaFunction
public final Object[] getInfectedEntity(IArguments args) throws LuaException {
if (!Config.enableNanoBots)
throw new LuaException("Nano bots have been disabled");
String id = args.getString(0);
Entity ent = entityFromId(id);
if (ent != null)
return new Object[]{new LuaObjectEntityControl(identifier, ent)};
throw new LuaException("Entity with id " + id + " not found");
}

public static List<Player> getPlayersWearingSmartHelmets() {
List<Player> players = new ArrayList<>();
for (ServerLevel level : ServerLifecycleHooks.getCurrentServer().getAllLevels()) {
for (Player player : level.players()) {
for (ItemStack itemStack : player.getArmorSlots()) {
if (itemStack.getItem() instanceof ItemSmartHelmet) {
players.add(player);
break;
}
}
}
}
return players;
}

private Entity entityFromId(String id) {
for (Entity entity : associatedEntities)
if (entity.getUUID().toString().equals(id))
return entity;
return null;
}

@Override
public void attach(IComputerAccess computer) {
computers.put(computer, true);
}

@Override
public void detach(IComputerAccess computer) {
computers.remove(computer);
}

@Override
public boolean equals(IPeripheral other) {
return this == other;
}

public void onResponse(int id, int width, int height) {
if (huds.containsKey(id)) {
huds.get(id).height = height;
huds.get(id).width = width;
for (IComputerAccess comp : computers.keySet())
if (comp.getID() == id)
comp.queueEvent("resolution", new Object[]{height, width});
}
}

public void registerEntity(Entity entity) {
for (Entity containedEntity : new ArrayList<>(associatedEntities)) {
if (containedEntity.getUUID().equals(entity.getUUID())) {
associatedEntities.remove(containedEntity);
break;
}
}
associatedEntities.add(entity);
}

public boolean isEntityRegistered(Entity entity) {
return associatedEntities.contains(entity);
}

public void removeEntity(Entity entity) {
associatedEntities.remove(entity);
}
}
