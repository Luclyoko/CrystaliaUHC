package fr.luclyoko.crystaliauhc.gamemodes.arena.powers;

import fr.luclyoko.crystaliauhc.Main;
import fr.luclyoko.crystaliauhc.gamemodes.arena.ArenaUHC;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRole;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import org.bukkit.Bukkit;
import org.bukkit.potion.PotionEffectType;

public class Nora extends ArenaPower {

    public Nora(ArenaUHC arenaUHC, ArenaRole arenaRole, Main main) {
        super(arenaUHC, arenaRole, main);
        this.arenaPowerEnum = ArenaPowerEnum.NORA;
        this.cooldown = 120;
        this.duration = 90;
    }

    @Override
    public void execute(CrystaliaPlayer user) {
        user.getPlayer().setVelocity(user.getPlayer().getLocation().getDirection().multiply(2.5D).setY(0.9D));
        user.getRole().addTempEffect(PotionEffectType.INCREASE_DAMAGE, 0, duration * 20, false);
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
        return "Vous permet d'effectuer un dash et d'obtenir l'effet §bStrength 1 §fpendant 1 minute et 30 secondes.";
    }
}
