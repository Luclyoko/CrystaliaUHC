package fr.luclyoko.crystaliauhc.guis;

import fr.luclyoko.crystaliauhc.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class GuiManager implements Listener {
    private final Main main = Main.getInstance();

    private Map<Class<? extends GuiBuilder>, GuiBuilder> registeredMenus;

    public GuiManager() {
        main.getLogger().info("§aChargement des menus.");
        main.getServer().getPluginManager().registerEvents(this, main);
        loadGuis();
    }

    private void loadGuis() {
        this.registeredMenus = new HashMap<>();
        addMenu(new AdminGui());
        addMenu(new DevGui());
    }

    @EventHandler
    public void onClick(InventoryClickEvent event){

        Player player = (Player) event.getWhoClicked();
        Inventory inv = event.getInventory();
        ItemStack current = event.getCurrentItem();
        ClickType clickType = event.getClick();

        if(event.getCurrentItem() == null) return;

        getRegisteredMenus().values().stream()
                .filter(menu -> inv.getName().equalsIgnoreCase(menu.name()))
                .forEach(menu -> {
                    menu.onClick(player, inv, current, event.getSlot(), clickType);
                    event.setCancelled(true);
                });

    }

    public void addMenu(GuiBuilder m){
        getRegisteredMenus().put(m.getClass(), m);
    }

    public void open(Player player, Class<? extends GuiBuilder> gClass){

        if(!getRegisteredMenus().containsKey(gClass)) return;

        GuiBuilder menu = getRegisteredMenus().get(gClass);
        Inventory inv = Bukkit.createInventory(null, menu.getSize(), menu.name());
        menu.contents(player, inv);

        new BukkitRunnable() {

            @Override
            public void run() {
                player.openInventory(inv);
            }

        }.runTaskLater(Main.getInstance(), 1);

    }

    public Map<Class<? extends GuiBuilder>, GuiBuilder> getRegisteredMenus() {
        return registeredMenus;
    }

    public void setRegisteredMenus(Map<Class<? extends GuiBuilder>, GuiBuilder> registeredMenus) {
        this.registeredMenus = registeredMenus;
    }
}
