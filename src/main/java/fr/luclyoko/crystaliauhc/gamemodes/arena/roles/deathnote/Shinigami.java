package fr.luclyoko.crystaliauhc.gamemodes.arena.roles.deathnote;

import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRole;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRolesEnum;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

import java.util.Arrays;
import java.util.List;

public class Shinigami extends ArenaRole {

    BukkitTask shinigamiTask;

    public Shinigami(GameManager gameManager, CrystaliaPlayer crystaliaPlayer) {
        super(gameManager, crystaliaPlayer);
        this.arenaRolesEnum = ArenaRolesEnum.SHINIGAMI;
        addPermEffect(PotionEffectType.DAMAGE_RESISTANCE, 0);
        this.shinigamiTask = Bukkit.getScheduler().runTaskTimer(main, shinigamiRunnable, 5, 40);
    }

    Runnable shinigamiRunnable = () -> {
        if (crystaliaPlayer.isOnline()) {
            if (crystaliaPlayer.getRole() != null && crystaliaPlayer.getRole().equals(Shinigami.this)) {
                for (Entity entity : crystaliaPlayer.getPlayer().getNearbyEntities(15, 15, 15)) {
                    if (entity instanceof Player) {
                        Player target = (Player) entity;
                        CrystaliaPlayer crystaliaTarget = main.getPlayerManager().getExactPlayer(target);
                        if (crystaliaTarget.getRole() instanceof ArenaRole) {
                            ArenaRole arenaRole = (ArenaRole) crystaliaTarget.getRole();
                            if (arenaRole.getArenaRolesEnum().equals(ArenaRolesEnum.MISA)) {
                                addTempEffect(PotionEffectType.SPEED, 0, 4 * 20, false);
                            }
                            if (arenaRole.getArenaRolesEnum().equals(ArenaRolesEnum.LIGHT)) {
                                addTempEffect(PotionEffectType.INCREASE_DAMAGE, 0, 4 * 20, false);
                            }
                        }
                    }
                }
            }
        }
    };

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player target = event.getEntity();
        if (target.getKiller() != null) {
            CrystaliaPlayer crystaliaKiller = main.getPlayerManager().getExactPlayer(target.getKiller());
            if (crystaliaKiller != crystaliaPlayer) return;
            if (crystaliaPlayer.getRole() == null || !crystaliaPlayer.getRole().equals(this)) return;
            if (maxHealth < 30) {
                addMaxHealth(1);
                crystaliaPlayer.sendMessage("§aVotre kill vous rapporte §c1/2❤ §apermanent.");
            }
        }
        CrystaliaPlayer crystaliaDead = main.getPlayerManager().getExactPlayer(target);
        if (crystaliaDead != crystaliaPlayer) return;
        shinigamiTask.cancel();
    }

    @Override
    public List<String> getAdditionalDescription() {
        return Arrays.asList("Vous disposez de l'effet §bSpeed 1 §fpermanent à moins de 15 blocs de §eMisa §fainsi que l'effet §bStrength 1 §fpermanent à moins de 15 blocs de §eLight§f.",
                "Vous gagnez §c1/2❤ permanent §fpar kill (maximum: 15❤).");
    }
}
