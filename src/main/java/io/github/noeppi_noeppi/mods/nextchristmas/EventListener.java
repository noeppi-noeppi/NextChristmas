package io.github.noeppi_noeppi.mods.nextchristmas;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class EventListener {

    @SubscribeEvent
    public void entitySpawn(LivingSpawnEvent.SpecialSpawn event) {
        if (!event.getWorld().isRemote()) {
            EntityType<?> type = event.getEntityLiving().getType();
            if ((type == EntityType.ZOMBIE
                    || type == EntityType.SKELETON
                    || type == EntityType.STRAY
                    || type == EntityType.HUSK
                    || type == EntityType.DROWNED)
                    && event.getEntityLiving() instanceof MobEntity
                    && event.getEntityLiving().getItemStackFromSlot(EquipmentSlotType.HEAD).isEmpty()) {
                MobEntity entity = (MobEntity) event.getEntityLiving();
                ZonedDateTime zdt = ZonedDateTime.now();
                if (zdt.getMonth() == Month.DECEMBER) {
                    int chance = (zdt.getDayOfMonth() == 6 || (zdt.getDayOfMonth() >= 24 && zdt.getDayOfMonth() <= 26)) ? 2 : 30;
                    if (event.getWorld().getRandom().nextInt(chance) == 0) {
                        entity.setItemStackToSlot(EquipmentSlotType.HEAD, new ItemStack(ModItems.santaHat));
                        entity.setDropChance(EquipmentSlotType.HEAD, 0);
                    }
                }
            }
        }
    }
}
