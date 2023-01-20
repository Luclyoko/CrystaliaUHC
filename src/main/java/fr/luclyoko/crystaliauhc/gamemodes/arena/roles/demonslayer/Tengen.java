package fr.luclyoko.crystaliauhc.gamemodes.arena.roles.demonslayer;

import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.gamemodes.arena.powers.Fumigene;
import fr.luclyoko.crystaliauhc.gamemodes.arena.powers.Vitesse;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRolesEnum;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import org.bukkit.potion.PotionEffectType;

public class Tengen extends Slayer {

    public Tengen(GameManager gameManager, CrystaliaPlayer crystaliaPlayer) {
        super(gameManager, crystaliaPlayer);
        this.arenaRolesEnum = ArenaRolesEnum.TENGEN;
        addPermEffect(PotionEffectType.SPEED, 0);
        addPower(new Vitesse(getArenaUHC(), this, main));
        addPower(new Fumigene(getArenaUHC(), this, main));
    }
}
