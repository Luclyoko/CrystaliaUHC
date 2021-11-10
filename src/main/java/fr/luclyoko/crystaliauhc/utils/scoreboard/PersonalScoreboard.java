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

/*
 * This file is part of SamaGamesAPI.
 *
 * SamaGamesAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SamaGamesAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SamaGamesAPI.  If not, see <http://www.gnu.org/licenses/>.
 */
public class PersonalScoreboard {
    private final Main main = Main.getInstance();
    private final GameManager gameManager = main.getGameManager();
    private final Player player;
    private final UUID uuid;
    private final ObjectiveSign objectiveSign;
    private int lastShowedBoard = 0;

    PersonalScoreboard(Player player){
        this.player = player;
        uuid = player.getUniqueId();
        objectiveSign = new ObjectiveSign("sidebar", "CrystaliaUHC");

        reloadData();
        objectiveSign.addReceiver(player);
    }

    public void reloadData(){}

    public void setLines(String ip) {
        int i = 0;
        SimpleDateFormat timers = new SimpleDateFormat("mm:ss");
        timers.setTimeZone(TimeZone.getTimeZone("GMT+1"));
        SimpleDateFormat dateAndHour = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        dateAndHour.setTimeZone(TimeZone.getTimeZone("GMT+1"));
        objectiveSign.setDisplayName(ChatColor.BOLD + gameManager.getGamemodeUhc().getDisplayName());
        objectiveSign.setLine(i++, "§7§o" + dateAndHour.format(System.currentTimeMillis()));
        objectiveSign.setLine(i++, "§f§lINFOS");
        objectiveSign.setLine(i++, " §8» §aHost: §r" + gameManager.getGameSettings().getHostName());
        objectiveSign.setLine(i, " §8» §cStatut: §r" + gameManager.getGameState().getScoreboardDisplay());

        switch (gameManager.getGameState()) {
            case PLAYING:
                objectiveSign.setLine(i++, " §8» §cTimer: §r" + timers.format(gameManager.getGameTask().getTimer() * 1000));
                break;

            case FINISHED:
                //TODO Ajouter team victorieuse
                objectiveSign.setLine(i++, " §8» §6Victoire: §r");
                break;
            default:
                i++;
                break;
        }

        //TODO Ajouter nombre de joueurs / slots dispos
        objectiveSign.setLine(i++, " §8» §cJoueurs: §r");
        if (gameManager.getGameState().equals(GameState.PLAYING)) {
            objectiveSign.setLine(i++, "§2");
            objectiveSign.setLine(i++, "§f§lPARTIE");
            objectiveSign.setLine(i++, " §8» §cPvP: §r" + (gameManager.getGameSettings().getPvp().hasTriggered() ? "§aActif" : timers.format((gameManager.getGameSettings().getPvp().getTriggerTime() - gameManager.getGameTask().getTimer()) *1000)));
            for (String line : gameManager.getGamemodeUhc().getOptionalScoreboardLines(main.getPlayerManager().getExactPlayer(player))) {
                objectiveSign.setLine(i++, line);
            }
            objectiveSign.setLine(i++, "§3");
            objectiveSign.setLine(i++, "§f§lBORDURE");
            objectiveSign.setLine(i++, " §8» §eTemps: §r" + timers.format((gameManager.getGameSettings().getBorder().getTriggerTime() - gameManager.getGameTask().getTimer()) *1000));
            objectiveSign.setLine(i++, " §8» §eTaille: §r± " + (int) player.getWorld().getWorldBorder().getSize() / 2);
        }
        objectiveSign.setLine(i++, "§4");
        objectiveSign.setLine(i++, ip);
        for (int i1 = i; i1<i+10; i1++) {
            objectiveSign.setLine(i1, "§9");
            objectiveSign.removeScore("§9");
        }

        objectiveSign.updateLines();
    }


    public void onLogout(){
        objectiveSign.removeReceiver(Bukkit.getServer().getOfflinePlayer(uuid));
    }
}