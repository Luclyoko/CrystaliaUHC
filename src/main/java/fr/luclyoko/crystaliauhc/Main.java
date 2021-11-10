package fr.luclyoko.crystaliauhc;

import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.guis.GuiManager;
import fr.luclyoko.crystaliauhc.map.GameWorld;
import fr.luclyoko.crystaliauhc.map.MapGenerator;
import fr.luclyoko.crystaliauhc.map.MapManager;
import fr.luclyoko.crystaliauhc.modules.Commands;
import fr.luclyoko.crystaliauhc.modules.Listeners;
import fr.luclyoko.crystaliauhc.players.PlayerManager;
import fr.luclyoko.crystaliauhc.utils.schematics.Schematic;
import fr.luclyoko.crystaliauhc.utils.schematics.SchematicManager;
import fr.luclyoko.crystaliauhc.utils.scoreboard.ScoreboardManager;
import org.bukkit.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Main extends JavaPlugin {

    private static Main instance;
    public static Main getInstance() {
        return instance;
    }

    private ScheduledExecutorService executorMonoThread;
    public ScheduledExecutorService getExecutorMonoThread() {
        return executorMonoThread;
    }

    private ScheduledExecutorService scheduledExecutorService;
    public ScheduledExecutorService getScheduledExecutorService() {
        return scheduledExecutorService;
    }

    private ScoreboardManager scoreboardManager;
    public ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }

    private MapManager mapManager;
    private GameManager gameManager;

    private GuiManager guiManager;
    public GuiManager getGuiManager() {
        return guiManager;
    }

    private SchematicManager schematicManager;
    public SchematicManager getSchematicManager() {
        return schematicManager;
    }

    private PlayerManager playerManager;
    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public Main () {
        instance = this;
    }

    @Override
    public void onLoad() {
        File file = new File(getDataFolder().getParentFile().getParentFile(), "world");
        if(!deleteWorld(file)) {
            getLogger().severe("Erreur lors du reset de la map.");
            getServer().getPluginManager().disablePlugin(this);
        }
        this.mapManager = new MapManager(this);
        this.mapManager.modifyBiomes();
    }

    @Override
    public void onEnable() {
        this.guiManager = new GuiManager();
        this.schematicManager = new SchematicManager();
        getLogger().info("Starting CrystaliaUHC plugin...");
        Bukkit.getScheduler().runTaskLater(this, () -> {
            createWorld("world");
            this.gameManager = new GameManager(this, this.mapManager.loadMap("world", 0, 0).generate(100));
            Location location = new Location(Bukkit.getWorld("world"), 0.0D, 150.0D, 0.0D);
            SchematicManager schematicManager = getSchematicManager();
            Schematic centerSchematic = schematicManager
                    .loadSchematic(schematicManager.getSchematicFromRessources("spawn.schematic"));
            schematicManager.pasteSchematic(location.clone().add(-15, 0, -15), centerSchematic);
            new Commands(this).registerAll();
            new Listeners(this).registerAll();
            this.executorMonoThread = Executors.newScheduledThreadPool(1);
            this.scheduledExecutorService = Executors.newScheduledThreadPool(16);
            this.scoreboardManager = new ScoreboardManager();
            this.playerManager = new PlayerManager(this);
        }, 1L);
    }

    @Override
    public void onDisable() {

    }

    public MapManager getMapManager() {
        return mapManager;
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    private boolean deleteWorld(File path) {
        if (path.exists()) {
            final File[] files = path.listFiles();
            for (int i = 0; i < files.length; ++i) {
                if (files[i].isDirectory()) {
                    deleteWorld(files[i]);
                }
                else {
                    files[i].delete();
                }
            }
        }
        return path.delete();
    }

    public void createWorld(String worldName) {
        this.getServer().createWorld(new WorldCreator(worldName));
        for (World world : Bukkit.getWorlds()) {
            world.setGameRuleValue("naturalRegeneration", "false");
            world.setGameRuleValue("doFireTick", "false");
            world.setGameRuleValue("reducedDebugInfo", "true");
            world.setPVP(true);
            world.setTime(0L);
            world.setDifficulty(Difficulty.NORMAL);
        }
    }
}
