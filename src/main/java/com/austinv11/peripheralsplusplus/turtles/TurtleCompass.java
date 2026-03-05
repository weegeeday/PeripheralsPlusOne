package com.austinv11.peripheralsplusplus.turtles;


import com.austinv11.peripheralsplusplus.reference.Config;
import com.austinv11.peripheralsplusplus.reference.Reference;
import com.austinv11.peripheralsplusplus.turtles.peripherals.PeripheralCompass;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.turtle.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TurtleCompass implements ITurtleUpgrade {

    private final ResourceLocation upgradeId;

    public TurtleCompass(ResourceLocation id) {
        this.upgradeId = id;
    }

    @Nonnull
    @Override
    public ResourceLocation getUpgradeID() {
        return upgradeId;
    }

    @Override
	public String getUnlocalisedAdjective() {
		return "peripheralsplusone.turtle_upgrade.compass";
	}
	@Override
	public ItemStack getCraftingItem() {
		if (Config.enableNavigationTurtle)
			return new ItemStack(Items.COMPASS);
		return ItemStack.EMPTY;
	}

	@Override
	public IPeripheral createPeripheral(ITurtleAccess turtle, TurtleSide side) {
		return new PeripheralCompass(turtle);
	}

    @Nonnull
    @Override
    public TurtleCommandResult useTool(@Nonnull ITurtleAccess turtle, @Nonnull TurtleSide side,
                                       @Nonnull TurtleVerb verb, @Nonnull Direction direction) {
        return TurtleCommandResult.failure();
    }

	@Override
	public void update(ITurtleAccess turtle, TurtleSide side) {}
}