package io.github.noeppi_noeppi.mods.nextchristmas.network;

import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.libx.network.NetworkX;
import io.github.noeppi_noeppi.mods.nextchristmas.entities.Sledge;
import net.minecraftforge.fml.network.NetworkDirection;

public class NextNetwork extends NetworkX {

    public NextNetwork(ModX mod) {
        super(mod);
    }

    @Override
    protected void registerPackets() {
        this.register(new SteerSledgeSerializer(), () -> SteerSledgeHandler::handle, NetworkDirection.PLAY_TO_SERVER);
        this.register(new OpenSledgeGuiSerializer(), () -> OpenSledgeGuiHandler::handle, NetworkDirection.PLAY_TO_SERVER);
    }

    @Override
    protected String getProtocolVersion() {
        return "2";
    }

    public void updateSledgeMovement(Sledge sledge, boolean forward, boolean back, boolean right, boolean left, boolean boost, boolean fly) {
        if (sledge.world.isRemote) {
            this.instance.sendToServer(new SteerSledgeSerializer.SteerSledgeMessage(sledge.getUniqueID(), forward, back, right, left, boost, fly));
        }
    }
}
