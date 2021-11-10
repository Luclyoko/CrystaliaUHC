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
    @Override
    public String name() {
        return "§cPanel de gestion des joueurs";
    }

    @Override
    public int getSize() {
        return 9*9;
    }

    @Override
    public void contents(Player player, Inventory inv) {
        for (int i = 0; i < getSize(); i++) {
            if (((i <= 8 || i >= getSize() - 9) ||
                    i % 9 == 0 || i % 9 == 8) &&
                    i != getSize() - 1) {
                inv.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, (byte) 14)
                        .setAmount(1)
                        .setDisplayName(" ")
                        .toItemStack());
            }
        }

        int i = 10;
        for (Player player1 : Bukkit.getOnlinePlayers()) {
            CrystaliaPlayer crystaliaPlayer = main.getPlayerManager().getExactPlayer(player1);
            inv.setItem(i++,
                    ItemBuilder.skullFromName(1,
                            "§6" + player1.getName(),
                            Arrays.asList(" ",
                                    "§8» §7Spec: " + (crystaliaPlayer.isSpec() ? "§a§l✔" : "§c§l✘"),
                                    " ",
                                    "§8» §7Clic gauche | §6Définir en tant que Host",
                                    "§8» §7Clic droit | " + (crystaliaPlayer.isSpec() ? "§3Retirer des Specs" : "§3Ajouter aux Specs")),
                            null,
                            null,
                            player1.getName()));
        }

        inv.setItem(getSize() - 1, new ItemBuilder(Material.ARROW)
                .setAmount(1)
                .setDisplayName("§cFermer le menu")
                .setLore(Arrays.asList(" ",
                        "§8» §7Clic gauche | §aFermer"))
                .toItemStack());
    }

    @Override
    public void onClick(Player player, Inventory inv, ItemStack current, int slot, ClickType clickType) {
        switch (current.getType()) {
            case SKULL_ITEM:
                ItemMeta itemMeta = current.getItemMeta();
                Optional<Player> optionalPlayer1 = Optional.ofNullable(Bukkit.getPlayer(itemMeta.getDisplayName().substring(2)));
                if (!optionalPlayer1.isPresent()) {
                    main.getGuiManager().open(player, this.getClass());
                    break;
                }
                Player player1 = optionalPlayer1.get();
                Bukkit.broadcastMessage(player1.getName());
                CrystaliaPlayer crystaliaPlayer = main.getPlayerManager().getExactPlayer(player1);
                if (clickType.isLeftClick()) {
                    main.getGameManager().getGameSettings().setHost(player1.getUniqueId());
                    main.getGameManager().getGameSettings().setHostName(player1.getName());
                } else if (clickType.isRightClick()) {
                    if (!crystaliaPlayer.isSpec()) {
                        main.getGameManager().getGameSettings().addSpec(player1);
                        crystaliaPlayer.setSpec(true);
                    } else {
                        main.getGameManager().getGameSettings().removeSpec(player1);
                        crystaliaPlayer.setSpec(false);
                    }
                    main.getPlayerManager().updatePlayer(crystaliaPlayer);
                }
                main.getGuiManager().open(player, this.getClass());

            case ARROW:
                player.closeInventory();
                player.sendMessage(main.getGameManager().getGamemodeUhc().getDisplayNameChat() + "§aLes paramètres ont bien été sauvegardés.");
                break;

            default:
                break;
        }
    }
}
