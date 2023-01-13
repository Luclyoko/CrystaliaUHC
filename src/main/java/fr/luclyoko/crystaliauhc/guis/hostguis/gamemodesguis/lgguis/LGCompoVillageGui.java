package fr.luclyoko.crystaliauhc.guis.hostguis.gamemodesguis.lgguis;

import fr.luclyoko.crystaliauhc.gamemodes.lguhc.LGUHC;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.LGRolesEnum;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.LGSides;
import fr.luclyoko.crystaliauhc.guis.GuiBuilder;
import fr.luclyoko.crystaliauhc.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public class LGCompoVillageGui implements GuiBuilder {
    public String name() {
        return "§aComposition du Village";
    }

    public int getSize() {
        return 54;
    }

    public void contents(Player player, Inventory inv) {
        for (int i = 0; i < getSize(); i++) {
            if ((i <= 8 || i >= getSize() - 9 || i % 9 == 0 || i % 9 == 8) && i !=

                    getSize() - 1)
                inv.setItem(i, (new ItemBuilder(Material.STAINED_GLASS_PANE, (byte) 5))
                        .setAmount(1)
                        .setDisplayName(" ")
                        .toItemStack());
        }
        LGUHC lguhc = (LGUHC) main.getGameManager().getGamemodeUhc();
        for (LGRolesEnum lgRolesEnum : lguhc.getRolesManager().getCompoForSide(LGSides.LGBasicSides.VILLAGE).keySet().stream().sorted().collect(Collectors.toList())) {
            int j = lguhc.getRolesManager().getRoleSize(lgRolesEnum);
            inv.addItem((new ItemBuilder(Material.STAINED_CLAY, (byte) ((j <= 0) ? 14 : 5)))
                    .setDisplayName(lgRolesEnum.getBasicSide().getColor() + lgRolesEnum.getName())
                    .setAmount(j)
                    .setLore(Arrays.asList(" ", "§8» §7Clic gauche | §a§l+1", "§8» §7Clic droit | §c§l-1")).toItemStack());
        }
        inv.setItem(getSize() - 1, (new ItemBuilder(Material.ARROW))
                .setDisplayName("§cRetour en arrière")
                .setLore(Arrays.asList(" ", "§8» §7Clic gauche | §cRetour")).toItemStack());
    }

        public void onClick(Player player, Inventory inv, ItemStack current, int slot, ClickType clickType) {
            if (current.getType().equals(Material.STAINED_CLAY)) {
                Optional<LGRolesEnum> opRoleToEdit = Arrays.<LGRolesEnum>stream(LGRolesEnum.values()).filter(lgRolesEnum -> lgRolesEnum.getName().equals(current.getItemMeta().getDisplayName().substring(2))).findFirst();
                LGUHC lguhc = (LGUHC)main.getGameManager().getGamemodeUhc();
                if (opRoleToEdit.isPresent()) {
                    LGRolesEnum roleToEdit = opRoleToEdit.get();
                    if (clickType.isLeftClick()) {
                        lguhc.getRolesManager().increaseRole(roleToEdit);
                    } else if (clickType.isRightClick()) {
                        lguhc.getRolesManager().decreaseRole(roleToEdit);
                    }
                }
                main.getGuiManager().open(player, getClass());
            } else if (current.getType().equals(Material.ARROW)) {
                main.getGuiManager().open(player, LGCompoMainGui.class);
            }
        }
    }
