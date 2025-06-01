package dev.forward.packetTracker.network;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.events.PacketListener;
import dev.forward.packetTracker.AbstractModule;
import dev.forward.packetTracker.PacketTracker;
import dev.forward.packetTracker.PlayerPacketList;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;

public class NetworkModule extends AbstractModule {
    private PacketListener listener;

    @Override
    public void load() {
        listener = new PacketAdapter(PacketTracker.getInstance(), PacketType.Play.Client.getInstance().values()) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                PlayerPacketList packetList = PacketTracker.getInstance().getPlayerPacketMap().computeIfAbsent(event.getPlayer(), PlayerPacketList::new);
                packetList.update(event.getPacket().getHandle());
            }
        };
        ProtocolLibrary.getProtocolManager().addPacketListener(listener);
    }

    @Override
    public void unload() {
        ProtocolLibrary.getProtocolManager().removePacketListener(listener);
    }
}
