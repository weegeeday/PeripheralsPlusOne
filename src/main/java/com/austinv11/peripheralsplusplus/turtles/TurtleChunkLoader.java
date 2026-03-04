package com.austinv11.peripheralsplusplus.turtles;

import com.austinv11.peripheralsplusplus.init.ModItems;
import com.austinv11.peripheralsplusplus.reference.Reference;
import com.austinv11.peripheralsplusplus.turtles.peripherals.PeripheralChunkLoader;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.turtle.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TurtleChunkLoader implements ITurtleUpgrade {

	@Override
	public ResourceLocation getUpgradeID() {
		return new ResourceLocation(Reference.CHUNK_LOADER_UPGRADE);
	}

	@Override
	public String getUnlocalisedAdjective() {
		return Reference.MOD_ID + ".turtle_upgrade.loader";
	}

	@Override
	public TurtleUpgradeType getType() {
		return TurtleUpgradeType.Peripheral;
	}

	@Override
	public ItemStack getCraftingItem() {
		return new ItemStack(ModItems.CHUNK_LOADER_UPGRADE.get());
	}

	@Override
	public IPeripheral createPeripheral(ITurtleAccess turtle, TurtleSide side) {
		return new PeripheralChunkLoader(turtle);
	}

	@Nonnull
	@Override
	public TurtleCommandResult useTool(@Nonnull ITurtleAccess iTurtleAccess, @Nonnull TurtleSide turtleSide,
									   @Nonnull TurtleVerb turtleVerb, @Nonnull Direction enumFacing) {
		return TurtleCommandResult.failure();
	}

	@Override
	public void update(ITurtleAccess turtle, TurtleSide side) {
		IPeripheral peripheral = turtle.getPeripheral(side);
		if (peripheral instanceof PeripheralChunkLoader) {
			PeripheralChunkLoader loader = (PeripheralChunkLoader) peripheral;
			loader.update();
		}
	}
}
