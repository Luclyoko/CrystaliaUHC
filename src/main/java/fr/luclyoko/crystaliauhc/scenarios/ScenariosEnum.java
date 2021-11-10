package fr.luclyoko.crystaliauhc.scenarios;

import org.bukkit.Material;

public enum ScenariosEnum {
    DIAMOND_LIMIT(1,
            new DiamondLimit(),
            Material.DIAMOND);

    private int id;
    private Scenario scenarioClass;
    private Material displayItem;

    ScenariosEnum(int id,
                  Scenario scenarioClass,
                  Material displayItem) {
        this.id = id;
        this.scenarioClass = scenarioClass;
        this.displayItem = displayItem;
    }

    public Scenario getScenarioClass() {
        return scenarioClass;
    }

    public int getId() {
        return id;
    }

    public Material getDisplayItem() {
        return displayItem;
    }
}
