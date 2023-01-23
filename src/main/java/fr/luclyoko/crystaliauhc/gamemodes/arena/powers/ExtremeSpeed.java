package fr.luclyoko.crystaliauhc.gamemodes.arena.powers;

import fr.luclyoko.crystaliauhc.Main;
import fr.luclyoko.crystaliauhc.gamemodes.arena.ArenaUHC;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRole;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffectType;

public class ExtremeSpeed extends ArenaPower {

    private final int malusTime;

    public ExtremeSpeed(ArenaUHC arenaUHC, ArenaRole arenaRole, Main main) {
        super(arenaUHC, arenaRole, main);
        this.arenaPowerEnum = ArenaPowerEnum.VITESSE_EXTREME;
        this.uses = 1;
        this.duration = 30;
        this.malusTime = 10;
    }

    @Override
    public void execute(CrystaliaPlayer user) {
        if (user.getRole() instanceof ArenaRole) {
            ArenaRole role = (ArenaRole) user.getRole();
            role.addTempEffect(PotionEffectType.SPEED, 2, duration * 20, false);
            user.getPlayer().getWorld().strikeLightningEffect(user.getPlayer().getLocation());
            Bukkit.getScheduler().runTaskLater(main, () -> {
                if (user.getRole() != null && user.getRole().equals(role)) {
                    user.sendMessage("§cVotre course vous a épuisé, vous écopez donc des effets §bSlowness 1 §cet §bWeakness 1 §cpendant 10 secondes.");
                    role.addTempEffect(PotionEffectType.SLOW, 1, malusTime * 20, false);
                    role.addTempEffect(PotionEffectType.WEAKNESS, 0, malusTime * 20, false);
                }
            }, duration * 20L);
        }
    }

    @Override
    public String getDescription() {
        return "Vous permet d'obtenir l'effet §bSpeed 3 §fpendant 30 secondes. A la fin de ce temps, vous écoperez des effets §bSlowness 1 §fet §bWeakness 1 §fpendant 10 secondes.";
    }
}
