package fr.luclyoko.crystaliauhc.guis;

import fr.luclyoko.crystaliauhc.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public interface GuiBuilder {
    Main main = Main.getInstance();
    String name();
    int getSize();
    void contents(Player player, Inventory inv);
    void onClick(Player player, Inventory inv, ItemStack current, int slot, ClickType clickType);
}