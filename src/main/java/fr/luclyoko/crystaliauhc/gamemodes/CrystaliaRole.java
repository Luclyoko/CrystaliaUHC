package fr.luclyoko.crystaliauhc.gamemodes;

import fr.luclyoko.crystaliauhc.Main;
import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.game.TeleportationTask;
import fr.luclyoko.crystaliauhc.gamemodes.customevents.GameDayStartingEvent;
import fr.luclyoko.crystaliauhc.gamemodes.customevents.GameNightStartingEvent;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    protected boolean isInvincible;
    protected boolean noFall;

    protected int selfStrengthPercent;

    protected int selfResistancePercent;

    protected int selfSpeedPercent;

    protected boolean canSeeLife;

    public CrystaliaRole(GameManager gameManager, CrystaliaPlayer crystaliaPlayer) {
        this.gameManager = gameManager;
        this.name = "";
        this.crystaliaPlayer = crystaliaPlayer;
        this.maxHealth = 20;
        this.permanentEffects = new HashMap<>();
        this.dayEffects = new HashMap<>();
        this.nightEffects = new HashMap<>();
        this.tempEffects = new ArrayList<>();
        this.isInvincible = false;
        this.noFall = false;
        this.canSeeLife = false;
        this.main.getServer().getPluginManager().registerEvents(this.updates, this.main);
    }

    Listener updates = new Listener() {
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

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CrystaliaPlayer getCrystaliaPlayer() {
        return this.crystaliaPlayer;
    }

    public void setCrystaliaPlayer(CrystaliaPlayer crystaliaPlayer) {
        this.crystaliaPlayer = crystaliaPlayer;
    }

    public int getMaxHealth() {
        return this.maxHealth;
    }

    public boolean isInvincible() {
        return isInvincible;
    }

    public void setInvincible(boolean invincible) {
        isInvincible = invincible;
    }

    public boolean isNoFall() {
        return noFall;
    }

    public void setNoFall(boolean noFall) {
        this.noFall = noFall;
    }

    public void addMaxHealth(int health) {
        setMaxHealth(maxHealth + health);
    }

    public void removeMaxHealth(int health) {
        setMaxHealth(maxHealth - health);
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

    public void heal() {
        if (this.crystaliaPlayer.isOnline() && this.crystaliaPlayer.isAlive()) {
            Player player = this.crystaliaPlayer.getPlayer();
            player.setHealth(maxHealth);
        }
    }

    public void heal(int toHeal) {
        if (this.crystaliaPlayer.isOnline() && this.crystaliaPlayer.isAlive()) {
            Player player = this.crystaliaPlayer.getPlayer();
            if (player.getHealth() + toHeal >= maxHealth) player.setHealth(maxHealth);
            else player.setHealth(player.getHealth() + toHeal);
        }
    }

    public void resetEffects() {
        this.dayEffects = new HashMap<>();
        this.nightEffects = new HashMap<>();
        this.tempEffects = new ArrayList<>();
        this.permanentEffects = new HashMap<>();
        updatePlayerEffects();
    }

    public void addDayEffect(PotionEffectType potionEffectType, int level) {
        this.dayEffects.put(potionEffectType, level);
        updatePlayerEffects();
    }

    public void removeDayEffect(PotionEffectType potionEffectType) {
        this.dayEffects.remove(potionEffectType);
        updatePlayerEffects();
    }

    public void addNightEffect(PotionEffectType potionEffectType, int level) {
        this.nightEffects.put(potionEffectType, level);
        updatePlayerEffects();
    }

    public void removeNightEffect(PotionEffectType potionEffectType) {
        this.nightEffects.remove(potionEffectType);
        updatePlayerEffects();
    }

    public void addTempEffect(PotionEffectType potionEffectType, int level, int duration, boolean particles) {
        PotionEffect potionEffect = new PotionEffect(potionEffectType, duration, level, false, particles);
        this.tempEffects.add(potionEffectType);
        if (this.crystaliaPlayer.isOnline()) {
            Player player = this.crystaliaPlayer.getPlayer();
            player.removePotionEffect(potionEffectType);
            player.addPotionEffect(potionEffect);
        }
        (new BukkitRunnable() {
            public void run() {
                CrystaliaRole.this.tempEffects.remove(potionEffect.getType());
                CrystaliaRole.this.updatePlayerEffects();
            }
        }).runTaskLater(this.main, duration + 10);
    }

    public boolean hasTempEffect(PotionEffectType potionEffectType) {
        return tempEffects.contains(potionEffectType);
    }

    public void addPermEffect(PotionEffectType potionEffectType, int level) {
        this.permanentEffects.put(potionEffectType, level);
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
                if (!this.nightEffects.isEmpty())
                    for (PotionEffectType potionEffectType : this.nightEffects.keySet()) {
                        if (!this.tempEffects.contains(potionEffectType))
                            player.removePotionEffect(potionEffectType);
                    }
                if (!this.dayEffects.isEmpty())
                    for (PotionEffectType potionEffectType : this.dayEffects.keySet()) {
                        if (!this.tempEffects.contains(potionEffectType))
                            player.addPotionEffect(new PotionEffect(potionEffectType, (this.gameManager
                                    .getGameSettings().getDayNightCycle() - this.gameManager.getGameTask().getDayNightCycleTimer()) * 20, this.dayEffects
                                    .get(potionEffectType), false, false));
                    }
            } else {
                if (!this.dayEffects.isEmpty())
                    for (PotionEffectType potionEffectType : this.dayEffects.keySet()) {
                        if (!this.tempEffects.contains(potionEffectType))
                            player.removePotionEffect(potionEffectType);
                    }
                if (!this.nightEffects.isEmpty())
                    for (PotionEffectType potionEffectType : this.nightEffects.keySet()) {
                        if (!this.tempEffects.contains(potionEffectType))
                            player.addPotionEffect(new PotionEffect(potionEffectType, (this.gameManager
                                    .getGameSettings().getDayNightCycle() - this.gameManager.getGameTask().getDayNightCycleTimer()) * 20, this.nightEffects
                                    .get(potionEffectType), false, false));
                    }
            }
            if (!this.permanentEffects.isEmpty())
                for (PotionEffectType potionEffectType : this.permanentEffects.keySet()) {
                    if (!this.tempEffects.contains(potionEffectType))
                        player.addPotionEffect(new PotionEffect(potionEffectType, 99999, this.permanentEffects
                                .get(potionEffectType), false, false));
                }
        }
    }

    public void updatePlayerAttributes() {
        updatePlayerEffects();
        updatePlayerHealth();
        updatePlayerSpeed();
        updateCanSeeLife();
    }

    public List<PotionEffectType> getActiveEffects() {
        List<PotionEffectType> potionEffectTypes = new ArrayList<>();
        if (this.crystaliaPlayer.isOnline())
            this.crystaliaPlayer.getPlayer().getActivePotionEffects().forEach(potionEffect -> potionEffectTypes.add(potionEffect.getType()));
        potionEffectTypes.addAll(this.gameManager.getGameSettings().isDay() ? this.dayEffects
                .keySet().stream().filter(potionEffectType -> !potionEffectTypes.contains(potionEffectType)).collect(Collectors.toList()) : this.nightEffects
                .keySet().stream().filter(potionEffectType -> !potionEffectTypes.contains(potionEffectType)).collect(Collectors.toList()));
        potionEffectTypes.addAll(this.permanentEffects.keySet().stream().filter(potionEffectType -> !potionEffectTypes.contains(potionEffectType)).collect(Collectors.toList()));
        potionEffectTypes.addAll(this.tempEffects.stream().filter(potionEffectType -> !potionEffectTypes.contains(potionEffectType)).collect(Collectors.toList()));
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
            addTempEffect(PotionEffectType.DAMAGE_RESISTANCE, 9, 100, false);
        }
    }

    public int getSelfStrengthPercent() {
        return selfStrengthPercent;
    }

    public void setSelfStrengthPercent(int selfStrengthPercent) {
        this.selfStrengthPercent = selfStrengthPercent;
    }

    public void addSelfStrengthPercent(int selfStrengthPercent) {
        this.selfStrengthPercent += selfStrengthPercent;
    }

    public void removeSelfStrengthPercent(int selfStrengthPercent) {
        this.selfStrengthPercent -= selfStrengthPercent;
    }

    public int getSelfResistancePercent() {
        return selfResistancePercent;
    }

    public void setSelfResistancePercent(int selfResistancePercent) {
        this.selfResistancePercent = selfResistancePercent;
    }

    public void addSelfResistancePercent(int selfResistancePercent) {
        this.selfResistancePercent += selfResistancePercent;
    }

    public void removeSelfResistancePercent(int selfResistancePercent) {
        this.selfResistancePercent -= selfResistancePercent;
    }

    public int getSelfSpeedPercent() {
        return selfSpeedPercent;
    }

    public void setSelfSpeedPercent(int selfSpeedPercent) {
        this.selfSpeedPercent = selfSpeedPercent;
        updatePlayerSpeed();
    }

    public void addSelfSpeedPercent(int selfSpeedPercent) {
        this.selfSpeedPercent += selfSpeedPercent;
        updatePlayerSpeed();
    }

    public void removeSelfSpeedPercent(int selfSpeedPercent) {
        this.selfSpeedPercent -= selfSpeedPercent;
        updatePlayerSpeed();
    }

    public void updatePlayerSpeed() {
        if (this.crystaliaPlayer.isOnline() && this.crystaliaPlayer.isAlive()) {
            Player player = this.crystaliaPlayer.getPlayer();
            float defaultSpeed = 0.2f;
            player.setWalkSpeed(defaultSpeed + (defaultSpeed * this.selfSpeedPercent) / 100);
        }
    }

    public boolean canSeeLife() {
        return canSeeLife;
    }

    public void setCanSeeLife(boolean canSeeLife) {
        this.canSeeLife = canSeeLife;
        updateCanSeeLife();
    }

    public void updateCanSeeLife() {
        Scoreboard sb = crystaliaPlayer.getScoreboard();
        if (this.canSeeLife) {
            Objective displayNameLife;
            if (sb.getObjective("vie") != null) displayNameLife = sb.getObjective("vie");
            else displayNameLife = sb.registerNewObjective("vie", "health");
            displayNameLife.setDisplayName("§c❤");
            displayNameLife.setDisplaySlot(DisplaySlot.BELOW_NAME);
        } else {
            if (sb.getObjective("vie") != null) sb.getObjective("vie").unregister();
        }
    }
}
