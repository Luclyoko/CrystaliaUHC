package fr.luclyoko.crystaliauhc.guis;

import fr.luclyoko.crystaliauhc.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;

public class DevGui implements GuiBuilder {

    @Override
    public String name() {
        return "§aPanel de développement";
    }

    @Override
    public int getSize() {
        return 9*5;
    }

    @Override
    public void contents(Player player, Inventory inv) {
        for (int i = 0; i < getSize(); i++) {
            if (((i <= 8 || i >= getSize() - 9) ||
                    i % 9 == 0 || i % 9 == 8) &&
                    i != getSize() - 1) {
                inv.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, (byte) 5)
                        .setAmount(1)
                        .setDisplayName("")
                        .toItemStack());
            }

            inv.setItem(10, new ItemBuilder(Material.COMMAND)
                    .setAmount(1)
                    .setDisplayName("§6Mode développement")
                    .setLore(Collections.singletonList(main.getGameManager().getGameSettings().isDevMode() ? "§7Clic gauche | §c§lDésactiver" : "§7Clic gauche | §a§lActiver"))
                    .toItemStack());

            inv.setItem(getSize() - 1, new ItemBuilder(Material.ARROW)
                    .setAmount(1)
                    .setDisplayName("§cFermer le menu")
                    .setLore(Collections.singletonList("§7Clic gauche | §a§lFermer"))
                    .toItemStack());
        }
    }

    @Override
    public void onClick(Player player, Inventory inv, ItemStack current, int slot, ClickType clickType) {
        switch (current.getType()) {
            case COMMAND:
                if (main.getGameManager().getGameSettings().isDevMode()) {
                    main.getGameManager().getGameSettings().setDevMode(false);
                    main.getGuiManager().open(player, this.getClass());
                } else {
                    main.getGameManager().getGameSettings().setDevMode(true);
                    main.getGuiManager().open(player, this.getClass());
                }
                break;

            case ARROW:
                player.closeInventory();
                player.sendMessage("§aLes paramètres ont bien été sauvegardés.");
                break;

            default:
                break;
        }
    }
}
