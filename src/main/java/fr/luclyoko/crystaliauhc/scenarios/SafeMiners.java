package fr.luclyoko.crystaliauhc.scenarios;

import fr.luclyoko.crystaliauhc.guis.GuiBuilder;
import fr.luclyoko.crystaliauhc.utils.ItemBuilder;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.Plugin;

public class SafeMiners implements Scenario, Listener {
    private boolean isEnabled;

    public SafeMiners() {
        main.getServer().getPluginManager().registerEvents(this, (Plugin)main);
        this.isEnabled = false;
    }

    public String getName() {
        return "§eSafeMiners";
    }

    public boolean isEnabled() {
        return this.isEnabled;
    }

    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    @Nullable
    public Class<? extends GuiBuilder> getConfigGui() {
        return null;
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.isCancelled())
            return;
        if (!this.isEnabled)
            return;
        if (!main.getGameManager().isStarted())
            return;
        if (!(event.getEntity() instanceof Player))
            return;
        Player player = (Player)event.getEntity();
        if (event.getDamager() instanceof Player)
            return;
        if (player.getLocation().getBlockY() <= 40)
            event.setDamage(event.getDamage() / 2.0D);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.isCancelled())
            return;
        if (!this.isEnabled)
            return;
        if (!main.getGameManager().isStarted())
            return;
        if (!(event.getEntity() instanceof Player))
            return;
        Player player = (Player)event.getEntity();
        if (player.getLocation().getBlockY() <= 40) {
            List<EntityDamageEvent.DamageCause> causesToCancel = Arrays.asList(EntityDamageEvent.DamageCause.LAVA, EntityDamageEvent.DamageCause.FIRE, EntityDamageEvent.DamageCause.FIRE_TICK, EntityDamageEvent.DamageCause.FALL);
            if (causesToCancel.contains(event.getCause()))
                event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (!this.isEnabled)
            return;
        if (!main.getGameManager().isStarted())
            return;
        if (!(event.getEntity() instanceof Zombie))
            return;
        Zombie zombie = (Zombie)event.getEntity();
        Random random = new Random();
        int feathersToDrop = random.nextInt(2);
        if (feathersToDrop > 0)
            zombie.getWorld().dropItem(zombie.getLocation().add(0.5D, 0.5D, 0.5D), (new ItemBuilder(Material.FEATHER)).setAmount(feathersToDrop).toItemStack());
    }
}
