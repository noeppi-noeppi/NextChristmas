package io.github.noeppi_noeppi.mods.nextchristmas;

import io.github.noeppi_noeppi.mods.nextchristmas.entities.Reindeer;
import io.github.noeppi_noeppi.mods.nextchristmas.entities.Sledge;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;

public class ModEntities {

    public static final EntityType<Reindeer> reindeer = EntityType.Builder.<Reindeer>create(Reindeer::new, EntityClassification.CREATURE).size(2.4375f, 2.375f).trackingRange(10).build(NextChristmas.getInstance().modid + "_reindeer");
    public static final EntityType<Sledge> sledge = EntityType.Builder.<Sledge>create(Sledge::new, EntityClassification.MISC).size(1.5f, 0.875f).trackingRange(10).build(NextChristmas.getInstance().modid + "_sledge");

    public static void register() {
        NextChristmas.getInstance().register("reindeer", reindeer);
        NextChristmas.getInstance().register("sledge", sledge);
    }
}
