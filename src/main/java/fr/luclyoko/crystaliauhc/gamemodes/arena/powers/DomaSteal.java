package fr.luclyoko.crystaliauhc.gamemodes.arena.powers;

import fr.luclyoko.crystaliauhc.Main;
import fr.luclyoko.crystaliauhc.gamemodes.arena.ArenaUHC;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRole;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class DomaSteal extends ArenaPower {
    public DomaSteal(ArenaUHC arenaUHC, ArenaRole arenaRole, Main main) {
        super(arenaUHC, arenaRole, main);
        this.arenaPowerEnum = ArenaPowerEnum.DOMA;
        this.uses = 1;
    }

    @Override
    public void execute(CrystaliaPlayer user) {
        Player player = user.getPlayer();

        for (Entity entity : player.getNearbyEntities(30, 30, 30)) {
            if (entity instanceof Player) {
                Player target = (Player) entity;
                if (target != player) {
                    if (main.getPlayerManager().getExactPlayer(target) != null) {
                        CrystaliaPlayer crystaliaTarget = main.getPlayerManager().getExactPlayer(target);
                        if (crystaliaTarget.getRole() != null && crystaliaTarget.getRole() instanceof ArenaRole) {
                            ArenaRole targetRole = (ArenaRole) crystaliaTarget.getRole();
                            if (!targetRole.getPowers().isEmpty()) {
                                List<ArenaPower> targetPowers = new ArrayList<>(targetRole.getPowers());
                                for (ArenaPower power : targetPowers) {
                                    if (power.getUses() != 0) {
                                        targetRole.removePower(power);
                                        arenaRole.addPower(power);
                                        power.setArenaRole(arenaRole);
                                        user.giveItem(power.getPowerItem());
                                        target.getInventory().remove(power.getPowerItem());
                                        user.sendMessage("§aVous volez le pouvoir " + power.getColoredName() + " §aà §e" + crystaliaTarget.getPlayerName() + "§a.");
                                        crystaliaTarget.sendMessage("§e" + arenaRole.getName() + " §cvous vole votre " + power.getColoredName() + "§c.");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public String getDescription() {
        return "Vous permet de voler les pouvoirs de tous les joueurs situés à moins de 30 blocs de vous.";
    }
}
