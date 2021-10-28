package fr.luclyoko.crystaliauhc.game;

import fr.luclyoko.crystaliauhc.Main;
import fr.luclyoko.crystaliauhc.gamemodes.UHC;
import fr.luclyoko.crystaliauhc.gamemodes.vanilla.VanillaUHC;
import fr.luclyoko.crystaliauhc.map.GameWorld;

public class GameManager {

    private final Main main;
    private GameWorld gameWorld;
    private UHC uhc;
    private GameState gameState;
    private boolean isStarted;

    public GameManager(Main main,
                       GameWorld gameWorld) {
        this.main = main;
        this.gameWorld = gameWorld;
        this.uhc = new VanillaUHC(this);
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

    public UHC getUhc() {
        return uhc;
    }

    public void setUhc(UHC uhc) {
        this.uhc = uhc;
    }
}
