package fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles;

import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.hybrides.ChienLoup;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.hybrides.Cupidon;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.hybrides.EnfantSauvage;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.hybrides.Rival;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.hybrides.Trublion;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.hybrides.Voleur;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.loups.GrandMechantLoup;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.loups.InfectPereDesLoups;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.loups.LoupGarou;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.loups.LoupGarouAlpha;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.loups.LoupGarouAmnesique;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.loups.LoupGarouCraintif;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.loups.LoupGarouFeutre;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.loups.LoupGarouGrimeur;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.loups.LoupGarouMystique;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.loups.LoupGarouPerfide;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.loups.LoupGarouVengeur;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.loups.VilainPetitLoup;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.solitaires.Ange;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.solitaires.Assassin;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.solitaires.Charmeuse;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.solitaires.FeuFollet;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.solitaires.Imitateur;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.solitaires.JoueurDeFlute;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.solitaires.LoupGarouBlanc;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.village.Analyste;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.village.Ancien;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.village.BoucEmissaire;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.village.Chaman;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.village.Chasseur;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.village.Citoyen;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.village.Conteuse;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.village.Corbeau;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.village.Detective;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.village.Ermite;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.village.Garde;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.village.IdiotDuVillage;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.village.Jumeau;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.village.MontreurDOurs;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.village.Oracle;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.village.PetiteFille;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.village.Pretresse;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.village.Renard;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.village.Salvateur;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.village.SimpleVillageois;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.village.Soeur;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.village.Sorciere;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.village.VoyanteBavarde;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.village.VoyanteDiscrete;

