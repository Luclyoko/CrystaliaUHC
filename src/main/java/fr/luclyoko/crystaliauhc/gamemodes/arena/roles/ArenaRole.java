package fr.luclyoko.crystaliauhc.gamemodes.arena.roles;

import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.gamemodes.CrystaliaRole;
import fr.luclyoko.crystaliauhc.gamemodes.arena.ArenaUHC;
import fr.luclyoko.crystaliauhc.gamemodes.arena.powers.ArenaPower;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import fr.luclyoko.crystaliauhc.utils.Effects;
import fr.luclyoko.crystaliauhc.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class ArenaRole extends CrystaliaRole implements Listener {

    protected ArenaRolesEnum arenaRolesEnum;

    protected final ArenaUHC arenaUHC;

    protected int kills;

    protected boolean silent;

    private List<ArenaPower> powers;

    private BukkitTask powersCdTask;

    public ArenaRole(GameManager gameManager, CrystaliaPlayer crystaliaPlayer) {
        super(gameManager, crystaliaPlayer);
        this.arenaUHC = (ArenaUHC) gameManager.getGamemodeUhc();
        this.kills = 0;
        this.silent = false;
        this.powers = new ArrayList<>();
        this.powersCdTask = Bukkit.getScheduler().runTaskTimer(main, this.powersCdRunnable, 20, 20);
        main.getServer().getPluginManager().registerEvents(this, main);
    }

    Runnable powersCdRunnable = () -> {
        if (!this.powers.isEmpty()) {
            if (crystaliaPlayer.isOnline() && crystaliaPlayer.isAlive()) {
                if (crystaliaPlayer.getRole() != null && crystaliaPlayer.getRole().equals(this)) {
                    StringBuilder sb = new StringBuilder();
                    int i = 0;
                    for (ArenaPower power : this.powers) {
                        if (power.getUses() != 0 || power.getCooldown() != -1) {
                            sb.append(power.getColoredName())
                                    .append(" §7» ");
                            if (power.isOnUse()) sb.append("§eActif");
                            else if (power.isOnCooldown()) sb.append("§c")
                                    .append(power.formatTimeDefault(power.getActualCooldown()));
                            else sb.append("§aDisponible");
                            i++;
                            if (i != powers.size()) sb.append(" §8| ");
                        }
                    }
                    main.getTitle().sendActionBar(crystaliaPlayer.getPlayer(), sb.toString());
                }
            }
        }
    };

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player target = event.getEntity();
        CrystaliaPlayer crystaliaDead = main.getPlayerManager().getExactPlayer(target);
        if (crystaliaDead != crystaliaPlayer) return;
        powersCdTask.cancel();
    }

    @Override
    public String getName() {
        if (arenaRolesEnum != null) {
            return arenaRolesEnum.getName();
        }
        return "Nom introuvable";
    }

    public ArenaUHC getArenaUHC() {
        return arenaUHC;
    }

    public ArenaRolesEnum getArenaRolesEnum() {
        return arenaRolesEnum;
    }

    public String getDescription() {
        return "§7Votre rôle: §6§l" + getName() + getEffectsDescription() + "\n \n§3§lPouvoirs:" + getPowersDescription();
    }

    public String getEffectsDescription() {
        StringBuilder stringBuilder = new StringBuilder("\n \n§6§lEffets:");
        boolean hasEffects = false;
        if (maxHealth != 20) {
            stringBuilder.append("\n  §f➢ §cBarre de vie:");
            stringBuilder.append("\n   §7• §f");
            stringBuilder.append(maxHealth > 20 ? "+ " + (maxHealth - 20) / 2 : "- " + (20 - maxHealth) / 2);
            stringBuilder.append("§c❤");
            hasEffects = true;
        }
        if (!permanentEffects.isEmpty()) {
            stringBuilder.append("\n  §f➢ §dPermanents:");
            permanentEffects.keySet().forEach(potionEffectType -> stringBuilder.append("\n   §7• §f")
                    .append(Effects.getDisplayName(potionEffectType))
                    .append(" ")
                    .append(permanentEffects.get(potionEffectType) + 1));
            hasEffects = true;
        }
        if (!dayEffects.isEmpty()) {
            stringBuilder.append("\n  §f➢ §eJour:");
            dayEffects.keySet().forEach(potionEffectType -> stringBuilder.append("\n   §7• §f")
                    .append(Effects.getDisplayName(potionEffectType))
                    .append(" ")
                    .append(dayEffects.get(potionEffectType) + 1));
            hasEffects = true;
        }
        if (!nightEffects.isEmpty()) {
            stringBuilder.append("\n  §f➢ §8Nuit:");
            nightEffects.keySet().forEach(potionEffectType -> stringBuilder.append("\n   §7• §f")
                    .append(Effects.getDisplayName(potionEffectType))
                    .append(" ")
                    .append(nightEffects.get(potionEffectType) + 1));
            hasEffects = true;
        }
        if (!hasEffects) stringBuilder.append("\n   §7• §fAucun");
        return stringBuilder.toString();
    }

    public String getPowersDescription() {
        StringBuilder sb = new StringBuilder();
        List<String> powersList = new ArrayList<>(getPowersDescriptionList());
        powersList.forEach(s -> sb.append("\n   §7• §f").append(s));
        return sb.toString();
    }

    public List<String> getAdditionalDescription() {
        return Collections.emptyList();
    }

    public List<String> getPowersDescriptionList() {
        List<String> strings = new ArrayList<>();
        if (!getPowers().isEmpty()) getPowers().forEach(power -> strings.add(power.getFormattedDescription()));
        if (!getAdditionalDescription().isEmpty()) strings.addAll(new ArrayList<>(getAdditionalDescription()));
        if (strings.isEmpty()) return Collections.singletonList("Aucun");
        return strings;
    }

    public int getKills() {
        return kills;
    }

    public void increaseKills() {
        kills++;
    }

    public boolean isSilent() {
        return silent;
    }

    public void setSilent(boolean silent) {
        this.silent = silent;
    }

    public ItemStack getHelmet() {
        return new ItemBuilder(Material.DIAMOND_HELMET).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).setUnbreakable(true).toItemStack();
    }

    public ItemStack getChestplate() {
        return new ItemBuilder(Material.DIAMOND_CHESTPLATE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).setUnbreakable(true).toItemStack();
    }

    public ItemStack getLeggings() {
        return new ItemBuilder(Material.IRON_LEGGINGS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).setUnbreakable(true).toItemStack();
    }

    public ItemStack getBoots() {
        return new ItemBuilder(Material.DIAMOND_BOOTS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).setUnbreakable(true).toItemStack();
    }

    public ItemStack getSword() {
        return new ItemBuilder(Material.DIAMOND_SWORD).addEnchant(Enchantment.DAMAGE_ALL, 3).setUnbreakable(true).toItemStack();
    }

    public ItemStack getBow() {
        return new ItemBuilder(Material.BOW).addEnchant(Enchantment.ARROW_DAMAGE, 2).setUnbreakable(true).toItemStack();
    }

    public List<ItemStack> getItems() {
        List<ItemStack> items = new ArrayList<>(getRoleItems());
        items.addAll(getPowersItems());
        return items;
    }

    public List<ItemStack> getRoleItems() {
        return Collections.emptyList();
    }

    public List<ItemStack> getPowersItems() {
        List<ItemStack> items = new ArrayList<>();
        this.powers.forEach(power -> items.add(power.getPowerItem()));
        return items;
    }

    public List<ArenaPower> getPowers() {
        return powers;
    }

    public void addPower(ArenaPower power) {
        this.powers.add(power);
    }
}
