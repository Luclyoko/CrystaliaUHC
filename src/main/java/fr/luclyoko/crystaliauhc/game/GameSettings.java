package fr.luclyoko.crystaliauhc.game;

public class GameSettings {

    private GameManager gameManager;
    private boolean devMode;

    public GameSettings(GameManager gameManager) {
        this.gameManager = gameManager;
        this.devMode = false;
    }

    public boolean isDevMode() {
        return devMode;
    }

    public void setDevMode(boolean devMode) {
        this.devMode = devMode;
    }
}
