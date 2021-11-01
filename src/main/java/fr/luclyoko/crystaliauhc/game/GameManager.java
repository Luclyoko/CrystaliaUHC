package fr.luclyoko.crystaliauhc.game;

import fr.luclyoko.crystaliauhc.Main;
import fr.luclyoko.crystaliauhc.gamemodes.GamemodeUHC;
import fr.luclyoko.crystaliauhc.gamemodes.vanilla.VanillaGamemodeUHC;
import fr.luclyoko.crystaliauhc.map.GameWorld;

public class GameManager {

    private final Main main;
    private GameWorld gameWorld;
    private GamemodeUHC gamemodeUhc;
    private GameSettings gameSettings;
    private GameState gameState;
    private boolean isStarted;

    public GameManager(Main main,
                       GameWorld gameWorld) {
        this.main = main;
        this.gameWorld = gameWorld;
        this.gamemodeUhc = new VanillaGamemodeUHC(this);
        this.gameSettings = new GameSettings(this);
        this.gameState = GameState.LOADING;
        this.isStarted = false;
    }

    public GameWorld getGameWorld() {
        return gameWorld;
    }

    public void setGameWorld(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
    }

    public boolean isStarted() {
        return isStarted;
    }

    public void setStarted(boolean started) {
        isStarted = started;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public GamemodeUHC getGamemodeUhc() {
        return gamemodeUhc;
    }

    public void setGamemodeUhc(GamemodeUHC gamemodeUhc) {
        this.gamemodeUhc = gamemodeUhc;
    }

    public GameSettings getGameSettings() {
        return gameSettings;
    }
}
