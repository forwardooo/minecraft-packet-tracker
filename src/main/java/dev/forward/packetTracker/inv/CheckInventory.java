package dev.forward.packetTracker.inv;

import dev.forward.packetTracker.PacketTracker;
import dev.forward.packetTracker.PlayerPacketList;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Getter
public class CheckInventory {
    private final PlayerPacketList packetList;
    private final Player pl;
    private Inventory inventory;
    private int page = 1;
    private int totalPages;

    public CheckInventory(PlayerPacketList packetList, Player pl) {
        this.packetList = packetList;
        this.pl = pl;
    }

    public void open() {
        this.inventory = Bukkit.createInventory(null, 54, String.format("Список пакетов (Стр. %s)", page));
        int packetCount = packetList.getData().size();
        totalPages = (int) Math.ceil((double) packetCount / 45);
        PacketTracker.getInstance().getInventories().add(this);
        pl.openInventory(inventory);
        int startIndex = (page - 1) * 45;
        int endIndex = (page - 1) * 45 + 45;
        if (packetCount < endIndex) {
            endIndex = packetCount;
        }
        for (int i = startIndex; i < endIndex; i++) {
            PlayerPacketList.PacketData pd = packetList.getData().get(i);
            ItemStack item = new ItemStack(Material.PAPER);
            ItemMeta meta = item.getItemMeta();
            String time = new SimpleDateFormat("HH:mm:ss.SSS").format(new Date(pd.acceptTime()));
            PersistentDataContainer data = meta.getPersistentDataContainer();
            data.set(NamespacedKey.randomKey(), PersistentDataType.STRING, time);
            meta.setDisplayName(ChatColor.YELLOW + pd.packetName());
            meta.setLore(List.of(
                    String.format("§6Пакет #1 §7(%s)", packetList.getPlayer().getDisplayName()),
                    "§7————————————",
                    String.format("§6Время отправки: %s", time)
            ));

            item.setItemMeta(meta);
            inventory.addItem(item);
        }
        if (page > 1) {
            inventory.setItem(48,createButton(Material.ARROW, "§a← Назад"));
        }
        if (page < totalPages) {
            inventory.setItem(50, createButton(Material.ARROW, "§aВперёд →"));
        }
        inventory.setItem(49, createButton(Material.COMPASS, "§eОбновить ⟳"));
    }

    public void update(InventoryClickEvent event) {
        event.setCancelled(true);
        if (event.getRawSlot() == 48 && event.getClickedInventory().getItem(48) != null && page > 0) {
            page--;
            open();
        } else if (event.getRawSlot() == 50 && event.getClickedInventory().getItem(50) != null && page < totalPages) {
            page++;
            open();
        } else if (event.getRawSlot() == 49 && event.getClickedInventory().getItem(49) != null) {
            open();
        }
    }

    private ItemStack createButton(Material mat, String name) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }
}
