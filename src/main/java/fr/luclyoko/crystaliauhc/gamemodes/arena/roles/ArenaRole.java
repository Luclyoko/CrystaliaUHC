package fr.luclyoko.crystaliauhc.gamemodes.arena.roles;

import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.gamemodes.CrystaliaRole;
import fr.luclyoko.crystaliauhc.gamemodes.arena.ArenaUHC;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import fr.luclyoko.crystaliauhc.utils.Effects;
import fr.luclyoko.crystaliauhc.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class ArenaRole extends CrystaliaRole {

    protected ArenaRolesEnum arenaRolesEnum;

    protected final ArenaUHC arenaUHC;

    protected int kills;

    protected boolean silent;


    public ArenaRole(GameManager gameManager, CrystaliaPlayer crystaliaPlayer) {
        super(gameManager, crystaliaPlayer);
        this.arenaUHC = (ArenaUHC)gameManager.getGamemodeUhc();
        this.kills = 0;
        this.silent = false;
    }

    @Override
    public String getName() {
        if (arenaRolesEnum != null) {
            return arenaRolesEnum.getName();
        }
        return "Nom introuvable";
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
            stringBuilder.append("\n    §7• §c");
            stringBuilder.append(maxHealth > 20 ? "+ " + (maxHealth - 20) / 2 : "- " + (20 - maxHealth) / 2);
            stringBuilder.append("❤");
            hasEffects = true;
        }
        if (!permanentEffects.isEmpty()) {
            stringBuilder.append("\n  §f➢ §dPermanents:");
            permanentEffects.keySet().forEach(potionEffectType -> stringBuilder.append("\n    §7• §b")
                    .append(Effects.getDisplayName(potionEffectType))
                    .append(" ")
                    .append(permanentEffects.get(potionEffectType) + 1));
            hasEffects = true;
        }
        if (!dayEffects.isEmpty()) {
            stringBuilder.append("\n  §f➢ §eJour:");
            dayEffects.keySet().forEach(potionEffectType -> stringBuilder.append("\n    §7• §b")
                    .append(Effects.getDisplayName(potionEffectType))
                    .append(" ")
                    .append(dayEffects.get(potionEffectType) + 1));
            hasEffects = true;
        }
        if (!nightEffects.isEmpty()) {
            stringBuilder.append("\n  §f➢ §8Nuit:");
            nightEffects.keySet().forEach(potionEffectType -> stringBuilder.append("\n    §7• §b")
                    .append(Effects.getDisplayName(potionEffectType))
                    .append(" ")
                    .append(nightEffects.get(potionEffectType) + 1));
            hasEffects = true;
        }
        if (!hasEffects) stringBuilder.append("\n    §7• §fAucun");
        return stringBuilder.toString();
    }

    public String getPowersDescription() {
        StringBuilder sb = new StringBuilder();
        List<String> powersList = new ArrayList<>(getPowersDescriptionList());
        powersList.forEach(s -> sb.append("\n    §7• §f").append(s));
        return sb.toString();
    }

    public List<String> getPowersDescriptionList() {
        return Collections.singletonList("Aucun");
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

    public List<ItemStack> getRoleItems() {
        return new ArrayList<>();
    }

}
