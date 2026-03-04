package com.austinv11.peripheralsplusplus.lua;

import com.austinv11.peripheralsplusplus.reference.Config;
import com.austinv11.peripheralsplusplus.tiles.TileEntityPlayerInterface;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;

/**
 * Lua interface for a player's inventory, exposed by PlayerInterface peripheral.
 * In CC:Tweaked 1.20.1, returned as an opaque object with @LuaFunction methods.
 */
public class LuaObjectPlayerInv {

private final Inventory inv;
private final TileEntityPlayerInterface playerInterface;
private final ItemStack permCard;

public LuaObjectPlayerInv(Player player, TileEntityPlayerInterface playerInterface, ItemStack permCard) {
this.inv = player.getInventory();
this.playerInterface = playerInterface;
this.permCard = permCard;
}

@dan200.computercraft.api.lua.LuaFunction
public Object[] getStackInSlot(dan200.computercraft.api.lua.IArguments args) throws dan200.computercraft.api.lua.LuaException {
if (!hasGetStacksPermission())
throw new dan200.computercraft.api.lua.LuaException("Permission denied");
return new Object[]{getObjectFromStack(inv.getItem(args.getInt(0)))};
}

@dan200.computercraft.api.lua.LuaFunction
public Object[] getSize(dan200.computercraft.api.lua.IArguments args) throws dan200.computercraft.api.lua.LuaException {
if (!hasGetStacksPermission())
throw new dan200.computercraft.api.lua.LuaException("Permission denied");
return new Object[]{inv.getContainerSize()};
}

private boolean hasGetStacksPermission() {
return !Config.enableInterfacePermissions || (permCard.hasTag() && permCard.getTag().getBoolean("getStacks"));
}

private boolean hasWithdrawPermission() {
return !Config.enableInterfacePermissions || (permCard.hasTag() && permCard.getTag().getBoolean("withdraw"));
}

private boolean hasDepositPermission() {
return !Config.enableInterfacePermissions || (permCard.hasTag() && permCard.getTag().getBoolean("deposit"));
}

private HashMap<String, Object> getObjectFromStack(ItemStack stack) {
if (stack.isEmpty()) return null;
HashMap<String, Object> map = new HashMap<>();
map.put("count", stack.getCount());
ResourceLocation id = ForgeRegistries.ITEMS.getKey(stack.getItem());
map.put("name", id != null ? id.toString() : "unknown");
map.put("displayName", stack.getHoverName().getString());
return map;
}
}
