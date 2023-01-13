package fr.luclyoko.crystaliauhc.guis.hostguis.gamemodesguis.lgguis;

import fr.luclyoko.crystaliauhc.gamemodes.lguhc.LGUHC;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.LGSides;
import fr.luclyoko.crystaliauhc.guis.GuiBuilder;
import fr.luclyoko.crystaliauhc.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class LGCompoMainGui implements GuiBuilder {
    public String name() {
        return "§eComposition de la partie";
    }

    public int getSize() {
        return 27;
    }

    public void contents(Player player, Inventory inv) {
        for (int i = 0; i < getSize(); i++) {
            if ((i <= 8 || i >= getSize() - 9 || i % 9 == 0 || i % 9 == 8) && i !=

                    getSize() - 1)
                inv.setItem(i, (new ItemBuilder(Material.STAINED_GLASS_PANE, (byte) 4))
                        .setAmount(1)
                        .setDisplayName(" ")
                        .toItemStack());
        }
        LGUHC lguhc = (LGUHC) main.getGameManager().getGamemodeUhc();
        int villageSize = lguhc.getRolesManager().getSideSize(LGSides.LGBasicSides.VILLAGE);
        inv.setItem(10, (new ItemBuilder(Material.STAINED_CLAY, (byte) 5))
                .setAmount(villageSize)
                .setDisplayName("§aVillage: §2" + villageSize)
                .setLore(Arrays.asList(" ", "§8» §7Clic gauche | §eModifier la composition")).toItemStack());
        int loupsSize = lguhc.getRolesManager().getSideSize(LGSides.LGBasicSides.LOUPS);
        inv.setItem(11, (new ItemBuilder(Material.STAINED_CLAY, (byte) 14))
                .setAmount(loupsSize)
                .setDisplayName("§cLoups: §4" + loupsSize)
                .setLore(Arrays.asList(" ", "§8» §7Clic gauche | §eModifier la composition")).toItemStack());
        boolean blindCompo = lguhc.getRolesManager().isBlindCompo();
        inv.setItem(13, (new ItemBuilder(Material.WOOL, (byte) (blindCompo ? 5 : 14)))
                .setDisplayName("§eComposition cachée")
                .setLore(Arrays.asList(" ", "§8» §7État: " + (blindCompo ? "§a§lActivé" : "§c§lDesactivé"), " ", blindCompo ? "§8» §7Clic gauche | §cDesactiver" : "§8» §7Clic gauche | §aActiver")).toItemStack());
        int solosSize = lguhc.getRolesManager().getSideSize(LGSides.LGBasicSides.SOLITAIRE);
        inv.setItem(15, (new ItemBuilder(Material.STAINED_CLAY, (byte) 1))
                .setAmount(solosSize)
                .setDisplayName("§6Solitaires: §e" + solosSize)
                .setLore(Arrays.asList(" ", "§8» §7Clic gauche | §eModifier la composition")).toItemStack());
        int hybridesSize = lguhc.getRolesManager().getSideSize(LGSides.LGBasicSides.HYBRIDE);
        inv.setItem(16, (new ItemBuilder(Material.STAINED_CLAY, (byte) 2))
                .setAmount(hybridesSize)
                .setDisplayName("§dHybrides: §5" + hybridesSize)
                .setLore(Arrays.asList(" ", "§8» §7Clic gauche | §eModifier la composition")).toItemStack());
        inv.setItem(getSize() - 1, (new ItemBuilder(Material.ARROW))
                .setDisplayName("§cRetour en arrière")
                .setLore(Arrays.asList(" ", "§8» §7Clic gauche | §cRetour")).toItemStack());
    }

        public void onClick(Player player, Inventory inv, ItemStack current, int slot, ClickType clickType) {
            if (current.getType().equals(Material.STAINED_CLAY)) {
                switch ((byte)current.getDurability()) {
                    case 5:
                        main.getGuiManager().open(player, LGCompoVillageGui.class);
                        break;
                    case 14:
                        main.getGuiManager().open(player, LGCompoLoupsGui.class);
                        break;
                    case 1:
                        main.getGuiManager().open(player, LGCompoSolosGui.class);
                        break;
                    case 2:
                        main.getGuiManager().open(player, LGCompoHybridesGui.class);
                        break;
                }
            } else if (current.getType().equals(Material.WOOL)) {
                LGUHC lguhc = (LGUHC)main.getGameManager().getGamemodeUhc();
                boolean blindCompo = lguhc.getRolesManager().isBlindCompo();
                lguhc.getRolesManager().setBlindCompo(!blindCompo);
                main.getGuiManager().open(player, getClass());
            } else if (current.getType().equals(Material.ARROW)) {
                main.getGuiManager().open(player, LGMainGui.class);
            }
        }
    }
