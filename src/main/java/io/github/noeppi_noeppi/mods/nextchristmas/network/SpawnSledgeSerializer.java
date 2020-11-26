package io.github.noeppi_noeppi.mods.nextchristmas.network;

import io.github.noeppi_noeppi.libx.network.PacketSerializer;
import net.minecraft.network.PacketBuffer;

import java.util.UUID;

public class SpawnSledgeSerializer implements PacketSerializer<SpawnSledgeSerializer.SpawnSledgeMessage> {

    @Override
    public Class<SpawnSledgeMessage> messageClass() {
        return SpawnSledgeMessage.class;
    }

    @Override
    public void encode(SpawnSledgeMessage msg, PacketBuffer buffer) {
        buffer.writeDouble(msg.x);
        buffer.writeDouble(msg.y);
        buffer.writeDouble(msg.z);
        buffer.writeFloat(msg.yaw);
        buffer.writeFloat(msg.pitch);
        buffer.writeInt(msg.entityId);
        buffer.writeUniqueId(msg.uuid);
    }

    @Override
    public SpawnSledgeMessage decode(PacketBuffer buffer) {
        double x = buffer.readDouble();
        double y = buffer.readDouble();
        double z = buffer.readDouble();
        float yaw = buffer.readFloat();
        float pitch = buffer.readFloat();
        int entityId = buffer.readInt();
        UUID uuid = buffer.readUniqueId();
        return new SpawnSledgeMessage(x, y, z, yaw, pitch, entityId, uuid);
    }

    public static class SpawnSledgeMessage {
        public double x, y, z;
        public float yaw, pitch;
        public int entityId;
        public UUID uuid;

        public SpawnSledgeMessage() {

        }

        public SpawnSledgeMessage(double x, double y, double z, float yaw, float pitch, int entityId, UUID uuid) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.yaw = yaw;
            this.pitch = pitch;
            this.entityId = entityId;
            this.uuid = uuid;
        }
    }
}
