package io.github.noeppi_noeppi.mods.nextchristmas.network;

import io.github.noeppi_noeppi.mods.nextchristmas.ModEntities;
import io.github.noeppi_noeppi.mods.nextchristmas.entities.Sledge;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SpawnSledgeHandler {

    public static void handle(SpawnSledgeSerializer.SpawnSledgeMessage msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if (Minecraft.getInstance().world != null) {
                ClientWorld world = Minecraft.getInstance().world;
                Sledge sledge = new Sledge(ModEntities.sledge, world);
                sledge.setLocationAndAngles(msg.x, msg.y, msg.z, msg.yaw, msg.pitch);
                sledge.setPacketCoordinates(msg.x, msg.y, msg.z);
                sledge.moveForced(msg.x, msg.y, msg.z);
                sledge.rotationYaw = msg.yaw;
                sledge.rotationPitch = msg.pitch;
                sledge.setEntityId(msg.entityId);
                sledge.setUniqueId(msg.uuid);
                world.addEntity(msg.entityId, sledge);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
