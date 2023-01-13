package fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.village;

import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.gamemodes.customevents.GamePlayerDefinitiveDeathEvent;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffectType;

public class Ermite extends LGRoleVillage {
    Runnable ermiteTask;

    Listener ermiteListener;

    public Ermite(GameManager gameManager, CrystaliaPlayer crystaliaPlayer) {
        super(gameManager, crystaliaPlayer);
        this.ermiteTask = (() -> {
            if (this.crystaliaPlayer.isOnline()) {
                Player ermitePlayer = this.crystaliaPlayer.getPlayer();
                int playersAround = 0;
                for (CrystaliaPlayer alivePlayer : this.main.getPlayerManager().getOnlineAlivePlayers()) {
                    Player player = alivePlayer.getPlayer();
                    if (player.getWorld().equals(ermitePlayer.getWorld())) {
                        double distance = ermitePlayer.getLocation().distance(player.getLocation());
                        if (distance <= 20.0D)
                            playersAround++;
                    }
                }
                if (playersAround >= 5) {
                    addTempEffect(PotionEffectType.WEAKNESS, 0, 60, false);
                } else {
                    if (this.gameManager.getGameSettings().isDay()) {
                        addTempEffect(PotionEffectType.INCREASE_DAMAGE, 0, 60, false);
                    } else {
                        addTempEffect(PotionEffectType.DAMAGE_RESISTANCE, 0, 60, false);
                    }
                    if (playersAround == 1) {
                        this.crystaliaPlayer.getPlayer().setWalkSpeed(0.22F);
                    } else {
                        this.crystaliaPlayer.getPlayer().setWalkSpeed(0.2F);
                    }
                }
            }
        });
        this.ermiteListener = new Listener() {
            @EventHandler(priority = EventPriority.HIGHEST)
            public void onQuit(PlayerQuitEvent event) {
                if (!Ermite.this.crystaliaPlayer.isAlive() && Ermite.this.main.getPlayerManager().getExactPlayer(event.getPlayer()).equals(Ermite.this.crystaliaPlayer))
                    event.setQuitMessage(null);
            }

            @EventHandler(priority = EventPriority.HIGHEST)
            public void onJoin(PlayerJoinEvent event) {
                if (!Ermite.this.crystaliaPlayer.isAlive() && Ermite.this.main.getPlayerManager().getExactPlayer(event.getPlayer()).equals(Ermite.this.crystaliaPlayer))
                    event.setJoinMessage(null);
            }

            @EventHandler(priority = EventPriority.LOWEST)
            public void onDefinitiveDeath(GamePlayerDefinitiveDeathEvent event) {
                if (event.getCrystaliaPlayer().equals(Ermite.this.crystaliaPlayer))
                    event.setDeathShown(false);
            }
        };
        setName("Ermite");
        this.main.getServer().getPluginManager().registerEvents(this.ermiteListener, (Plugin)this.main);
        Bukkit.getScheduler().runTaskTimer(this.main, this.ermiteTask, 40L, 20L);
    }

    public String getPowersDescription() {
        return "Vos pouvoirs évoluent selon le nombre de joueurs présents à moins de 20 blocs de vous (y compris vous-même).\nA 5 joueurs ou plus, vous avez Weakness I. A 4 joueurs ou moins, vous avez Strength I s'il fait jour, Resistance I s'il fait nuit. Si vous êtes seul, vous avez 10% de Speed.\nVous ne pouvez pas voter et ne produisez aucun message lors de votre mort.";
    }
}
