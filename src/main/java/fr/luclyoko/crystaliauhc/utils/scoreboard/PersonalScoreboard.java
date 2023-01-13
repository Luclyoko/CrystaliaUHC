package fr.luclyoko.crystaliauhc.utils.scoreboard;

import fr.luclyoko.crystaliauhc.Main;
import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.game.GameState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.UUID;

public class PersonalScoreboard {
    private final Main main = Main.getInstance();

    private final GameManager gameManager = this.main.getGameManager();

    private final Player player;

    private final UUID uuid;

    private final ObjectiveSign objectiveSign;

    private int lastShowedBoard = 0;

    PersonalScoreboard(Player player) {
        this.player = player;
        this.uuid = player.getUniqueId();
        this.objectiveSign = new ObjectiveSign("sidebar", "CrystaliaUHC");
        reloadData();
        this.objectiveSign.addReceiver(player);
    }

    public void reloadData() {}

    public void setLines(String ip) {
        int i = 0;
        SimpleDateFormat timers = new SimpleDateFormat("mm:ss");
        timers.setTimeZone(TimeZone.getTimeZone("GMT+1"));
        SimpleDateFormat dateAndHour = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        dateAndHour.setTimeZone(TimeZone.getTimeZone("GMT+1"));
        this.objectiveSign.setDisplayName(ChatColor.BOLD + this.gameManager.getGamemodeUhc().getDisplayName() + (this.gameManager.getGameSettings().isDevMode() ? " §a§o(dev mode)" : ""));
        this.objectiveSign.setLine(i++, "§7§o"+ dateAndHour.format(System.currentTimeMillis()));
        this.objectiveSign.setLine(i++, "§f§lINFOS");
        this.objectiveSign.setLine(i++, " §8» §aHost: §r" + this.gameManager.getGameSettings().getHostName());
        this.objectiveSign.setLine(i, " §8» §cStatut: §r" + this.gameManager.getGameState().getScoreboardDisplay());
        switch (this.gameManager.getGameState()) {
            case LOADING:
                i++;
                this.objectiveSign.setLine(i++, " §8» §cÉquipes: §r" + ((this.main.getTeamManager().getTeamsSize() == 1) ? "FFA" : ("To" + this.main.getTeamManager().getTeamsSize())));
                this.objectiveSign.setLine(i++, " §8» §cMode: §r" + this.gameManager.getGamemodeUhc().getDefaultName());
                break;
            case PLAYING:
                this.objectiveSign.setLine(i++, " §8» §cTimer: §r" + timers.format(this.gameManager.getGameTask().getTimer() * 1000) + (this.gameManager.getGameSettings().isDay() ? " §7(§e§lJour§7)" : " §7(§8§lNuit§7)"));
                break;
            case FINISHED:
                this.objectiveSign.setLine(i++, " §8» §cDurée: §r" + timers.format(this.gameManager.getGameTask().getTimer() * 1000));
                this.objectiveSign.setLine(i++, "§2");
                this.objectiveSign.setLine(i++, " §8» §6Victoire: §r" + this.gameManager.getGameSettings().getWinner());
                this.objectiveSign.setLine(i++, "§3");
                break;
            default:
                i++;
                break;
        }
        this.objectiveSign.setLine(i++, " §8» §cJoueurs: §r" + this.main.getPlayerManager().getGamePlayers().size() + "/" + this.gameManager.getGameSettings().getSlots());
        if (this.gameManager.getGameState().equals(GameState.PLAYING)) {
            this.objectiveSign.setLine(i++, "§4");
            this.objectiveSign.setLine(i++, "§f§lPARTIE");
            this.objectiveSign.setLine(i++, " §8» §cPvP: §r" + (this.gameManager.getGameSettings().getPvp().hasTriggered() ? "§a§l✔" : timers.format((this.gameManager.getGameSettings().getPvp().getTriggerTime() - this.gameManager.getGameTask().getTimer()) * 1000)));
            for (String line : this.gameManager.getGamemodeUhc().getOptionalScoreboardLines(this.main.getPlayerManager().getExactPlayer(this.player)))
                this.objectiveSign.setLine(i++, line);
            this.objectiveSign.setLine(i++, "§5");
            this.objectiveSign.setLine(i++, "§f§lBORDURE");
            this.objectiveSign.setLine(i++, " §8» §eTemps: §r" + (this.gameManager.getGameSettings().getBorder().hasTriggered() ? "§a§l✔" : timers.format((this.gameManager.getGameSettings().getBorder().getTriggerTime() - this.gameManager.getGameTask().getTimer()) * 1000)));
            this.objectiveSign.setLine(i++, " §8» §eTaille: §r± " + ((int)this.player.getWorld().getWorldBorder().getSize() / 2));
        }
        this.objectiveSign.setLine(i++, "§6");
        this.objectiveSign.setLine(i++, ip);
        for (int i1 = i; i1 < i + 10; i1++) {
            this.objectiveSign.setLine(i1, "§9");
            this.objectiveSign.removeScore("§9");
        }
        this.objectiveSign.updateLines();
    }

    public void onLogout() {
        this.objectiveSign.removeReceiver(Bukkit.getServer().getOfflinePlayer(this.uuid));
    }
}
