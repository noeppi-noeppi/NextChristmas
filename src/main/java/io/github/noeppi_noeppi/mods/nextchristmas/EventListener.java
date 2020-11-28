package io.github.noeppi_noeppi.mods.nextchristmas;

import io.github.noeppi_noeppi.mods.nextchristmas.entities.Sledge;
import io.github.noeppi_noeppi.mods.nextchristmas.network.OpenSledgeGuiSerializer;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.MovementInput;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.time.Month;
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

    @SubscribeEvent(priority = EventPriority.LOWEST)
    @OnlyIn(Dist.CLIENT)
    public void sledgeControl(InputUpdateEvent event) {
        if (Minecraft.getInstance().player != null) {
            Entity ridden = Minecraft.getInstance().player.getRidingEntity();
            if (ridden instanceof Sledge) {
                MovementInput movement = event.getMovementInput();
                ((Sledge) ridden).updateInputs(movement.leftKeyDown, movement.rightKeyDown, movement.forwardKeyDown, movement.backKeyDown, Minecraft.getInstance().gameSettings.keyBindSprint.isKeyDown(), movement.jump);
            }
        }
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void catchPressE(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player != null && mc.player.getRidingEntity() instanceof Sledge && ((Sledge) mc.player.getRidingEntity()).isChested()) {
                if (mc.loadingGui == null && (mc.currentScreen == null || mc.currentScreen.passEvents)) {
                    if (Minecraft.getInstance().gameSettings.keyBindInventory.isPressed()) {
                        NextChristmas.getNetwork().instance.sendToServer(new OpenSledgeGuiSerializer.OpenSledgeGuiMessage());
                    }
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void toolUse(BlockEvent.BlockToolInteractEvent event) {
        if (!event.getWorld().isRemote()) {
            if (event.getToolType() == ToolType.AXE && event.getState().getBlock() == Blocks.OAK_LOG) {
                if (event.getWorld().getRandom().nextInt(10) == 0) {
                    BlockPos pos = event.getPos().toImmutable().offset(Direction.byHorizontalIndex(event.getWorld().getRandom().nextInt(4)));
                    ItemEntity ie = new ItemEntity(event.getPlayer().world, pos.getX() + 0.5, pos.getY() + 0.2, pos.getZ() + 0.5);
                    ie.setItem(new ItemStack(ModItems.cinnamonBark));
                    event.getPlayer().world.addEntity(ie);
                }
            }
        }
    }
}
