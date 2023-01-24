package fr.luclyoko.crystaliauhc.gamemodes.arena.roles;

import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.aot.Eren;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.aot.Livai;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.aot.Mikasa;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.aot.Sieg;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.deathnote.Light;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.deathnote.Misa;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.deathnote.Shinigami;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.demonslayer.*;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.disney.FlashMcQueen;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.inazuma.AxelBlaze;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.inazuma.ErikEagle;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.inazuma.MarkEvans;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.lotr.*;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.loupgarou.*;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.naruto.Asuma;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.noragami.Bishamon;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.noragami.Yato;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.reborn.Reborn;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.reborn.Tsuna;

public enum ArenaRolesEnum {

    AXEL_BLAZE("Axel Blaze", RoleGamemodeEnum.INAZUMA, AxelBlaze.class),
    MARK_EVANS("Mark Evans", RoleGamemodeEnum.INAZUMA, MarkEvans.class),
    ERIK_EAGLE("Erik Eagle", RoleGamemodeEnum.INAZUMA, ErikEagle.class),
    LEGOLAS("Legolas", RoleGamemodeEnum.LOTR, Legolas.class),
    BILBON("Bilbon", RoleGamemodeEnum.LOTR, Bilbon.class),
    GIMLY("Gimly", RoleGamemodeEnum.LOTR, Gimly.class),
    GOLLUM("Gollum", RoleGamemodeEnum.LOTR, Gollum.class),
    ARAGORN("Aragorn", RoleGamemodeEnum.LOTR, Aragorn.class),
    REBORN("Reborn", RoleGamemodeEnum.REBORN, Reborn.class),
    TSUNA("Tsuna", RoleGamemodeEnum.REBORN, Tsuna.class),
    FLASH_MCQUEEN("Flash McQueen", RoleGamemodeEnum.DISNEY, FlashMcQueen.class),
    LG_SIMPLE("Loup-Garou", RoleGamemodeEnum.LGUHC, LGSimple.class),
    LG_BLANC("Loup-Garou Blanc", RoleGamemodeEnum.LGUHC, LGBlanc.class),
    ASSASSIN("Assassin", RoleGamemodeEnum.LGUHC, AAssassin.class),
    CUPIDON("Cupidon", RoleGamemodeEnum.LGUHC, ACupidon.class),
    MINEUR("Mineur", RoleGamemodeEnum.LGUHC, Mineur.class),
    SIMPLE_VILLAGEOIS("Simple Villageois", RoleGamemodeEnum.LGUHC, ASimpleVillageois.class),
    EREN("Eren", RoleGamemodeEnum.AOT, Eren.class),
    SIEG("Sieg", RoleGamemodeEnum.AOT, Sieg.class),
    LIVAI("Livaï", RoleGamemodeEnum.AOT, Livai.class),
    MIKASA("Mikasa", RoleGamemodeEnum.AOT, Mikasa.class),
    MUZAN("Muzan", RoleGamemodeEnum.DEMON_SLAYER, Muzan.class),
    KOKUSHIBO("Kokushibo", RoleGamemodeEnum.DEMON_SLAYER, Kokushibo.class),
    DOMA("Doma", RoleGamemodeEnum.DEMON_SLAYER, Doma.class),
    AKAZA("Akaza", RoleGamemodeEnum.DEMON_SLAYER, Akaza.class),
    KAIGAKU("Kaigaku", RoleGamemodeEnum.DEMON_SLAYER, Kaigaku.class),
    TANJIRO("Tanjiro", RoleGamemodeEnum.DEMON_SLAYER, Tanjiro.class),
    NEZUKO("Nezuko", RoleGamemodeEnum.DEMON_SLAYER, Nezuko.class),
    ZENITSU("Zenitsu", RoleGamemodeEnum.DEMON_SLAYER, Zenitsu.class),
    GYOMEI("Gyomei", RoleGamemodeEnum.DEMON_SLAYER, Gyomei.class),
    SANEMI("Sanemi", RoleGamemodeEnum.DEMON_SLAYER, Sanemi.class),
    TENGEN("Tengen", RoleGamemodeEnum.DEMON_SLAYER, Tengen.class),
    JIGORO("Jigoro", RoleGamemodeEnum.DEMON_SLAYER, Jigoro.class),
    YORIICHI("Yoriichi", RoleGamemodeEnum.DEMON_SLAYER, Yoriichi.class),
    LIGHT("Light", RoleGamemodeEnum.DEATH_NOTE, Light.class),
    MISA("Misa", RoleGamemodeEnum.DEATH_NOTE, Misa.class),
    SHINIGAMI("Shinigami", RoleGamemodeEnum.DEATH_NOTE, Shinigami.class),
    YATO("Yato", RoleGamemodeEnum.NORAGAMI, Yato.class),
    BISHAMON("Bishamon", RoleGamemodeEnum.NORAGAMI, Bishamon.class),
    ASUMA("Asuma", RoleGamemodeEnum.NARUTO, Asuma.class);

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
