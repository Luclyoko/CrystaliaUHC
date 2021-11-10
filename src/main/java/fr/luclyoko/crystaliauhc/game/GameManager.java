package fr.luclyoko.crystaliauhc.game;

import fr.luclyoko.crystaliauhc.Main;
import fr.luclyoko.crystaliauhc.gamemodes.GamemodeUHC;
import fr.luclyoko.crystaliauhc.gamemodes.vanilla.VanillaUHC;
import fr.luclyoko.crystaliauhc.map.GameWorld;

public class GameManager {

    private final Main main;
    private GameWorld gameWorld;
    private GamemodeUHC gamemodeUhc;
    private GameSettings gameSettings;
    private GameState gameState;
    private StartTask startTask;
    private GameTask gameTask;
    private boolean isStarted;

    public GameManager(Main main,
                       GameWorld gameWorld) {
        this.main = main;
        this.gameWorld = gameWorld;
        this.gamemodeUhc = new VanillaUHC(this);
        this.gameState = GameState.LOADING;
        this.gameSettings = new GameSettings(this);
        this.isStarted = false;
        this.startTask = new StartTask(main, this);
        this.gameTask = new GameTask(this);
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

    public GameTask getGameTask() {
        return gameTask;
    }

    public StartTask getStartTask() {
        return startTask;
    }

    public void setStartTask(StartTask startTask) {
        this.startTask = startTask;
    }
}
