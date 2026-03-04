package com.austinv11.peripheralsplusplus.turtles;

import com.austinv11.peripheralsplusplus.reference.Config;
import com.austinv11.peripheralsplusplus.reference.Reference;
import com.austinv11.peripheralsplusplus.utils.FakeTurtlePlayer;
import com.austinv11.peripheralsplusplus.utils.TurtleUtil;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.turtle.*;
import net.minecraft.world.entity.Entity;

import net.minecraft.world.item.ItemStack;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.IShearable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class TurtleShear implements ITurtleUpgrade {

    private final ResourceLocation upgradeId;

    public TurtleShear(ResourceLocation id) {
        this.upgradeId = id;
    }


	@Override
	public ResourceLocation getUpgradeID() {
		return upgradeId;
	}

    @Override
	public String getUnlocalisedAdjective() {
		return Reference.MOD_ID + ".turtle_upgrade.shears";
	}

	@Override
	public TurtleUpgradeType getType() {
		return TurtleUpgradeType.Tool;
	}

	@Override
	public ItemStack getCraftingItem() {
		if (Config.enableShearTurtle)
			return new ItemStack(net.minecraft.world.item.Items.SHEARS);
		return ItemStack.EMPTY;
	}

	@Override
	public IPeripheral createPeripheral(ITurtleAccess turtle, TurtleSide side) {
		return null;
	}

    @Nonnull
    @Override
    public TurtleCommandResult useTool(@Nonnull ITurtleAccess turtle, @Nonnull TurtleSide side,
									   @Nonnull TurtleVerb verb, @Nonnull Direction direction) {
		if (!Config.enableShearTurtle)
			return TurtleCommandResult.failure("Shearing turtles have been disabled");
		FakeTurtlePlayer player = new FakeTurtlePlayer(turtle);
		switch (verb) {
			case Attack:
				List<Entity> entities = TurtleUtil.getEntitiesNearTurtle(turtle, player, direction);
				Entity ent = null;
				for (Entity e : entities) {
					if (e instanceof IShearable) { ent = e; break; }
				}
				if (ent != null)
					if (((IShearable) ent).isShearable(new ItemStack(net.minecraft.world.item.Items.SHEARS), ent.level(), ent.blockPosition())) {
						TurtleUtil.addItemListToInv(((IShearable) ent).onSheared(null, ent.level(),
								ent.blockPosition(), 0), turtle);
						return TurtleCommandResult.success();
					}
				return TurtleCommandResult.failure();
			case Dig:
				List<ItemStack> items = TurtleUtil.harvestBlock(turtle, player, direction, new ItemStack(net.minecraft.world.item.Items.SHEARS));
				if (items != null) {
					TurtleUtil.addItemListToInv(items, turtle);
					return TurtleCommandResult.success();
				}
				return TurtleCommandResult.failure();
		}
		return TurtleCommandResult.failure("An unknown error has occurred, please tell the mod author");
    }

	@Override
	public void update(ITurtleAccess turtle, TurtleSide side) {}
}
