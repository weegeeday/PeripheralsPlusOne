package com.austinv11.peripheralsplusplus.turtles;

import com.austinv11.peripheralsplusplus.reference.Config;
import com.austinv11.peripheralsplusplus.reference.Reference;
import com.austinv11.peripheralsplusplus.turtles.peripherals.PeripheralBarrel;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.turtle.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TurtleBarrel implements ITurtleUpgrade {

    private final ResourceLocation upgradeId;

    public TurtleBarrel(ResourceLocation id) {
        this.upgradeId = id;
    }


	@Nonnull
	@Override
	public ResourceLocation getUpgradeID() {
		return upgradeId;
	}

	@Override
	public String getUnlocalisedAdjective() {
		return Reference.MOD_ID + ".turtle_upgrade.barrel";
	}

	@Override
	public TurtleUpgradeType getType() {
		return TurtleUpgradeType.Peripheral;
	}

	@Override
	public ItemStack getCraftingItem() {
		if (!Config.enableBarrelTurtle)
			return ItemStack.EMPTY;
		return new ItemStack(Blocks.OAK_LOG);
	}

	@Override
	public IPeripheral createPeripheral(ITurtleAccess turtle, TurtleSide side) {
		return new PeripheralBarrel(turtle, side);
	}

	@Nonnull
	@Override
	public TurtleCommandResult useTool(@Nonnull ITurtleAccess turtle, @Nonnull TurtleSide side,
									   @Nonnull TurtleVerb verb, @Nonnull Direction direction) {
		return TurtleCommandResult.failure();
	}

	@Override
	public void update(ITurtleAccess turtle, TurtleSide side) {
		IPeripheral peripheral = turtle.getPeripheral(side);
		if (peripheral instanceof PeripheralBarrel) {
			PeripheralBarrel barrel = (PeripheralBarrel) peripheral;
			if (barrel.changed)
				barrel.update();
		}
	}
}
