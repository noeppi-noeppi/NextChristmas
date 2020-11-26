package io.github.noeppi_noeppi.mods.nextchristmas.entities;

import io.github.noeppi_noeppi.mods.nextchristmas.ModItems;
import io.github.noeppi_noeppi.mods.nextchristmas.NextChristmas;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.LazyValue;
import net.minecraft.util.ResourceLocation;

public enum SledgeType {

    ACACIA(Items.ACACIA_PLANKS, new ResourceLocation(NextChristmas.getInstance().modid, "textures/entity/sledge/acacia.png")),
    BIRCH(Items.BIRCH_PLANKS, new ResourceLocation(NextChristmas.getInstance().modid, "textures/entity/sledge/birch.png")),
    CRIMSON(Items.CRIMSON_PLANKS, new ResourceLocation(NextChristmas.getInstance().modid, "textures/entity/sledge/crimson.png")),
    DARK_OAK(Items.DARK_OAK_PLANKS, new ResourceLocation(NextChristmas.getInstance().modid, "textures/entity/sledge/dark_oak.png")),
    JUNGLE(Items.JUNGLE_PLANKS, new ResourceLocation(NextChristmas.getInstance().modid, "textures/entity/sledge/jungle.png")),
    OAK(Items.OAK_PLANKS, new ResourceLocation(NextChristmas.getInstance().modid, "textures/entity/sledge/oak.png")),
    SPRUCE(Items.SPRUCE_PLANKS, new ResourceLocation(NextChristmas.getInstance().modid, "textures/entity/sledge/spruce.png")),
    WARPED(Items.WARPED_PLANKS, new ResourceLocation(NextChristmas.getInstance().modid, "textures/entity/sledge/warped.png"));

    public final LazyValue<Item> sledgeItem;
    public final Item material;
    public final ResourceLocation texture;

    SledgeType(Item material, ResourceLocation texture) {
        this.sledgeItem = new LazyValue<>(() -> ModItems.sledge.map.get(this));
        this.material = material;
        this.texture = texture;
    }
}
