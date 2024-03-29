package fr.luclyoko.crystaliauhc.game;

public enum GameState {
    LOADING("§eEn préparation..."),
    STARTING("§6Démarrage en cours..."),
    PLAYING(""),
    FINISHED("§bPartie terminée");

    private final String scoreboardDisplay;

    GameState(String scoreboardDisplay) {
        this.scoreboardDisplay = scoreboardDisplay;
    }

    public String getScoreboardDisplay() {
        return this.scoreboardDisplay;
    }
    }
