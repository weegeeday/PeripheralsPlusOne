package com.austinv11.peripheralsplusplus.turtles;


import com.austinv11.peripheralsplusplus.entities.EntityRidableTurtle;
import com.austinv11.peripheralsplusplus.reference.Config;
import com.austinv11.peripheralsplusplus.reference.Reference;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.turtle.*;
import net.minecraft.client.renderer.texture.TextureMap;

import net.minecraft.world.item.ItemStack;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TurtleRidable implements ITurtleUpgrade, TextureManager.TextureRegistrar {
	@Nonnull
	@Override
	public ResourceLocation getUpgradeID() {
		return new ResourceLocation(Reference.RIDABLE_UPGRADE);
	}

	@Override
	public String getUnlocalisedAdjective() {
		return Reference.MOD_ID + ".turtle_upgrade.ridable";
	}

	@Override
	public TurtleUpgradeType getType() {
		return TurtleUpgradeType.Peripheral;
	}

	@Override
	public ItemStack getCraftingItem() {
		if (Config.enableRidableTurtle)
			return new ItemStack(Items.SADDLE);
		return ItemStack.EMPTY;
	}

	@Override
	public IPeripheral createPeripheral(ITurtleAccess turtle, TurtleSide side) {
		EntityRidableTurtle entity = new EntityRidableTurtle(turtle.getLevel());
		entity.setPosition(turtle.getPosition().getX(), turtle.getPosition().getY(), turtle.getPosition().getZ());
		entity.setTurtle(turtle);
		turtle.getLevel().spawnEntity(entity);
		return entity;
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
		if (peripheral instanceof EntityRidableTurtle)
			((EntityRidableTurtle)peripheral).update(turtle);
	}

	@Override
	public void registerTextures(TextureMap textureMap) {
		textureMap.registerSprite(new ResourceLocation(Reference.MOD_ID, "blocks/ridable_turtle_upgrade"));
	}
}
