package dev.forward.packetTracker;

import dev.forward.packetTracker.command.CommandModule;
import dev.forward.packetTracker.inv.CheckInventory;
import dev.forward.packetTracker.network.NetworkModule;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
@Getter
public final class PacketTracker extends JavaPlugin implements Listener {
    public static final Logger LOGGER = Logger.getLogger("PlayerPacketTracker");
    private final Map<Player, PlayerPacketList> playerPacketMap = new HashMap<>();
    private final List<CheckInventory> inventories = new ArrayList<>();
    private final CommandModule cmd = new CommandModule();
    private final NetworkModule networkModule = new NetworkModule();

    @Override
    public void onEnable() {
        instance = this;
        cmd.load();
        networkModule.load();

        getServer().getPluginManager().registerEvents(this, this);
    }

    @Getter
    private static PacketTracker instance;

    @Override
    public void onDisable() {

    }

    public PlayerPacketList getPlayerPacketList(Player pl) {
        return playerPacketMap.get(pl);
    }
}
