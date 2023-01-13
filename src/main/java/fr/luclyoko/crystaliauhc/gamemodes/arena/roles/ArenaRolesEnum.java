package fr.luclyoko.crystaliauhc.gamemodes.arena.roles;

import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.demonslayer.*;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.inazuma.AxelBlaze;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.inazuma.MarkEvans;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.lotr.Legolas;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.loupgarou.*;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.reborn.Reborn;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.reborn.Tsuna;

public enum ArenaRolesEnum {

    AXEL_BLAZE("Axel Blaze", RoleGamemodeEnum.INAZUMA, AxelBlaze.class),
    MARK_EVANS("Mark Evans", RoleGamemodeEnum.INAZUMA, MarkEvans.class),
    LEGOLAS("Legolas", RoleGamemodeEnum.LOTR, Legolas.class),
    REBORN("Reborn", RoleGamemodeEnum.REBORN, Reborn.class),
    TSUNA("Tsuna", RoleGamemodeEnum.REBORN, Tsuna.class),
    LGSIMPLE("Loup-Garou", RoleGamemodeEnum.LGUHC, LGSimple.class),
    LGBLANC("Loup-Garou Blanc", RoleGamemodeEnum.LGUHC, LGBlanc.class),
    ASSASSIN("Assassin", RoleGamemodeEnum.LGUHC, AAssassin.class),
    CUPIDON("Cupidon", RoleGamemodeEnum.LGUHC, ACupidon.class),
    MINEUR("Mineur", RoleGamemodeEnum.LGUHC, Mineur.class),
    SIMPLEVILLAGEOIS("Simple Villageois", RoleGamemodeEnum.LGUHC, ASimpleVillageois.class),
    MUZAN("Muzan", RoleGamemodeEnum.DEMON_SLAYER, Muzan.class),
    KOKUSHIBO("Kokushibo", RoleGamemodeEnum.DEMON_SLAYER, Kokushibo.class),
    AKAZA("Akaza", RoleGamemodeEnum.DEMON_SLAYER, Akaza.class),
    TANJIRO("Tanjiro", RoleGamemodeEnum.DEMON_SLAYER, Tanjiro.class),
    NEZUKO("Nezuko", RoleGamemodeEnum.DEMON_SLAYER, Nezuko.class),
    GYOMEI("Gyomei", RoleGamemodeEnum.DEMON_SLAYER, Gyomei.class),
    SANEMI("Sanemi", RoleGamemodeEnum.DEMON_SLAYER, Sanemi.class),
    TENGEN("Tengen", RoleGamemodeEnum.DEMON_SLAYER, Tengen.class),
    JIGORO("Jigoro", RoleGamemodeEnum.DEMON_SLAYER, Jigoro.class),
    YORIICHI("Yoriichi", RoleGamemodeEnum.DEMON_SLAYER, Yoriichi.class);

    private String name;

    private RoleGamemodeEnum gamemodeEnum;
    private Class<? extends ArenaRole> roleClass;

    ArenaRolesEnum(String name, RoleGamemodeEnum gamemodeEnum, Class<? extends ArenaRole> roleClass) {
        this.name = name;
        this.gamemodeEnum = gamemodeEnum;
        this.roleClass = roleClass;
    }

    public String getName() {
        return this.name;
    }

    public RoleGamemodeEnum getGamemodeEnum() {
        return gamemodeEnum;
    }

    public Class<? extends ArenaRole> getRoleClass() {
        return this.roleClass;
    }
}
