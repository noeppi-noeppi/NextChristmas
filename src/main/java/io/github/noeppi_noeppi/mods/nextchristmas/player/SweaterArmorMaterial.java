package io.github.noeppi_noeppi.mods.nextchristmas.player;

import io.github.noeppi_noeppi.mods.nextchristmas.NextChristmas;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

import javax.annotation.Nonnull;

public class SweaterArmorMaterial implements IArmorMaterial {

    public static final SweaterArmorMaterial INSTANCE = new SweaterArmorMaterial();

    private SweaterArmorMaterial() {

    }

    @Override
    public int getDurability(@Nonnull EquipmentSlotType slot) {
        return 0;
    }

    @Override
    public int getDamageReductionAmount(@Nonnull EquipmentSlotType slot) {
        return 0;
    }

    @Override
    public int getEnchantability() {
        return 0;
    }

    @Nonnull
    @Override
    public SoundEvent getSoundEvent() {
        return SoundEvents.ITEM_ARMOR_EQUIP_LEATHER;
    }

    @Nonnull
    @Override
    public Ingredient getRepairMaterial() {
        return Ingredient.EMPTY;
    }

    @Nonnull
    @Override
    public String getName() {
        return NextChristmas.getInstance().modid +  "_christmas_sweater";
    }

    @Override
    public float getToughness() {
        return 0;
    }

    @Override
    public float getKnockbackResistance() {
        return 0;
    }
}
