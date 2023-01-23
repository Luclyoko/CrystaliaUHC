package fr.luclyoko.crystaliauhc.gamemodes.arena.powers;

import org.bukkit.Material;

public enum ArenaPowerEnum {
    SOUFFLE_DE_LA_FOUDRE("Souffle de la Foudre", "§6", Material.NETHER_STAR, ThunderBreath.class),
    VITESSE_EXTREME("Vitesse Extrême", "§e", Material.NETHER_STAR, ExtremeSpeed.class),
    FRAPPE_FOUDROYANTE("Frappe Foudroyante", "§e", Material.NETHER_STAR, ThunderingHit.class),
    DISPERSION("Dispersion", "§e", Material.NETHER_STAR, Dispersion.class),
    DANSE_DU_DIEU_DU_FEU("Danse du Dieu du Feu", "§6", Material.BLAZE_ROD, FireGodDance.class),
    VITESSE("Vitesse", "§b", Material.NETHER_STAR, Vitesse.class),
    FUMIGENE("Fumigène", "§6", Material.NETHER_STAR, Fumigene.class),
    DOMA("Doma", "§5", Material.QUARTZ, DomaSteal.class),
    FLASH("Flash", "§e", Material.NETHER_STAR, Flash.class),
    RAGE_DE_VAINCRE("Rage de Vaincre", "§4", Material.BLAZE_POWDER, FightingSpirit.class),
    TORNADE_DE_FEU("Tornade de Feu", "§6", Material.MAGMA_CREAM, FireTornado.class),
    MAIN_CELESTE("Main Céleste", "§e", Material.GLOWSTONE_DUST, CelestialHand.class),
    TIR_HELICOPTERE("Tir Hélicoptère", "§6", Material.NETHER_STAR, HelicopterShot.class),
    TITAN_ASSAILLANT("Titan Assaillant", "§e", Material.FEATHER, AttackingTitan.class),
    TITAN_BESTIAL("Titan Bestial", "§4", Material.FEATHER, BeastTitan.class),
    LIVAI_BOOSTER("Booster", "§f", Material.SUGAR, LivaiBooster.class),
    MIKASA_BOOSTER("Booster", "§f", Material.SUGAR, MikasaBooster.class),
    KIRA_DEATH_NOTE("Death Note", "§c", Material.BOOK, KiraDeathNote.class),
    MISA_DEATH_NOTE("Death Note", "§c", Material.BOOK, MisaDeathNote.class),
    JINKI("Jinki", "§c", Material.LEASH, Jinki.class),
    NUEES_ARDENTES("Nuées Ardentes", "§b", Material.NETHER_STAR, FieryClouds.class);


    private String name;
    private String color;
    private Material material;
    private Class<? extends ArenaPower> powerClass;

    ArenaPowerEnum(String name,
                   String color,
                   Material material,
                   Class<? extends ArenaPower> powerClass) {
        this.name = name;
        this.color = color;
        this.material = material;
        this.powerClass = powerClass;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public Material getMaterial() {
        return material;
    }

    public Class<? extends ArenaPower> getPowerClass() {
        return powerClass;
    }
}
