package fr.luclyoko.crystaliauhc.modules;

import fr.luclyoko.crystaliauhc.Main;
import fr.luclyoko.crystaliauhc.commands.AdminCommand;
import fr.luclyoko.crystaliauhc.commands.DevCommand;
import fr.luclyoko.crystaliauhc.commands.ForceCommand;
import fr.luclyoko.crystaliauhc.commands.HostCommand;
import fr.luclyoko.crystaliauhc.commands.MapCommand;
import org.bukkit.command.CommandExecutor;

public class Commands {
    private final Main main;

    public Commands(Main main) {
        this.main = main;
    }

    public void registerAll() {
        this.main.getCommand("map").setExecutor(new MapCommand(this.main));
        this.main.getCommand("admin").setExecutor(new AdminCommand(this.main));
        this.main.getCommand("dev").setExecutor(new DevCommand(this.main));
        this.main.getCommand("host").setExecutor(new HostCommand(this.main));
        this.main.getCommand("force").setExecutor(new ForceCommand(this.main));
    }
}
