package io.github.noeppi_noeppi.mods.nextchristmas.util;

import com.google.common.collect.ImmutableSet;
import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.libx.mod.registration.Registerable;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;
import java.util.Set;

public class BlockColored extends Block implements Registerable {

    protected final ModX mod;
    private final Item item;
    private final String translationKey;

    public final DyeColor color;

    public BlockColored(ModX mod, DyeColor color, String translationKey, Properties properties) {
        this(mod, color, translationKey, properties, new net.minecraft.item.Item.Properties());
    }

    public BlockColored(ModX mod, DyeColor color, String translationKey, Properties properties, Item.Properties itemProperties) {
        super(properties);
        this.mod = mod;
        this.color = color;
        this.translationKey = translationKey;
        if (mod.tab != null) {
            itemProperties.group(mod.tab);
        }

        this.item = new BlockItem(this, itemProperties) {

            @Nonnull
            @Override
            public ITextComponent getName() {
                return BlockColored.this.getName();
            }

            @Nonnull
            @Override
            public ITextComponent getDisplayName(@Nonnull ItemStack stack) {
                return BlockColored.this.getName();
            }
        };
    }

    public Set<Object> getAdditionalRegisters() {
        return ImmutableSet.of(this.item);
    }

    @Nonnull
    public IFormattableTextComponent getName() {
        return new TranslationTextComponent("color.minecraft." + this.color.getString()).append(new StringTextComponent(" ")).append(new TranslationTextComponent(this.translationKey));
    }

    @Nonnull
    @Override
    public IFormattableTextComponent getTranslatedName() {
        return this.getName();
    }
}
