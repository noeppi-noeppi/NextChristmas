package io.github.noeppi_noeppi.mods.nextchristmas;

import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;

public class ModItems {

    public static final Item reindeerSpawnEgg = new SpawnEggItem(ModEntities.reindeer, 0x93640C, 0xE6D5BC, new Item.Properties().group(NextChristmas.getInstance().tab));

    public static void register() {
        NextChristmas.getInstance().register("reindeer_spawn_egg", reindeerSpawnEgg);
    }
}
