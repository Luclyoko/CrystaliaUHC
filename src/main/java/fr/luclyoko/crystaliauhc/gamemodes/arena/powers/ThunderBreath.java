package fr.luclyoko.crystaliauhc.gamemodes.arena.powers;

import fr.luclyoko.crystaliauhc.Main;
import fr.luclyoko.crystaliauhc.gamemodes.arena.ArenaUHC;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRole;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import org.bukkit.potion.PotionEffectType;

public class ThunderBreath extends ArenaPower {

    public ThunderBreath(ArenaUHC arenaUHC, ArenaRole arenaRole, Main main) {
        super(arenaUHC, arenaRole, main);
        this.arenaPowerEnum = ArenaPowerEnum.SOUFFLE_DE_LA_FOUDRE;
        this.cooldown = 4*60;
        this.duration = 60;
    }

    @Override
    public void execute(CrystaliaPlayer user) {
        user.getRole().addTempEffect(PotionEffectType.SPEED, 2, duration * 20, false);
        user.getPlayer().getWorld().strikeLightningEffect(user.getPlayer().getLocation());
    }

    @Override
    public String getDescription() {
        return "Vous permet d'obtenir l'effet §bSpeed 3 §fpendant 1 minute.";
    }
}
