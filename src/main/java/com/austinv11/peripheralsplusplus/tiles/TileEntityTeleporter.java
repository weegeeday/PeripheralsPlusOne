package com.austinv11.peripheralsplusplus.tiles;

import com.austinv11.peripheralsplusplus.PeripheralsPlusPlus;
import com.austinv11.peripheralsplusplus.blocks.BlockPppDirectional;
import com.austinv11.peripheralsplusplus.blocks.BlockTeleporter;
import com.austinv11.peripheralsplusplus.network.ParticlePacket;
import com.austinv11.peripheralsplusplus.reference.Config;
import com.austinv11.peripheralsplusplus.utils.IPlusPlusPeripheral;
import com.austinv11.peripheralsplusplus.utils.ReflectionHelper;
import dan200.computercraft.api.lua.IArguments;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.turtle.ITurtleAccess;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.HashMap;
import java.util.Stack;

public class TileEntityTeleporter extends BlockEntity implements IPlusPlusPeripheral {

private String name = "tileEntityTeleporter";
public Stack<LinkData> links = new Stack<>();
public String tag = null;

public TileEntityTeleporter(BlockPos pos, BlockState state) {
super(com.austinv11.peripheralsplusplus.init.ModTileEntities.TELEPORTER.get(), pos, state);
}

public int getMaxLinks() {
if (level == null) return 1;
BlockState bs = level.getBlockState(getBlockPos());
if (!bs.hasProperty(BlockTeleporter.TIER)) return 1;
int tier = bs.getValue(BlockTeleporter.TIER);
return tier == 0 ? 1 : 8;
}

@Override
public void load(CompoundTag nbt) {
super.load(nbt);
if (nbt.contains("tTag"))
tag = nbt.getString("tTag");
ListTag linksList = nbt.getList("links", 10);
for (int i = 0; i < linksList.size(); i++) {
CompoundTag link = linksList.getCompound(i);
if (link.contains("linkX") && link.contains("linkY") && link.contains("linkZ") && link.contains("linkDim")) {
links.add(new LinkData(link.getInt("linkDim"),
new BlockPos(link.getInt("linkX"), link.getInt("linkY"), link.getInt("linkZ"))));
}
}
}

@Override
protected void saveAdditional(CompoundTag nbt) {
super.saveAdditional(nbt);
if (tag != null)
nbt.putString("tTag", tag);
ListTag list = new ListTag();
for (LinkData link : links) {
if (link != null) {
CompoundTag lc = new CompoundTag();
lc.putInt("linkX", link.link.getX());
lc.putInt("linkY", link.link.getY());
lc.putInt("linkZ", link.link.getZ());
lc.putInt("linkDim", link.linkDim);
list.add(lc);
}
}
nbt.put("links", list);
}

@Override
public String getType() {
return "teleporter";
}

@LuaFunction
public final Object[] teleport(IArguments args) throws LuaException {
return tp(args);
}

@LuaFunction
public final Object[] tp(IArguments args) throws LuaException {
if (!Config.enableTurtleTeleporter)
throw new LuaException("Turtle teleporters have been disabled");
int index = args.count() > 0 ? args.getInt(0) - 1 : 0;
if (index < 0 || index >= getMaxLinks())
throw new LuaException("Bad link " + (index + 1) + " (expected 1-" + getMaxLinks() + ")");
if (index >= links.size())
throw new LuaException("No such link");
LinkData link = links.get(index);
if (link == null)
throw new LuaException("No such link");

BlockState blockState = level.getBlockState(getBlockPos());
Direction direction = blockState.getValue(BlockPppDirectional.FACING);
BlockEntity te = level.getBlockEntity(getBlockPos().relative(direction));

ITurtleAccess turtle;
try {
turtle = ReflectionHelper.getTurtle(te);
} catch (Exception e) {
throw new LuaException("No turtle in front");
}
if (turtle == null)
throw new LuaException("No turtle in front");

ServerLevel destWorld = null;
for (ServerLevel sl : ServerLifecycleHooks.getCurrentServer().getAllLevels()) {
if (sl.dimension().location().toString().equals(link.levelKey)) {
destWorld = sl;
break;
}
}
if (destWorld == null)
throw new LuaException("Destination world missing");

BlockEntity dest = destWorld.getBlockEntity(link.link);
if (!(dest instanceof TileEntityTeleporter))
throw new LuaException("Destination is not a teleporter");

BlockState destBlockState = destWorld.getBlockState(link.link);
Direction destDir = destBlockState.getValue(BlockPppDirectional.FACING);
BlockPos destPos = link.link.relative(destDir);

if (!destWorld.isEmptyBlock(destPos) || destPos.getY() < 0 || destPos.getY() > 319)
throw new LuaException("Destination obstructed");

double distance = Math.sqrt(getBlockPos().distSqr(link.link));
double fuelUsed = distance * Config.teleporterPenalty;
if (!turtle.consumeFuel(Math.abs((int) Math.ceil(fuelUsed))))
throw new LuaException("Not enough fuel");

boolean result = turtle.teleportTo(destWorld, destPos);
if (result) {
BlockPos particlePos = getBlockPos().relative(direction);
PeripheralsPlusPlus.NETWORK.send(
PacketDistributor.NEAR.with(() -> new PacketDistributor.TargetPoint(
particlePos.getX(), particlePos.getY(), particlePos.getZ(), 16, level.dimension())),
new ParticlePacket("portal", particlePos.getX(), particlePos.getY(), particlePos.getZ(),
level.random.nextGaussian(), 0, level.random.nextGaussian()));
}
return new Object[]{result};
}

@LuaFunction
public final Object[] getLinks(IArguments args) throws LuaException {
if (!Config.enableTurtleTeleporter)
throw new LuaException("Turtle teleporters have been disabled");
HashMap<Integer, Object> map = new HashMap<>();
for (int i = 0; i < links.size(); i++) {
HashMap<String, Object> entry = new HashMap<>();
entry.put("dim", links.get(i).levelKey);
entry.put("x", links.get(i).link.getX());
entry.put("y", links.get(i).link.getY());
entry.put("z", links.get(i).link.getZ());
entry.put("name", name);
map.put(i + 1, entry);
}
return new Object[]{map};
}

@LuaFunction
public final Object[] setName(IArguments args) throws LuaException {
if (!Config.enableTurtleTeleporter)
throw new LuaException("Turtle teleporters have been disabled");
this.name = args.getString(0);
setChanged();
return new Object[]{name};
}

@Override
public boolean equals(IPeripheral other) {
return other == this;
}

public void blockActivated(Player player, InteractionHand hand) {
ItemStack held = player.getItemInHand(hand);
if (!held.isEmpty() && held.is(Items.REPEATER)) {
CompoundTag tag = held.getOrCreateTag();
if (tag.contains("p++LinkX") && tag.contains("p++LinkY") && tag.contains("p++LinkZ")) {
BlockPos link = new BlockPos(tag.getInt("p++LinkX"), tag.getInt("p++LinkY"), tag.getInt("p++LinkZ"));
String linkDim = tag.getString("p++LinkDim");
ServerLevel srcWorld = null;
for (ServerLevel sl : ServerLifecycleHooks.getCurrentServer().getAllLevels()) {
if (sl.dimension().location().toString().equals(linkDim)) {
srcWorld = sl;
break;
}
}
if (srcWorld == null) {
player.sendSystemMessage(Component.literal("Link failed: World is missing"));
} else {
BlockEntity te = srcWorld.getBlockEntity(link);
if (!(te instanceof TileEntityTeleporter src)) {
player.sendSystemMessage(Component.literal("Link failed: Teleporter no longer exists"));
} else {
String thisDim = level.dimension().location().toString();
if (link.equals(getBlockPos())) {
player.sendSystemMessage(Component.literal("Link canceled"));
} else {
boolean unlinked = false;
for (int i = 0; i < src.links.size(); i++) {
LinkData rlink = src.links.get(i);
if (rlink.link.equals(getBlockPos()) && rlink.levelKey.equals(thisDim)) {
src.links.remove(i);
unlinked = true;
break;
}
}
if (!unlinked) {
src.addLink(thisDim, getBlockPos());
}
}
}
}
tag.remove("p++LinkX");
tag.remove("p++LinkY");
tag.remove("p++LinkZ");
tag.remove("p++LinkDim");
return;
}
String thisDim = level.dimension().location().toString();
tag.putInt("p++LinkX", getBlockPos().getX());
tag.putInt("p++LinkY", getBlockPos().getY());
tag.putInt("p++LinkZ", getBlockPos().getZ());
tag.putString("p++LinkDim", thisDim);
player.sendSystemMessage(Component.literal("Link started"));
}
}

public int addLink(String levelKey, BlockPos link) {
links.add(new LinkData(levelKey, link));
while (links.size() > getMaxLinks())
links.pop();
setChanged();
return links.size();
}

public static class LinkData {
public String levelKey;
public BlockPos link;

public LinkData(String levelKey, BlockPos link) {
this.levelKey = levelKey;
this.link = link;
}

/** Legacy constructor - converts int dim to string */
public LinkData(int linkDim, BlockPos link) {
this.levelKey = "minecraft:overworld"; // legacy fallback
this.link = link;
}
}
}
