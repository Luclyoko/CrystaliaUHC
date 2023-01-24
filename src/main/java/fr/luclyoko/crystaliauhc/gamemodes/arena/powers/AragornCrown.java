package fr.luclyoko.crystaliauhc.gamemodes.arena.powers;

import fr.luclyoko.crystaliauhc.Main;
import fr.luclyoko.crystaliauhc.gamemodes.arena.ArenaUHC;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRole;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import org.bukkit.potion.PotionEffectType;

public class AragornCrown extends ArenaPower {

    public AragornCrown(ArenaUHC arenaUHC, ArenaRole arenaRole, Main main) {
        super(arenaUHC, arenaRole, main);
        this.arenaPowerEnum = ArenaPowerEnum.CROWN;
        this.duration = 120;
        this.cooldown = 180;
    }

    @Override
    public void execute(CrystaliaPlayer user) {
        user.getRole().addTempEffect(PotionEffectType.INCREASE_DAMAGE, 0, duration * 20, false);
        user.getRole().addMaxHealth(6);
        user.getRole().heal(6);
    }

    @Override
    public String getDescription() {
        return "Vous permet d'obtenir l'effet §bStrength 1 §fainsi que §c3❤ §fsupplémentaires pendant 2 minutes.";
    }
}
