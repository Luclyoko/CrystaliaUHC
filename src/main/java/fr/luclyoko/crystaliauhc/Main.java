package fr.luclyoko.crystaliauhc;

import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.guis.GuiManager;
import fr.luclyoko.crystaliauhc.map.MapManager;
import fr.luclyoko.crystaliauhc.modules.Commands;
import fr.luclyoko.crystaliauhc.modules.Listeners;
import fr.luclyoko.crystaliauhc.players.PlayerManager;
import fr.luclyoko.crystaliauhc.teams.TeamManager;
import fr.luclyoko.crystaliauhc.utils.Title;
import fr.luclyoko.crystaliauhc.utils.schematics.SchematicManager;
import fr.luclyoko.crystaliauhc.utils.scoreboard.ScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Main extends JavaPlugin {
    private static Main instance;

    private ScheduledExecutorService executorMonoThread;

    private ScheduledExecutorService scheduledExecutorService;

    private ScoreboardManager scoreboardManager;

    private MapManager mapManager;

    private GameManager gameManager;

    private GuiManager guiManager;

    private SchematicManager schematicManager;

    private PlayerManager playerManager;

    private TeamManager teamManager;

    private Title title;

    public static Main getInstance() {
        return instance;
    }

    public ScheduledExecutorService getExecutorMonoThread() {
        return this.executorMonoThread;
    }

    public ScheduledExecutorService getScheduledExecutorService() {
        return this.scheduledExecutorService;
    }

    public ScoreboardManager getScoreboardManager() {
        return this.scoreboardManager;
    }

    public GuiManager getGuiManager() {
        return this.guiManager;
    }

    public SchematicManager getSchematicManager() {
        return this.schematicManager;
    }

    public PlayerManager getPlayerManager() {
        return this.playerManager;
    }

    public TeamManager getTeamManager() {
        return this.teamManager;
    }

    public Title getTitle() {
        return this.title;
    }

    public Main() {
        instance = this;
    }

    public void onLoad() {
        this.mapManager = new MapManager(this);
        this.mapManager.modifyBiomes();
    }

    public void onEnable() {
        this.guiManager = new GuiManager(this);
        this.schematicManager = new SchematicManager();
        getLogger().info("Starting CrystaliaUHC plugin...");
        Bukkit.getScheduler().runTaskLater(this, () -> {
            this.mapManager.createLobby();
            this.gameManager = new GameManager(this, this.mapManager.loadMap("gameworld", 0, 0, null));
            (new Commands(this)).registerAll();
            (new Listeners(this)).registerAll();
            this.executorMonoThread = Executors.newScheduledThreadPool(1);
            this.scheduledExecutorService = Executors.newScheduledThreadPool(16);
            this.scoreboardManager = new ScoreboardManager();
            this.playerManager = new PlayerManager(this);
            this.teamManager = new TeamManager(this);
            this.title = new Title();
        }, 1L);
    }

    public void onDisable() {}

    public MapManager getMapManager() {
        return this.mapManager;
    }

    public GameManager getGameManager() {
        return this.gameManager;
    }
}
