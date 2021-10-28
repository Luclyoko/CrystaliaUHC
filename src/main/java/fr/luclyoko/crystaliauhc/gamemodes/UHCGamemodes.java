package fr.luclyoko.crystaliauhc.gamemodes;

public enum UHCGamemodes {
    VANILLA("Vanilla", "vanilla"),
    BLEACH("BleachUHC", "bleachuhc");

    private final String displayName;
    private final String rawName;

    UHCGamemodes(String displayName, String rawName) {
        this.displayName = displayName;
        this.rawName = rawName;
    }
}
