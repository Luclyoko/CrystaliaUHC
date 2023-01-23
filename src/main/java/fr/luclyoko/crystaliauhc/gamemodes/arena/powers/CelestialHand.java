package fr.luclyoko.crystaliauhc.gamemodes.arena.powers;

import fr.luclyoko.crystaliauhc.Main;
import fr.luclyoko.crystaliauhc.gamemodes.arena.ArenaUHC;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRole;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffectType;

public class CelestialHand extends ArenaPower {
    public CelestialHand(ArenaUHC arenaUHC, ArenaRole arenaRole, Main main) {
        super(arenaUHC, arenaRole, main);
        this.arenaPowerEnum = ArenaPowerEnum.MAIN_CELESTE;
        this.duration = 30;
        this.cooldown = 180;
    }

    @Override
    public void execute(CrystaliaPlayer user) {
        user.getRole().addTempEffect(PotionEffectType.DAMAGE_RESISTANCE, 1, duration * 20, false);
    }

    @Override
    public String getDescription() {
        return "Vous permet d'obtenir l'effet §bResistance 2 §fpendant 30 secondes.";
    }
}
