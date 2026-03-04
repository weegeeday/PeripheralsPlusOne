package com.austinv11.peripheralsplusplus.pocket;

import com.austinv11.peripheralsplusplus.init.ModBlocks;
import com.austinv11.peripheralsplusplus.pocket.peripherals.PeripheralPeripheralContainer;
import com.austinv11.peripheralsplusplus.reference.Reference;
import com.austinv11.peripheralsplusplus.utils.TurtleUtil;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.pocket.IPocketAccess;
import dan200.computercraft.api.pocket.IPocketUpgrade;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class PocketPeripheralContainer implements IPocketUpgrade {

    private final ResourceLocation upgradeId;

    public PocketPeripheralContainer(ResourceLocation id) {
        this.upgradeId = id;
    }


@Override
public ResourceLocation getUpgradeID() {
return upgradeId;
}

@Override
public String getUnlocalisedAdjective() {
return "peripheralsplusone.pocket_upgrade.peripheral_container";
}

@Override
public ItemStack getCraftingItem() {
return new ItemStack(ModBlocks.PERIPHERAL_CONTAINER.get());
}

@Nullable
@Override
public IPeripheral createPeripheral(@Nonnull IPocketAccess access) {
Map<IPocketUpgrade, IPeripheral> pocketUpgrades = new HashMap<>();
return new PeripheralPeripheralContainer(pocketUpgrades);
}

@Override
public void update(@Nonnull IPocketAccess access, @Nullable IPeripheral peripheral) {
if (!(peripheral instanceof PeripheralPeripheralContainer container)) return;
for (Map.Entry<IPocketUpgrade, IPeripheral> entry : container.getUpgrades().entrySet())
entry.getKey().update(access, entry.getValue());
}

@Override
public boolean onRightClick(@Nonnull Level world, @Nonnull IPocketAccess access, @Nullable IPeripheral peripheral) {
if (!(peripheral instanceof PeripheralPeripheralContainer container)) return false;
boolean result = false;
for (Map.Entry<IPocketUpgrade, IPeripheral> entry : container.getUpgrades().entrySet())
result |= entry.getKey().onRightClick(world, access, entry.getValue());
return result;
}
}
