package fr.luclyoko.crystaliauhc.gamemodes.arena.powers;

import fr.luclyoko.crystaliauhc.Main;
import fr.luclyoko.crystaliauhc.gamemodes.arena.ArenaUHC;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRole;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Optional;

public class MisaDeathNote extends ArenaPower {

    public MisaDeathNote(ArenaUHC arenaUHC, ArenaRole arenaRole, Main main) {
        super(arenaUHC, arenaRole, main);
        this.arenaPowerEnum = ArenaPowerEnum.MISA_DEATH_NOTE;
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
            CrystaliaPlayer crystaliaPlayer = main.getPlayerManager().getExactPlayer(target);
            if (crystaliaPlayer.getRole() != null && crystaliaPlayer.getRole() instanceof ArenaRole) {
                crystaliaPlayer.getRole().removeMaxHealth(6);
                user.sendMessage("§aVous avez appliqué votre " + getColoredName() + " sur §e" + target.getName() + "§a.");
                target.sendMessage("§e" + user.getRole().getName() + " §cvous a appliqué son " + getColoredName() + "§c.");
            }
        } else {
            user.sendMessage("§cAucun joueur sur lequel appliquer votre " + getColoredName() + " §cn'a été détecté.");
            lastUse = -1;
        }
    }

    @Override
    public String getDescription() {
        return "Vous permet de retirer §c3❤ permanents §fau joueur que vous visez.";
    }
}
