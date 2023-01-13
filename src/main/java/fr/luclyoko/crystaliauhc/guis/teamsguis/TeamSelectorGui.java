package fr.luclyoko.crystaliauhc.guis.teamsguis;

import fr.luclyoko.crystaliauhc.guis.GuiBuilder;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import fr.luclyoko.crystaliauhc.teams.Team;
import fr.luclyoko.crystaliauhc.teams.TeamAssets;
import fr.luclyoko.crystaliauhc.utils.ItemBuilder;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;

import java.util.*;

public class TeamSelectorGui implements GuiBuilder {
    public Map<BannerMeta, Team> teamMap = new HashMap<>();

    public String name() {
        return "§dSélection des équipes";
    }

    public int getSize() {
        return 54;
    }

    public void contents(Player player, Inventory inv) {
        for (int i = 0; i < getSize(); i++) {
            if ((i <= 8 || i >= getSize() - 9 || i % 9 == 0 || i % 9 == 8) && i !=

                    getSize() - 1)
                inv.setItem(i, (new ItemBuilder(Material.STAINED_GLASS_PANE, (byte)6))
                        .setAmount(1)
                        .setDisplayName(" ")
                        .toItemStack());
        }
        for (Team team : main.getTeamManager().getTeams()) {
            List<String> lore = new ArrayList<>();
            if (team.getMembers().size() > 0)
                lore.add(" ");
            for (CrystaliaPlayer crystaliaPlayer : team.getMembers())
                lore.add("§8» §7" + crystaliaPlayer.getPlayerName());
            ItemBuilder banner = (new ItemBuilder(Material.BANNER)).setDisplayName(team.getTeamColor().getDisplayColor() + team.getTeamSymbol().getSymbol().concat(team.getTeamSymbol().equals(TeamAssets.TeamSymbols.CLASSIC) ? "" : " ") + team.getTeamColor().getDisplayName() + " §7" + team.getMembers().size() + "/" + main.getTeamManager().getTeamsSize()).setLore(lore).setDefaultBannerColor(team.getTeamColor().getDyeColor()).setBannerPatterns(main.getTeamManager().getBannerPattern(team)).addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
            inv.addItem(banner.toItemStack());
            this.teamMap.put(banner.getBannerMeta(), team);
        }
        inv.setItem(49, (new ItemBuilder(Material.BANNER))
                .setDisplayName("§f§lÉquipe aléatoire")
                        .setDefaultBannerColor(DyeColor.WHITE)
                        .toItemStack());
        inv.setItem(getSize() - 1, (new ItemBuilder(Material.BARRIER))
                .setDisplayName("§cFermer le menu")
                .setLore(Arrays.asList(" ", "§8» §7Clic gauche | §aFermer")).toItemStack());
                }

        public void onClick(Player player, Inventory inv, ItemStack current, int slot, ClickType clickType) {
            if (current.getType().equals(Material.BANNER)) {
                BannerMeta bannerMeta = (BannerMeta)current.getItemMeta();
                if (this.teamMap.containsKey(bannerMeta)) {
                    Team teamToJoin = this.teamMap.get(bannerMeta);
                    if (clickType.isLeftClick()) {
                        if (teamToJoin.isFull()) {
                            player.sendMessage(main.getGameManager().getGamemodeUhc().getDisplayNameChat() + "§cCette équipe est déjà pleine.");
                            main.getGuiManager().open(player, getClass());
                            return;
                        }
                        CrystaliaPlayer crystaliaPlayer = main.getPlayerManager().getExactPlayer(player);
                        Team team = crystaliaPlayer.getTeam();
                        if (team != null)
                            team.removePlayer(crystaliaPlayer);
                        teamToJoin.addMember(crystaliaPlayer);
                        main.getTeamManager().refreshTeams();
                        player.closeInventory();
                        player.sendMessage(main.getGameManager().getGamemodeUhc().getDisplayNameChat() + "§aVous avez rejoint l'équipe " + teamToJoin.getName() + "§a.");
                                player.getInventory().setItem(0, (new ItemBuilder(Material.BANNER))
                                        .setDisplayName("§6Sélection des équipes §8| §7Clic droit")
                                        .setDefaultBannerColor(teamToJoin.getTeamColor().getDyeColor())
                                        .setBannerPatterns(main.getTeamManager().getBannerPattern(teamToJoin))
                                        .addItemFlags(ItemFlag.HIDE_POTION_EFFECTS)
                                        .toItemStack());
                    }
                } else if (clickType.isLeftClick()) {
                    CrystaliaPlayer crystaliaPlayer = main.getPlayerManager().getExactPlayer(player);
                    Team team = crystaliaPlayer.getTeam();
                    if (team != null)
                        team.removePlayer(crystaliaPlayer);
                    crystaliaPlayer.setTeam(null);
                    main.getTeamManager().refreshTeams();
                    player.closeInventory();
                    player.sendMessage(main.getGameManager().getGamemodeUhc().getDisplayNameChat() + "§aVous serez placé dans une équipe aléatoire.");
                            player.getInventory().setItem(0, (new ItemBuilder(Material.BANNER))
                                    .setDisplayName("§6Sélection des équipes §8| §7Clic droit")
                                    .setDefaultBannerColor(DyeColor.WHITE)
                                    .addItemFlags(ItemFlag.HIDE_POTION_EFFECTS)
                                    .toItemStack());
                }
            } else if (current.getType().equals(Material.BARRIER)) {
                player.closeInventory();
            }
        }
    }
