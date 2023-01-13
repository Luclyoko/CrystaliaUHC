package fr.luclyoko.crystaliauhc.guis.hostguis.borderguis;

import fr.luclyoko.crystaliauhc.game.timers.Border;
import fr.luclyoko.crystaliauhc.guis.GuiBuilder;
import fr.luclyoko.crystaliauhc.guis.hostguis.HostMainGui;
import fr.luclyoko.crystaliauhc.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class BorderMainGui implements GuiBuilder {
    public String name() {
        return "§fGestion de la bordure";
    }

    public int getSize() {
        return 27;
    }

    public void contents(Player player, Inventory inv) {
        for (int i = 0; i < getSize(); i++) {
            if ((i <= 8 || i >= getSize() - 9 || i % 9 == 0 || i % 9 == 8) && i !=

                    getSize() - 1)
                inv.setItem(i, (new ItemBuilder(Material.STAINED_GLASS_PANE, (byte) 0))
                        .setAmount(1)
                        .setDisplayName(" ")
                        .toItemStack());
        }
        Border border = (Border) main.getGameManager().getGameSettings().getBorder();
        int bis = border.getBorderInitialSize();
        inv.setItem(10, (new ItemBuilder(Material.STAINED_GLASS, (byte) 3))
                .setDisplayName("§3Bordure initiale: §b" + bis + "§3/§b" + bis)
                .setLore(Arrays.asList(" ", "§8» §7Clic gauche | §a§l+100 §7(+Shift: §a+50§7)", "§8» §7Clic droit | §c§l-100 §7(+Shift: §c-50§7)")).toItemStack());
        int bfs = border.getBorderFinalSize();
        inv.setItem(12, (new ItemBuilder(Material.STAINED_GLASS, (byte) 14))
                .setDisplayName("§5Bordure finale: §d" + bfs + "§5/§d" + bfs)
                .setLore(Arrays.asList(" ", "§8» §7Clic gauche | §a§l+100 §7(+Shift: §a+50§7)", "§8» §7Clic droit | §c§l-100 §7(+Shift: §c-50§7)")).toItemStack());
        inv.setItem(14, (new ItemBuilder(Material.WATCH))
                .setDisplayName("§2Timer réduction: §a" + (main.getGameManager().getGameSettings().getBorder().getTriggerTime() / 60) + " §2min")
                .setLore(Arrays.asList(" ", "§8» §7Clic gauche | §a§l+1 §7(+Shift: §a+10§7)", "§8» §7Clic droit | §c§l-1 §7(+Shift: §c-10§7)")).toItemStack());
        inv.setItem(16, (new ItemBuilder(Material.SUGAR))
                .setDisplayName("§6Vitesse réduction: §e" + ((Border) main.getGameManager().getGameSettings().getBorder()).getBorderSpeed() + " §6bloc(s)/seconde")
                .setLore(Arrays.asList(" ", "§8» §7Clic gauche | §a§l+0.5", "§8» §7Clic droit | §c§l-0.5")).toItemStack());
        inv.setItem(getSize() - 1, (new ItemBuilder(Material.ARROW))
                .setAmount(1)
                .setDisplayName("§cRetour en arrière")
                .setLore(Arrays.asList(" ", "§8» §7Clic gauche | §aRetour")).toItemStack());
    }

        public void onClick(Player player, Inventory inv, ItemStack current, int slot, ClickType clickType) {
            Border border = (Border)main.getGameManager().getGameSettings().getBorder();
            if (current.getType().equals(Material.STAINED_GLASS)) {
                byte data = (byte)current.getDurability();
                int i = 100;
                if (clickType.isShiftClick())
                    i = 50;
                if (data == 3) {
                    if (clickType.isLeftClick()) {
                        border.setBorderInitialSize(border.getBorderInitialSize() + i);
                    } else if (clickType.isRightClick()) {
                        border.setBorderInitialSize(border.getBorderInitialSize() - i);
                        if (border.getBorderInitialSize() < 100)
                            border.setBorderInitialSize(100);
                    }
                } else if (data == 14) {
                    if (clickType.isLeftClick()) {
                        border.setBorderFinalSize(border.getBorderFinalSize() + i);
                    } else if (clickType.isRightClick()) {
                        border.setBorderFinalSize(border.getBorderFinalSize() - i);
                        if (border.getBorderFinalSize() < 100)
                            border.setBorderFinalSize(100);
                    }
                }
                main.getGuiManager().open(player, getClass());
            } else if (current.getType().equals(Material.WATCH)) {
                int i = 60;
                if (clickType.isShiftClick())
                    i = 600;
                if (clickType.isLeftClick()) {
                    border.setTriggerTime(border.getTriggerTime() + i);
                } else if (clickType.isRightClick()) {
                    border.setTriggerTime(border.getTriggerTime() - i);
                    if (border.getTriggerTime() < 60)
                        border.setTriggerTime(60);
                }
                main.getGuiManager().open(player, getClass());
            } else if (current.getType().equals(Material.SUGAR)) {
                if (clickType.isLeftClick()) {
                    border.setBorderSpeed(border.getBorderSpeed() + 0.5D);
                } else if (clickType.isRightClick()) {
                    border.setBorderSpeed(border.getBorderSpeed() - 0.5D);
                    if (border.getBorderSpeed() < 0.5D)
                        border.setBorderSpeed(0.5D);
                }
                main.getGuiManager().open(player, getClass());
            } else if (current.getType().equals(Material.ARROW)) {
                main.getGuiManager().open(player, HostMainGui.class);
            }
        }
    }
