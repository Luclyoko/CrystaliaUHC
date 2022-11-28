package fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.solitaires;

import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.gamemodes.customevents.GamePlayerDefinitiveDeathEvent;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.LGSides;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import fr.luclyoko.crystaliauhc.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Assassin extends LGRoleSolitaires {
    Listener assassin;

    public Assassin(GameManager gameManager, CrystaliaPlayer crystaliaPlayer) {
        super(gameManager, crystaliaPlayer);
        this.assassin = new Listener() {
            @EventHandler(priority = EventPriority.HIGHEST)
            public void onPlayerDefinitiveDeath(GamePlayerDefinitiveDeathEvent event) {
                if (!Assassin.this.crystaliaPlayer.isOnline() || !Assassin.this.crystaliaPlayer.isAlive())
                    return;
                if (event.isCancelled())
                    return;
                if (event.getKiller() != null && event.getKiller().equals(Assassin.this.crystaliaPlayer)) {
                    Assassin.this.addTempEffect(new PotionEffect(PotionEffectType.SPEED, 1200, 0, false, false));
                    Assassin.this.addTempEffect(new PotionEffect(PotionEffectType.ABSORPTION, 1200, 0, false, false));
                }
            }
        };
        setName("Assassin");
        setLgSide(LGSides.ASSASSIN);
        addDayEffect(PotionEffectType.INCREASE_DAMAGE, 0);
        crystaliaPlayer.giveItem((new ItemBuilder(Material.ENCHANTED_BOOK)).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).toItemStack());
        crystaliaPlayer.giveItem((new ItemBuilder(Material.ENCHANTED_BOOK)).addEnchant(Enchantment.DAMAGE_ALL, 3).toItemStack());
        crystaliaPlayer.giveItem((new ItemBuilder(Material.ENCHANTED_BOOK)).addEnchant(Enchantment.ARROW_DAMAGE, 3).toItemStack());
        this.main.getServer().getPluginManager().registerEvents(this.assassin, (Plugin)this.main);
    }

    public String getPowersDescription() {
        return "Vous disposez de Strength I le jour ainsi que de 3 livres: Protection III, Sharpness III et Power III.\nLorsque vous faites un kill, vous gagnez Speed et 2 coeurs d'absorption pendant 1 minute.";
    }
}
