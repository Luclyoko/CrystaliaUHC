package fr.luclyoko.crystaliauhc.modules;

import fr.luclyoko.crystaliauhc.Main;
import fr.luclyoko.crystaliauhc.listeners.MobsListeners;
import fr.luclyoko.crystaliauhc.listeners.PlayersListeners;

public class Listeners {

    private final Main main;

    public Listeners(Main main) {
        this.main = main;
    }

    public void registerAll() {
        main.getServer().getPluginManager().registerEvents(new PlayersListeners(main), main);
        main.getServer().getPluginManager().registerEvents(new MobsListeners(main), main);
    }
}
