package fr.luclyoko.crystaliauhc.teams;

import fr.luclyoko.crystaliauhc.Main;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import fr.luclyoko.crystaliauhc.utils.scoreboard.ScoreboardTeam;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;

public class Team {
    private Main main;

    private TeamAssets.TeamColors teamColor;

    private TeamAssets.TeamSymbols teamSymbol;

    private ScoreboardTeam scoreboardTeam;

    private List<CrystaliaPlayer> members;

    private Location spawnLoc;

    public Team(Main main, TeamAssets.TeamColors teamColor, TeamAssets.TeamSymbols teamSymbol, int id) {
        this.main = main;
        this.teamColor = teamColor;
        this.teamSymbol = teamSymbol;
        this.members = new ArrayList<>();
        this.scoreboardTeam = new ScoreboardTeam(Integer.toString(id), teamColor.getDisplayColor() + teamSymbol.getSymbol().concat(teamSymbol.equals(TeamAssets.TeamSymbols.CLASSIC) ? "" : " "));
        this.spawnLoc = main.getGameManager().getGameWorld().getCenter();
    }

    public TeamAssets.TeamColors getTeamColor() {
        return this.teamColor;
    }

    public TeamAssets.TeamSymbols getTeamSymbol() {
        return this.teamSymbol;
    }

    public String getName() {
        return this.teamColor.getDisplayColor() + this.teamSymbol.getSymbol().concat(this.teamSymbol.equals(TeamAssets.TeamSymbols.CLASSIC) ? "" : " ") + this.teamColor.getDisplayName();
    }

    public boolean isFull() {
        return (this.members.size() == this.main.getTeamManager().getTeamsSize());
    }

    public void addMember(CrystaliaPlayer crystaliaPlayer) {
        if (this.members.size() >= this.main.getTeamManager().getTeamsSize())
            return;
        this.members.add(crystaliaPlayer);
        crystaliaPlayer.setTeam(this);
    }

    public void removePlayer(CrystaliaPlayer crystaliaPlayer) {
        this.members.remove(crystaliaPlayer);
        crystaliaPlayer.setTeam(null);
    }

    public List<CrystaliaPlayer> getMembers() {
        return this.members;
    }

    public ScoreboardTeam getScoreboardTeam() {
        return this.scoreboardTeam;
    }

    public Location getSpawnLoc() {
        return this.spawnLoc;
    }

    public void setSpawnLoc(Location spawnLoc) {
        this.spawnLoc = spawnLoc;
    }
}
