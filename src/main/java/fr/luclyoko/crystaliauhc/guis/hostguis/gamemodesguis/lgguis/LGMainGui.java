package fr.luclyoko.crystaliauhc.guis.hostguis.gamemodesguis.lgguis;

import fr.luclyoko.crystaliauhc.gamemodes.lguhc.LGUHC;
import fr.luclyoko.crystaliauhc.guis.GuiBuilder;
import fr.luclyoko.crystaliauhc.guis.hostguis.HostMainGui;
import fr.luclyoko.crystaliauhc.utils.ItemBuilder;
import java.util.Arrays;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class LGMainGui implements GuiBuilder {
    public String name() {
        return "§cParamètres du LG UHC";
    }

    public int getSize() {
        return 54;
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
        inv.setItem(13, (new ItemBuilder(Material.WATCH))
                .setDisplayName("§3Annonces des rôles: §b" + (((LGUHC) main.getGameManager().getGamemodeUhc()).getRolesManager().getTriggerTime() / 60) + " §3min")
                .setLore(Arrays.asList(" ", "§8» §7Clic gauche | §a§l+1", "§8» §7Clic droit | §c§l-1")).toItemStack());
        inv.setItem(37, (new ItemBuilder(Material.BOOK_AND_QUILL))
                .setDisplayName("§9Gestion de la composition")
                .setLore(Arrays.asList(" ", "§8» §7Clic gauche | §eModifier la composition")).toItemStack());
        inv.setItem(getSize() - 1, (new ItemBuilder(Material.ARROW))
                .setDisplayName("§cRetour en arrière")
                .setLore(Arrays.asList(" ", "§8» §7Clic gauche | §cRetour")).toItemStack());
    }

        public void onClick(Player player, Inventory inv, ItemStack current, int slot, ClickType clickType) {
            int roles;
            LGUHC lguhc = (LGUHC)main.getGameManager().getGamemodeUhc();
            switch (current.getType()) {
                case BOOK_AND_QUILL:
                    main.getGuiManager().open(player, LGCompoMainGui.class);
                    break;
                case WATCH:
                    roles = lguhc.getRolesManager().getTriggerTime();
                    if (clickType.isLeftClick()) {
                        lguhc.getRolesManager().setTriggerTime(roles + 60);
                    } else if (clickType.isRightClick()) {
                        int i = 0;
                        if (roles > 60)
                            i = 60;
                        lguhc.getRolesManager().setTriggerTime(roles - i);
                    }
                    main.getGuiManager().open(player, getClass());
                    break;
                case ARROW:
                    main.getGuiManager().open(player, HostMainGui.class);
                    break;
            }
        }
    }
