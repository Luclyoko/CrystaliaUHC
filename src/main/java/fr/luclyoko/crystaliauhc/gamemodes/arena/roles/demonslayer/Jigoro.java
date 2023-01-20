package fr.luclyoko.crystaliauhc.gamemodes.arena.roles.demonslayer;

import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.gamemodes.arena.powers.ThunderBreath;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRolesEnum;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import org.bukkit.potion.PotionEffectType;

public class Jigoro extends Slayer {

    public Jigoro(GameManager gameManager, CrystaliaPlayer crystaliaPlayer) {
        super(gameManager, crystaliaPlayer);
        this.arenaRolesEnum = ArenaRolesEnum.JIGORO;
        addPermEffect(PotionEffectType.SPEED, 0);
        addPermEffect(PotionEffectType.DAMAGE_RESISTANCE, 0);
        addPower(new ThunderBreath(getArenaUHC(), this, main));
    }
}
