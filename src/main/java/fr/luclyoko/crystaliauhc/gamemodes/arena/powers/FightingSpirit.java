package fr.luclyoko.crystaliauhc.gamemodes.arena.powers;

import fr.luclyoko.crystaliauhc.Main;
import fr.luclyoko.crystaliauhc.gamemodes.arena.ArenaUHC;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRole;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import org.bukkit.Bukkit;

public class FightingSpirit extends ArenaPower {
    public FightingSpirit(ArenaUHC arenaUHC, ArenaRole arenaRole, Main main) {
        super(arenaUHC, arenaRole, main);
        this.arenaPowerEnum = ArenaPowerEnum.RAGE_DE_VAINCRE;
        this.cooldown = 30;
    }

    @Override
    public void execute(CrystaliaPlayer user) {
        user.getPlayer().setVelocity(user.getPlayer().getLocation().getDirection().multiply(2.0D).setY(0.9D));
        if (user.getRole() instanceof ArenaRole) {
            ArenaRole role = (ArenaRole) user.getRole();
            user.getRole().setNoFall(true);
            Bukkit.getScheduler().runTaskLater(main, () -> {
                if (user.getRole() != null && user.getRole().equals(role))
                    user.getRole().setNoFall(false);
            }, 60);
        }
    }

    @Override
    public String getDescription() {
        return "Vous permet d'effectuer un bond en avant.";
    }
}
