package fr.luclyoko.crystaliauhc.gamemodes.arena.roles.demonslayer;

import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRole;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRolesEnum;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

import java.util.Collections;
import java.util.List;

public class Nezuko extends ArenaRole {

    BukkitTask nezukoTask;

    public Nezuko(GameManager gameManager, CrystaliaPlayer crystaliaPlayer) {
        super(gameManager, crystaliaPlayer);
        this.arenaRolesEnum = ArenaRolesEnum.NEZUKO;
        addPermEffect(PotionEffectType.INCREASE_DAMAGE, 0);
        this.nezukoTask = Bukkit.getScheduler().runTaskTimer(main, nezukoRunnable, 40, 40);
        main.getServer().getPluginManager().registerEvents(this.listener, main);

    }

    Runnable nezukoRunnable = () -> {
        if (crystaliaPlayer.isOnline()) {
            if (crystaliaPlayer.getRole() != null && crystaliaPlayer.getRole().equals(Nezuko.this)) {
                for (Entity entity : crystaliaPlayer.getPlayer().getNearbyEntities(30, 30, 30)) {
                    if (entity instanceof Player) {
                        Player target = (Player) entity;
                        CrystaliaPlayer crystaliaTarget = main.getPlayerManager().getExactPlayer(target);
                        if (crystaliaTarget.getRole() instanceof ArenaRole) {
                            ArenaRole arenaRole = (ArenaRole) crystaliaTarget.getRole();
                            if (arenaRole.getArenaRolesEnum().equals(ArenaRolesEnum.TANJIRO)) {
                                addTempEffect(PotionEffectType.DAMAGE_RESISTANCE, 0, 4 * 20, false);
                            }
                        }
                    }
                }
            }
        }
    };

    Listener listener = new Listener() {
        @EventHandler
        public void onPlayerDeath(PlayerDeathEvent event) {
            Player target = event.getEntity();
            CrystaliaPlayer crystaliaDead = main.getPlayerManager().getExactPlayer(target);
            if (crystaliaDead != crystaliaPlayer) return;
            nezukoTask.cancel();
        }
    };

    @Override
    public List<String> getPowersDescriptionList() {
        return Collections.singletonList("Vous disposez de l'effet §bResistance 1 §fpermanent à moins de 30 blocs de §eTanjiro§f.");
    }
}
