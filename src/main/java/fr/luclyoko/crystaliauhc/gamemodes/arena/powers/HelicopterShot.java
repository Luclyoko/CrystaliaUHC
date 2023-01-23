package fr.luclyoko.crystaliauhc.gamemodes.arena.powers;

import fr.luclyoko.crystaliauhc.Main;
import fr.luclyoko.crystaliauhc.gamemodes.arena.ArenaUHC;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRole;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HelicopterShot extends ArenaPower {

    List<CrystaliaPlayer> blockFallDie;
    public HelicopterShot(ArenaUHC arenaUHC, ArenaRole arenaRole, Main main) {
        super(arenaUHC, arenaRole, main);
        this.arenaPowerEnum = ArenaPowerEnum.TIR_HELICOPTERE;
        this.cooldown = 180;
        this.blockFallDie = new ArrayList<>();
    }

    @Override
    public void execute(CrystaliaPlayer user) {
        Optional<Player> nearestPlayer = Optional.empty();
        double lowestDistance = 0;
        Player player = user.getPlayer();

        for (Entity entity : player.getNearbyEntities(30, 30, 30)) {
            if (entity instanceof Player) {
                Player target = (Player) entity;
                if (target != player) {
                    if (main.getPlayerManager().getExactPlayer(target) != null) {
                        CrystaliaPlayer crystaliaTarget = main.getPlayerManager().getExactPlayer(target);
                        if (crystaliaTarget.getRole() != null && crystaliaTarget.getRole() instanceof ArenaRole) {
                            if (player.hasLineOfSight(target)) {
                                nearestPlayer = Optional.of(target);
                            } else {
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
        }

        if (nearestPlayer.isPresent()) {
            Player target = nearestPlayer.get();

            target.setVelocity(target.getLocation().add(0, 50, 0).toVector().setX(0).setZ(0));
            CrystaliaPlayer crystaliaTarget = main.getPlayerManager().getExactPlayer(target);
            blockFallDie.add(crystaliaTarget);
            Bukkit.getScheduler().runTaskLater(main, () -> blockFallDie.remove(crystaliaTarget), 8*20);

            user.sendMessage("§aVous envoyez §e" + target.getName() + " §adans les airs grâce à votre " + getColoredName() + "§a.");
            target.sendMessage("§e" + user.getRole().getName() + " §cvous envoie dans les airs grâce à son " + getColoredName() + "§c.");
        } else {
            user.sendMessage("§cAucun joueur sur lequel utiliser votre " + getColoredName() + " §cn'a été détecté.");
            lastUse = -1;
        }
    }

    @EventHandler
    public void onFallDeath(EntityDamageEvent event) {
        if (event.isCancelled()) return;
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();

            if (event.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
                CrystaliaPlayer crystaliaPlayer = main.getPlayerManager().getExactPlayer(player);

                if (blockFallDie.contains(crystaliaPlayer) && player.getHealth() - event.getFinalDamage() <= 0) {
                    event.setDamage(0);
                    player.setVelocity(new Vector());
                    player.setHealth(1);
                }
            }
        }
    }

    @Override
    public String getDescription() {
        return "Vous permet d'envoyer le joueur que vous visez dans les airs (environ 50 blocs, laisse à 1/2❤).";
    }
}
