package fr.luclyoko.crystaliauhc.guis.hostguis;

import fr.luclyoko.crystaliauhc.game.GameSettings;
import fr.luclyoko.crystaliauhc.game.GameState;
import fr.luclyoko.crystaliauhc.game.StartTask;
import fr.luclyoko.crystaliauhc.game.timers.Border;
import fr.luclyoko.crystaliauhc.game.timers.Pvp;
import fr.luclyoko.crystaliauhc.guis.GuiBuilder;
import fr.luclyoko.crystaliauhc.guis.hostguis.borderguis.BorderMainGui;
import fr.luclyoko.crystaliauhc.guis.hostguis.enchantsguis.EnchantLimitGui;
import fr.luclyoko.crystaliauhc.guis.hostguis.scenariosguis.ScenarioManagerGui;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import fr.luclyoko.crystaliauhc.teams.Team;
import fr.luclyoko.crystaliauhc.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionType;

import java.util.*;

public class HostMainGui implements GuiBuilder {
    public String name() {
        return "§aPanel de host";
    }

    public int getSize() {
        return 54;
    }

    public void contents(Player player, Inventory inv) {
        int i;
        for (i = 0; i < getSize(); i++) {
            if ((i <= 8 || i >= getSize() - 9 || i % 9 == 0 || i % 9 == 8) && i !=

                    getSize() - 1)
                inv.setItem(i, (new ItemBuilder(Material.STAINED_GLASS_PANE, (byte)5))
                        .setAmount(1)
                        .setDisplayName(" ")
                        .toItemStack());
        }
        inv.setItem(10, (new ItemBuilder(Material.IRON_DOOR))
                .setDisplayName("§7Temps avant mort de déco: §f" + (main.getGameManager().getGameSettings().getIdleTime() / 60) + " §7min")
                .setLore(Arrays.asList(" ", "§8» §7Clic gauche | §a§l+1", "§8» §7Clic droit | §c§l-1")).toItemStack());
        if (!main.getGameManager().isStarted() && main.getGameManager().getGamemodeUhc().getMaxTeamSize() > 1)
            inv.setItem(13, (new ItemBuilder(Material.SKULL_ITEM, (byte)3))
                    .setDisplayName("§3Taille des teams: §b" + main.getTeamManager().getTeamsSize())
                    .setLore(Arrays.asList(" ", "§8» §7Clic gauche | §a§l+1", "§8» §7Clic droit | §c§l-1")).toItemStack());
        i = ((Border)main.getGameManager().getGameSettings().getBorder()).getBorderInitialSize();
        inv.setItem(16, (new ItemBuilder(Material.SAPLING))
                .setDisplayName("§2Prégénération (§a" + i + "§2/§a" + i + "§2)")
                        .setLore(Arrays.asList(" ", "§8» §7Clic gauche | §eLancer la prégénération du monde")).toItemStack());
        inv.setItem(20, (new ItemBuilder(Material.DIAMOND_SWORD))
                .setDisplayName("§2PvP: §a" + (main.getGameManager().getGameSettings().getPvp().getTriggerTime() / 60) + " §2min")
                .setLore(Arrays.asList(" ", "§8» §7Clic gauche | §a§l+1 §7(+Shift: §a+10§7)", "§8» §7Clic droit | §c§l-1 §7(+Shift: §c-10§7)")).toItemStack());
        inv.setItem(21, (new ItemBuilder(Material.STAINED_GLASS, (byte)0))
                .setDisplayName("§fGestion de la bordure")
                .setLore(Arrays.asList(" ", "§8» §7Clic gauche | §eConfigurer la bordure")).toItemStack());
        inv.setItem(23, (new ItemBuilder(Material.ENCHANTED_BOOK))
                .setDisplayName("§dLimites d'enchantements")
                .setLore(Arrays.asList(" ", "§8» §7Clic gauche | §eConfigurer les enchantements")).addEnchant(Enchantment.DURABILITY, 10)
                .addItemFlags(ItemFlag.HIDE_ENCHANTS)
                .toItemStack());
        if (!main.getGameManager().isStarted())
            inv.setItem(24, (new ItemBuilder(Material.WORKBENCH))
                    .setDisplayName("§6Inventaire de départ")
                            .setLore(Arrays.asList(" ", "§8» §7Clic gauche | §eConfigurer l'inventaire")).toItemStack());
        inv.setItem(30, (new ItemBuilder(Material.POTION))
                .setDisplayName("§dNerfs des effets")
                .setPotionMeta(PotionType.INSTANT_HEAL, false)
                .setLore(Arrays.asList(" ", "§8» §7Clic gauche | §eConfigurer les effets")).addItemFlags(ItemFlag.HIDE_POTION_EFFECTS)
                .toItemStack());
        if (!main.getGameManager().isStarted())
            inv.setItem(31, (new ItemBuilder(Material.WATCH))
                    .setDisplayName("§eCycle Jour/Nuit")
                    .setLore(Arrays.asList(" ", "§8» §7Clic gauche | §eConfigurer le cycle jour/nuit")).toItemStack());
        List<String> lore = new ArrayList<>();
        lore.add(" ");
        if (!main.getGameManager().isStarted())
            lore.add("§8» §7Clic gauche | §eChoisir le mode de jeu");
        if (main.getGameManager().getGamemodeUhc().getConfigGui() != null)
            lore.add("§8» §7Clic droit | §6Options du mode de jeu");
        inv.setItem(37, (new ItemBuilder(Material.SIGN))
                .setDisplayName("§eMode de jeu: " + main.getGameManager().getGamemodeUhc().getDefaultName())
                .setLore(lore)
                .toItemStack());
        inv.setItem(43, (new ItemBuilder(Material.BOOK_AND_QUILL))
                .setDisplayName("§9Gestion des scénarios")
                        .setLore(Arrays.asList(" ", "§8» §7Clic gauche | §eConfigurer les scénarios")).toItemStack());
        if (!main.getGameManager().isStarted())
            inv.setItem(49, (new ItemBuilder(main.getGameManager().getGameState().equals(GameState.STARTING) ? Material.REDSTONE_BLOCK : Material.EMERALD_BLOCK))
                    .setDisplayName("§aDémarrage de la partie")
                    .setLore(Arrays.asList(" ", "§8» §7Clic gauche | " + (main.getGameManager().getGameState().equals(GameState.STARTING) ? "§c§lAnnuler" : "§a§lDémarrer"))).toItemStack());
        inv.setItem(getSize() - 1, (new ItemBuilder(Material.BARRIER))
                .setDisplayName("§cFermer le menu")
                .setLore(Arrays.asList(" ", "§8» §7Clic gauche | §aFermer")).toItemStack());
                }

