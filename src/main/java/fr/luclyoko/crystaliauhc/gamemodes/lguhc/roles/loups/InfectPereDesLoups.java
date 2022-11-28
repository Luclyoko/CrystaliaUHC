package fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.loups;

import fr.luclyoko.crystaliauhc.Main;
import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.gamemodes.customevents.GamePlayerKillEvent;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.lgevents.LGInfectionEvent;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.lgevents.LGSideChangeEvent;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.LGRole;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.LGSides;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import fr.luclyoko.crystaliauhc.utils.ItemBuilder;
import java.util.ArrayList;
import java.util.List;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionType;

public class InfectPereDesLoups extends LGRoleLoups {
    private boolean hasInfected;

    private List<CrystaliaPlayer> canInfectPlayer;

    Listener ipdl;

    public InfectPereDesLoups(GameManager gameManager, CrystaliaPlayer crystaliaPlayer) {
        super(gameManager, crystaliaPlayer);
        this.ipdl = new Listener() {
            @EventHandler
            public void onPlayerKill(GamePlayerKillEvent event) {
                CrystaliaPlayer killer = event.getKiller();
                if (((LGRole)killer.getRole()).isLoup() &&
                        !InfectPereDesLoups.this.hasInfected && !event.getDeadPlayer().isAlive()) {
                    InfectPereDesLoups.this.crystaliaPlayer.sendMessage("§aLe joueur " + event.getDeadPlayer().getPlayerName() + " est mort. Vous avez 5 secondes pour l'infecter.");
                    TextComponent msg = new TextComponent("§2§l[Infecter]");
                            msg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/lg infect " + event.getDeadPlayer().getPlayerName()));
                    InfectPereDesLoups.this.crystaliaPlayer.getPlayer().spigot().sendMessage((BaseComponent)msg);
                    InfectPereDesLoups.this.canInfectPlayer.add(event.getDeadPlayer());
                    Bukkit.getScheduler().runTaskLater((Plugin)InfectPereDesLoups.this.main, () -> InfectPereDesLoups.this.canInfectPlayer.remove(event.getDeadPlayer()), 100L);
                }
            }

            @EventHandler
            public void onInfection(LGInfectionEvent event) {
                if (event.getIpdl().equals(InfectPereDesLoups.this.crystaliaPlayer)) {
                    CrystaliaPlayer infected = event.getInfected();
                    LGRole infectedRole = (LGRole)infected.getRole();
                    infectedRole.respawn(false);
                    infectedRole.setLoup(true);
                    InfectPereDesLoups.this.canInfectPlayer.remove(infected);
                    infectedRole.setSelfDisplayModifiers(infectedRole.getSelfDisplayModifiers() + " §c§o(infecté)");
                    InfectPereDesLoups.this.setHasInfected(true);
                    InfectPereDesLoups.this.crystaliaPlayer.sendMessage(InfectPereDesLoups.this.gameManager.getGamemodeUhc().getDisplayNameChat() + "§aVous venez d'infecter " + infected.getPlayerName() + ".");
                    infected.sendMessage(InfectPereDesLoups.this.gameManager.getGamemodeUhc().getDisplayNameChat() + "§cL'Infect Père des Loups a décidé de vous ressusciter au sein du camp des Loups-Garous. /lg role pour plus d'informations.");
                    if (!(infectedRole instanceof fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.solitaires.LoupGarouBlanc))
                        InfectPereDesLoups.this.main.getServer().getPluginManager().callEvent((Event)new LGSideChangeEvent(infected, LGSides.LOUPS));
                    infectedRole.addEvent("Infecté par " + InfectPereDesLoups.this.crystaliaPlayer.getPlayerName());
                    InfectPereDesLoups.this.addEvent("Infection de " + infected.getPlayerName());
                }
            }
        };
        setName("Infect Père des Loups");
        this.hasInfected = false;
        this.canInfectPlayer = new ArrayList<>();
        crystaliaPlayer.giveItem((new ItemBuilder(Material.POTION)).setPotionMeta(PotionType.INSTANT_HEAL, true).setAmount(2).toItemStack());
        this.main.getServer().getPluginManager().registerEvents(this.ipdl, (Plugin)this.main);
    }

    public String getPowersDescription() {
        return "Pour ce faire, vous disposez des effets Strength I (la nuit) et Night Vision ainsi que deux potions splash de soin.\nA chaque kill, vous gagnez 1 minute de Speed et 2 coeurs d'absorption pendant 1 minute.\nVous avez le pouvoir de ressusciter une personne dans votre camp une fois dans la partie (cette personne doit être tuée par un Loup-Garou).\nChaque nuit, un chat reservé aux Loups-Garous est disponible mais vous ne pouvez y envoyer qu'un seul message. Attention car certains rôles peuvent voir ou écrire dans ce chat.";
    }

    public boolean hasInfected() {
        return this.hasInfected;
    }

    public void setHasInfected(boolean hasInfected) {
        this.hasInfected = hasInfected;
    }

    public List<CrystaliaPlayer> getCanInfectPlayer() {
        return this.canInfectPlayer;
    }
}
