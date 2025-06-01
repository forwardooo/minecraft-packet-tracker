package dev.forward.packetTracker.command.impl;

import dev.forward.packetTracker.PacketTracker;
import dev.forward.packetTracker.command.AbstractCommand;
import dev.forward.packetTracker.command.Arguments;
import dev.forward.packetTracker.inv.CheckInventory;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CheckCommand extends AbstractCommand {
    public CheckCommand() {
        super("check", false);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, Command command, Arguments args) {
        if (args.isEmpty()) {
            sender.sendMessage("Введите ник игрока!");
            return false;
        }
        for (Player player1 : Bukkit.getOnlinePlayers()) {
            if (player1.getName().equalsIgnoreCase(args.get(0))) {
                CheckInventory inv = new CheckInventory(PacketTracker.getInstance().getPlayerPacketList(player1), (Player) sender);
                inv.open();
                return true;
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, Command command, Arguments args) {
        return Bukkit.getOnlinePlayers().stream().map(Player::getName).toList();
    }
}
