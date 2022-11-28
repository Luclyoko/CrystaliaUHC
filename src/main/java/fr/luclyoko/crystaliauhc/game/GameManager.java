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

    public GameManager(Main main, GameWorld gameWorld) {
        this.main = main;
        this.gameWorld = gameWorld;
        this.gamemodeUhc = (GamemodeUHC)new VanillaUHC(main, this);
        this.gameState = GameState.LOADING;
        this.gameSettings = new GameSettings(main, this);
        this.isStarted = false;
        this.startTask = new StartTask(main, this);
        this.gameTask = new GameTask(main, this);
    }

    public GameWorld getGameWorld() {
        return this.gameWorld;
    }

    public void setGameWorld(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
    }

    public boolean isStarted() {
        return this.isStarted;
    }

    public void setStarted(boolean started) {
        this.isStarted = started;
    }

    public GameState getGameState() {
        return this.gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public GamemodeUHC getGamemodeUhc() {
        return this.gamemodeUhc;
    }

    public void setGamemodeUhc(GamemodeUHC gamemodeUhc) {
        this.gamemodeUhc = gamemodeUhc;
    }

    public GameSettings getGameSettings() {
        return this.gameSettings;
    }

    public GameTask getGameTask() {
        return this.gameTask;
    }

    public StartTask getStartTask() {
        return this.startTask;
    }

    public void setStartTask(StartTask startTask) {
        this.startTask = startTask;
    }
}
