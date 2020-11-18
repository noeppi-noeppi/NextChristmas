package io.github.noeppi_noeppi.mods.nextchristmas;

import io.github.noeppi_noeppi.libx.inventory.container.ContainerBase;
import io.github.noeppi_noeppi.libx.mod.registration.BlockGUI;
import io.github.noeppi_noeppi.mods.nextchristmas.oven.BlockOven;
import io.github.noeppi_noeppi.mods.nextchristmas.oven.ContainerOven;
import io.github.noeppi_noeppi.mods.nextchristmas.oven.TileOven;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.material.Material;

public class ModBlocks {

    public static final BlockGUI<TileOven, ContainerOven> oven = new BlockOven(NextChristmas.getInstance(), TileOven.class, ContainerBase.createContainerType(ContainerOven::new), AbstractBlock.Properties.create(Material.IRON));

    public static void register() {
        NextChristmas.getInstance().register("oven", oven);
    }
}
