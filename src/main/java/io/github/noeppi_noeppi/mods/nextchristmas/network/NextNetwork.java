package io.github.noeppi_noeppi.mods.nextchristmas.network;

import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.libx.network.NetworkX;
import io.github.noeppi_noeppi.mods.nextchristmas.entities.Sledge;
import net.minecraft.network.IPacket;
import net.minecraftforge.fml.network.NetworkDirection;

public class NextNetwork extends NetworkX {

    public NextNetwork(ModX mod) {
        super(mod);
    }

    @Override
    protected void registerPackets() {
        this.register(new SpawnSledgeSerializer(), () -> SpawnSledgeHandler::handle, NetworkDirection.PLAY_TO_CLIENT);
        this.register(new SteerSledgeSerializer(), () -> SteerSledgeHandler::handle, NetworkDirection.PLAY_TO_SERVER);
        this.register(new OpenSledgeGuiSerializer(), () -> OpenSledgeGuiHandler::handle, NetworkDirection.PLAY_TO_SERVER);
    }

    @Override
    protected String getProtocolVersion() {
        return "1";
    }

    public IPacket<?> getSledgeSpawnPacket(Sledge sledge) {
        return this.instance.toVanillaPacket(new SpawnSledgeSerializer.SpawnSledgeMessage(sledge.getPosX(), sledge.getPosY(), sledge.getPosZ(), sledge.rotationYaw, sledge.rotationPitch, sledge.getEntityId(), sledge.getUniqueID()), NetworkDirection.PLAY_TO_CLIENT);
    }

    public void updateSledgeMovement(Sledge sledge, boolean forward, boolean back, boolean right, boolean left, boolean boost, boolean fly) {
        if (sledge.world.isRemote) {
            this.instance.sendToServer(new SteerSledgeSerializer.SteerSledgeMessage(sledge.getUniqueID(), forward, back, right, left, boost, fly));
        }
    }
}
