package fr.luclyoko.crystaliauhc.utils;

import org.bukkit.enchantments.Enchantment;

public enum Enchants {
    PROTECTION("Protection", Enchantment.PROTECTION_ENVIRONMENTAL),
    FIRE_PROTECTION("Fire Protection", Enchantment.PROTECTION_FIRE),
    FEATHER_FALLING("Feather Falling", Enchantment.PROTECTION_FALL),
    BLAST_PROTECTION("Blast Protection", Enchantment.PROTECTION_EXPLOSIONS),
    PROJECTILE_PROTECTION("Projectile Protection", Enchantment.PROTECTION_PROJECTILE),
    RESPIRATION("Respiration", Enchantment.OXYGEN),
    AQUA_AFFINITY("Aqua Affinity", Enchantment.WATER_WORKER),
    THORNS("Thorns", Enchantment.THORNS),
    DEPTH_STRIDER("Depth Strider", Enchantment.DEPTH_STRIDER),
    SHARPNESS("Sharpness", Enchantment.DAMAGE_ALL),
    SMITE("Smite", Enchantment.DAMAGE_UNDEAD),
    BANE_OF_ARTHROPODS("Bane of Arthropods", Enchantment.DAMAGE_ARTHROPODS),
    KNOCKBACK("Knockback", Enchantment.KNOCKBACK),
    FIRE_ASPECT("Fire Aspect", Enchantment.FIRE_ASPECT),
    LOOTING("Looting", Enchantment.LOOT_BONUS_MOBS),
    EFFICIENCY("Efficiency", Enchantment.DIG_SPEED),
    SILK_TOUCH("Silk Touch", Enchantment.SILK_TOUCH),
    UNBREAKING("Unbreaking", Enchantment.DURABILITY),
    FORTUNE("Fortune", Enchantment.LOOT_BONUS_BLOCKS),
    POWER("Power", Enchantment.ARROW_DAMAGE),
    PUNCH("Punch", Enchantment.ARROW_KNOCKBACK),
    FLAME("Flame", Enchantment.ARROW_FIRE),
    INFINITY("Infinity", Enchantment.ARROW_INFINITE),
    LUCK_OF_THE_SEA("Luck of the Sea", Enchantment.LUCK),
    LURE("Lure", Enchantment.LURE);

    private String displayName;

    private Enchantment enchantment;

    Enchants(String displayName, Enchantment enchantment) {
        this.displayName = displayName;
        this.enchantment = enchantment;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public Enchantment getEnchantment() {
        return this.enchantment;
    }

    public static String getDisplayName(Enchantment enchantment) {
        for (Enchants enchants : values()) {
            if (enchantment.equals(enchants.getEnchantment()))
                return enchants.getDisplayName();
        }
        return null;
    }
}
