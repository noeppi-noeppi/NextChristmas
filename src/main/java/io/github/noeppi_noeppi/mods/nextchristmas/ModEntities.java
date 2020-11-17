package io.github.noeppi_noeppi.mods.nextchristmas;

import io.github.noeppi_noeppi.mods.nextchristmas.entities.Reindeer;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;

public class ModEntities {

    public static final EntityType<Reindeer> reindeer = EntityType.Builder.create(Reindeer::new, EntityClassification.CREATURE).size(2.4375f, 2.375f).trackingRange(10).build(NextChristmas.getInstance().modid + "_reindeer");

    public static void register() {
        NextChristmas.getInstance().register("reinder", reindeer);
    }
}
