package fr.luclyoko.crystaliauhc.gamemodes.kwamiuhc;

import fr.luclyoko.crystaliauhc.Main;
import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.gamemodes.GamemodeUHC;
import fr.luclyoko.crystaliauhc.gamemodes.UHCGamemodes;
import org.bukkit.Bukkit;
import org.bukkit.Material;

public class KwamiUHC extends GamemodeUHC {
    public KwamiUHC(Main main, GameManager gameManager) {
        super(main, gameManager);
        this.defaultName = "§c§lKwami §f§lUHC";
        this.gamemodeEnum = UHCGamemodes.KWAMI_UHC;
        this.maxTeamSize = 1;
        this.displayName = getDefaultName();
        main.getTeamManager().setTeamsSize(1);
        main.getTeamManager().resetTeams();
        Bukkit.getOnlinePlayers().forEach(player1 -> player1.getInventory().remove(Material.BANNER));
    }
}
