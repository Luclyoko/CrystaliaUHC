package fr.luclyoko.crystaliauhc.gamemodes.arena.powers;

import fr.luclyoko.crystaliauhc.Main;
import fr.luclyoko.crystaliauhc.gamemodes.arena.ArenaUHC;
import fr.luclyoko.crystaliauhc.gamemodes.arena.roles.ArenaRole;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import fr.luclyoko.crystaliauhc.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;

public abstract class ArenaPower implements Listener {

    protected final Main main;
    protected final ArenaUHC arenaUHC;
    protected ArenaRole arenaRole;
    protected ArenaPowerEnum arenaPowerEnum;
    protected int id;

    protected int uses;
    protected int cooldown;
    protected int duration;
    protected long lastUse;

    public ArenaPower(ArenaUHC arenaUHC, ArenaRole arenaRole, Main main) {
        this.main = main;
        this.arenaUHC = arenaUHC;
        this.arenaRole = arenaRole;
        this.duration = -1;
        this.uses = -1;
        this.cooldown = -1;
        this.lastUse = -1;
        this.id = this.arenaUHC.getArenaPowerManager().getNewPowerID();
        main.getServer().getPluginManager().registerEvents(this, main);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        CrystaliaPlayer playerWhoClicked = main.getPlayerManager().getExactPlayer(event.getPlayer());
        if (playerWhoClicked != arenaRole.getCrystaliaPlayer()) return;
        if (playerWhoClicked.getRole() == null || !(playerWhoClicked.getRole() instanceof ArenaRole)) return;
        ArenaRole playerRole = (ArenaRole) playerWhoClicked.getRole();
        if (playerRole != this.arenaRole) return;
        Action action = event.getAction();
        if (action.equals(Action.RIGHT_CLICK_BLOCK) || action.equals(Action.RIGHT_CLICK_AIR)) {
            ItemStack clickedItem = event.getItem();
            if (clickedItem.equals(getPowerItem())) {
                event.setCancelled(true);
                if (isOnUse()) {
                    playerWhoClicked.sendMessage("§cVotre " + getColoredName() + " §cest déjà en cours d'utilisation.");
                    return;
                }
                if (uses == 0) {
                    playerWhoClicked.sendMessage("§cVous ne possédez plus d'utilisations de votre " + getColoredName() + "§c.");
                    return;
                }
                if (getActualCooldown() > 0) {
                    playerWhoClicked.sendMessage("§cVeuillez patienter " + formatLongTime(getActualCooldown()) + " avant de pouvoir réutiliser votre " + getColoredName() + "§c.");
                    return;
                }
                lastUse = System.currentTimeMillis();
                if (uses > 0) uses--;
                execute(playerWhoClicked);
                if (lastUse != -1) {
                    playerWhoClicked.sendMessage("§aVous venez d'utiliser votre " + getColoredName() + "§a.");
                    Bukkit.getScheduler().runTaskLater(main, () -> {
                        if (uses >= 1 || uses == -1) {
                            if (playerWhoClicked.isOnline() && playerWhoClicked.isAlive()) {
                                if (playerWhoClicked.getRole() != null && playerWhoClicked.getRole() instanceof ArenaRole) {
                                    ArenaRole testRole = (ArenaRole) playerWhoClicked.getRole();
                                    if (testRole == this.arenaRole) {
                                        playerWhoClicked.sendMessage("§aVous pouvez de nouveau utiliser votre " + getColoredName() + "§a.");
                                    }
                                }
                            }
                        }
                    }, getTotalCooldown() * 20L);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerDrop(PlayerDropItemEvent event) {
        if (event.getItemDrop().getItemStack().equals(getPowerItem())) event.setCancelled(true);
    }

    public void execute(CrystaliaPlayer user) {
        user.sendMessage("§cErreur: Impossible d'exécuter ce pouvoir.");
    }

    public String getName() {
        return arenaPowerEnum.getName();
    }

    public String getColor() {
        return arenaPowerEnum.getColor();
    }

    public String getColoredName() {
        return getColor() + getName();
    }

    public int getTimeSinceLastUse() {
        if (lastUse == -1) return -1;
        return Math.round((System.currentTimeMillis() - lastUse) / 1000f);
    }

    public int getTotalCooldown() {
        int i = 0;
        if (duration != -1) i += duration;
        if (cooldown != -1) i += cooldown;
        return i;
    }

    public int getActualCooldown() {
        if (getTimeSinceLastUse() == -1) return -1;
        return getTotalCooldown() - getTimeSinceLastUse();
    }

    public String formatLongTime(int time) {
        int cd = time;
        int sec = cd % 60;
        cd -= sec;
        int min = cd / 60;
        if (min > 0) {
            return "§4" + min + " §cminute(s) et §4" + sec + " §cseconde(s)";
        }
        return "§4" + sec + " §cseconde(s)";
    }

    public String formatTimeDefault(int time) {
        StringBuilder sb = new StringBuilder();
        int cd = time;
        int sec = cd % 60;
        cd -= sec;
        int min = cd / 60;
        if (min != 0) {
            sb.append(min).append("min");
        }
        if (sec != 0) {
            sb.append(sec).append("s");
        }
        return sb.toString();
    }

    public int getUses() {
        return uses;
    }

    public boolean isOnUse() {
        if (duration == -1) return false;
        if (lastUse == -1) return false;
        return duration - getTimeSinceLastUse() >= 0;
    }

    public boolean isOnCooldown() {
        return getActualCooldown() > 0 && !isOnUse();
    }

    public int getCooldown() {
        return cooldown;
    }

    public ItemStack getPowerItem() {
        return new ItemBuilder(arenaPowerEnum.getMaterial())
                .setDisplayName(arenaPowerEnum.getColor() + ChatColor.BOLD + arenaPowerEnum.getName() + " §8• §7Clic-Droit")
                .setLore(Collections.singletonList("§8» §7" + getDescription().replace("§f", "§7")))
                .addEnchant(Enchantment.DURABILITY, id)
                .addItemFlags(ItemFlag.HIDE_ENCHANTS)
                .toItemStack();
    }

    public String getDescription() {
        return "La description du pouvoir " + getColoredName() + " §fest introuvable.";
    }

    public String getFormattedDescription() {
        StringBuilder sb = new StringBuilder(getColor() + ChatColor.BOLD + getName() + " ");
        if (cooldown != -1) sb.append(ChatColor.RESET)
                .append(getColor())
                .append(ChatColor.ITALIC)
                .append("(cooldown: ")
                .append(formatTimeDefault(cooldown))
                .append(") ");
        if (uses != -1) sb.append(ChatColor.RESET)
                .append(getColor())
                .append(ChatColor.ITALIC)
                .append("(utilisations: ")
                .append(uses)
                .append(") ");
        sb.append("§8• §7Clic-Droit\n §8» §f");
        sb.append(getDescription());
        return sb.toString();
    }
}
