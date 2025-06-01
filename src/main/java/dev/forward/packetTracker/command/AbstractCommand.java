package dev.forward.packetTracker.command;
import dev.forward.packetTracker.PacketTracker;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public abstract class AbstractCommand implements CommandExecutor, TabCompleter {
    private final String commandName;
    private final boolean consoleAccess;

    public AbstractCommand(String commandName, boolean consoleAccess) {
        this.commandName = commandName;
        this.consoleAccess = consoleAccess;
    }

    public void register(JavaPlugin plugin) {
        PluginCommand command = plugin.getCommand(commandName);
        if (command != null) {
            command.setExecutor(this);
            command.setTabCompleter(this);
        } else {
            PacketTracker.LOGGER.warning(String.format("Command not %s not exists!", commandName));
        }
    }

    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String string, String[] strings) {
        Arguments args = new Arguments(strings);

        if(!(commandSender instanceof Player) && !consoleAccess) {
            commandSender.sendMessage("Cannot execute this command as console!");
            return false;
        }
        return onCommand(commandSender, command, command, args);
    }

    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, String[] strings) {
        Arguments args = new Arguments(strings);
        return onTabComplete(commandSender, command, command, args);
    }

    public abstract boolean onCommand(CommandSender sender, Command cmd, Command command, Arguments args);

    public abstract List<String> onTabComplete(CommandSender sender, Command cmd, Command command, Arguments args);
}
