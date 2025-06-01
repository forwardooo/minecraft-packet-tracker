package dev.forward.packetTracker;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
@Getter
public class PlayerPacketList {
    private final List<PacketData> data = new ArrayList<>();
    private final Player player;

    public PlayerPacketList(Player player) {
        this.player = player;
    }
    public void update(Object packet) {
        this.data.add(new PacketData(packet.getClass().getSimpleName(),System.currentTimeMillis()));
        this.data.sort(Comparator.comparingLong(PacketData::acceptTime).reversed());
    }

    public record PacketData(String packetName, long acceptTime) {
    }
}
