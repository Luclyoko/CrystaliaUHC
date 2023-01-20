package fr.luclyoko.crystaliauhc.gamemodes.arena.roles.lotr;

import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRole;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRolesEnum;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import org.bukkit.potion.PotionEffectType;

import java.util.Collections;
import java.util.List;

public class Gimly extends ArenaRole {
    public Gimly(GameManager gameManager, CrystaliaPlayer crystaliaPlayer) {
        super(gameManager, crystaliaPlayer);
        this.arenaRolesEnum = ArenaRolesEnum.GIMLY;
        addPermEffect(PotionEffectType.DAMAGE_RESISTANCE, 0);
    }

    @Override
    public boolean isNoFall() {
        return true;
    }

    @Override
    public List<String> getAdditionalDescription() {
        return Collections.singletonList("Vous possédez §bNoFall §fde manière permanente.");
    }
}
