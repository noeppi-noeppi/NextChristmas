package io.github.noeppi_noeppi.mods.nextchristmas.oven;

import io.github.noeppi_noeppi.libx.inventory.container.ContainerBase;
import io.github.noeppi_noeppi.libx.inventory.slot.SlotOutputOnly;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ContainerOven extends ContainerBase<TileOven> {

    public ContainerOven(@Nullable ContainerType<?> type, int windowId, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player) {
        super(type, windowId, world, pos, playerInventory, player, 3, 5);
        this.addSlot(new SlotItemHandler(this.tile.getInventory(), TileOven.SLOT_IN1, 56, 19));
        this.addSlot(new SlotItemHandler(this.tile.getInventory(), TileOven.SLOT_IN2, 56, 51));
        this.addSlot(new SlotItemHandler(this.tile.getInventory(), TileOven.SLOT_FUEL, 22, 51));
        this.addSlot(new SlotOutputOnly(this.tile.getInventory(), TileOven.SLOT_OUT1, 116, 19));
        this.addSlot(new SlotOutputOnly(this.tile.getInventory(), TileOven.SLOT_OUT2, 116, 51));
        this.layoutPlayerInventorySlots(8, 84);
        this.tile.playerUse();
    }

    @Override
    public void onContainerClosed(@Nonnull PlayerEntity player) {
        super.onContainerClosed(player);
    }
}
