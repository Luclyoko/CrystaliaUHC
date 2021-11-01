package fr.luclyoko.crystaliauhc.utils.scoreboard;

import fr.luclyoko.crystaliauhc.Main;
import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.game.GameState;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

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
    private Player player;
    private final UUID uuid;
    private final ObjectiveSign objectiveSign;
    private int lastShowedBoard = 0;

    PersonalScoreboard(Player player){
        this.player = player;
        uuid = player.getUniqueId();
        objectiveSign = new ObjectiveSign("sidebar", "BleachUHC");

        reloadData();
        objectiveSign.addReceiver(player);
    }

    public void reloadData(){}

    public void setLines(String ip) {
        int i = 0;
        objectiveSign.setDisplayName(gameManager.getGamemodeUhc().displayName);
        objectiveSign.setLine(i++, "§1");
        objectiveSign.setLine(i++, "§f§lINFOS");
        objectiveSign.setLine(i, " §7» §cStatut: " + gameManager.getGameState().getScoreboardDisplay());

        switch (gameManager.getGameState()) {
            case PLAYING:
                //TODO Ajouter timer quand il sera codé
                objectiveSign.setLine(i++, " §7» §cTemps: ");
                break;

            case FINISHED:
                //TODO Ajouter team victorieuse
                objectiveSign.setLine(i++, " §7» §6Victoire: ");
                break;
            default:
                i++;
                break;
        }

        //TODO Ajouter nombre de joueurs / slots dispos
        objectiveSign.setLine(i++, " §7» §cJoueurs: ");
        if (gameManager.getGameState().equals(GameState.PLAYING)) {
            objectiveSign.setLine(i++, "§2");
            objectiveSign.setLine(i++, "§f§lPARTIE");
            for (String line : gameManager.getGamemodeUhc().getOptionalScoreboardLines()) {
                objectiveSign.setLine(i++, line);
            }
            objectiveSign.setLine(i++, "§3");
            objectiveSign.setLine(i++, "§f§lBORDURE");
            //TODO Ajouter infos bordure
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