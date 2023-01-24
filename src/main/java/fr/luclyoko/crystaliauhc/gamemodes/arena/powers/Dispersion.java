package fr.luclyoko.crystaliauhc.gamemodes.arena.powers;

import fr.luclyoko.crystaliauhc.Main;
import fr.luclyoko.crystaliauhc.game.TeleportationTask;
import fr.luclyoko.crystaliauhc.gamemodes.arena.ArenaUHC;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRole;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import org.bukkit.Bukkit;

public class Dispersion extends ArenaPower {
    public Dispersion(ArenaUHC arenaUHC, ArenaRole arenaRole, Main main) {
        super(arenaUHC, arenaRole, main);
        this.arenaPowerEnum = ArenaPowerEnum.DISPERSION;
        this.uses = 1;
    }

    @Override
    public void execute(CrystaliaPlayer user) {
        main.getPlayerManager().getOnlineAlivePlayers().stream().filter(crystaliaPlayer -> crystaliaPlayer != user).forEach(crystaliaPlayer -> {
            crystaliaPlayer.getPlayer().teleport(TeleportationTask.generateRandomLocation());
            crystaliaPlayer.getRole().setNoFall(true);
            Bukkit.getScheduler().runTaskLater(main, () -> crystaliaPlayer.getRole().setNoFall(false), 60);
        });
        Bukkit.broadcastMessage("§e" + user.getRole().getName() + " §ca décidé de disperser les joueurs aléatoirement sur la carte.");
        user.getRole().removeMaxHealth(4);
    }

    @Override
    public String getDescription() {
        return "Vous permet de téléporter aléatoirement sur la carte l'ensemble des joueurs en échange de §c2❤ permanents§f.";
    }
}
