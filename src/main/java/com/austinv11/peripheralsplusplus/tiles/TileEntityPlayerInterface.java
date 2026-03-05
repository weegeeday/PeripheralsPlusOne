package com.austinv11.peripheralsplusplus.tiles;

import com.austinv11.peripheralsplusplus.lua.LuaObjectPlayerInv;
import com.austinv11.peripheralsplusplus.reference.Config;
import com.austinv11.peripheralsplusplus.utils.IPlusPlusPeripheral;
import com.mojang.authlib.GameProfile;
import dan200.computercraft.api.lua.IArguments;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.server.ServerLifecycleHooks;

import javax.annotation.Nullable;
import java.util.UUID;

public class TileEntityPlayerInterface extends BlockEntity implements IPlusPlusPeripheral.HasPeripheral, MenuProvider, Container {

public Direction outputSide;
public Direction inputSide;
private final ItemStack[] items = new ItemStack[8];

public TileEntityPlayerInterface(BlockPos pos, BlockState state) {
super(com.austinv11.peripheralsplusplus.init.ModTileEntities.PLAYER_INTERFACE.get(), pos, state);
for (int i = 0; i < items.length; i++) items[i] = ItemStack.EMPTY;
}

@Override
public void load(CompoundTag tag) {
super.load(tag);
for (int i = 0; i < items.length; i++) {
if (tag.contains("inv" + i))
items[i] = ItemStack.of(tag.getCompound("inv" + i));
}
if (tag.contains("outputSide"))
outputSide = Direction.byName(tag.getString("outputSide"));
if (tag.contains("inputSide"))
inputSide = Direction.byName(tag.getString("inputSide"));
}

@Override
protected void saveAdditional(CompoundTag tag) {
super.saveAdditional(tag);
for (int i = 0; i < items.length; i++)
if (!items[i].isEmpty())
tag.put("inv" + i, items[i].save(new CompoundTag()));
if (outputSide != null) tag.putString("outputSide", outputSide.getName());
if (inputSide != null) tag.putString("inputSide", inputSide.getName());
}
public final Object[] getPlayerInv(IArguments args) throws LuaException {
if (!Config.enablePlayerInterface)
throw new LuaException("Player Interfaces have been disabled");
String playerName = args.getString(0);
for (net.minecraft.server.level.ServerLevel sl : ServerLifecycleHooks.getCurrentServer().getAllLevels()) {
for (Player player : sl.players()) {
if (player.getName().getString().equals(playerName)) {
if (hasPermissionsCardFor(player) || !Config.enableInterfacePermissions)
return new Object[]{new LuaObjectPlayerInv(player, this, getPermCardFor(player))};
else
throw new LuaException("Missing permissions for player " + playerName);
}
}
}
throw new LuaException("Player not found");
}

public final void setOutputSide(IArguments args) throws LuaException {
if (!Config.enablePlayerInterface)
throw new LuaException("Player Interfaces have been disabled");
outputSide = Direction.byName(args.getString(0).toLowerCase());
setChanged();
}

public final void setInputSide(IArguments args) throws LuaException {
if (!Config.enablePlayerInterface)
throw new LuaException("Player Interfaces have been disabled");
inputSide = Direction.byName(args.getString(0).toLowerCase());
setChanged();
}

public final Object[] getOutputSide(IArguments args) throws LuaException {
if (!Config.enablePlayerInterface)
throw new LuaException("Player Interfaces have been disabled");
return outputSide == null ? new Object[0] : new Object[]{outputSide.getName()};
}

public final Object[] getInputSide(IArguments args) throws LuaException {
if (!Config.enablePlayerInterface)
throw new LuaException("Player Interfaces have been disabled");
return inputSide == null ? new Object[0] : new Object[]{inputSide.getName()};
}

private boolean hasPermissionsCardFor(Player player) {
return !getPermCardFor(player).isEmpty();
}

private ItemStack getPermCardFor(Player player) {
for (ItemStack stack : items) {
if (!stack.isEmpty() && stack.hasTag()) {
CompoundTag profileTag = stack.getTag().getCompound("profile");
GameProfile profile = NbtUtils.readGameProfile(profileTag);
if (profile != null && profile.getId() != null &&
profile.getId().equals(player.getGameProfile().getId()))
return stack;
}
}
return ItemStack.EMPTY;
}
// Container
@Override
public int getContainerSize() { return items.length; }
@Override
public boolean isEmpty() { for (ItemStack s : items) if (!s.isEmpty()) return false; return true; }
@Override
public ItemStack getItem(int i) { return i < items.length ? items[i] : ItemStack.EMPTY; }
@Override
public ItemStack removeItem(int i, int amt) { if (i < items.length) { ItemStack s = items[i].split(amt); setChanged(); return s; } return ItemStack.EMPTY; }
@Override
public ItemStack removeItemNoUpdate(int i) { if (i < items.length) { ItemStack s = items[i]; items[i] = ItemStack.EMPTY; return s; } return ItemStack.EMPTY; }
@Override
public void setItem(int i, ItemStack s) { if (i < items.length) { items[i] = s; setChanged(); } }
@Override
public boolean stillValid(Player player) { return Container.stillValidBlockEntity(this, player); }
@Override
public void clearContent() { for (int i = 0; i < items.length; i++) items[i] = ItemStack.EMPTY; }

// MenuProvider
@Override
public Component getDisplayName() { return Component.translatable("block.peripheralsplusone.player_interface"); }
@Nullable
@Override
public AbstractContainerMenu createMenu(int id, Inventory playerInv, Player player) {
return new com.austinv11.peripheralsplusplus.tiles.containers.ContainerPlayerInterface(id, playerInv, this);
}
    private final IPeripheral peripheral = new IPeripheral() {
        @Override
        public String getType() { return "playerInterface"; }

        @Override
        public boolean equals(IPeripheral other) { return TileEntityPlayerInterface.this == other; }

        @LuaFunction
        public final Object[] getPlayerInv(IArguments args) throws LuaException {
            return TileEntityPlayerInterface.this.getPlayerInv(args);
        }

        @LuaFunction
        public final void setOutputSide(IArguments args) throws LuaException {
            TileEntityPlayerInterface.this.setOutputSide(args);
        }

        @LuaFunction
        public final void setInputSide(IArguments args) throws LuaException {
            TileEntityPlayerInterface.this.setInputSide(args);
        }

        @LuaFunction
        public final Object[] getOutputSide(IArguments args) throws LuaException {
            return TileEntityPlayerInterface.this.getOutputSide(args);
        }

        @LuaFunction
        public final Object[] getInputSide(IArguments args) throws LuaException {
            return TileEntityPlayerInterface.this.getInputSide(args);
        }

    };

    @Override
    public IPeripheral getModPeripheral() { return peripheral; }

}
