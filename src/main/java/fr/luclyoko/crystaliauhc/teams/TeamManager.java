package fr.luclyoko.crystaliauhc.teams;

import fr.luclyoko.crystaliauhc.Main;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import fr.luclyoko.crystaliauhc.utils.ItemBuilder;
import fr.luclyoko.crystaliauhc.utils.scoreboard.ScoreboardTeam;
import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class TeamManager {
    private Main main;

    private List<Team> teams;

    private int teamsSize;

    public TeamManager(Main main) {
        this.main = main;
        this.teams = new ArrayList<>();
        this.teamsSize = 1;
        int id = 0;
        for (TeamAssets.TeamSymbols teamSymbols : TeamAssets.TeamSymbols.values()) {
            for (TeamAssets.TeamColors teamColors : TeamAssets.TeamColors.values())
                this.teams.add(new Team(main, teamColors, teamSymbols, ++id));
        }
    }

    public int getTeamsSize() {
        return this.teamsSize;
    }

    public void setTeamsSize(int teamsSize) {
        this.teamsSize = teamsSize;
    }

    public void resetTeams() {
        this.teams.forEach(team -> {
            team.getMembers().forEach(crystaliaPlayer -> crystaliaPlayer.setTeam(null));
            team.getMembers().clear();
        });
        refreshTeams();
    }

    public List<Team> getTeams() {
        return this.teams;
    }

    public void refreshTeams() {
        ScoreboardTeam defaultTeam = new ScoreboardTeam("999", "§r");
                ScoreboardTeam modTeam = new ScoreboardTeam("0", "§bMod §8» §r");
        for (Player player : Bukkit.getOnlinePlayers()) {
            for (Team team : this.main.getTeamManager().getTeams()) {
                ScoreboardTeam sbTeam = team.getScoreboardTeam();
                (((CraftPlayer)player).getHandle()).playerConnection.sendPacket((Packet)sbTeam.createTeam());
            }
            (((CraftPlayer)player).getHandle()).playerConnection.sendPacket((Packet)modTeam.createTeam());
            (((CraftPlayer)player).getHandle()).playerConnection.sendPacket((Packet)defaultTeam.createTeam());
        }
        for (Player players : Bukkit.getOnlinePlayers()) {
            for (Player players1 : Bukkit.getOnlinePlayers()) {
                CrystaliaPlayer crystaliaPlayer = this.main.getPlayerManager().getExactPlayer(players1);
                if (crystaliaPlayer != null) {
                    Optional<Team> optionalTeam = Optional.ofNullable(crystaliaPlayer.getTeam());
                    if (optionalTeam.isPresent()) {
                        Team team = optionalTeam.get();
                        (((CraftPlayer)players).getHandle()).playerConnection.sendPacket((Packet)team.getScoreboardTeam().addOrRemovePlayer(3, players1.getName()));
                    }
                    if (crystaliaPlayer.isMod()) {
                        (((CraftPlayer)players).getHandle()).playerConnection.sendPacket((Packet)modTeam.addOrRemovePlayer(3, players1.getName()));
                        continue;
                    }
                    (((CraftPlayer)players).getHandle()).playerConnection.sendPacket((Packet)defaultTeam.addOrRemovePlayer(3, players1.getName()));
                }
            }
        }
    }

    public ItemStack getTeamsBannerSelection(CrystaliaPlayer crystaliaPlayer) {
        Optional<Team> optionalTeam = Optional.ofNullable(crystaliaPlayer.getTeam());
        return (new ItemBuilder(Material.BANNER))
                .setDisplayName("§6Sélection des équipes §8| §7Clic droit")
                .setDefaultBannerColor(optionalTeam.isPresent() ? ((Team)optionalTeam.get()).getTeamColor().getDyeColor() : DyeColor.WHITE)
                .setBannerPatterns(optionalTeam.isPresent() ? getBannerPattern(optionalTeam.get()) : Collections.emptyList())
                .addItemFlags(ItemFlag.HIDE_POTION_EFFECTS)
                .toItemStack();
    }

    public List<Pattern> getBannerPattern(Team team) {
        DyeColor backColor = team.getTeamColor().getDyeColor();
        switch (team.getTeamSymbol()) {
            case HEART:
                return Arrays.asList(new Pattern[] { new Pattern(DyeColor.WHITE, PatternType.RHOMBUS_MIDDLE),
                        new Pattern(backColor, PatternType.TRIANGLE_TOP) });
            case STAR:
                return Arrays.asList(new Pattern[] { new Pattern(backColor, PatternType.STRIPE_MIDDLE),
                        new Pattern(DyeColor.WHITE, PatternType.FLOWER),
                        new Pattern(backColor, PatternType.STRIPE_TOP),
                        new Pattern(backColor, PatternType.STRIPE_BOTTOM),
                        new Pattern(DyeColor.WHITE, PatternType.RHOMBUS_MIDDLE) });
            case SKULL:
                return Collections.singletonList(new Pattern(DyeColor.WHITE, PatternType.SKULL));
        }
        return Collections.emptyList();
    }
}
