package io.github.noeppi_noeppi.mods.nextchristmas;

import net.minecraft.block.Block;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;

public class ModTags {

    public static class Blocks {
        public static final Tags.IOptionalNamedTag<Block> sledgeSurface = BlockTags.createOptional(new ResourceLocation(NextChristmas.getInstance().modid, "sledge_surface"));
    }

    public static class Items {

    }
}
