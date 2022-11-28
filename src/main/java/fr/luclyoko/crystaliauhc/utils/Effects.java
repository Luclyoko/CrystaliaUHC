package fr.luclyoko.crystaliauhc.utils;

import org.bukkit.potion.PotionEffectType;

public enum Effects {
    SPEED("Speed", PotionEffectType.SPEED),
    SLOWNESS("Slowness", PotionEffectType.SLOW),
    HASTE("Haste", PotionEffectType.FAST_DIGGING),
    MINING_FATIGUE("Mining Fatigue", PotionEffectType.SLOW_DIGGING),
    STRENGTH("Strength", PotionEffectType.INCREASE_DAMAGE),
    INSTANT_HEALTH("Instant Health", PotionEffectType.HEAL),
    INSTANT_DAMAGE("Instant Damage", PotionEffectType.HARM),
    JUMP_BOOST("Jump Boost", PotionEffectType.JUMP),
    NAUSEA("Nausea", PotionEffectType.CONFUSION),
    REGENERATION("Regeneration", PotionEffectType.REGENERATION),
    RESISTANCE("Resistance", PotionEffectType.DAMAGE_RESISTANCE),
    FIRE_RESISTANCE("Fire Resistance", PotionEffectType.FIRE_RESISTANCE),
    WATER_BREATHING("Water Breathing", PotionEffectType.WATER_BREATHING),
    INVISIBILITY("Invisibility", PotionEffectType.INVISIBILITY),
    BLINDNESS("Blindness", PotionEffectType.BLINDNESS),
    NIGHT_VISION("Night Vision", PotionEffectType.NIGHT_VISION),
    HUNGER("Hunger", PotionEffectType.HUNGER),
    WEAKNESS("Weakness", PotionEffectType.WEAKNESS),
    POISON("Poison", PotionEffectType.POISON),
    WITHER("Wither", PotionEffectType.WITHER),
    HEALTH_BOOST("Health Boost", PotionEffectType.HEALTH_BOOST),
    ABSORPTION("Absorption", PotionEffectType.ABSORPTION),
    SATURATION("Saturation", PotionEffectType.SATURATION);

    private String displayName;

    private PotionEffectType potionEffectType;

    Effects(String displayName, PotionEffectType potionEffectType) {
        this.displayName = displayName;
        this.potionEffectType = potionEffectType;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public PotionEffectType getPotionEffectType() {
        return this.potionEffectType;
    }

    public static String getDisplayName(PotionEffectType potionEffectType) {
        for (Effects effects : values()) {
            if (potionEffectType.equals(effects.getPotionEffectType()))
                return effects.getDisplayName();
        }
        return null;
    }
}
