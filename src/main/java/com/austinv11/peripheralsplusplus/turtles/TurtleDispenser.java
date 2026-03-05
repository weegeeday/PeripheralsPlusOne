package com.austinv11.peripheralsplusplus.turtles;

import com.austinv11.peripheralsplusplus.reference.Reference;
import com.austinv11.peripheralsplusplus.turtles.peripherals.PeripheralDispenser;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.turtle.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TurtleDispenser implements ITurtleUpgrade {

    private final ResourceLocation upgradeId;

    public TurtleDispenser(ResourceLocation id) {
        this.upgradeId = id;
    }

	
	@Override
	public ResourceLocation getUpgradeID() {
		return upgradeId;
	}

    @Override
	public String getUnlocalisedAdjective() {
		return Reference.MOD_ID + ".turtle_upgrade.dispenser";
	}
	@Override
	public ItemStack getCraftingItem() {
		return new ItemStack(Blocks.DISPENSER);
	}
	
	@Override
	public IPeripheral createPeripheral(ITurtleAccess turtle, TurtleSide side) {
		return new PeripheralDispenser(turtle);
	}

    @Nonnull
    @Override
    public TurtleCommandResult useTool(@Nonnull ITurtleAccess turtle, @Nonnull TurtleSide side, @Nonnull TurtleVerb verb, @Nonnull Direction direction) {
        return TurtleCommandResult.failure();
    }
	
	@Override
	public void update(ITurtleAccess turtle, TurtleSide side) {
		
	}
}
