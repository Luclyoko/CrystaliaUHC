package fr.luclyoko.crystaliauhc.guis.hostguis;

import fr.luclyoko.crystaliauhc.guis.GuiBuilder;
import fr.luclyoko.crystaliauhc.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class DayNightCycleConfigGui implements GuiBuilder {
    public String name() {
        return "§eGestion du cycle Jour/Nuit";
    }

    public int getSize() {
        return 27;
    }

    public void contents(Player player, Inventory inv) {
        for (int i = 0; i < getSize(); i++) {
            if ((i <= 8 || i >= getSize() - 9 || i % 9 == 0 || i % 9 == 8) && i !=

                    getSize() - 1)
                inv.setItem(i, (new ItemBuilder(Material.STAINED_GLASS_PANE, (byte) 14))
                        .setAmount(1)
                        .setDisplayName(" ")
                        .toItemStack());
        }
        inv.setItem(11, (new ItemBuilder(Material.WATCH))
                .setDisplayName("§3Durée jour/nuit: §b" + (main.getGameManager().getGameSettings().getDayNightCycle() / 60) + " §3min")
                .setLore(Arrays.asList(" ", "§8» §7Clic gauche | §a§l+1", "§8» §7Clic droit | §c§l-1")).toItemStack());
        inv.setItem(13, (new ItemBuilder(Material.STAINED_CLAY, (byte) (main.getGameManager().getGameSettings().isEternalDay() ? 5 : 14)))
                .setDisplayName("§e" + (main.getGameManager().getGameSettings().isDay() ? "Jour éternel" : "Nuit éternelle"))
                .setLore(Arrays.asList(" ",
                        "§8» §7État: " .concat(main.getGameManager().getGameSettings().isEternalDay() ? "§a§lActivé" : "§c§lDésactivé"),
                        " ",
                        main.getGameManager().getGameSettings().isEternalDay() ? "§8» §7Clic gauche | §cDésactiver" : "§8» §7Clic gauche | §aActiver"))
                .toItemStack());
        inv.setItem(15, (new ItemBuilder(Material.STAINED_GLASS, (byte) (main.getGameManager().getGameSettings().isDay() ? 4 : 7)))
                .setDisplayName("§6Temps du début de partie")
                .setLore(Arrays.asList(" ",
                        "§8» §7État: " .concat(main.getGameManager().getGameSettings().isDay() ? "§e§lJour" : "§7§lNuit"),
                        " ",
                        main.getGameManager().getGameSettings().isDay() ? "§8» §7Clic gauche | §7Nuit" : "§8» §7Clic gauche | §eJour"))
                .toItemStack());
        inv.setItem(getSize() - 1, (new ItemBuilder(Material.ARROW))
                .setDisplayName("§cRetour en arrière")
                .setLore(Arrays.asList(" ", "§8» §7Clic gauche | §aRetour")).toItemStack());
    }

        public void onClick(Player player, Inventory inv, ItemStack current, int slot, ClickType clickType) {
            int dayNightCycle;
            switch (current.getType()) {
                case WATCH:
                    dayNightCycle = main.getGameManager().getGameSettings().getDayNightCycle();
                    if (clickType.isLeftClick()) {
                        main.getGameManager().getGameSettings().setDayNightCycle(dayNightCycle + 60);
                    } else if (clickType.isRightClick()) {
                        int i = 0;
                        if (dayNightCycle > 60)
                            i = 60;
                        main.getGameManager().getGameSettings().setDayNightCycle(dayNightCycle - i);
                    }
                    main.getGuiManager().open(player, getClass());
                    break;
                case STAINED_CLAY:
                    main.getGameManager().getGameSettings().setEternalDay(!main.getGameManager().getGameSettings().isEternalDay());
                    main.getGuiManager().open(player, getClass());
                    break;
                case STAINED_GLASS:
                    main.getGameManager().getGameSettings().setDay(!main.getGameManager().getGameSettings().isDay());
                    main.getGuiManager().open(player, getClass());
                    break;
                case ARROW:
                    main.getGuiManager().open(player, HostMainGui.class);
                    break;
            }
        }
    }
