package fr.luclyoko.crystaliauhc.gamemodes;

import fr.luclyoko.crystaliauhc.gamemodes.bleach.BleachUHC;
import fr.luclyoko.crystaliauhc.gamemodes.vanilla.VanillaUHC;

public enum UHCGamemodes {
    VANILLA("Vanilla", "vanilla", VanillaUHC.class),
    BLEACH("BleachUHC", "bleachuhc", BleachUHC.class);

    private final String displayName;
    private final String rawName;
    private final Class<? extends GamemodeUHC> gamemodeClass;

    UHCGamemodes(String displayName,
                 String rawName,
                 Class<? extends GamemodeUHC> gamemodeClass) {
        this.displayName = displayName;
        this.rawName = rawName;
        this.gamemodeClass = gamemodeClass;
    }
}
