package fr.luclyoko.crystaliauhc.modules;

import fr.luclyoko.crystaliauhc.Main;
import fr.luclyoko.crystaliauhc.commands.*;

public class Commands {
    private final Main main;

    public Commands(Main main) {
        this.main = main;
    }

    public void registerAll() {
        main.getCommand("map").setExecutor(new MapCommand(main));
        main.getCommand("admin").setExecutor(new AdminCommand(main));
        main.getCommand("dev").setExecutor(new DevCommand(main));
        main.getCommand("host").setExecutor(new HostCommand(main));
        main.getCommand("force").setExecutor(new ForceCommand(main));
        main.getCommand("alarm").setExecutor(new AlarmCommand(main));
    }
}
