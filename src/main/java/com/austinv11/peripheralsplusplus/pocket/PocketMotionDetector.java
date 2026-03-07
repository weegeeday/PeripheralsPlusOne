package com.austinv11.peripheralsplusplus.pocket;

import com.austinv11.peripheralsplusplus.init.ModItems;
import com.austinv11.peripheralsplusplus.pocket.peripherals.PeripheralMotionDetector;
import com.austinv11.peripheralsplusplus.reference.Reference;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.pocket.IPocketAccess;
import dan200.computercraft.api.pocket.IPocketUpgrade;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PocketMotionDetector implements IPocketUpgrade {

    private final ResourceLocation upgradeId;

    public PocketMotionDetector(ResourceLocation id) {
        this.upgradeId = id;
    }

	
	@Override
	public ResourceLocation getUpgradeID() {
		return upgradeId;
	}
	
	@Override
	public String getUnlocalisedAdjective() {
		return "peripheralsplusone.pocket_upgrade.motion_detector";
	}
	
	@Override
	public ItemStack getCraftingItem() {
		return new ItemStack(ModItems.MOTION_DETECTOR.get());
	}

    @Nullable
    @Override
    public IPeripheral createPeripheral(@Nonnull IPocketAccess access) {
        return new PeripheralMotionDetector(access.getEntity());
    }

	@Override
	public void update(@Nonnull IPocketAccess access, @Nullable IPeripheral peripheral) {
		if (peripheral instanceof PeripheralMotionDetector)
			((PeripheralMotionDetector)peripheral).update(access.getEntity());
	}

	@Override
	public boolean onRightClick(@Nonnull Level world, @Nonnull IPocketAccess access, @Nullable IPeripheral peripheral) {
		return false;
	}
}
