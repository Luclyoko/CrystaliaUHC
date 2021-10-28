package fr.luclyoko.crystaliauhc.game;

import javax.annotation.Nullable;

public enum GameState {
    LOADING("§eEn attente de joueurs..."),
    STARTING("§6Démarrage en cours..."),
    PLAYING(""),
    FINISHED("§bPartie terminée");

    private final String scoreboardDisplay;

    GameState(String scoreboardDisplay) {
        this.scoreboardDisplay = scoreboardDisplay;
    }

    public String getScoreboardDisplay() {
        return scoreboardDisplay;
    }
}
