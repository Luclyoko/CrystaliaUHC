package fr.luclyoko.crystaliauhc.gamemodes.arena.roles.lotr;

import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRole;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRolesEnum;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

import java.util.Collections;
import java.util.List;

public class Gollum extends ArenaRole {

    BukkitTask gollumTask;

    public Gollum(GameManager gameManager, CrystaliaPlayer crystaliaPlayer) {
        super(gameManager, crystaliaPlayer);
        this.arenaRolesEnum = ArenaRolesEnum.GOLLUM;
        addMaxHealth(4);
        heal();
        this.gollumTask = Bukkit.getScheduler().runTaskTimer(main, gollumRunnable, 5, 40);
    }

    Runnable gollumRunnable = () -> {
        if (arenaUHC.getArenaRolesManager().isPlayingRole(ArenaRolesEnum.BILBON)) {
            if (crystaliaPlayer.getRole() != null && crystaliaPlayer.getRole().equals(Gollum.this)) {
                addTempEffect(PotionEffectType.INCREASE_DAMAGE, 0, 3 * 20, false);
            }
        }
    };

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player target = event.getEntity();
        CrystaliaPlayer crystaliaDead = main.getPlayerManager().getExactPlayer(target);
        if (crystaliaDead != crystaliaPlayer) return;
        gollumTask.cancel();
    }

    @Override
    public List<String> getAdditionalDescription() {
        return Collections.singletonList("Vous possédez l'effet §bStrength 1 §fpermanent tant que §eBilbon §fest en vie.");
    }
}
