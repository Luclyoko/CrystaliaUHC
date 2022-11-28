package fr.luclyoko.crystaliauhc.guis.hostguis;

import fr.luclyoko.crystaliauhc.guis.GuiBuilder;
import fr.luclyoko.crystaliauhc.utils.ItemBuilder;
import java.util.Arrays;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionType;

public class EffectsConfigGui implements GuiBuilder {
    public String name() {
        return "§dConfiguration des effets";
    }

    public int getSize() {
        return 27;
    }

    public void contents(Player player, Inventory inv) {
        for (int i = 0; i < getSize(); i++) {
            if ((i <= 8 || i >= getSize() - 9 || i % 9 == 0 || i % 9 == 8) && i !=

                    getSize() - 1)
                inv.setItem(i, (new ItemBuilder(Material.STAINED_GLASS_PANE, (byte)10))
                        .setAmount(1)
                        .setDisplayName(" ")
                        .toItemStack());
        }
        inv.setItem(12, (new ItemBuilder(Material.POTION))
                .setPotionMeta(PotionType.STRENGTH, false)
                .setDisplayName("§4Force: §c" + main.getGameManager().getGameSettings().getStrengthPercent() + "§4%")
                .addItemFlags(ItemFlag.HIDE_POTION_EFFECTS)
                .toItemStack());
        inv.setItem(14, (new ItemBuilder(Material.POTION))
                .setPotionMeta(PotionType.WATER_BREATHING, false)
                .setDisplayName("§7Résistance: §f" + main.getGameManager().getGameSettings().getResistancePercent() + "§7%")
                .addItemFlags(ItemFlag.HIDE_POTION_EFFECTS)
                .toItemStack());
        inv.setItem(getSize() - 1, (new ItemBuilder(Material.ARROW))
                .setDisplayName("§cRetour en arrière")
                        .setLore(Arrays.asList(" ", "§8» §7Clic gauche | §aRetour")).toItemStack());
                        }

        public void onClick(Player player, Inventory inv, ItemStack current, int slot, ClickType clickType) {
            if (current.getType().equals(Material.POTION)) {
                if (slot == 12) {
                    int strength = main.getGameManager().getGameSettings().getStrengthPercent();
                    if (clickType.isLeftClick()) {
                        main.getGameManager().getGameSettings().setStrengthPercent(++strength);
                    } else if (clickType.isRightClick() &&
                            strength > 0) {
                        main.getGameManager().getGameSettings().setStrengthPercent(--strength);
                    }
                } else if (slot == 14) {
                    int resistance = main.getGameManager().getGameSettings().getResistancePercent();
                    if (clickType.isLeftClick()) {
                        main.getGameManager().getGameSettings().setResistancePercent(++resistance);
                    } else if (clickType.isRightClick() &&
                            resistance > 0) {
                        main.getGameManager().getGameSettings().setResistancePercent(--resistance);
                    }
                }
                main.getGuiManager().open(player, getClass());
            } else if (current.getType().equals(Material.ARROW)) {
                main.getGuiManager().open(player, HostMainGui.class);
            }
        }
    }
