package dev.forward.packetTracker;

import dev.forward.packetTracker.inv.CheckInventory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.List;

public class PluginEventHandler implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        List<CheckInventory> inventories = PacketTracker.getInstance().getInventories();
        for (CheckInventory inv : inventories) {
            if (event.getClickedInventory() == null) continue;
            if (event.getClickedInventory().equals(inv.getInventory())) {
                inv.update(event);
                break;
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        List<CheckInventory> inventories = PacketTracker.getInstance().getInventories();
        for (CheckInventory inv : inventories) {
            if (event.getInventory().equals(inv.getInventory())) {
                inventories.remove(inv);
                break;
            }
        }
    }
}
