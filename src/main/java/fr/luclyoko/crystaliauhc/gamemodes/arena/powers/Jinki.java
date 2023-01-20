package fr.luclyoko.crystaliauhc.gamemodes.arena.powers;

import fr.luclyoko.crystaliauhc.Main;
import fr.luclyoko.crystaliauhc.gamemodes.arena.ArenaUHC;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRole;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.Optional;

public class Jinki extends ArenaPower {
    public Jinki(ArenaUHC arenaUHC, ArenaRole arenaRole, Main main) {
        super(arenaUHC, arenaRole, main);
        this.arenaPowerEnum = ArenaPowerEnum.JINKI;
        this.cooldown = 120;
        this.duration = 10;
    }

    @Override
    public void execute(CrystaliaPlayer user) {
        Optional<Player> nearestPlayer = Optional.empty();
        double lowestDistance = 0;
        Player player = user.getPlayer();

        for (Entity entity : player.getNearbyEntities(10, 10, 10)) {
            if (entity instanceof Player) {
                Player target = (Player) entity;
                if (target != player) {
                    if (main.getPlayerManager().getExactPlayer(target) != null) {
                        CrystaliaPlayer crystaliaTarget = main.getPlayerManager().getExactPlayer(target);
                        if (crystaliaTarget.getRole() != null && crystaliaTarget.getRole() instanceof ArenaRole) {
                            Vector towardsEntity = target.getLocation().subtract(player.getLocation()).toVector().normalize();
                            if (player.getLocation().getDirection().distance(towardsEntity) < 0.1) {
                                double distance = player.getLocation().distance(target.getLocation());
                                if (lowestDistance == 0 || distance < lowestDistance) {
                                    lowestDistance = distance;
                                    nearestPlayer = Optional.of(target);
                                }
                            }
                        }
                    }
                }
            }
        }

        if (nearestPlayer.isPresent()) {
            Player target = nearestPlayer.get();
            CrystaliaPlayer crystaliaTarget = main.getPlayerManager().getExactPlayer(target);
            if (crystaliaTarget.getRole() != null && crystaliaTarget.getRole() instanceof ArenaRole) {
                crystaliaTarget.getRole().addTempEffect(PotionEffectType.BLINDNESS, 0, duration * 20, false);
                user.sendMessage("§aVous avez appliqué " + getColoredName() + " sur §e" + target.getName() + "§a.");
                crystaliaTarget.sendMessage("§e" + user.getRole().getName() + " §cvous a infligé le pouvoir de " + getColoredName() + "§c.");
            }
        } else {
            user.sendMessage("§cAucun joueur sur lequel appliquer votre pouvoir n'a été détecté.");
            lastUse = -1;
        }
    }

    @Override
    public String getDescription() {
        return "Vous permet d'infliger l'effet §bBlindness 1 §fà un joueur pendant 10 secondes.";
    }
}
