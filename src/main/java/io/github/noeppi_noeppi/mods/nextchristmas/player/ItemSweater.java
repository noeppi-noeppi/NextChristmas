package io.github.noeppi_noeppi.mods.nextchristmas.player;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.mods.nextchristmas.NextChristmas;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class ItemSweater extends ArmorItem {

    protected final ModX mod;

    public ItemSweater(ModX mod, Properties properties) {
        super(SweaterArmorMaterial.INSTANCE, EquipmentSlotType.CHEST, ((Supplier<Properties>)() -> {
            if (mod.tab != null) {
                properties.group(mod.tab);
            }

            return properties.maxDamage(0).maxStackSize(64);
        }).get());
        this.mod = mod;
    }

    @Nonnull
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(@Nonnull EquipmentSlotType slot) {
        return ImmutableMultimap.of();
    }

    @Override
    public final String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        if  (this.getRegistryName() != null) {
            return NextChristmas.getInstance().modid + ":textures/models/armor/" + this.getRegistryName().getPath() + ".png";
        } else {
            return "minecraft:missigno";
        }
    }
}
