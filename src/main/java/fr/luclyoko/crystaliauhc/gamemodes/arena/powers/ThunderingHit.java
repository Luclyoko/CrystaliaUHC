package fr.luclyoko.crystaliauhc.gamemodes.arena.powers;

import fr.luclyoko.crystaliauhc.Main;
import fr.luclyoko.crystaliauhc.gamemodes.arena.ArenaUHC;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRole;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Optional;

public class ThunderingHit extends ArenaPower {
    public ThunderingHit(ArenaUHC arenaUHC, ArenaRole arenaRole, Main main) {
        super(arenaUHC, arenaRole, main);
        this.arenaPowerEnum = ArenaPowerEnum.FRAPPE_FOUDROYANTE;
        this.cooldown = 180;
    }

    @Override
    public void execute(CrystaliaPlayer user) {
        Optional<Player> nearestPlayer = Optional.empty();
        double lowestDistance = 0;
        Player player = user.getPlayer();

        for (Entity entity : player.getNearbyEntities(60, 60, 60)) {
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
            player.teleport(target.getLocation());
            if (target.getHealth() <= 4) target.setHealth(1);
            else target.setHealth(target.getHealth() - 4);
            target.getWorld().strikeLightningEffect(target.getLocation());
            user.sendMessage("§aVous avez été téléporté sur §e" + target.getName() + " §agrâce à votre " + getColoredName() + "§a.");
            target.sendMessage("§e" + user.getRole().getName() + " §cs'est téléporté sur vous grâce à sa " + getColoredName() + "§c.");
        } else {
            user.sendMessage("§cAucun joueur sur lequel vous téléporter n'a été détecté.");
            lastUse = -1;
        }
    }

    @Override
    public String getDescription() {
        return "Vous permet de vous téléporter sur un joueur, lui infligeant §c2❤ §fde dégâts avec un éclair.";
    }
}
