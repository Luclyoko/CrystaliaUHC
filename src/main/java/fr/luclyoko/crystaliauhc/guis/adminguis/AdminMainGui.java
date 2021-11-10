package fr.luclyoko.crystaliauhc.guis.adminguis;

import fr.luclyoko.crystaliauhc.guis.GuiBuilder;
import fr.luclyoko.crystaliauhc.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class AdminMainGui implements GuiBuilder {
    @Override
    public String name() {
        return "§cPanel d'administration";
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
                inv.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, (byte) 14)
                        .setAmount(1)
                        .setDisplayName(" ")
                        .toItemStack());
            }
        }

        inv.setItem(10,
                new ItemBuilder(Material.SKULL_ITEM, (byte) 3)
                        .setAmount(1)
                        .setDisplayName("§6Gestion des joueurs")
                        .setLore(Arrays.asList(" ",
                                "§8» §7Clic gauche | §cOuvrir"))
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
            case SKULL_ITEM:
                main.getGuiManager().open(player, PlayerManagerGui.class);

            case ARROW:
                player.closeInventory();
                player.sendMessage(main.getGameManager().getGamemodeUhc().getDisplayNameChat() + "§aLes paramètres ont bien été sauvegardés.");
                break;

            default:
                break;
        }
    }
}
