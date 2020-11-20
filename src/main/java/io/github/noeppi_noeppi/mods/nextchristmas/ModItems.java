package io.github.noeppi_noeppi.mods.nextchristmas;

import io.github.noeppi_noeppi.libx.mod.registration.ItemBase;
import io.github.noeppi_noeppi.mods.nextchristmas.biscuit.ItemBiscuit;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class ModItems {

    public static final Item reindeerSpawnEgg = new SpawnEggItem(ModEntities.reindeer, 0x93640C, 0xE6D5BC, new Item.Properties().group(NextChristmas.getInstance().tab));
    public static final Item flour = new ItemBase(NextChristmas.getInstance(), new Item.Properties());
    public static final Item vanillaFruits = new ItemBase(NextChristmas.getInstance(), new Item.Properties());
    public static final Item vanilla = new ItemBase(NextChristmas.getInstance(), new Item.Properties());
    public static final ItemBiscuit vanillaCrescent = new ItemBiscuit(NextChristmas.getInstance(), new Item.Properties().food(new Food.Builder().hunger(1).saturation(1).fastToEat().setAlwaysEdible().effect(() -> new EffectInstance(Effects.HASTE, 120, 0), 1).build()));



    public static void register() {
        NextChristmas.getInstance().register("reindeer_spawn_egg", reindeerSpawnEgg);
        NextChristmas.getInstance().register("flour", flour);
        NextChristmas.getInstance().register("vanilla_fruits", vanillaFruits);
        NextChristmas.getInstance().register("vanilla", vanilla);
        NextChristmas.getInstance().register("vanilla_crescent", vanillaCrescent);
    }
}
