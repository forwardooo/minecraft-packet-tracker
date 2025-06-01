package dev.forward.packetTracker.command;

import dev.forward.packetTracker.AbstractModule;
import dev.forward.packetTracker.PacketTracker;
import dev.forward.packetTracker.command.impl.CheckCommand;

import java.util.ArrayList;
import java.util.List;

public class CommandModule extends AbstractModule {
    private final List<AbstractCommand> commands = new ArrayList<>();

    public void load() {
        commands.add(
                new CheckCommand()
        );
        commands.forEach((cmd) -> cmd.register(PacketTracker.getInstance()));
    }

    public void unload() {

    }
}
