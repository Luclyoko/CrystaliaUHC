package fr.luclyoko.crystaliauhc.guis;

import fr.luclyoko.crystaliauhc.Main;
import fr.luclyoko.crystaliauhc.guis.adminguis.AdminMainGui;
import fr.luclyoko.crystaliauhc.guis.adminguis.PlayerManagerGui;
import fr.luclyoko.crystaliauhc.guis.devguis.DevMainGui;
import fr.luclyoko.crystaliauhc.guis.hostguis.DayNightCycleConfigGui;
import fr.luclyoko.crystaliauhc.guis.hostguis.EffectsConfigGui;
import fr.luclyoko.crystaliauhc.guis.hostguis.GamemodeSelectorGui;
import fr.luclyoko.crystaliauhc.guis.hostguis.HostMainGui;
import fr.luclyoko.crystaliauhc.guis.hostguis.borderguis.BorderMainGui;
import fr.luclyoko.crystaliauhc.guis.hostguis.enchantsguis.EnchantEditGui;
import fr.luclyoko.crystaliauhc.guis.hostguis.enchantsguis.EnchantLimitGui;
import fr.luclyoko.crystaliauhc.guis.hostguis.gamemodesguis.lgguis.*;
import fr.luclyoko.crystaliauhc.guis.hostguis.scenariosguis.DiamondLimitGui;
import fr.luclyoko.crystaliauhc.guis.hostguis.scenariosguis.ScenarioManagerGui;
import fr.luclyoko.crystaliauhc.guis.teamsguis.TeamSelectorGui;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class GuiManager implements Listener {
    private final Main main;

    private Map<Class<? extends GuiBuilder>, GuiBuilder> registeredMenus;

    public GuiManager(Main main) {
        this.main = main;
        main.getLogger().info("§aChargement des menus.");
        main.getServer().getPluginManager().registerEvents(this, (Plugin)main);
        loadGuis();
    }

    private void loadGuis() {
        this.registeredMenus = new HashMap<>();
        addMenu((GuiBuilder)new AdminMainGui());
        addMenu((GuiBuilder)new DevMainGui());
        addMenu((GuiBuilder)new HostMainGui());
        addMenu((GuiBuilder)new PlayerManagerGui());
        addMenu((GuiBuilder)new ScenarioManagerGui());
        addMenu((GuiBuilder)new DiamondLimitGui());
        addMenu((GuiBuilder)new TeamSelectorGui());
        addMenu((GuiBuilder)new GamemodeSelectorGui());
        addMenu((GuiBuilder)new BorderMainGui());
        addMenu((GuiBuilder)new EnchantLimitGui());
        addMenu((GuiBuilder)new EnchantEditGui());
        addMenu((GuiBuilder)new LGMainGui());
        addMenu((GuiBuilder)new LGCompoMainGui());
        addMenu((GuiBuilder)new LGCompoVillageGui());
        addMenu((GuiBuilder)new LGCompoLoupsGui());
        addMenu((GuiBuilder)new LGCompoSolosGui());
        addMenu((GuiBuilder)new LGCompoHybridesGui());
        addMenu((GuiBuilder)new DayNightCycleConfigGui());
        addMenu((GuiBuilder)new EffectsConfigGui());
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player)event.getWhoClicked();
        Inventory inv = event.getInventory();
        ItemStack current = event.getCurrentItem();
        ClickType clickType = event.getClick();
        if (event.getCurrentItem() == null)
            return;
        getRegisteredMenus().values().stream()
                .filter(menu -> inv.getName().equalsIgnoreCase(menu.name()))
                .forEach(menu -> {
                    menu.onClick(player, inv, current, event.getSlot(), clickType);
                    event.setCancelled(true);
                });
    }

    public void addMenu(GuiBuilder m) {
        getRegisteredMenus().put(m.getClass(), m);
    }

    public void open(final Player player, Class<? extends GuiBuilder> gClass) {
        if (!getRegisteredMenus().containsKey(gClass))
            return;
        GuiBuilder menu = getRegisteredMenus().get(gClass);
        final Inventory inv = Bukkit.createInventory(null, menu.getSize(), menu.name());
        menu.contents(player, inv);
        (new BukkitRunnable() {
            public void run() {
                player.openInventory(inv);
            }
        }).runTaskLater((Plugin)Main.getInstance(), 1L);
    }

    public Map<Class<? extends GuiBuilder>, GuiBuilder> getRegisteredMenus() {
        return this.registeredMenus;
    }

    public void setRegisteredMenus(Map<Class<? extends GuiBuilder>, GuiBuilder> registeredMenus) {
        this.registeredMenus = registeredMenus;
    }
}
