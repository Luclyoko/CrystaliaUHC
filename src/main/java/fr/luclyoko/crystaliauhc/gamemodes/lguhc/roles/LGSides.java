package fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles;

public enum LGSides {
    VILLAGE("Village", "§a", "Votre objectif est de gagner avec le village. "),
    LOUPS("Loups-Garous", "§c", "Votre objectif est de gagner avec les Loups-Garous. "),
    COUPLE("Amoureux", "§d", "Votre objectif est de gagner avec les amoureux. "),
    LOUP_GAROU_BLANC("Loup-Garou Blanc", "§4", "Votre objectif est de gagner seul. "),
    IMITATEUR("Imitateur", "§e", "Votre objectif est de gagner seul. "),
    ANGE("Ange", "§6", "Votre objectif est de gagner seul. "),
    RIVAL("Rival", "§5", "Votre objectif est de gagner seul. "),
    ASSASSIN("Assassin", "§1", "Votre objectif est de gagner seul. "),
    TRUBLION("Trublion", "§b", "Votre objectif est de gagner avec le village. "),
    CHARMEUSE("Charmeuse", "§2", "Votre objectif est de gagner seul."),
    JOUEUR_DE_FLUTE("Joueur de Flûte", "§9", "Votre objectif est de gagner seul. "),
    FEU_FOLLET("Feu Follet", "§3", "Votre objectif est de gagner seul. "),
    VOLEUR("Voleur", "§8", "Votre objectif est de gagner seul. ");

    private String rawName;

    private String color;

    private String objectiveDisplay;

    LGSides(String rawName, String color, String objectiveDisplay) {
        this.rawName = rawName;
        this.color = color;
        this.objectiveDisplay = objectiveDisplay;
    }

    public String getRawName() {
        return this.rawName;
    }

    public String getDisplayName() {
        return this.color + this.rawName;
    }

    public String getColor() {
        return this.color;
    }

    public String getObjectiveDisplay() {
        return this.objectiveDisplay;
    }

    public enum LGBasicSides {
        VILLAGE("Village", "§a"),
        LOUPS("Loups-Garous", "§c"),
        SOLITAIRE("Solitaire", "§6"),
        HYBRIDE("Hybride", "§d");

        private String rawName;

        private String color;

        LGBasicSides(String rawName, String color) {
            this.rawName = rawName;
            this.color = color;
        }

        public String getRawName() {
            return this.rawName;
        }

        public String getColor() {
            return this.color;
        }
        }
    }
