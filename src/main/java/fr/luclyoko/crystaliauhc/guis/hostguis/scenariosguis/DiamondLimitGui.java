package fr.luclyoko.crystaliauhc.guis.hostguis.scenariosguis;

import fr.luclyoko.crystaliauhc.guis.GuiBuilder;
import fr.luclyoko.crystaliauhc.scenarios.DiamondLimit;
import fr.luclyoko.crystaliauhc.scenarios.ScenariosEnum;
import fr.luclyoko.crystaliauhc.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class DiamondLimitGui implements GuiBuilder {

    DiamondLimit diamondLimit = (DiamondLimit) ScenariosEnum.DIAMOND_LIMIT.getScenarioClass();

    @Override
    public String name() {
        return "§3DiamondLimit";
    }

    @Override
    public int getSize() {
        return 3*9;
    }

    @Override
    public void contents(Player player, Inventory inv) {
        for (int i = 0; i < getSize(); i++) {
            if (((i <= 8 || i >= getSize() - 9) ||
                    i % 9 == 0 || i % 9 == 8) &&
                    i != getSize() - 1) {
                inv.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, (byte) 9)
                        .setAmount(1)
                        .setDisplayName(" ")
                        .toItemStack());
            }
        }

        inv.setItem(11, new ItemBuilder(Material.WOOL, (byte) 14)
                .setAmount(1)
                .setDisplayName("§c§l-1")
                .setLore(Arrays.asList(" ",
                        "§8» §7Clic gauche | §cRetirer 1"))
                .toItemStack());

        inv.setItem(13, new ItemBuilder(Material.DIAMOND)
                .setAmount(diamondLimit.getDiamondLimit())
                .setDisplayName("§3Valeur actuelle: §b" + diamondLimit.getDiamondLimit())
                .toItemStack());

        inv.setItem(15, new ItemBuilder(Material.WOOL, (byte) 5)
                .setAmount(1)
                .setDisplayName("§a§l+1")
                .setLore(Arrays.asList(" ",
                        "§8» §7Clic gauche | §aAjouter 1"))
                .toItemStack());

        inv.setItem(getSize() - 1, new ItemBuilder(Material.ARROW)
                .setAmount(1)
                .setDisplayName("§cFermer le menu")
                .setLore(Arrays.asList(" ",
                        "§8» §7Clic gauche | §aFermer"))
                .toItemStack());
    }

    @Override
    public void onClick(Player player, Inventory inv, ItemStack current, int slot, ClickType clickType) {
        if (slot == 11) {
            if (diamondLimit.getDiamondLimit() == 0) return;
            diamondLimit.setDiamondLimit(diamondLimit.getDiamondLimit() - 1);
            main.getGuiManager().open(player, this.getClass());
            return;
        } else if (slot == 15) {
            diamondLimit.setDiamondLimit(diamondLimit.getDiamondLimit() + 1);
            main.getGuiManager().open(player, this.getClass());
            return;
        }

        if (current.getType().equals(Material.ARROW)) {
            player.closeInventory();
            player.sendMessage(main.getGameManager().getGamemodeUhc().getDisplayNameChat() + "§aLes paramètres ont bien été sauvegardés.");
        }
    }
}
