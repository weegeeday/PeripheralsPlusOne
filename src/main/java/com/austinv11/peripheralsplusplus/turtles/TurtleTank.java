package com.austinv11.peripheralsplusplus.turtles;

import com.austinv11.peripheralsplusplus.init.ModItems;
import com.austinv11.peripheralsplusplus.reference.Reference;
import com.austinv11.peripheralsplusplus.turtles.peripherals.PeripheralTank;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.turtle.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TurtleTank implements ITurtleUpgrade {
	
	@Override
	public ResourceLocation getUpgradeID() {
		return new ResourceLocation(Reference.TANK_UPGRADE);
	}

    @Override
	public String getUnlocalisedAdjective() {
		return Reference.MOD_ID + ".turtle_upgrade.tank";
	}

	@Override
	public TurtleUpgradeType getType() {
		return TurtleUpgradeType.Peripheral;
	}

	@Override
	public ItemStack getCraftingItem() {
		return new ItemStack(ModItems.TANK.get());
	}

	@Override
	public IPeripheral createPeripheral(ITurtleAccess turtle, TurtleSide side) {
		return new PeripheralTank(turtle, side);
	}

    @Nonnull
    @Override
    public TurtleCommandResult useTool(@Nonnull ITurtleAccess turtle, @Nonnull TurtleSide side,
                                       @Nonnull TurtleVerb verb, @Nonnull Direction direction) {
        return TurtleCommandResult.failure();
    }

	@Override
	public void update(ITurtleAccess turtle, TurtleSide side) {

	}
}
