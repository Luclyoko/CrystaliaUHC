package fr.luclyoko.crystaliauhc.guis.hostguis.enchantsguis;

import fr.luclyoko.crystaliauhc.guis.GuiBuilder;
import fr.luclyoko.crystaliauhc.guis.hostguis.HostMainGui;
import fr.luclyoko.crystaliauhc.utils.Enchants;
import fr.luclyoko.crystaliauhc.utils.ItemBuilder;
import java.util.Arrays;
import java.util.Optional;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class EnchantLimitGui implements GuiBuilder {
    public String name() {
        return "§dLimites d'enchantements";
    }

    public int getSize() {
        return 54;
    }

    public void contents(Player player, Inventory inv) {
        for (int i = 0; i < getSize(); i++) {
            if ((i <= 8 || i >= getSize() - 9 || i % 9 == 0 || i % 9 == 8) && i !=

                    getSize() - 1)
                inv.setItem(i, (new ItemBuilder(Material.STAINED_GLASS_PANE, (byte) 10))
                        .setAmount(1)
                        .setDisplayName(" ")
                        .toItemStack());
        }
        for (Enchantment enchantment : main.getGameManager().getGameSettings().getEnchantsLimit().keySet()) {
            inv.addItem((new ItemBuilder(Material.ENCHANTED_BOOK))
                    .setDisplayName("§d" + Enchants.getDisplayName(enchantment) + ": §5" + main.getGameManager().getGameSettings().getEnchantLimit(enchantment))
                    .setAmount(main.getGameManager().getGameSettings().getEnchantLimit(enchantment))
                    .setLore(Arrays.asList(" ", "§8» §7Clic gauche | §a§l+1", "§8» §7Clic droit | §c§l-1")).addEnchant(enchantment, 1)
                    .addItemFlags(ItemFlag.HIDE_ENCHANTS)
                    .toItemStack());
        }
        inv.setItem(getSize() - 1, (new ItemBuilder(Material.ARROW))
                .setAmount(1)
                .setDisplayName("§cRetour en arrière")
                .setLore(Arrays.asList(" ", "§8» §7Clic gauche | §aRetour")).toItemStack());
    }

        public void onClick(Player player, Inventory inv, ItemStack current, int slot, ClickType clickType) {
            if (current.getType().equals(Material.ENCHANTED_BOOK)) {
                Optional<Enchantment> opEnchantment = current.getItemMeta().getEnchants().keySet().stream().findFirst();
                if (opEnchantment.isPresent()) {
                    Enchantment enchantment = opEnchantment.get();
                    if (clickType.isLeftClick()) {
                        main.getGameManager().getGameSettings().increaseEnchantLimit(enchantment);
                    } else if (clickType.isRightClick()) {
                        main.getGameManager().getGameSettings().decreaseEnchantLimit(enchantment);
                    }
                }
                main.getGuiManager().open(player, getClass());
            } else if (current.getType().equals(Material.ARROW)) {
                main.getGuiManager().open(player, HostMainGui.class);
            }
        }
    }
