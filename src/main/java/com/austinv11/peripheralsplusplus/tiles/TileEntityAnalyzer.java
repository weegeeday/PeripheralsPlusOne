package com.austinv11.peripheralsplusplus.tiles;

import com.austinv11.peripheralsplusplus.reference.Config;
import com.austinv11.peripheralsplusplus.utils.IPlusPlusPeripheral;
import dan200.computercraft.api.lua.IArguments;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;

public abstract class TileEntityAnalyzer extends BlockEntity implements IPlusPlusPeripheral, MenuProvider, Container {

protected final ItemStack[] items = new ItemStack[]{ItemStack.EMPTY};

public TileEntityAnalyzer(BlockPos pos, BlockState state) {
super(com.austinv11.peripheralsplusplus.init.ModTileEntities.ANALYZER_CHAT_BOX.get(), pos, state);
}

@Override
public String getType() {
return "generic_analyzer_this_is_a_bug";
}

@LuaFunction
public final Object[] analyze(IArguments args) throws LuaException {
if (!Config.enableAnalyzers)
throw new LuaException("Analyzers have been disabled");
return doAnalyze();
}

@LuaFunction
public final Object[] isMember(IArguments args) throws LuaException {
if (!Config.enableAnalyzers)
throw new LuaException("Analyzers have been disabled");
ItemStack stack = getItem(0);
if (stack.isEmpty())
return new Object[]{false};
return new Object[]{isMemberOf(stack)};
}

protected abstract Object[] doAnalyze() throws LuaException;
protected abstract boolean isMemberOf(ItemStack stack);
protected abstract IPeripheral getInstance();

@Override
public boolean equals(@Nullable IPeripheral other) {
return this == other;
}

// Container methods
@Override
public int getContainerSize() { return 1; }
@Override
public boolean isEmpty() { return items[0].isEmpty(); }
@Override
public ItemStack getItem(int slot) { return slot == 0 ? items[0] : ItemStack.EMPTY; }
@Override
public ItemStack removeItem(int slot, int amount) { if (slot == 0) { ItemStack s = items[0].split(amount); setChanged(); return s; } return ItemStack.EMPTY; }
@Override
public ItemStack removeItemNoUpdate(int slot) { if (slot == 0) { ItemStack s = items[0]; items[0] = ItemStack.EMPTY; return s; } return ItemStack.EMPTY; }
@Override
public void setItem(int slot, ItemStack stack) { if (slot == 0) { items[0] = stack; setChanged(); } }
@Override
public boolean stillValid(Player player) { return Container.stillValidBlockEntity(this, player); }
@Override
public void clearContent() { items[0] = ItemStack.EMPTY; }

@Override
public Component getDisplayName() { return Component.translatable("block.peripheralsplusplus.analyzer"); }

@Nullable
@Override
public AbstractContainerMenu createMenu(int id, Inventory playerInv, Player player) {
return new com.austinv11.peripheralsplusplus.tiles.containers.ContainerAnalyzer(id, playerInv, this);
}
}