public enum LGRolesEnum {
    SIMPLE_VILLAGEOIS("Simple Villageois", (Class)SimpleVillageois.class, LGSides.LGBasicSides.VILLAGE),
    VOYANTE_DISCRETE("Voyante Discrète", (Class)VoyanteDiscrete.class, LGSides.LGBasicSides.VILLAGE),
    VOYANTE_BAVARDE("Voyante Bavarde", (Class)VoyanteBavarde.class, LGSides.LGBasicSides.VILLAGE),
    SORCIERE("Sorcière", (Class)Sorciere.class, LGSides.LGBasicSides.VILLAGE),
    ANCIEN("Ancien", (Class)Ancien.class, LGSides.LGBasicSides.VILLAGE),
    PRETRESSE("Prêtresse", (Class)Pretresse.class, LGSides.LGBasicSides.VILLAGE),
    PETITE_FILLE("Petite Fille", (Class)PetiteFille.class, LGSides.LGBasicSides.VILLAGE),
    MONTREUR_D_OURS("Montreur d'Ours", (Class)MontreurDOurs.class, LGSides.LGBasicSides.VILLAGE),
    RENARD("Renard", (Class)Renard.class, LGSides.LGBasicSides.VILLAGE),
    ORACLE("Oracle", (Class)Oracle.class, LGSides.LGBasicSides.VILLAGE),
    DETECTIVE("Détective", (Class)Detective.class, LGSides.LGBasicSides.VILLAGE),
    CHASSEUR("Chasseur", (Class)Chasseur.class, LGSides.LGBasicSides.VILLAGE),
    CHAMAN("Chaman", (Class)Chaman.class, LGSides.LGBasicSides.VILLAGE),
    JUMEAUX("Jumeau", (Class)Jumeau.class, LGSides.LGBasicSides.VILLAGE),
    CONTEUSE("Conteuse", (Class)Conteuse.class, LGSides.LGBasicSides.VILLAGE),
    SALVATEUR("Salvateur", (Class)Salvateur.class, LGSides.LGBasicSides.VILLAGE),
    GARDE("Garde", (Class)Garde.class, LGSides.LGBasicSides.VILLAGE),
    SOEURS("Soeur", (Class)Soeur.class, LGSides.LGBasicSides.VILLAGE),
    CITOYEN("Citoyen", (Class)Citoyen.class, LGSides.LGBasicSides.VILLAGE),
    IDIOT_DU_VILLAGE("Idiot du Village", (Class)IdiotDuVillage.class, LGSides.LGBasicSides.VILLAGE),
    CUPIDON("Cupidon", (Class)Cupidon.class, LGSides.LGBasicSides.HYBRIDE),
    ERMITE("Ermite", (Class)Ermite.class, LGSides.LGBasicSides.VILLAGE),
    CORBEAU("Corbeau", (Class)Corbeau.class, LGSides.LGBasicSides.VILLAGE),
    BOUC_EMISSAIRE("Bouc Émissaire", (Class)BoucEmissaire.class, LGSides.LGBasicSides.VILLAGE),
    ANALYSTE("Analyste", (Class)Analyste.class, LGSides.LGBasicSides.VILLAGE),
    ENFANT_SAUVAGE("Enfant Sauvage", (Class)EnfantSauvage.class, LGSides.LGBasicSides.HYBRIDE),
    CHIEN_LOUP("Chien-Loup", (Class)ChienLoup.class, LGSides.LGBasicSides.HYBRIDE),
    LOUP_GAROU("Loup-Garou", (Class)LoupGarou.class, LGSides.LGBasicSides.LOUPS),
    INFECT_PERE_DES_LOUPS("Infect Père des Loups", (Class)InfectPereDesLoups.class, LGSides.LGBasicSides.LOUPS),
    LOUP_GAROU_FEUTRE("Loup-Garou Feutré", (Class)LoupGarouFeutre.class, LGSides.LGBasicSides.LOUPS),
    LOUP_GAROU_PERFIDE("Loup-Garou Perfide", (Class)LoupGarouPerfide.class, LGSides.LGBasicSides.LOUPS),
    LOUP_GAROU_MYSTIQUE("Loup-Garou Mystique", (Class)LoupGarouMystique.class, LGSides.LGBasicSides.LOUPS),
    LOUP_GAROU_AMNESIQUE("Loup-Garou Amnésique", (Class)LoupGarouAmnesique.class, LGSides.LGBasicSides.LOUPS),
    VILAIN_PETIT_LOUP("Vilain Petit Loup", (Class)VilainPetitLoup.class, LGSides.LGBasicSides.LOUPS),
    LOUP_GAROU_VENGEUR("Loup-Garou Vengeur", (Class)LoupGarouVengeur.class, LGSides.LGBasicSides.LOUPS),
    LOUP_GAROU_GRIMEUR("Loup-Garou Grimeur", (Class)LoupGarouGrimeur.class, LGSides.LGBasicSides.LOUPS),
    LOUP_GAROU_ALPHA("Loup-Garou Alpha", (Class)LoupGarouAlpha.class, LGSides.LGBasicSides.LOUPS),
    GRAND_MECHANT_LOUP("Grand Méchant Loup", (Class)GrandMechantLoup.class, LGSides.LGBasicSides.LOUPS),
    LOUP_GAROU_CRAINTIF("Loup-Garou Craintif", (Class)LoupGarouCraintif.class, LGSides.LGBasicSides.LOUPS),
    LOUP_GAROU_BLANC("Loup-Garou Blanc", (Class)LoupGarouBlanc.class, LGSides.LGBasicSides.SOLITAIRE),
    IMITATEUR("Imitateur", (Class)Imitateur.class, LGSides.LGBasicSides.SOLITAIRE),
    ANGE("Ange", (Class)Ange.class, LGSides.LGBasicSides.SOLITAIRE),
    RIVAL("Rival", (Class)Rival.class, LGSides.LGBasicSides.HYBRIDE),
    TRUBLION("Trublion", (Class)Trublion.class, LGSides.LGBasicSides.HYBRIDE),
    ASSASSIN("Assassin", (Class)Assassin.class, LGSides.LGBasicSides.SOLITAIRE),
    CHARMEUSE("Charmeuse", (Class)Charmeuse.class, LGSides.LGBasicSides.SOLITAIRE),
    JOUEUR_DE_FLUTE("Joueur de Flûte", (Class)JoueurDeFlute.class, LGSides.LGBasicSides.SOLITAIRE),
    VOLEUR("Voleur", (Class)Voleur.class, LGSides.LGBasicSides.HYBRIDE),
    FEU_FOLLET("Feu Follet", (Class)FeuFollet.class, LGSides.LGBasicSides.SOLITAIRE);

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
