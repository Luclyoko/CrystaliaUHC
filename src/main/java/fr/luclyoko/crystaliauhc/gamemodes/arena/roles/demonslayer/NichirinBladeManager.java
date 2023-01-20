package fr.luclyoko.crystaliauhc.gamemodes.arena.roles.demonslayer;

import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRole;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class NichirinBladeManager {

    public static NichirinBlade chooseBlade(ArenaRole arenaRole) {
        List<NichirinBlade> nichirinBlades = Arrays.stream(NichirinBlade.values()).filter(blade -> blade != NichirinBlade.NONE).collect(Collectors.toList());
        Collections.shuffle(nichirinBlades);
        Random random = new Random();
        NichirinBlade nichirinBlade = nichirinBlades.get(random.nextInt(nichirinBlades.size() - 1));
        switch (nichirinBlade) {
            case BLACK:
                arenaRole.setSelfStrengthPercent(10);
                break;
            case GRAY:
                arenaRole.setSelfResistancePercent(10);
                break;
            case YELLOW:
                arenaRole.setSelfSpeedPercent(10);
                break;
            case PINK:
                arenaRole.addMaxHealth(4);
                arenaRole.heal(4);
                break;
            case RED:
                arenaRole.setCanSeeLife(true);
                break;
            case GREEN:
                arenaRole.setNoFall(true);
                break;
            default:
                break;
        }
        return nichirinBlade;
    }

} enum NichirinBlade {
    NONE("Aucune", "", "Aucun"),
    BLACK("Lame Noire", "§0", "10% de Force"),
    GRAY("Lame Grise", "§7", "10% de Résistance"),
    YELLOW("Lame Jaune", "§e", "10% de Vitesse"),
    PINK("Lame Rose", "§d", "2❤ supplémentaires"),
    RED("Lame Rouge", "§c", "Voir la vie des joueurs"),
    GREEN("Lame Verte", "§a", "No Fall");

    private final String name;
    private final String color;
    private final String bonus;

    NichirinBlade(String name,
                  String color,
                  String bonus) {
        this.name = name;
        this.color = color;
        this.bonus = bonus;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public String getBonus() {
        return bonus;
    }
}
