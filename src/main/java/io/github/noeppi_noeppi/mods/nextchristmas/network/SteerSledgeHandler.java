package io.github.noeppi_noeppi.mods.nextchristmas.network;

import io.github.noeppi_noeppi.mods.nextchristmas.ModEntities;
import io.github.noeppi_noeppi.mods.nextchristmas.entities.Sledge;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SteerSledgeHandler {

    public static void handle(SteerSledgeSerializer.SteerSledgeMessage msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity player = ctx.get().getSender();
            if (player != null){
                ServerWorld world = player.getServerWorld();
                Entity entity = world.getEntityByUuid(msg.entityId);
                if (entity instanceof Sledge) {
                    Sledge sledge = (Sledge) entity;
                    if (sledge.isPassenger(player)) {
                        sledge.updateInputs(msg.left, msg.right, msg.forward, msg.back, msg.boost, msg.fly);
                    }
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
