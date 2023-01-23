package fr.luclyoko.crystaliauhc.gamemodes.arena.powers;

import fr.luclyoko.crystaliauhc.Main;
import fr.luclyoko.crystaliauhc.gamemodes.arena.ArenaUHC;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRole;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffectType;

public class BeastTitan extends ArenaPower {
    public BeastTitan(ArenaUHC arenaUHC, ArenaRole arenaRole, Main main) {
        super(arenaUHC, arenaRole, main);
        this.arenaPowerEnum = ArenaPowerEnum.TITAN_BESTIAL;
        this.duration = 120;
        this.cooldown = 180;
    }

    @Override
    public void execute(CrystaliaPlayer user) {
        user.getRole().addMaxHealth(6);
        user.getRole().heal(6);
        user.getRole().addTempEffect(PotionEffectType.INCREASE_DAMAGE, 0, duration * 20, false);
        user.getRole().addTempEffect(PotionEffectType.DAMAGE_RESISTANCE, 0, duration * 20, false);
        user.getPlayer().getWorld().strikeLightningEffect(user.getPlayer().getLocation());
        Bukkit.getScheduler().runTaskLater(main, () -> {
            if (user.getRole() != null && user.getRole().equals(arenaRole)) user.getRole().removeMaxHealth(6);
        }, 20L*duration);
    }

    @Override
    public String getDescription() {
        return "Vous permet d'obtenir les effets §bStrength 1 §fet §bResistance 1 §fainsi que §c3❤ permanents §fpendant 2 minutes.";
    }
}
