package fr.luclyoko.crystaliauhc.gamemodes.arena.powers;

import fr.luclyoko.crystaliauhc.Main;
import fr.luclyoko.crystaliauhc.gamemodes.arena.ArenaUHC;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRole;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import org.bukkit.potion.PotionEffectType;

public class MikasaBooster extends ArenaPower {
    public MikasaBooster(ArenaUHC arenaUHC, ArenaRole arenaRole, Main main) {
        super(arenaUHC, arenaRole, main);
        this.arenaPowerEnum = ArenaPowerEnum.MIKASA_BOOSTER;
        this.duration = 120;
        this.cooldown = 180;
    }

    @Override
    public void execute(CrystaliaPlayer user) {
        user.getRole().addTempEffect(PotionEffectType.SPEED, 0, duration * 20, false);
        user.getRole().addTempEffect(PotionEffectType.INCREASE_DAMAGE, 0, duration * 20, false);
    }

    @Override
    public String getDescription() {
        return "Vous permet d'obtenir les effets §bSpeed 1 §fet §bStrength 1 §fpendant 2 minutes.";
    }
}
