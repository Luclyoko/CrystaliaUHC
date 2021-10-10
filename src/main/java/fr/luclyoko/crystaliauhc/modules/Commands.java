package fr.luclyoko.crystaliauhc.modules;

import fr.luclyoko.crystaliauhc.Main;
import fr.luclyoko.crystaliauhc.commands.MapCommand;

public class Commands {

    private final Main main;

    public Commands(Main main) {
        this.main = main;
    }

    public void registerAll() {
        main.getCommand("map").setExecutor(new MapCommand());
    }
}
