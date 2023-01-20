package fr.luclyoko.crystaliauhc.gamemodes.arena.roles.demonslayer;

import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.gamemodes.arena.powers.ExtremeSpeed;
import fr.luclyoko.crystaliauhc.gamemodes.arena.powers.ThunderingHit;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRolesEnum;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;

public class Zenitsu extends Slayer {
    public Zenitsu(GameManager gameManager, CrystaliaPlayer crystaliaPlayer) {
        super(gameManager, crystaliaPlayer);
        this.arenaRolesEnum = ArenaRolesEnum.ZENITSU;
        addPower(new ExtremeSpeed(getArenaUHC(), this, main));
        addPower(new ThunderingHit(getArenaUHC(), this, main));
    }
}
