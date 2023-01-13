package fr.luclyoko.crystaliauhc.guis.hostguis.enchantsguis;

import fr.luclyoko.crystaliauhc.guis.GuiBuilder;
import fr.luclyoko.crystaliauhc.utils.Enchants;
import fr.luclyoko.crystaliauhc.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Optional;

public class EnchantEditGui implements GuiBuilder {
    public String name() {
        return "§dÉdition des enchantements";
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
        inv.setItem(3, player.getItemInHand());
        inv.setItem(5, (new ItemBuilder(Material.ENCHANTED_BOOK))
                .setDisplayName("§dUnbreakable: §5" + (player.getItemInHand().getItemMeta().spigot().isUnbreakable() ? "§a§l✔" : "§c§l✘"))
                .setLore(Arrays.asList(" ", "§8» §7Clic gauche | " + (player.getItemInHand().getItemMeta().spigot().isUnbreakable() ? "§c§lRetirer" : "§a§lAppliquer"))).addEnchant(Enchantment.DURABILITY, 1)
                .addItemFlags(ItemFlag.HIDE_ENCHANTS)
                .toItemStack());
        for (Enchantment enchantment : main.getGameManager().getGameSettings().getEnchantsLimit().keySet()) {
            inv.addItem((new ItemBuilder(Material.ENCHANTED_BOOK))
                    .setDisplayName("§d" + Enchants.getDisplayName(enchantment) + ": §5" + (player.getItemInHand().getEnchantments().containsKey(enchantment) ? player.getItemInHand().getItemMeta().getEnchantLevel(enchantment) : 0))
                    .setAmount(player.getItemInHand().getEnchantments().containsKey(enchantment) ? player.getItemInHand().getItemMeta().getEnchantLevel(enchantment) : 0)
                    .setLore(Arrays.asList(" ", "§8» §7Clic gauche | §a§l+1", "§8» §7Clic droit | §c§l-1")).addEnchant(enchantment, 1)
                    .addItemFlags(ItemFlag.HIDE_ENCHANTS)
                    .toItemStack());
        }
        inv.setItem(getSize() - 1, (new ItemBuilder(Material.BARRIER))
                .setDisplayName("§cFermer le menu")
                .setLore(Arrays.asList(" ", "§8» §7Clic gauche | §aFermer")).toItemStack());
    }

        public void onClick(Player player, Inventory inv, ItemStack current, int slot, ClickType clickType) {
            if (current.getType().equals(Material.ENCHANTED_BOOK)) {
                if (current.getItemMeta().getDisplayName().contains("Unbreakable")) {
                    player.getInventory().setItem(player.getInventory().getHeldItemSlot(), (new ItemBuilder(player.getItemInHand())).setUnbreakable(!player.getItemInHand().getItemMeta().spigot().isUnbreakable()).toItemStack());
                } else {
                    Optional<Enchantment> opEnchantment = current.getItemMeta().getEnchants().keySet().stream().findFirst();
                    if (opEnchantment.isPresent()) {
                        Enchantment enchantment = opEnchantment.get();
                        int i = 0;
                        if (player.getItemInHand().getEnchantments().containsKey(enchantment))
                            i = player.getItemInHand().getEnchantmentLevel(enchantment);
                        if (clickType.isLeftClick()) {
                            player.getInventory().setItem(player.getInventory().getHeldItemSlot(), (new ItemBuilder(player.getItemInHand())).addEnchant(enchantment, ++i).toItemStack());
                        } else if (clickType.isRightClick()) {
                            if (i > 1) {
                                player.getInventory().setItem(player.getInventory().getHeldItemSlot(), (new ItemBuilder(player.getItemInHand())).addEnchant(enchantment, --i).toItemStack());
                            } else {
                                player.getInventory().setItem(player.getInventory().getHeldItemSlot(), (new ItemBuilder(player.getItemInHand())).removeEnchant(enchantment).toItemStack());
                            }
                        }
                    }
                }
                main.getGuiManager().open(player, getClass());
            } else if (current.getType().equals(Material.BARRIER)) {
                player.closeInventory();
            }
        }
    }
