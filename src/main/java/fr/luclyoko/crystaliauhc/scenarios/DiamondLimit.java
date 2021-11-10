package fr.luclyoko.crystaliauhc.scenarios;

import fr.luclyoko.crystaliauhc.guis.GuiBuilder;
import fr.luclyoko.crystaliauhc.guis.hostguis.scenariosguis.DiamondLimitGui;
import fr.luclyoko.crystaliauhc.utils.ItemBuilder;
import fr.luclyoko.crystaliauhc.utils.PlayerUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DiamondLimit implements Scenario, Listener {

    private Map<UUID, Integer> limitByPlayer;
    private int diamondLimit;
    private boolean isEnabled;

    public DiamondLimit() {
        main.getServer().getPluginManager().registerEvents(this, main);
        this.limitByPlayer = new HashMap<>();
        this.diamondLimit = 0;
        this.isEnabled = false;
    }

    @Override
    public String getName() {
        return "§3DiamondLimit";
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    @Override
    public Class<? extends GuiBuilder> getConfigGui() {
        return DiamondLimitGui.class;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.isCancelled()) return;

        if (!isEnabled) return;

        if (!main.getGameManager().isStarted()) return;

        if (diamondLimit == 0) return;

        Block block = event.getBlock();

        if (!block.getType().equals(Material.DIAMOND_ORE)) return;

        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();
        int diamondsMined = 0;

        if (limitByPlayer.containsKey(playerUUID)) {
            diamondsMined = limitByPlayer.get(playerUUID);
        }

        diamondsMined++;

        if (diamondsMined > diamondLimit) {
            event.setCancelled(true);
            block.setType(Material.AIR);
            block.getWorld().dropItem(block.getLocation(),
                    new ItemBuilder(Material.GOLD_INGOT)
                            .setAmount(1)
                            .toItemStack());
            block.getWorld().spawn(block.getLocation(), ExperienceOrb.class).setExperience(event.getExpToDrop());
            PlayerUtils.sendActionText(player, "§3Vous avez déjà dépassé la limite de diamants.");
        }

        limitByPlayer.put(playerUUID, diamondsMined);
    }

    public int getDiamondLimit() {
        return diamondLimit;
    }

    public void setDiamondLimit(int diamondLimit) {
        this.diamondLimit = diamondLimit;
    }
}
