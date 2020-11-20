package io.github.noeppi_noeppi.mods.nextchristmas;

import io.github.noeppi_noeppi.libx.inventory.container.ContainerBase;
import io.github.noeppi_noeppi.libx.mod.registration.BlockGUI;
import io.github.noeppi_noeppi.libx.mod.registration.BlockTE;
import io.github.noeppi_noeppi.mods.nextchristmas.biscuit.BlockBakingSheet;
import io.github.noeppi_noeppi.mods.nextchristmas.mill.BlockMill;
import io.github.noeppi_noeppi.mods.nextchristmas.mill.TileMill;
import io.github.noeppi_noeppi.mods.nextchristmas.oven.BlockOven;
import io.github.noeppi_noeppi.mods.nextchristmas.oven.ContainerOven;
import io.github.noeppi_noeppi.mods.nextchristmas.oven.TileOven;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ModBlocks {

    public static final BlockGUI<TileOven, ContainerOven> oven = new BlockOven(NextChristmas.getInstance(), TileOven.class, ContainerBase.createContainerType(ContainerOven::new), AbstractBlock.Properties.create(Material.IRON));
    public static final BlockTE<TileMill> grainMill = new BlockMill(NextChristmas.getInstance(), TileMill.class, AbstractBlock.Properties.create(Material.IRON));
    public static final BlockBakingSheet bakingSheet = new BlockBakingSheet(NextChristmas.getInstance(), ItemStack.EMPTY, false, new Item.Properties());

    public static void register() {
        NextChristmas.getInstance().register("oven", oven);
        NextChristmas.getInstance().register("grain_mill", grainMill);
        NextChristmas.getInstance().register("baking_sheet", bakingSheet);
    }
}
