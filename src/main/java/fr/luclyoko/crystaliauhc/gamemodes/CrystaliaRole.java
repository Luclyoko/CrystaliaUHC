package fr.luclyoko.crystaliauhc.gamemodes;

import fr.luclyoko.crystaliauhc.Main;
import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.game.TeleportationTask;
import fr.luclyoko.crystaliauhc.gamemodes.customevents.GameDayStartingEvent;
import fr.luclyoko.crystaliauhc.gamemodes.customevents.GameNightStartingEvent;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class CrystaliaRole {
    protected Main main = Main.getInstance();

    protected GameManager gameManager;

    protected String name;

    protected CrystaliaPlayer crystaliaPlayer;

    protected int maxHealth;

    protected Map<PotionEffectType, Integer> permanentEffects;

    protected Map<PotionEffectType, Integer> dayEffects;

    protected Map<PotionEffectType, Integer> nightEffects;

    protected List<PotionEffectType> tempEffects;

    Listener updates;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CrystaliaPlayer getCrystaliaPlayer() {
        return this.crystaliaPlayer;
    }

    public int getMaxHealth() {
        return this.maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
        updatePlayerHealth();
    }

    public void updatePlayerHealth() {
        if (this.crystaliaPlayer.isOnline() && this.crystaliaPlayer.isAlive()) {
            Player player = this.crystaliaPlayer.getPlayer();
            if (this.maxHealth > 0) {
                player.setMaxHealth(this.maxHealth);
            } else {
                player.setMaxHealth(1.0D);
            }
        }
    }

    public void addDayEffect(PotionEffectType potionEffectType, int level) {
        this.dayEffects.put(potionEffectType, Integer.valueOf(level));
        updatePlayerEffects();
    }

    public void removeDayEffect(PotionEffectType potionEffectType) {
        this.dayEffects.remove(potionEffectType);
        updatePlayerEffects();
    }

    public void addNightEffect(PotionEffectType potionEffectType, int level) {
        this.nightEffects.put(potionEffectType, Integer.valueOf(level));
        updatePlayerEffects();
    }

    public void removeNightEffect(PotionEffectType potionEffectType) {
        this.nightEffects.remove(potionEffectType);
        updatePlayerEffects();
    }

    public void addTempEffect(final PotionEffect potionEffect) {
        this.tempEffects.add(potionEffect.getType());
        if (this.crystaliaPlayer.isOnline()) {
            Player player = this.crystaliaPlayer.getPlayer();
            player.addPotionEffect(potionEffect);
        }
        (new BukkitRunnable() {
            public void run() {
                CrystaliaRole.this.tempEffects.remove(potionEffect.getType());
                CrystaliaRole.this.updatePlayerEffects();
            }
        }).runTaskLater((Plugin)this.main, potionEffect.getDuration());
    }

    public void addPermEffect(PotionEffectType potionEffectType, int level) {
        this.permanentEffects.put(potionEffectType, Integer.valueOf(level));
        updatePlayerEffects();
    }

    public void removePermEffect(PotionEffectType potionEffectType, int level) {
        this.permanentEffects.remove(potionEffectType);
        updatePlayerEffects();
    }

    public void updatePlayerEffects() {
        if (this.crystaliaPlayer.isOnline() && this.crystaliaPlayer.isAlive()) {
            Player player = this.crystaliaPlayer.getPlayer();
            if (this.gameManager.getGameSettings().isDay()) {
                if (!this.dayEffects.isEmpty())
                    for (PotionEffectType potionEffectType : this.dayEffects.keySet()) {
                        if (!this.tempEffects.contains(potionEffectType))
                            player.addPotionEffect(new PotionEffect(potionEffectType, (this.gameManager
                                    .getGameSettings().getDayNightCycle() - this.gameManager.getGameTask().getDayNightCycleTimer()) * 20, ((Integer)this.dayEffects
                                    .get(potionEffectType)).intValue(), false, false));
                    }
                if (!this.nightEffects.isEmpty())
                    for (PotionEffectType potionEffectType : this.nightEffects.keySet()) {
                        if (!this.tempEffects.contains(potionEffectType))
                            player.removePotionEffect(potionEffectType);
                    }
            } else {
                if (!this.nightEffects.isEmpty())
                    for (PotionEffectType potionEffectType : this.nightEffects.keySet()) {
                        if (!this.tempEffects.contains(potionEffectType))
                            player.addPotionEffect(new PotionEffect(potionEffectType, (this.gameManager
                                    .getGameSettings().getDayNightCycle() - this.gameManager.getGameTask().getDayNightCycleTimer()) * 20, ((Integer)this.nightEffects
                                    .get(potionEffectType)).intValue(), false, false));
                    }
                if (!this.dayEffects.isEmpty())
                    for (PotionEffectType potionEffectType : this.dayEffects.keySet()) {
                        if (!this.tempEffects.contains(potionEffectType))
                            player.removePotionEffect(potionEffectType);
                    }
            }
            if (!this.permanentEffects.isEmpty())
                for (PotionEffectType potionEffectType : this.permanentEffects.keySet()) {
                    if (!this.tempEffects.contains(potionEffectType))
                        player.addPotionEffect(new PotionEffect(potionEffectType, 99999, ((Integer)this.permanentEffects

                                .get(potionEffectType)).intValue(), false, false));
                }
        }
    }

    public void updatePlayerAttributes() {
        updatePlayerEffects();
        updatePlayerHealth();
    }

    public List<PotionEffectType> getActiveEffects() {
        List<PotionEffectType> potionEffectTypes = new ArrayList<>();
        if (this.crystaliaPlayer.isOnline())
            this.crystaliaPlayer.getPlayer().getActivePotionEffects().forEach(potionEffect -> potionEffectTypes.add(potionEffect.getType()));
        potionEffectTypes.addAll(this.gameManager.getGameSettings().isDay() ? (Collection<? extends PotionEffectType>)this.dayEffects
                .keySet().stream().filter(potionEffectType -> !potionEffectTypes.contains(potionEffectType)).collect(Collectors.toList()) : (Collection<? extends PotionEffectType>)this.nightEffects
                .keySet().stream().filter(potionEffectType -> !potionEffectTypes.contains(potionEffectType)).collect(Collectors.toList()));
        potionEffectTypes.addAll((Collection<? extends PotionEffectType>)this.permanentEffects.keySet().stream().filter(potionEffectType -> !potionEffectTypes.contains(potionEffectType)).collect(Collectors.toList()));
        potionEffectTypes.addAll((Collection<? extends PotionEffectType>)this.tempEffects.stream().filter(potionEffectType -> !potionEffectTypes.contains(potionEffectType)).collect(Collectors.toList()));
        return potionEffectTypes;
    }

    public void respawn(boolean respawnOnDeathLocation) {
        if (this.crystaliaPlayer.isOnline() && !this.crystaliaPlayer.isAlive() && !this.crystaliaPlayer.isSpec()) {
            Player player = this.crystaliaPlayer.getPlayer();
            if (respawnOnDeathLocation) {
                player.teleport(this.crystaliaPlayer.getDeathLocation());
            } else {
                player.teleport(TeleportationTask.generateRandomLocation());
            }
            player.setGameMode(GameMode.SURVIVAL);
            player.setHealth(player.getMaxHealth());
            player.setFoodLevel(20);
            player.setSaturation(20.0F);
            this.crystaliaPlayer.setAlive(true);
            addTempEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 9, false, false));
        }
    }

    public CrystaliaRole(GameManager gameManager, CrystaliaPlayer crystaliaPlayer) {
        this.updates = new Listener() {
            @EventHandler
            public void onDayStarting(GameDayStartingEvent event) {
                CrystaliaRole.this.updatePlayerEffects();
            }

            @EventHandler
            public void onNightStarting(GameNightStartingEvent event) {
                CrystaliaRole.this.updatePlayerEffects();
            }

            @EventHandler(priority = EventPriority.HIGHEST)
            public void onJoin(PlayerJoinEvent event) {
                if (CrystaliaRole.this.main.getPlayerManager().getExactPlayer(event.getPlayer()) != null && CrystaliaRole.this.main.getPlayerManager().getExactPlayer(event.getPlayer()).equals(CrystaliaRole.this.crystaliaPlayer))
                    CrystaliaRole.this.updatePlayerAttributes();
            }
        };
        this.gameManager = gameManager;
        this.name = "";
        this.crystaliaPlayer = crystaliaPlayer;
        this.maxHealth = 20;
        this.permanentEffects = new HashMap<>();
        this.dayEffects = new HashMap<>();
        this.nightEffects = new HashMap<>();
        this.tempEffects = new ArrayList<>();
        this.main.getServer().getPluginManager().registerEvents(this.updates, (Plugin)this.main);
    }
}
