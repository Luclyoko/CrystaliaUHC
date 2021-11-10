package fr.luclyoko.crystaliauhc.guis.hostguis.scenariosguis;

import fr.luclyoko.crystaliauhc.guis.GuiBuilder;
import fr.luclyoko.crystaliauhc.scenarios.Scenario;
import fr.luclyoko.crystaliauhc.scenarios.ScenariosEnum;
import fr.luclyoko.crystaliauhc.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ScenarioManagerGui implements GuiBuilder {

    public Map<Integer, Scenario> scenarioMap = new HashMap<>();

    @Override
    public String name() {
        return "§9Gestion des scénarios";
    }

    @Override
    public int getSize() {
        return 9*9;
    }

    @Override
    public void contents(Player player, Inventory inv) {
        for (int i = 0; i < getSize(); i++) {
            if (((i <= 8 || i >= getSize() - 9) ||
                    i % 9 == 0 || i % 9 == 8) &&
                    i != getSize() - 1) {
                inv.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, (byte) 11)
                        .setAmount(1)
                        .setDisplayName(" ")
                        .toItemStack());
            }
        }

        Arrays.stream(ScenariosEnum.values()).forEach(scenariosEnum -> {
            int scenarioSlot = 9 + scenariosEnum.getId();
            scenarioMap.put(scenarioSlot, scenariosEnum.getScenarioClass());
            inv.setItem(scenarioSlot,
                    new ItemBuilder(scenariosEnum.getDisplayItem())
                            .setAmount(1)
                            .setDisplayName(scenariosEnum.getScenarioClass().getName())
                            .setLore(Arrays.asList(" ",
                                    "§8» §7État: ".concat(scenariosEnum.getScenarioClass().isEnabled() ? "§a§lActivé" : "§c§lDésactivé"),
                                    " ",
                                    scenariosEnum.getScenarioClass().isEnabled() ? "§8» §7Clic gauche | §cDésactiver" : "§8» §7Clic gauche | §aActiver",
                                    (scenariosEnum.getScenarioClass().getConfigGui() != null ? "§8» §7Clic droit | §eConfigurer le scénario" : "")))
                            .toItemStack());
        });

        inv.setItem(getSize() - 1, new ItemBuilder(Material.ARROW)
                .setAmount(1)
                .setDisplayName("§cFermer le menu")
                .setLore(Arrays.asList(" ",
                        "§8» §7Clic gauche | §aFermer"))
                .toItemStack());
    }

    @Override
    public void onClick(Player player, Inventory inv, ItemStack current, int slot, ClickType clickType) {

        if (current.getType() == Material.ARROW) {
            player.closeInventory();
            player.sendMessage(main.getGameManager().getGamemodeUhc().getDisplayNameChat() + "§aLes paramètres ont bien été sauvegardés.");
        }

        scenarioMap.keySet().forEach(scenarioSlot -> {
            Scenario scenario = scenarioMap.get(scenarioSlot);
            if (scenarioSlot == slot) {
                if (clickType.isLeftClick()) {
                    if (scenario.isEnabled()) {
                        scenario.setEnabled(false);
                        main.getGuiManager().open(player, this.getClass());
                    } else {
                        scenario.setEnabled(true);
                        main.getGuiManager().open(player, this.getClass());
                    }
                } else if (clickType.isRightClick()) {
                    if (scenario.getConfigGui() != null) main.getGuiManager().open(player, scenario.getConfigGui());
                }
            }
        });
    }
}
