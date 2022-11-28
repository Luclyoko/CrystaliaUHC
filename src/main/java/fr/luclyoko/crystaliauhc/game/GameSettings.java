package fr.luclyoko.crystaliauhc.game;

import fr.luclyoko.crystaliauhc.Main;
import fr.luclyoko.crystaliauhc.game.timers.Border;
import fr.luclyoko.crystaliauhc.game.timers.Invincibility;
import fr.luclyoko.crystaliauhc.game.timers.Pvp;
import fr.luclyoko.crystaliauhc.timers.Timer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class GameSettings {
    private Main main;

    private GameManager gameManager;

    private boolean devMode;

    private UUID host;

    private String hostName;

    private int slots;

    private int idleTime;

    private Map<Enchantment, Integer> enchantsLimit;

    private ItemStack[] startInventory;

    private ItemStack[] startArmor;

    private Timer pvp;

    private Timer invincibility;

    private Timer border;

    private List<Timer> otherTimers;

    private List<Timer> allTimers;

    private int dayNightCycle;

    private boolean eternalDay;

    private boolean isDay;

    private String winner;

    private int strengthPercent;

    private int resistancePercent;

    public GameSettings(Main main, GameManager gameManager) {
        this.main = main;
        this.gameManager = gameManager;
        this.devMode = false;
        this.host = null;
        this.hostName = "Aucun";
        this.slots = 40;
        this.idleTime = 900;
        this.enchantsLimit = getDefaultEnchantLimit();
        this.startInventory = new ItemStack[36];
        this.startArmor = new ItemStack[4];
        this.pvp = (Timer)new Pvp(gameManager, 1200);
        this.invincibility = (Timer)new Invincibility(gameManager, 60);
        this.border = (Timer)new Border(gameManager, 3600);
        this.otherTimers = new ArrayList<>();
        this.allTimers = new ArrayList<>(this.otherTimers);
        this.allTimers.add(this.pvp);
        this.allTimers.add(this.invincibility);
        this.allTimers.add(this.border);
        this.dayNightCycle = 600;
        this.eternalDay = false;
        this.isDay = true;
        this.strengthPercent = 20;
        this.resistancePercent = 20;
    }

    public boolean isDevMode() {
        return this.devMode;
    }

    public void setDevMode(boolean devMode) {
        this.devMode = devMode;
    }

    public UUID getHost() {
        return this.host;
    }

    public void setHost(UUID host) {
        this.host = host;
    }

    public String getHostName() {
        return this.hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public int getIdleTime() {
        return this.idleTime;
    }

    public void setIdleTime(int idleTime) {
        this.idleTime = idleTime;
    }

    public Timer getPvp() {
        return this.pvp;
    }

    public void setPvp(Timer pvp) {
        this.pvp = pvp;
    }

    public Timer getInvincibility() {
        return this.invincibility;
    }

    public void setInvincibility(Timer invincibility) {
        this.invincibility = invincibility;
    }

    public Timer getBorder() {
        return this.border;
    }

    public void setBorder(Timer border) {
        this.border = border;
    }

    public List<Timer> getOtherTimers() {
        return this.otherTimers;
    }

    public void addTimer(Timer timer) {
        this.otherTimers.add(timer);
        this.allTimers.add(timer);
    }

    public void setOtherTimers(List<Timer> otherTimers) {
        this.otherTimers = otherTimers;
    }

    public List<Timer> getAllTimers() {
        return this.allTimers;
    }

    public String getWinner() {
        return this.winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public int getSlots() {
        return this.slots;
    }

    public void setSlots(int slots) {
        this.slots = slots;
    }

    public Map<Enchantment, Integer> getDefaultEnchantLimit() {
        Map<Enchantment, Integer> limitMap = new HashMap<>();
        for (Enchantment enchantment : Enchantment.values())
            limitMap.put(enchantment, Integer.valueOf(enchantment.getMaxLevel()));
        return limitMap;
    }

    public Map<Enchantment, Integer> getEnchantsLimit() {
        return this.enchantsLimit;
    }

    public int getEnchantLimit(Enchantment enchantment) {
        return ((Integer)this.enchantsLimit.get(enchantment)).intValue();
    }

    public void increaseEnchantLimit(Enchantment enchantment) {
        int actualLimit = ((Integer)this.enchantsLimit.get(enchantment)).intValue();
        if (actualLimit == enchantment.getMaxLevel())
            return;
        this.enchantsLimit.put(enchantment, Integer.valueOf(++actualLimit));
    }

    public void decreaseEnchantLimit(Enchantment enchantment) {
        int actualLimit = ((Integer)this.enchantsLimit.get(enchantment)).intValue();
        if (actualLimit == 0)
            return;
        this.enchantsLimit.put(enchantment, Integer.valueOf(--actualLimit));
    }

    public ItemStack[] getStartInventory() {
        return this.startInventory;
    }

    public ItemStack[] getStartArmor() {
        return this.startArmor;
    }

    public void setStartInventory(ItemStack[] startInventory) {
        this.startInventory = startInventory;
    }

    public void setStartArmor(ItemStack[] startArmor) {
        this.startArmor = startArmor;
    }

    public int getDayNightCycle() {
        return this.dayNightCycle;
    }

    public boolean isDay() {
        return this.isDay;
    }

    public boolean isEternalDay() {
        return this.eternalDay;
    }

    public void setDayNightCycle(int dayNightCycle) {
        this.dayNightCycle = dayNightCycle;
    }

    public void setDay(boolean day) {
        this.isDay = day;
    }

    public void setEternalDay(boolean eternalDay) {
        this.eternalDay = eternalDay;
    }

    public int getStrengthPercent() {
        return this.strengthPercent;
    }

    public int getResistancePercent() {
        return this.resistancePercent;
    }

    public void setStrengthPercent(int strengthPercent) {
        this.strengthPercent = strengthPercent;
    }

    public void setResistancePercent(int resistancePercent) {
        this.resistancePercent = resistancePercent;
    }
}
