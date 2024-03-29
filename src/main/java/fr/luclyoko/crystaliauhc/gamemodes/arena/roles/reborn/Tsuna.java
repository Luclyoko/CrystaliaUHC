package fr.luclyoko.crystaliauhc.gamemodes.arena.roles.reborn;

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

public class Tsuna extends ArenaRole {

    BukkitTask tsunaTask;

    public Tsuna(GameManager gameManager, CrystaliaPlayer crystaliaPlayer) {
        super(gameManager, crystaliaPlayer);
        this.arenaRolesEnum = ArenaRolesEnum.TSUNA;
        addPermEffect(PotionEffectType.INCREASE_DAMAGE, 0);
        this.tsunaTask = Bukkit.getScheduler().runTaskTimer(main, tsunaRunnable, 5, 40);
    }

    Runnable tsunaRunnable = () -> {
        if (arenaUHC.getArenaRolesManager().isPlayingRole(ArenaRolesEnum.REBORN)) {
            if (crystaliaPlayer.getRole() != null && crystaliaPlayer.getRole().equals(Tsuna.this)) {
                addTempEffect(PotionEffectType.SPEED, 0, 3 * 20, false);
            }
        }
    };

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player target = event.getEntity();
        CrystaliaPlayer crystaliaDead = main.getPlayerManager().getExactPlayer(target);
        if (crystaliaDead != crystaliaPlayer) return;
        tsunaTask.cancel();
    }

    @Override
    public List<String> getAdditionalDescription() {
        return Collections.singletonList("Vous disposez de l'effet §bSpeed 1 §fen permanence tant que §eReborn §fest en vie.");
    }
}
