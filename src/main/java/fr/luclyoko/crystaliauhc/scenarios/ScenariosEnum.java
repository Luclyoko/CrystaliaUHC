package fr.luclyoko.crystaliauhc.scenarios;

import org.bukkit.Material;

public enum ScenariosEnum {
    DIAMOND_LIMIT(1, new DiamondLimit(), Material.DIAMOND),
    CUTCLEAN(2, new Cutclean(), Material.IRON_ORE),
    HASTEY_BOYS(3, new HasteyBoys(), Material.DIAMOND_PICKAXE),
    HASTEY_BABIES(4, new HasteyBabies(), Material.STONE_PICKAXE),
    SAFE_MINERS(5, new SafeMiners(), Material.ROTTEN_FLESH);

    private final int id;

    private final Scenario scenarioClass;

    private final Material displayItem;

    ScenariosEnum(int id, Scenario scenarioClass, Material displayItem) {
        this.id = id;
        this.scenarioClass = scenarioClass;
        this.displayItem = displayItem;
    }

    public Scenario getScenarioClass() {
        return this.scenarioClass;
    }

    public int getId() {
        return this.id;
    }

    public Material getDisplayItem() {
        return this.displayItem;
    }
}
