package fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.village;

import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.gamemodes.customevents.GamePlayerDefinitiveDeathEvent;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.lgevents.LGEspionnageEvent;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.LGRole;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import fr.luclyoko.crystaliauhc.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Pretresse extends LGRoleVillage {
    private List<CrystaliaPlayer> spiedLoups;

    Listener pretresseListener;

    public Pretresse(GameManager gameManager, CrystaliaPlayer crystaliaPlayer) {
        super(gameManager, crystaliaPlayer);
        this.pretresseListener = new Listener() {
            @EventHandler
            public void onEspionnage(LGEspionnageEvent event) {
                if (!event.getPretresse().equals(Pretresse.this.crystaliaPlayer))
                    return;
                Pretresse.this.setMaxHealth(Pretresse.this.getMaxHealth() - 4);
                LGRole lgRole = (LGRole)event.getEspionne().getRole();
                if (lgRole.isLoup()) {
                    Pretresse.this.crystaliaPlayer.sendMessage(Pretresse.this.gameManager.getGamemodeUhc().getDisplayNameChat() + "§aVous avez espionné le joueur " + event.getEspionne().getPlayerName() + " qui est " + lgRole.getName() + ".");
                    Pretresse.this.spiedLoups.add(event.getEspionne());
                } else {
                    Pretresse.this.crystaliaPlayer.sendMessage(Pretresse.this.gameManager.getGamemodeUhc().getDisplayNameChat() + "§aVous avez espionné le joueur " + event.getEspionne().getPlayerName() + " qui n'est pas Loup-Garou.");
                }
                lgRole.addEvent("Espionné par " + Pretresse.this.crystaliaPlayer.getPlayerName() + " (Prêtresse)");
                Pretresse.this.addEvent("Espionnage de " + event.getEspionne().getPlayerName());
            }

            @EventHandler
            public void onDefinitiveDeath(GamePlayerDefinitiveDeathEvent event) {
                if (event.isCancelled())
                    return;
                int villageChance = 80;
                int loupsChance = 20;
                int othersChance = 95;
                if (!Pretresse.this.crystaliaPlayer.isAlive() || event.getCrystaliaPlayer().equals(Pretresse.this.crystaliaPlayer)) {
                    villageChance = 20;
                    loupsChance = 80;
                }
                for (CrystaliaPlayer gamePlayer : event.getShownRole().keySet()) {
                    boolean canSeeRole = true;
                    if (gamePlayer.getRole() == null ||
                            !gamePlayer.isAlive())
                        continue;
                    LGRole gpRole = (LGRole)gamePlayer.getRole();
                    switch (gpRole.getLgSide()) {
                        case VILLAGE:
                            canSeeRole = ((new Random(System.currentTimeMillis())).nextInt(100) <= villageChance);
                            break;
                        case LOUPS:
                            canSeeRole = ((new Random(System.currentTimeMillis())).nextInt(100) <= loupsChance);
                            break;
                        default:
                            canSeeRole = ((new Random(System.currentTimeMillis())).nextInt(100) <= othersChance);
                            break;
                    }
                    if (gamePlayer.getRole() instanceof fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.hybrides.Cupidon)
                        canSeeRole = ((new Random(System.currentTimeMillis())).nextInt(100) <= villageChance);
                    if (gamePlayer.getRole() instanceof Pretresse || gamePlayer.getRole() instanceof Chaman)
                        canSeeRole = true;
                    if (!canSeeRole)
                        event.setShownRole(gamePlayer, "§k$$$$$$$$$$");
                }
                Bukkit.getScheduler().runTaskLater((Plugin)Pretresse.this.main, () -> {
                    if (Pretresse.this.spiedLoups.contains(event.getCrystaliaPlayer())) {
                        Pretresse.this.setMaxHealth(Pretresse.this.getMaxHealth() + 2);
                        Pretresse.this.crystaliaPlayer.sendMessage(Pretresse.this.gameManager.getGamemodeUhc().getDisplayNameChat() + "§aUn Loup-Garou que vous aviez espionné est mort. Vous regagnez donc un coeur permanent.");
                    }
                }, 1L);
            }
        };
        setName("Prêtresse");
        this.spiedLoups = new ArrayList<>();
        crystaliaPlayer.giveItem((new ItemBuilder(Material.OBSIDIAN)).setAmount(4).toItemStack());
        this.main.getServer().getPluginManager().registerEvents(this.pretresseListener, (Plugin)this.main);
    }
}
