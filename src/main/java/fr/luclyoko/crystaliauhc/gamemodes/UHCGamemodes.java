package fr.luclyoko.crystaliauhc.gamemodes;

import fr.luclyoko.crystaliauhc.gamemodes.arena.ArenaUHC;
import fr.luclyoko.crystaliauhc.gamemodes.kwamiuhc.KwamiUHC;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.LGUHC;
import fr.luclyoko.crystaliauhc.gamemodes.vanilla.VanillaUHC;
import org.bukkit.Material;

public enum UHCGamemodes {
    VANILLA("Classique", "vanilla", Material.GOLDEN_APPLE, (Class)VanillaUHC.class),
    ARENA("CrystArena", "arenauhc", Material.DIAMOND_CHESTPLATE, ArenaUHC.class),
    LG_UHC("LG UHC (WIP)", "lguhc", Material.BONE, (Class)LGUHC.class),
    KWAMI_UHC("Kwami UHC (WIP)", "kwamiuhc", Material.HUGE_MUSHROOM_2, (Class)KwamiUHC.class);

    private final String displayName;

    private final String rawName;

    private final Material displayItem;

    private final Class<? extends GamemodeUHC> gamemodeClass;

    UHCGamemodes(String displayName, String rawName, Material displayItem, Class<? extends GamemodeUHC> gamemodeClass) {
        this.displayName = displayName;
        this.rawName = rawName;
        this.displayItem = displayItem;
        this.gamemodeClass = gamemodeClass;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public Material getDisplayItem() {
        return this.displayItem;
    }

    public Class<? extends GamemodeUHC> getGamemodeClass() {
        return this.gamemodeClass;
    }
}
