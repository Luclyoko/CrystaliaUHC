package fr.luclyoko.crystaliauhc.players;

import fr.luclyoko.crystaliauhc.Main;
import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.gamemodes.CrystaliaRole;
import fr.luclyoko.crystaliauhc.teams.Team;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

public class CrystaliaPlayer {
    private Main main;

    private GameManager gameManager;

    private UUID playerUUID;

    private String playerName;

    private Team team;

    private CrystaliaRole role;

    private boolean isSpec;

    private boolean isMod;

    private boolean isAlive;

    private BukkitRunnable idleTask;

    private ItemStack[] playerInvContents;

    private ItemStack[] playerArmorContents;

    private Location deathLocation;

    public CrystaliaPlayer(Main main, GameManager gameManager, Player player, boolean isSpec) {
        this.main = main;
        this.gameManager = gameManager;
        this.playerUUID = player.getUniqueId();
        this.playerName = player.getName();
        this.team = null;
        this.role = null;
        this.isSpec = isSpec;
        this.isMod = false;
        this.isAlive = true;
        this.deathLocation = null;
    }

    public UUID getPlayerUUID() {
        return this.playerUUID;
    }

    public void setPlayerUUID(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public boolean isSpec() {
        return this.isSpec;
    }

    public void setSpec(boolean spec) {
        this.isSpec = spec;
    }

    public boolean isAlive() {
        return this.isAlive;
    }

    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public boolean isOnline() {
        return (Bukkit.getPlayer(this.playerUUID) != null);
    }

    public Team getTeam() {
        return this.team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public CrystaliaRole getRole() {
        return this.role;
    }

    public void setRole(CrystaliaRole role) {
        this.role = role;
    }

    public BukkitRunnable getIdleTask() {
        return this.idleTask;
    }

    public void setIdleTask(BukkitRunnable idleTask) {
        this.idleTask = idleTask;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(this.playerUUID);
    }

    public ItemStack[] getPlayerInvContents() {
        return this.playerInvContents;
    }

    public ItemStack[] getPlayerArmorContents() {
        return this.playerArmorContents;
    }

    public void setPlayerInvContents(ItemStack[] playerInvContents) {
        this.playerInvContents = playerInvContents;
    }

    public void setPlayerArmorContents(ItemStack[] playerArmorContents) {
        this.playerArmorContents = playerArmorContents;
    }

    public Location getDeathLocation() {
        return this.deathLocation;
    }

    public void setDeathLocation(Location deathLocation) {
        this.deathLocation = deathLocation;
    }

    public void sendMessage(String message) {
        if (isOnline())
            getPlayer().sendMessage(message);
    }

    public void giveItem(ItemStack itemStack) {
        if (isOnline()) {
            PlayerInventory playerInv = getPlayer().getInventory();
            if (playerInv.firstEmpty() != -1) {
                playerInv.addItem(new ItemStack[] { itemStack });
            } else {
                getPlayer().getWorld().dropItem(getPlayer().getLocation().add(0.0D, 0.5D, 0.0D), itemStack);
            }
        }
    }

    public boolean isMod() {
        return this.isMod;
    }

    public void setMod(boolean isMod) {
        this.isMod = isMod;
        if (!this.gameManager.isStarted())
            this.isSpec = isMod;
    }
}
