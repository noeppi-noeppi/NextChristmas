package io.github.noeppi_noeppi.mods.nextchristmas;

import io.github.noeppi_noeppi.libx.mod.registration.ItemBase;
import io.github.noeppi_noeppi.mods.nextchristmas.biscuit.ItemBiscuit;
import io.github.noeppi_noeppi.mods.nextchristmas.entities.ItemSledge;
import io.github.noeppi_noeppi.mods.nextchristmas.entities.SledgeType;
import io.github.noeppi_noeppi.mods.nextchristmas.player.ItemSweater;
import io.github.noeppi_noeppi.mods.nextchristmas.player.SantaHat;
import io.github.noeppi_noeppi.mods.nextchristmas.util.EnumValues;
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
    public static final Item hazelnut = new ItemBase(NextChristmas.getInstance(), new Item.Properties());
    public static final Item crushedNut = new ItemBase(NextChristmas.getInstance(), new Item.Properties());
    public static final ItemBiscuit vanillaCrescent = new ItemBiscuit(NextChristmas.getInstance(), new Item.Properties().food(new Food.Builder().hunger(1).saturation(1).fastToEat().setAlwaysEdible().effect(() -> new EffectInstance(Effects.HASTE, 120, 0), 1).build()));
    public static final ItemBiscuit gingerbread = new ItemBiscuit(NextChristmas.getInstance(), new Item.Properties().food(new Food.Builder().hunger(1).saturation(1).fastToEat().setAlwaysEdible().effect(() -> new EffectInstance(Effects.NIGHT_VISION, 120, 0), 1).build()));
    public static final ItemBase santaHat = new SantaHat(NextChristmas.getInstance(), new Item.Properties());
    public static final ItemSweater sweaterTree = new ItemSweater(NextChristmas.getInstance(), new Item.Properties());
    public static final ItemSweater sweaterReindeer = new ItemSweater(NextChristmas.getInstance(), new Item.Properties());
    public static final ItemSweater sweaterSnowflake = new ItemSweater(NextChristmas.getInstance(), new Item.Properties());
    public static final ItemSweater sweaterSnowman = new ItemSweater(NextChristmas.getInstance(), new Item.Properties());
    public static final EnumValues<SledgeType, ItemSledge> sledge = new EnumValues<>(SledgeType.values(), type -> new ItemSledge(NextChristmas.getInstance(), type, new Item.Properties()));
    public static final Item cinnamonBark = new ItemBase(NextChristmas.getInstance(), new Item.Properties());
    public static final Item cinnamon = new ItemBase(NextChristmas.getInstance(), new Item.Properties());
    public static final ItemBiscuit speculaas = new ItemBiscuit(NextChristmas.getInstance(), new Item.Properties().food(new Food.Builder().hunger(1).saturation(1).fastToEat().setAlwaysEdible().effect(() -> new EffectInstance(Effects.LUCK, 120, 0), 1).build()));
    public static final ItemBiscuit cinnamonStar = new ItemBiscuit(NextChristmas.getInstance(), new Item.Properties().food(new Food.Builder().hunger(1).saturation(1).fastToEat().setAlwaysEdible().effect(() -> new EffectInstance(Effects.RESISTANCE, 120, 0), 1).build()));

    public static void register() {
        NextChristmas.getInstance().register("reindeer_spawn_egg", reindeerSpawnEgg);
        NextChristmas.getInstance().register("flour", flour);
        NextChristmas.getInstance().register("vanilla_fruits", vanillaFruits);
        NextChristmas.getInstance().register("vanilla", vanilla);
        NextChristmas.getInstance().register("hazelnut", hazelnut);
        NextChristmas.getInstance().register("crushed_nut", crushedNut);
        NextChristmas.getInstance().register("vanilla_crescent", vanillaCrescent);
        NextChristmas.getInstance().register("gingerbread", gingerbread);
        NextChristmas.getInstance().register("santa_hat", santaHat);
        NextChristmas.getInstance().register("sweater_tree", sweaterTree);
        NextChristmas.getInstance().register("sweater_reindeer", sweaterReindeer);
        NextChristmas.getInstance().register("sweater_snowflake", sweaterSnowflake);
        NextChristmas.getInstance().register("sweater_snowman", sweaterSnowman);
        NextChristmas.getInstance().register("sledge", sledge);
        NextChristmas.getInstance().register("cinnamon_bark", cinnamonBark);
        NextChristmas.getInstance().register("cinnamon", cinnamon);
        NextChristmas.getInstance().register("speculaas", speculaas);
        NextChristmas.getInstance().register("cinnamon_star", cinnamonStar);
    }
}
