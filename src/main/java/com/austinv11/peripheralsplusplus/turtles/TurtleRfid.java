package com.austinv11.peripheralsplusplus.turtles;

import com.austinv11.peripheralsplusplus.init.ModItems;
import com.austinv11.peripheralsplusplus.reference.Reference;
import com.austinv11.peripheralsplusplus.tiles.TileEntityRfidReaderWriter;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.turtle.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TurtleRfid implements ITurtleUpgrade {

    private final ResourceLocation upgradeId;

    public TurtleRfid(ResourceLocation id) {
        this.upgradeId = id;
    }

    @Nonnull
    @Override
    public ResourceLocation getUpgradeID() {
        return upgradeId;
    }

    @Nonnull
    @Override
    public String getUnlocalisedAdjective() {
        return Reference.MOD_ID + ".turtle_upgrade.rfid";
    }

    @Nonnull
    @Nonnull
    @Override
    public ItemStack getCraftingItem() {
        return new ItemStack(ModItems.RFID_READER_WRITER.get());
    }

    @Nullable
    @Override
    public IPeripheral createPeripheral(@Nonnull ITurtleAccess turtle, @Nonnull TurtleSide side) {
        return new TileEntityRfidReaderWriter(turtle.getPosition(),
                turtle.getLevel().getBlockState(turtle.getPosition())).getModPeripheral();
    }

    @Nonnull
    @Override
    public TurtleCommandResult useTool(@Nonnull ITurtleAccess turtle, @Nonnull TurtleSide side,
                                       @Nonnull TurtleVerb verb, @Nonnull Direction direction) {
        return TurtleCommandResult.failure();
    }

    @Override
    public void update(@Nonnull ITurtleAccess turtle, @Nonnull TurtleSide side) {}
}
