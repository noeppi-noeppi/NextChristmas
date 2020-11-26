package io.github.noeppi_noeppi.mods.nextchristmas.network;

import io.github.noeppi_noeppi.libx.network.PacketSerializer;
import net.minecraft.network.PacketBuffer;

import java.util.UUID;

public class SteerSledgeSerializer implements PacketSerializer<SteerSledgeSerializer.SteerSledgeMessage> {

    @Override
    public Class<SteerSledgeMessage> messageClass() {
        return SteerSledgeMessage.class;
    }

    @Override
    public void encode(SteerSledgeMessage msg, PacketBuffer buffer) {
        buffer.writeUniqueId(msg.entityId);
        int mask = 0;
        if (msg.forward) mask |= 1;
        if (msg.back) mask |= 1 << 1;
        if (msg.right) mask |= 1 << 2;
        if (msg.left) mask |= 1 << 3;
        if (msg.boost) mask |= 1 << 4;
        if (msg.fly) mask |= 1 << 5;
        buffer.writeInt(mask);
    }

    @Override
    public SteerSledgeMessage decode(PacketBuffer buffer) {
        UUID entityId = buffer.readUniqueId();
        int mask = buffer.readInt();
        return new SteerSledgeMessage(
                entityId,
                (mask & 1) != 0,
                (mask & (1 << 1)) != 0,
                (mask & (1 << 2)) != 0,
                (mask & (1 << 3)) != 0,
                (mask & (1 << 4)) != 0,
                (mask & (1 << 5)) != 0

        );
    }

    public static class SteerSledgeMessage {

        public UUID entityId;
        public boolean forward, back, right, left, boost, fly;

        public SteerSledgeMessage() {

        }

        public SteerSledgeMessage(UUID entityId, boolean forward, boolean back, boolean right, boolean left, boolean boost, boolean fly) {
            this.entityId = entityId;
            this.forward = forward;
            this.back = back;
            this.right = right;
            this.left = left;
            this.boost = boost;
            this.fly = fly;
        }
    }
}
