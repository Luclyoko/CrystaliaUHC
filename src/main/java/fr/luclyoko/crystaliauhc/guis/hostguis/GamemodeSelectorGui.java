package fr.luclyoko.crystaliauhc.guis.hostguis;

import fr.luclyoko.crystaliauhc.Main;
import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.gamemodes.GamemodeUHC;
import fr.luclyoko.crystaliauhc.gamemodes.UHCGamemodes;
import fr.luclyoko.crystaliauhc.guis.GuiBuilder;
import fr.luclyoko.crystaliauhc.utils.ItemBuilder;
import java.util.Arrays;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GamemodeSelectorGui implements GuiBuilder {
    public String name() {
        return "§aSélection du mode de jeu";
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
        for (UHCGamemodes gamemodes : UHCGamemodes.values()) {
            inv.addItem((new ItemBuilder(gamemodes.getDisplayItem()))
                    .setDisplayName("§6" + gamemodes.getDisplayName())
                    .setLore(Arrays.asList(" ", "§8» §7Clic gauche | §aAppliquer")).toItemStack());
        }
        inv.setItem(getSize() - 1, (new ItemBuilder(Material.ARROW))
                .setDisplayName("§cRetour en arrière")
                .setLore(Arrays.asList(" ", "§8» §7Clic gauche | §aRetour")).toItemStack());
    }

            public void onClick(Player player, Inventory inv, ItemStack current, int slot, ClickType clickType) {
                if (current.getType().equals(Material.ARROW))
                    main.getGuiManager().open(player, HostMainGui.class);
                Arrays.<UHCGamemodes>stream(UHCGamemodes.values()).forEach(uhcGamemodes -> {
                    if (current.getType().equals(uhcGamemodes.getDisplayItem())) {
                        Class<? extends GamemodeUHC> gamemodeClass = uhcGamemodes.getGamemodeClass();
                        if (clickType.isLeftClick()) {
                            player.closeInventory();
                            player.sendMessage(main.getGameManager().getGamemodeUhc().getDisplayNameChat() + "§aApplication du mode de jeu...");
                            Class[] classArgs = new Class[2];
                            classArgs[0] = Main.class;
                            classArgs[1] = GameManager.class;
                            try {
                                main.getGameManager().setGamemodeUhc(gamemodeClass.getDeclaredConstructor(classArgs).newInstance(main, main.getGameManager()));
                                player.sendMessage(main.getGameManager().getGamemodeUhc().getDisplayNameChat() + "§aMode de jeu appliqué avec succès.");
                            } catch (NoSuchMethodException|InstantiationException|IllegalAccessException|java.lang.reflect.InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        }
