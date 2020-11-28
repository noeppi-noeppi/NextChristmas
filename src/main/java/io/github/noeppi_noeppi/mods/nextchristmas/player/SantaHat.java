package io.github.noeppi_noeppi.mods.nextchristmas.player;

import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.libx.mod.registration.ItemBase;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SantaHat extends ItemBase {

    public SantaHat(ModX mod, Properties properties) {
        super(mod, properties.setISTER(() -> RenderSantaHat::get));
    }

    @Nullable
    @Override
    public EquipmentSlotType getEquipmentSlot(ItemStack stack) {
        return EquipmentSlotType.HEAD;
    }

    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World world, @Nonnull PlayerEntity player, @Nonnull Hand hand) {
        ItemStack stack = player.getHeldItem(hand);
        ItemStack current = player.getItemStackFromSlot(EquipmentSlotType.HEAD);
        if (current.isEmpty()) {
            player.setItemStackToSlot(EquipmentSlotType.HEAD, stack.copy());
            player.setHeldItem(hand, ItemStack.EMPTY);
            return ActionResult.resultConsume(stack);
        } else {
            return ActionResult.resultFail(stack);
        }
    }
}
