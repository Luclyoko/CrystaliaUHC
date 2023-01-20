package fr.luclyoko.crystaliauhc.gamemodes.arena.powers;

import fr.luclyoko.crystaliauhc.Main;
import fr.luclyoko.crystaliauhc.gamemodes.arena.ArenaUHC;

public class ArenaPowerManager {

    private final ArenaUHC arenaUHC;
    private final Main main;

    private int powerID;

    public ArenaPowerManager(ArenaUHC arenaUHC, Main main) {
        this.arenaUHC = arenaUHC;
        this.main = main;
        this.powerID = 0;
    }

    public int getNewPowerID() {
        return ++powerID;
    }
}
