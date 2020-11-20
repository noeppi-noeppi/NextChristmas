package io.github.noeppi_noeppi.mods.nextchristmas;

import io.github.noeppi_noeppi.libx.inventory.container.ContainerBase;
import io.github.noeppi_noeppi.libx.mod.registration.BlockGUI;
import io.github.noeppi_noeppi.mods.nextchristmas.biscuit.BlockBakingSheet;
import io.github.noeppi_noeppi.mods.nextchristmas.oven.BlockOven;
import io.github.noeppi_noeppi.mods.nextchristmas.oven.ContainerOven;
import io.github.noeppi_noeppi.mods.nextchristmas.oven.TileOven;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;

public class ModBlocks {

    public static final BlockGUI<TileOven, ContainerOven> oven = new BlockOven(NextChristmas.getInstance(), TileOven.class, ContainerBase.createContainerType(ContainerOven::new), AbstractBlock.Properties.create(Material.IRON));

    public static final Block s1 = new BlockBakingSheet(NextChristmas.getInstance(), Items.DIAMOND, true, AbstractBlock.Properties.create(Material.IRON));
    public static final Block s6 = new BlockBakingSheet(NextChristmas.getInstance(), new ResourceLocation("minecraft", "item/diamond"), false, AbstractBlock.Properties.create(Material.IRON));

    public static void register() {
        NextChristmas.getInstance().register("oven", oven);
        NextChristmas.getInstance().register("s1", s1);
        NextChristmas.getInstance().register("s6", s6);
    }
}