        public void onClick(Player player, Inventory inv, ItemStack current, int slot, ClickType clickType) {
            int i;
            GameSettings gameSettings;
            int teamSize;
            Pvp pvp;
            switch (current.getType()) {
                case IRON_DOOR:
                    i = 60;
                    gameSettings = main.getGameManager().getGameSettings();
                    if (clickType.isShiftClick())
                        i = 600;
                    if (clickType.isLeftClick()) {
                        gameSettings.setIdleTime(gameSettings.getIdleTime() + i);
                    } else if (clickType.isRightClick()) {
                        gameSettings.setIdleTime(gameSettings.getIdleTime() - i);
                        if (gameSettings.getIdleTime() < 60)
                            gameSettings.setIdleTime(60);
                    }
                    main.getGuiManager().open(player, getClass());
                    break;
                case BOOK_AND_QUILL:
                    main.getGuiManager().open(player, ScenarioManagerGui.class);
                    break;
                case POTION:
                    main.getGuiManager().open(player, EffectsConfigGui.class);
                    break;
                case SKULL_ITEM:
                    teamSize = main.getTeamManager().getTeamsSize();
                    if (clickType.isLeftClick()) {
                        main.getTeamManager().setTeamsSize(teamSize + 1);
                        if (teamSize == 1)
                            Bukkit.getOnlinePlayers().forEach(player1 -> {
                                CrystaliaPlayer crystaliaPlayer = main.getPlayerManager().getExactPlayer(player1);
                                Optional<Team> optionalTeam = Optional.ofNullable(crystaliaPlayer.getTeam());
                                player1.getInventory().setItem(0, (new ItemBuilder(Material.BANNER)).setDisplayName("§6Sélection des équipes §8| §7Clic droit").setDefaultBannerColor(DyeColor.WHITE).setBannerPatterns(optionalTeam.isPresent() ? main.getTeamManager().getBannerPattern(optionalTeam.get()) : Collections.emptyList()).toItemStack());
                            });
                    } else if (clickType.isRightClick()) {
                        if (teamSize > 1)
                            main.getTeamManager().setTeamsSize(teamSize - 1);
                        if (teamSize == 2)
                            Bukkit.getOnlinePlayers().forEach(player1 -> player1.getInventory().remove(Material.BANNER));
                        main.getTeamManager().resetTeams();
                        main.getTeamManager().refreshTeams();
                    }
                    main.getGuiManager().open(player, getClass());
                    break;
                case SAPLING:
                    player.closeInventory();
                    main.getGameManager().getGameWorld().generate(((Border)main.getGameManager().getGameSettings().getBorder()).getBorderInitialSize());
                    break;
                case DIAMOND_SWORD:
                    i = 60;
                    pvp = (Pvp)main.getGameManager().getGameSettings().getPvp();
                    if (clickType.isShiftClick())
                        i = 600;
                    if (clickType.isLeftClick()) {
                        pvp.setTriggerTime(pvp.getTriggerTime() + i);
                    } else if (clickType.isRightClick()) {
                        pvp.setTriggerTime(pvp.getTriggerTime() - i);
                        if (pvp.getTriggerTime() < 60)
                            pvp.setTriggerTime(60);
                    }
                    main.getGuiManager().open(player, getClass());
                    break;
                case WATCH:
                    main.getGuiManager().open(player, DayNightCycleConfigGui.class);
                    break;
                case STAINED_GLASS:
                    main.getGuiManager().open(player, BorderMainGui.class);
                    break;
                case SIGN:
                    if (clickType.isLeftClick() && !main.getGameManager().isStarted()) {
                        main.getGuiManager().open(player, GamemodeSelectorGui.class);
                        break;
                    }
                    if (clickType.isRightClick())
                        main.getGuiManager().open(player, main.getGameManager().getGamemodeUhc().getConfigGui());
                    break;
                case ENCHANTED_BOOK:
                    main.getGuiManager().open(player, EnchantLimitGui.class);
                    break;
                case WORKBENCH:
                    player.closeInventory();
                    player.setGameMode(GameMode.CREATIVE);
                    player.sendMessage(main.getGameManager().getGamemodeUhc().getDisplayNameChat() + "§aVous pouvez désormais éditer l'inventaire de départ.\n§8» §f/h enchant §7pour enchanter un item.\n§8» §f/h finish §7pour définir l'inventaire de départ.");
                            player.getInventory().setContents(main.getGameManager().getGameSettings().getStartInventory());
                    player.getInventory().setArmorContents(main.getGameManager().getGameSettings().getStartArmor());
                    break;
                case EMERALD_BLOCK:
                    player.closeInventory();
                    main.getGameManager().getGamemodeUhc().start(player);
                    break;
                case REDSTONE_BLOCK:
                    player.closeInventory();
                    player.sendMessage(main.getGameManager().getGamemodeUhc().getDisplayNameChat() + "§cAnnulation du démarrage de la partie.");
                    main.getGameManager().getStartTask().cancel();
                    main.getGameManager().setStartTask(new StartTask(main, main.getGameManager()));
                    main.getGameManager().setGameState(GameState.LOADING);
                    break;
                case BARRIER:
                    player.closeInventory();
                    break;
            }
        }
    }
