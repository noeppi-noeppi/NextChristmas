package io.github.noeppi_noeppi.mods.nextchristmas.network;

import io.github.noeppi_noeppi.mods.nextchristmas.entities.Sledge;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class OpenSledgeGuiHandler {

    public static void handle(OpenSledgeGuiSerializer.OpenSledgeGuiMessage msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity player = ctx.get().getSender();
            if (player != null && (player.openContainer == null || player.openContainer == player.container)) {
                Entity entity = player.getRidingEntity();
                if (entity instanceof Sledge && ((Sledge) entity).isChested()) {
                    ((Sledge) entity).openInventory(player);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}