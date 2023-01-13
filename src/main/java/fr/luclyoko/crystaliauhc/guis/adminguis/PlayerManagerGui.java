package fr.luclyoko.crystaliauhc.guis.adminguis;

import fr.luclyoko.crystaliauhc.guis.GuiBuilder;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import fr.luclyoko.crystaliauhc.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Optional;

public class PlayerManagerGui implements GuiBuilder {
    public String name() {
        return "§cPanel de gestion des joueurs";
    }

    public int getSize() {
        return 54;
    }

    public void contents(Player player, Inventory inv) {
        int i;
        for (i = 0; i < getSize(); i++) {
            if ((i <= 8 || i >= getSize() - 9 || i % 9 == 0 || i % 9 == 8) && i !=

                    getSize() - 1)
                inv.setItem(i, (new ItemBuilder(Material.STAINED_GLASS_PANE, (byte) 14))
                        .setAmount(1)
                        .setDisplayName(" ")
                        .toItemStack());
        }
        i = 10;
        for (Player player1 : Bukkit.getOnlinePlayers()) {
            CrystaliaPlayer crystaliaPlayer = main.getPlayerManager().getExactPlayer(player1);
            inv.setItem(i++,
                    ItemBuilder.skullFromName(1, "§6" + player1.getName(),
                            Arrays.asList(" ", "§8» §7Mod: " + (crystaliaPlayer.isMod() ? "§a§l✔" : "§c§l✘"), " ", "§8» §7Clic gauche | §6Définir en tant que Host", "§8» §7Clic droit | " +
                                    (crystaliaPlayer.isMod() ? "§3Retirer des Mods" : "§3Ajouter aux Mods")), null, null, player1.getName()));
        }
        inv.setItem(getSize() - 1, (new ItemBuilder(Material.ARROW))
                .setDisplayName("§cRetour en arrière")
                .setLore(Arrays.asList(" ", "§8» §7Clic gauche | §aRetour")).toItemStack());
    }

        public void onClick(Player player, Inventory inv, ItemStack current, int slot, ClickType clickType) {
            ItemMeta itemMeta;
            Optional<Player> optionalPlayer1;
            Player player1;
            CrystaliaPlayer crystaliaPlayer;
            switch (current.getType()) {
                case SKULL_ITEM:
                    itemMeta = current.getItemMeta();
                    optionalPlayer1 = Optional.ofNullable(Bukkit.getPlayer(itemMeta.getDisplayName().substring(2)));
                    if (!optionalPlayer1.isPresent()) {
                        main.getGuiManager().open(player, getClass());
                        break;
                    }
                    player1 = optionalPlayer1.get();
                    crystaliaPlayer = main.getPlayerManager().getExactPlayer(player1);
                    if (clickType.isLeftClick()) {
                        main.getGameManager().getGameSettings().setHost(player1.getUniqueId());
                        main.getGameManager().getGameSettings().setHostName(player1.getName());
                    } else if (clickType.isRightClick()) {
                        crystaliaPlayer.setMod(!crystaliaPlayer.isMod());
                        main.getTeamManager().refreshTeams();
                    }
                    main.getGuiManager().open(player, getClass());
                    break;
                case ARROW:
                    main.getGuiManager().open(player, AdminMainGui.class);
                    break;
            }
        }
    }
