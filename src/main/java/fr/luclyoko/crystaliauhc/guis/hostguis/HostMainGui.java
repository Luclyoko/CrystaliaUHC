package fr.luclyoko.crystaliauhc.guis.hostguis;

import fr.luclyoko.crystaliauhc.game.GameState;
import fr.luclyoko.crystaliauhc.game.StartTask;
import fr.luclyoko.crystaliauhc.guis.GuiBuilder;
import fr.luclyoko.crystaliauhc.guis.hostguis.scenariosguis.ScenarioManagerGui;
import fr.luclyoko.crystaliauhc.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class HostMainGui implements GuiBuilder {
    @Override
    public String name() {
        return "§aPanel de host";
    }

    @Override
    public int getSize() {
        return 9*8;
    }

    @Override
    public void contents(Player player, Inventory inv) {
        for (int i = 0; i < getSize(); i++) {
            if (((i <= 8 || i >= getSize() - 9) ||
                    i % 9 == 0 || i % 9 == 8) &&
                    i != getSize() - 1) {
                inv.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, (byte) 5)
                        .setAmount(1)
                        .setDisplayName(" ")
                        .toItemStack());
            }
        }

        inv.setItem(10, new ItemBuilder(Material.COMMAND_MINECART)
                .setDisplayName("§9Gestion des scénarios")
                .setAmount(1)
                .setLore(Arrays.asList(" ",
                        "§8» §7Clic gauche | §eConfigurer les scénarios"))
                .toItemStack());

        if (!main.getGameManager().isStarted())
            inv.setItem(getSize() - 11, new ItemBuilder((main.getGameManager().getGameState().equals(GameState.STARTING) ? Material.REDSTONE_BLOCK : Material.EMERALD_BLOCK))
                .setAmount(1)
                .setDisplayName("§aDémarrage de la partie")
                .setLore(Arrays.asList(" ",
                        "§7Clic gauche | " + (main.getGameManager().getGameState().equals(GameState.STARTING) ? "§c§lAnnuler" : "§a§lDémarrer")))
                .toItemStack());

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
            case COMMAND_MINECART:
                main.getGuiManager().open(player, ScenarioManagerGui.class);
                break;

            case EMERALD_BLOCK:
                player.closeInventory();
                player.sendMessage(main.getGameManager().getGamemodeUhc().getDisplayNameChat() + "§aDémarrage de la partie.");
                main.getGameManager().getStartTask().runTaskTimer(main, 20L, 20L);
                main.getGameManager().setGameState(GameState.STARTING);
                break;

            case REDSTONE_BLOCK:
                player.closeInventory();
                player.sendMessage(main.getGameManager().getGamemodeUhc().getDisplayNameChat() + "§cAnnulation du démarrage de la partie.");
                main.getGameManager().getStartTask().cancel();
                main.getGameManager().setStartTask(new StartTask(main, main.getGameManager()));
                main.getGameManager().setGameState(GameState.LOADING);
                break;

            case ARROW:
                player.closeInventory();
                player.sendMessage(main.getGameManager().getGamemodeUhc().getDisplayNameChat() + "§aLes paramètres ont bien été sauvegardés.");
                break;

            default:
                break;
        }
    }
}
