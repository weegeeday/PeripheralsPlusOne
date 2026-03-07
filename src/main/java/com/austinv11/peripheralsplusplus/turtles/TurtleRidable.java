package com.austinv11.peripheralsplusplus.turtles;

import com.austinv11.peripheralsplusplus.reference.Config;
import com.austinv11.peripheralsplusplus.reference.Reference;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.turtle.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TurtleRidable implements ITurtleUpgrade {

    private final ResourceLocation upgradeId;

    public TurtleRidable(ResourceLocation id) {
        this.upgradeId = id;
    }

@Nonnull
@Override
public ResourceLocation getUpgradeID() {
return upgradeId;
}

@Override
public String getUnlocalisedAdjective() {
return Reference.MOD_ID + ".turtle_upgrade.ridable";
}

	@Override
	public TurtleUpgradeType getType() {
		return TurtleUpgradeType.PERIPHERAL;
	}
@Override
public ItemStack getCraftingItem() {
if (Config.enableRidableTurtle)
return new ItemStack(Items.SADDLE);
return ItemStack.EMPTY;
}

@Override
@Nullable
public IPeripheral createPeripheral(ITurtleAccess turtle, TurtleSide side) {
// Ridable turtle peripheral - full entity spawning implementation pending
return null;
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
