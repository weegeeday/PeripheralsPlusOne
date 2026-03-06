package com.austinv11.peripheralsplusplus.turtles;

import com.austinv11.peripheralsplusplus.init.ModBlocks;
import com.austinv11.peripheralsplusplus.reference.Reference;
import com.austinv11.peripheralsplusplus.tiles.TileEntityEnvironmentScanner;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.turtle.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TurtleEnvironmentScanner implements ITurtleUpgrade {

    private final ResourceLocation upgradeId;
    private TileEntityEnvironmentScanner scanner = null;

    public TurtleEnvironmentScanner(ResourceLocation id) {
        this.upgradeId = id;
    }


	@Override
	public ResourceLocation getUpgradeID() {
		return upgradeId;
	}

    @Override
	public String getUnlocalisedAdjective() {
		return Reference.MOD_ID + ".turtle_upgrade.environment_scanner";
	}

	@Override
	public TurtleUpgradeType getType() {
		return TurtleUpgradeType.PERIPHERAL;
	}
	@Override
	public ItemStack getCraftingItem() {
		return new ItemStack(ModBlocks.ENVIRONMENT_SCANNER.get());
	}

	@Override
	public IPeripheral createPeripheral(ITurtleAccess turtle, TurtleSide side) {
		scanner = new TileEntityEnvironmentScanner(turtle);
		return scanner.getModPeripheral();
	}

    @Nonnull
    @Override
    public TurtleCommandResult useTool(@Nonnull ITurtleAccess turtle, @Nonnull TurtleSide side,
                                       @Nonnull TurtleVerb verb, @Nonnull Direction direction) {
        return TurtleCommandResult.failure();
    }

    @Override
	public void update(ITurtleAccess turtle, TurtleSide side) {
		if (scanner != null) {
			TileEntityEnvironmentScanner.serverTick(
				(net.minecraft.world.level.Level) turtle.getLevel(),
				turtle.getPosition(),
				turtle.getLevel().getBlockState(turtle.getPosition()),
				scanner
			);
		}
	}
}
