package fr.luclyoko.crystaliauhc.gamemodes.arena.roles;

public enum RoleGamemodeEnum {
    LGUHC("Loup-Garou"),
    INAZUMA("Inazuma Eleven"),
    DEMON_SLAYER("Demon Slayer"),
    LOTR("Lord Of The Rings"),
    REBORN("Reborn");

    private String name;


    RoleGamemodeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
