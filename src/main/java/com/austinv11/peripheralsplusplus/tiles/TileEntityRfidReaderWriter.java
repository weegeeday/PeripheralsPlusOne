package com.austinv11.peripheralsplusplus.tiles;

import com.austinv11.peripheralsplusplus.capabilities.rfid.RfidTagHolder;
import com.austinv11.peripheralsplusplus.init.ModItems;
import com.austinv11.peripheralsplusplus.reference.Config;
import com.austinv11.peripheralsplusplus.utils.IPlusPlusPeripheral;
import com.austinv11.peripheralsplusplus.utils.ReflectionHelper;
import com.austinv11.peripheralsplusplus.utils.Util;
import com.austinv11.peripheralsplusplus.utils.rfid.RfidAuthentication;
import com.austinv11.peripheralsplusplus.utils.rfid.RfidTag;
import dan200.computercraft.api.lua.IArguments;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.turtle.ITurtleAccess;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TileEntityRfidReaderWriter extends BlockEntity implements IPlusPlusPeripheral, MenuProvider, Container {

private final ItemStack[] items = new ItemStack[]{ItemStack.EMPTY};
private byte[] selectedId;
private RfidAuthentication authentication;

public TileEntityRfidReaderWriter(BlockPos pos, BlockState state) {
super(com.austinv11.peripheralsplusplus.init.ModTileEntities.RFID_READER_WRITER.get(), pos, state);
}

@Override
public void load(CompoundTag tag) {
super.load(tag);
if (tag.contains("inv0"))
items[0] = ItemStack.of(tag.getCompound("inv0"));
}

@Override
protected void saveAdditional(CompoundTag tag) {
super.saveAdditional(tag);
if (!items[0].isEmpty())
tag.put("inv0", items[0].save(new CompoundTag()));
}

@Nonnull
@Override
public String getType() {
return "rfid_reader_writer";
}

@LuaFunction
public final Object[] search(IArguments args) throws LuaException {
if (!Config.enableRfidItems)
throw new LuaException("RFID items are not enabled in the config");
return searchLua();
}

@LuaFunction
public final Object[] select(IArguments args) throws LuaException {
if (!Config.enableRfidItems)
throw new LuaException("RFID items are not enabled in the config");
return selectLua(new Object[]{args.get(0)});
}

@LuaFunction
public final Object[] auth(IArguments args) throws LuaException {
if (!Config.enableRfidItems)
throw new LuaException("RFID items are not enabled in the config");
return authLua(new Object[]{args.get(0), args.get(1), args.get(2)});
}

@LuaFunction
public final Object[] deauth(IArguments args) throws LuaException {
if (!Config.enableRfidItems)
throw new LuaException("RFID items are not enabled in the config");
authentication = null;
return new Object[0];
}

@LuaFunction
public final Object[] read(IArguments args) throws LuaException {
if (!Config.enableRfidItems)
throw new LuaException("RFID items are not enabled in the config");
return readLua(new Object[]{args.get(0)});
}

@LuaFunction
public final Object[] write(IArguments args) throws LuaException {
if (!Config.enableRfidItems)
throw new LuaException("RFID items are not enabled in the config");
return writeLua(new Object[]{args.get(0), args.get(1)});
}

private Object[] writeLua(Object[] arguments) throws LuaException {
if (arguments.length < 2)
throw new LuaException("Not enough arguments");
if (!(arguments[0] instanceof Double))
throw new LuaException("Argument 1 expected to be an int");
if (selectedId == null || authentication == null)
return new Object[]{false};
int block = ((Double) arguments[0]).intValue();
byte[] data = parseLuaIdArg(arguments[1], RfidTag.BLOCK_LENGTH);
ItemStack chip = getRfidItem(selectedId);
if (chip.isEmpty())
return new Object[]{false};
RfidTag rfidTag = new RfidTag(chip);
authentication.writeBlock(rfidTag, block, data);
RfidTag.removeTag(chip);
RfidTag.addTag(chip, rfidTag);
return new Object[]{true};
}

private Object[] readLua(Object[] arguments) throws LuaException {
if (arguments.length < 1)
throw new LuaException("Not enough arguments");
if (!(arguments[0] instanceof Double))
throw new LuaException("Argument 1 expected to be an integer");
if (selectedId == null || authentication == null)
return new Object[0];
int block = ((Double) arguments[0]).intValue();
ItemStack chip = getRfidItem(selectedId);
if (chip.isEmpty())
return new Object[0];
RfidTag rfidTag = new RfidTag(chip);
byte[] blockBytes = authentication.readBlock(rfidTag, block);
return new Object[]{Util.arrayToMap(Util.byteArraytoUnsignedIntArray(blockBytes))};
}

private Object[] authLua(Object[] arguments) throws LuaException {
if (arguments.length < 3)
throw new LuaException("Not enough arguments");
if (!(arguments[0] instanceof Double))
throw new LuaException("Argument 1 expected to be an integer");
if (!(arguments[1] instanceof Double))
throw new LuaException("Argument 2 expected to be an integer");
int type = ((Double) arguments[0]).intValue();
if (type != RfidTag.KeyType.A.ordinal() && type != RfidTag.KeyType.B.ordinal())
throw new LuaException("Invalid key type");
int block = ((Double) arguments[1]).intValue();
if (block < RfidTag.MANUFACTURER_BLOCK || block >= RfidTag.SECTORS * RfidTag.SECTOR_SIZE)
throw new LuaException("Block index out of range");
authentication = new RfidAuthentication(RfidTag.KeyType.values()[type], block,
parseLuaIdArg(arguments[2], RfidTag.DEFAULT_KEY.length));
return new Object[0];
}

private byte[] parseLuaIdArg(Object argument, int size) throws LuaException {
String error = "Expected an array of unsigned integers";
if (!(argument instanceof Map))
throw new LuaException(error);
List<Double> passedId;
try {
passedId = doubleMapToList((Map<Double, Double>) argument);
} catch (NumberFormatException e) {
throw new LuaException(error);
}
if (passedId.size() != size)
throw new LuaException(String.format("Table size is incorrect. Found: %d Expected: %d", passedId.size(), size));
return doubleListToByteArray(passedId);
}

private Object[] selectLua(Object[] arguments) throws LuaException {
if (arguments.length < 1)
throw new LuaException("Not enough arguments");
byte[] id = parseLuaIdArg(arguments[0], RfidTag.ID_SIZE);
ItemStack rfidItem = getRfidItem(id);
if (!rfidItem.isEmpty())
selectedId = id;
return new Object[0];
}

private List<Double> doubleMapToList(Map<Double, Double> map) throws NumberFormatException {
List<Double> list = new ArrayList<>();
for (double index = 1; index <= map.size(); index++) {
if (!map.containsKey(index))
throw new NumberFormatException();
list.add(map.get(index));
}
return list;
}

@Nonnull
private ItemStack getRfidItem(@Nullable byte[] id) {
// Check internal inventory
for (ItemStack item : items) {
if (!item.isEmpty() && (RfidTag.hasTag(item) || item.is(ModItems.RFID_CHIP.get())) &&
item.getCount() == 1 && (id == null || RfidTag.itemIdEquals(item, id)))
return item;
}
// Search in area
AABB searchBox = new AABB(getBlockPos()).inflate(3);
for (Entity entity : getLevel().getEntities(null, searchBox)) {
if (entity instanceof Player player) {
Inventory inv = player.getInventory();
for (int i = 0; i < inv.getContainerSize(); i++) {
ItemStack item = inv.getItem(i);
if ((RfidTag.hasTag(item) || item.is(ModItems.RFID_CHIP.get())) &&
item.getCount() == 1 && (id == null || RfidTag.itemIdEquals(item, id)))
return item;
}
} else if (entity instanceof ItemEntity ie) {
ItemStack item = ie.getItem();
if ((RfidTag.hasTag(item) || item.is(ModItems.RFID_CHIP.get())) &&
item.getCount() == 1 && (id == null || RfidTag.itemIdEquals(item, id)))
return item;
}
// Check RFID capability
entity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(h -> {});
}
return ItemStack.EMPTY;
}

private byte[] doubleListToByteArray(List<Double> doubleList) {
byte[] byteArray = new byte[doubleList.size()];
for (int i = 0; i < doubleList.size(); i++)
byteArray[i] = (byte) (int) Math.floor(doubleList.get(i));
return byteArray;
}

private Object[] searchLua() {
ItemStack foundItem = getRfidItem(null);
if (!foundItem.isEmpty()) {
RfidTag tag = new RfidTag(foundItem);
if (!RfidTag.hasTag(foundItem) || tag.getIdLong() <= 0)
RfidTag.addTag(foundItem, tag);
return new Object[]{Util.arrayToMap(Util.byteArraytoUnsignedIntArray(tag.getId()))};
}
return new Object[0];
}

@Override
public boolean equals(@Nullable IPeripheral other) {
return other == this;
}

// Container implementation
@Override
public int getContainerSize() { return items.length; }
@Override
public boolean isEmpty() { return items[0].isEmpty(); }
@Override
public ItemStack getItem(int slot) { return slot < items.length ? items[slot] : ItemStack.EMPTY; }
@Override
public ItemStack removeItem(int slot, int amount) {
if (slot < items.length) { ItemStack s = items[slot].split(amount); setChanged(); return s; } return ItemStack.EMPTY;
}
@Override
public ItemStack removeItemNoUpdate(int slot) {
if (slot < items.length) { ItemStack s = items[slot]; items[slot] = ItemStack.EMPTY; return s; } return ItemStack.EMPTY;
}
@Override
public void setItem(int slot, ItemStack stack) { if (slot < items.length) { items[slot] = stack; setChanged(); } }
@Override
public boolean stillValid(Player player) { return Container.stillValidBlockEntity(this, player); }
@Override
public void clearContent() { items[0] = ItemStack.EMPTY; }

// MenuProvider implementation
@Override
public Component getDisplayName() { return Component.translatable("block.peripheralsplusplus.rfid_reader_writer"); }

@Nullable
@Override
public AbstractContainerMenu createMenu(int id, Inventory playerInv, Player player) {
return new com.austinv11.peripheralsplusplus.tiles.containers.ContainerRfidReaderWriter(id, playerInv, this);
}
}
