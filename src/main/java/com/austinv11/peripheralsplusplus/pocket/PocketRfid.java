package com.austinv11.peripheralsplusplus.pocket;

import com.austinv11.peripheralsplusplus.init.ModItems;
import com.austinv11.peripheralsplusplus.reference.Reference;
import com.austinv11.peripheralsplusplus.tiles.TileEntityRfidReaderWriter;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.pocket.IPocketAccess;
import dan200.computercraft.api.pocket.IPocketUpgrade;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PocketRfid implements IPocketUpgrade {

    private final ResourceLocation upgradeId;

    public PocketRfid(ResourceLocation id) {
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
        return "peripheralsplusone.pocket_upgrade.rfid";
    }

    @Nonnull
    @Override
    public ItemStack getCraftingItem() {
        return new ItemStack(ModItems.RFID_READER_WRITER.get());
    }

    @Nullable
    @Override
    public IPeripheral createPeripheral(@Nonnull IPocketAccess access) {
        net.minecraft.world.entity.Entity e = access.getEntity();
        if (e == null) return null;
        net.minecraft.core.BlockPos pos = e.blockPosition();
        return new TileEntityRfidReaderWriter(pos, e.level().getBlockState(pos)).getModPeripheral();
    }

    @Override
    public void update(@Nonnull IPocketAccess access, @Nullable IPeripheral peripheral) {
        // position is set at creation time; no update needed
    }

    @Override
    public boolean onRightClick(@Nonnull Level world, @Nonnull IPocketAccess access, @Nullable IPeripheral peripheral) {
        return false;
    }
}
