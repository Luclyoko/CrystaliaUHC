package fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles;

import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.hybrides.*;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.loups.*;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.solitaires.*;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.village.*;

public enum LGRolesEnum {
    SIMPLE_VILLAGEOIS("Simple Villageois", SimpleVillageois.class, LGSides.LGBasicSides.VILLAGE),
    VOYANTE_DISCRETE("Voyante Discrète", VoyanteDiscrete.class, LGSides.LGBasicSides.VILLAGE),
    VOYANTE_BAVARDE("Voyante Bavarde", VoyanteBavarde.class, LGSides.LGBasicSides.VILLAGE),
    SORCIERE("Sorcière", Sorciere.class, LGSides.LGBasicSides.VILLAGE),
    ANCIEN("Ancien", Ancien.class, LGSides.LGBasicSides.VILLAGE),
    PRETRESSE("Prêtresse", Pretresse.class, LGSides.LGBasicSides.VILLAGE),
    PETITE_FILLE("Petite Fille", PetiteFille.class, LGSides.LGBasicSides.VILLAGE),
    MONTREUR_D_OURS("Montreur d'Ours", MontreurDOurs.class, LGSides.LGBasicSides.VILLAGE),
    RENARD("Renard", Renard.class, LGSides.LGBasicSides.VILLAGE),
    ORACLE("Oracle", Oracle.class, LGSides.LGBasicSides.VILLAGE),
    DETECTIVE("Détective", Detective.class, LGSides.LGBasicSides.VILLAGE),
    CHASSEUR("Chasseur", Chasseur.class, LGSides.LGBasicSides.VILLAGE),
    CHAMAN("Chaman", Chaman.class, LGSides.LGBasicSides.VILLAGE),
    JUMEAU("Jumeau", Jumeau.class, LGSides.LGBasicSides.VILLAGE),
    CONTEUSE("Conteuse", Conteuse.class, LGSides.LGBasicSides.VILLAGE),
    SALVATEUR("Salvateur", Salvateur.class, LGSides.LGBasicSides.VILLAGE),
    GARDE("Garde", Garde.class, LGSides.LGBasicSides.VILLAGE),
    SOEURS("Soeur", Soeur.class, LGSides.LGBasicSides.VILLAGE),
    CITOYEN("Citoyen", Citoyen.class, LGSides.LGBasicSides.VILLAGE),
    IDIOT_DU_VILLAGE("Idiot du Village", IdiotDuVillage.class, LGSides.LGBasicSides.VILLAGE),
    CUPIDON("Cupidon", Cupidon.class, LGSides.LGBasicSides.HYBRIDE),
    ERMITE("Ermite", Ermite.class, LGSides.LGBasicSides.VILLAGE),
    CORBEAU("Corbeau", Corbeau.class, LGSides.LGBasicSides.VILLAGE),
    BOUC_EMISSAIRE("Bouc Émissaire", BoucEmissaire.class, LGSides.LGBasicSides.VILLAGE),
    ANALYSTE("Analyste", Analyste.class, LGSides.LGBasicSides.VILLAGE),
    ENFANT_SAUVAGE("Enfant Sauvage", EnfantSauvage.class, LGSides.LGBasicSides.HYBRIDE),
    CHIEN_LOUP("Chien-Loup", ChienLoup.class, LGSides.LGBasicSides.HYBRIDE),
    LOUP_GAROU("Loup-Garou", LoupGarou.class, LGSides.LGBasicSides.LOUPS),
    INFECT_PERE_DES_LOUPS("Infect Père des Loups", InfectPereDesLoups.class, LGSides.LGBasicSides.LOUPS),
    LOUP_GAROU_FEUTRE("Loup-Garou Feutré", LoupGarouFeutre.class, LGSides.LGBasicSides.LOUPS),
    LOUP_GAROU_PERFIDE("Loup-Garou Perfide", LoupGarouPerfide.class, LGSides.LGBasicSides.LOUPS),
    LOUP_GAROU_MYSTIQUE("Loup-Garou Mystique", LoupGarouMystique.class, LGSides.LGBasicSides.LOUPS),
    LOUP_GAROU_AMNESIQUE("Loup-Garou Amnésique", LoupGarouAmnesique.class, LGSides.LGBasicSides.LOUPS),
    VILAIN_PETIT_LOUP("Vilain Petit Loup", VilainPetitLoup.class, LGSides.LGBasicSides.LOUPS),
    LOUP_GAROU_VENGEUR("Loup-Garou Vengeur", LoupGarouVengeur.class, LGSides.LGBasicSides.LOUPS),
    LOUP_GAROU_GRIMEUR("Loup-Garou Grimeur", LoupGarouGrimeur.class, LGSides.LGBasicSides.LOUPS),
    LOUP_GAROU_ALPHA("Loup-Garou Alpha", LoupGarouAlpha.class, LGSides.LGBasicSides.LOUPS),
    GRAND_MECHANT_LOUP("Grand Méchant Loup", GrandMechantLoup.class, LGSides.LGBasicSides.LOUPS),
    LOUP_GAROU_CRAINTIF("Loup-Garou Craintif", LoupGarouCraintif.class, LGSides.LGBasicSides.LOUPS),
    LOUP_GAROU_BLANC("Loup-Garou Blanc", LoupGarouBlanc.class, LGSides.LGBasicSides.SOLITAIRE),
    IMITATEUR("Imitateur", Imitateur.class, LGSides.LGBasicSides.SOLITAIRE),
    ANGE("Ange", Ange.class, LGSides.LGBasicSides.SOLITAIRE),
    RIVAL("Rival", Rival.class, LGSides.LGBasicSides.HYBRIDE),
    TRUBLION("Trublion", Trublion.class, LGSides.LGBasicSides.HYBRIDE),
    ASSASSIN("Assassin", Assassin.class, LGSides.LGBasicSides.SOLITAIRE),
    CHARMEUSE("Charmeuse", Charmeuse.class, LGSides.LGBasicSides.SOLITAIRE),
    JOUEUR_DE_FLUTE("Joueur de Flûte", JoueurDeFlute.class, LGSides.LGBasicSides.SOLITAIRE),
    VOLEUR("Voleur", Voleur.class, LGSides.LGBasicSides.HYBRIDE),
    FEU_FOLLET("Feu Follet", FeuFollet.class, LGSides.LGBasicSides.SOLITAIRE);

    private String name;

    private Class<? extends LGRole> roleClass;

    private LGSides.LGBasicSides basicSide;

    LGRolesEnum(String name, Class<? extends LGRole> roleClass, LGSides.LGBasicSides basicSide) {
        this.name = name;
        this.roleClass = roleClass;
        this.basicSide = basicSide;
    }

    public String getName() {
        return this.name;
    }

    public Class<? extends LGRole> getRoleClass() {
        return this.roleClass;
    }

    public LGSides.LGBasicSides getBasicSide() {
        return this.basicSide;
    }
}
