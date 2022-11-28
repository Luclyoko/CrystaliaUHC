package fr.luclyoko.crystaliauhc.guis.devguis;

import fr.luclyoko.crystaliauhc.guis.GuiBuilder;
import fr.luclyoko.crystaliauhc.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class DevMainGui implements GuiBuilder {

    @Override
    public String name() {
        return "§2Panel de développement";
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
                inv.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, (byte) 13)
                        .setAmount(1)
                        .setDisplayName(" ")
                        .toItemStack());
            }
        }

        inv.setItem(10, new ItemBuilder(Material.COMMAND)
                .setAmount(1)
                .setDisplayName("§6Mode développement")
                .setLore(Arrays.asList(" ",
                        "§8» §7État: ".concat(main.getGameManager().getGameSettings().isDevMode() ? "§a§lActivé" : "§c§lDésactivé"),
                        " ",
                        main.getGameManager().getGameSettings().isDevMode() ? "§8» §7Clic gauche | §cDésactiver" : "§8» §7Clic gauche | §aActiver"))
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
        switch (current.getType()) {
            case COMMAND:
                main.getGameManager().getGameSettings().setDevMode(!main.getGameManager().getGameSettings().isDevMode());
                main.getGuiManager().open(player, getClass());
                break;

            case BARRIER:
                player.closeInventory();
                break;
        }
    }
}
