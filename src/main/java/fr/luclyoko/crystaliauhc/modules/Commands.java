package fr.luclyoko.crystaliauhc.modules;

import fr.luclyoko.crystaliauhc.Main;
import fr.luclyoko.crystaliauhc.commands.AdminCommand;
import fr.luclyoko.crystaliauhc.commands.DevCommand;
import fr.luclyoko.crystaliauhc.commands.MapCommand;
import org.bukkit.command.SimpleCommandMap;

public class Commands {

    private final Main main;

    public Commands(Main main) {
        this.main = main;
    }

    public void registerAll() {
        main.getCommand("map").setExecutor(new MapCommand());
        main.getCommand("admin").setExecutor(new AdminCommand());
        main.getCommand("dev").setExecutor(new DevCommand());
    }
}
