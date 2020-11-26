package io.github.noeppi_noeppi.mods.nextchristmas.network;

import io.github.noeppi_noeppi.libx.network.PacketSerializer;
import net.minecraft.network.PacketBuffer;

public class OpenSledgeGuiSerializer implements PacketSerializer<OpenSledgeGuiSerializer.OpenSledgeGuiMessage> {

    @Override
    public Class<OpenSledgeGuiMessage> messageClass() {
        return OpenSledgeGuiMessage.class;
    }

    @Override
    public void encode(OpenSledgeGuiMessage msg, PacketBuffer buffer) {

    }

    @Override
    public OpenSledgeGuiMessage decode(PacketBuffer buffer) {
        return new OpenSledgeGuiMessage();
    }

    public static class OpenSledgeGuiMessage {

        public OpenSledgeGuiMessage() {

        }
    }
}
