package fr.luclyoko.crystaliauhc.gamemodes.arena.roles;

public enum RoleGamemodeEnum {
    LGUHC("Loup-Garou"),
    INAZUMA("Inazuma Eleven"),
    DEMON_SLAYER("Demon Slayer"),
    DISNEY("Disney"),
    LOTR("Lord Of The Rings"),
    AOT("Attack On Titan"),
    REBORN("Reborn"),
    DEATH_NOTE("Death Note"),
    NORAGAMI("Noragami"),
    NARUTO("Naruto");

    private String name;


    RoleGamemodeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
