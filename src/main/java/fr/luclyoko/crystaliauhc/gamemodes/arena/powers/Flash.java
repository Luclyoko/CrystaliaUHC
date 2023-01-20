package fr.luclyoko.crystaliauhc.gamemodes.arena.powers;

import fr.luclyoko.crystaliauhc.Main;
import fr.luclyoko.crystaliauhc.gamemodes.arena.ArenaUHC;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRole;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Flash extends ArenaPower {
    public Flash(ArenaUHC arenaUHC, ArenaRole arenaRole, Main main) {
        super(arenaUHC, arenaRole, main);
        this.arenaPowerEnum = ArenaPowerEnum.FLASH;
        this.cooldown = 150;
    }

    @Override
    public void execute(CrystaliaPlayer user) {
        Player player = user.getPlayer();

        for (Entity entity : player.getNearbyEntities(35, 35, 35)) {
            if (entity instanceof Player) {
                Player target = (Player) entity;
                if (target != player) {
                    if (main.getPlayerManager().getExactPlayer(target) != null) {
                        CrystaliaPlayer crystaliaTarget = main.getPlayerManager().getExactPlayer(target);
                        if (crystaliaTarget.getRole() != null && crystaliaTarget.getRole() instanceof ArenaRole) {
                            if (target.getHealth() <= 4) target.setHealth(1);
                            else target.setHealth(target.getHealth() - 4);
                            target.getWorld().strikeLightningEffect(target.getLocation());
                        }
                    }
                }
            }
        }
    }

    @Override
    public String getDescription() {
        return "Vous permet d'infliger des éclairs causant §c2❤ §fde dégâts à tous les joueurs dans un rayon de 35 blocs.";
    }
}
