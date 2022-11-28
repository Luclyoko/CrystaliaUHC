package fr.luclyoko.crystaliauhc.scenarios;

import fr.luclyoko.crystaliauhc.guis.GuiBuilder;
import fr.luclyoko.crystaliauhc.guis.hostguis.scenariosguis.DiamondLimitGui;
import fr.luclyoko.crystaliauhc.utils.ItemBuilder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

public class DiamondLimit implements Scenario, Listener {
    private final Map<UUID, Integer> limitByPlayer;

    private int diamondLimit;

    private boolean isEnabled;

    public DiamondLimit() {
        main.getServer().getPluginManager().registerEvents(this, (Plugin)main);
        this.limitByPlayer = new HashMap<>();
        this.diamondLimit = 0;
        this.isEnabled = false;
    }

    public String getName() {
        return "§3DiamondLimit";
    }

    public boolean isEnabled() {
        return this.isEnabled;
    }

    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public Class<? extends GuiBuilder> getConfigGui() {
        return DiamondLimitGui.class;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.isCancelled())
            return;
        if (!this.isEnabled)
            return;
        if (!main.getGameManager().isStarted())
            return;
        if (this.diamondLimit == 0)
            return;
        Block block = event.getBlock();
        if (!block.getType().equals(Material.DIAMOND_ORE))
            return;
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();
        int diamondsMined = 0;
        if (this.limitByPlayer.containsKey(playerUUID))
            diamondsMined = this.limitByPlayer.get(playerUUID);
        diamondsMined++;
        if (diamondsMined > this.diamondLimit) {
            event.setCancelled(true);
            block.breakNaturally((new ItemBuilder(Material.AIR)).toItemStack());
            block.getWorld().dropItem(block.getLocation().add(0.5D, 0.5D, 0.5D), (new ItemBuilder(Material.GOLD_INGOT)).setAmount(1).toItemStack()).setVelocity(new Vector(0.0D, 0.0D, 0.0D));
            ((ExperienceOrb)block.getWorld().spawn(block.getLocation().add(0.5D, 0.5D, 0.5D), ExperienceOrb.class)).setExperience(event.getExpToDrop());
            main.getTitle().sendActionBar(player, "§3Vous avez déjà dépassé la limite de diamants.");
        }
        this.limitByPlayer.put(playerUUID, diamondsMined);
    }

    public int getDiamondLimit() {
        return this.diamondLimit;
    }

    public void setDiamondLimit(int diamondLimit) {
        this.diamondLimit = diamondLimit;
    }
}
