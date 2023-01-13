package fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles;

import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.gamemodes.CrystaliaRole;
import fr.luclyoko.crystaliauhc.gamemodes.customevents.GamePlayerDefinitiveDeathEvent;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.LGUHC;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.lgevents.LGNewLoupEvent;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.lgevents.LGRevealListeLoupsEvent;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.lgevents.LGSideChangeEvent;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public abstract class LGRole extends CrystaliaRole {
    protected LGUHC lguhc;

    protected String selfDisplayModifiers;

    protected LGSides lgSide;

    protected boolean isLoup;

    protected List<String> listeLoups;

    protected LGRole coupleRole;

    protected List<String> events;

    Listener listener;

    public LGRole(GameManager gameManager, CrystaliaPlayer crystaliaPlayer) {
        super(gameManager, crystaliaPlayer);
        this.listener = new Listener() {
            @EventHandler(priority = EventPriority.HIGHEST)
            public void onPlayerDefinitiveDeath(GamePlayerDefinitiveDeathEvent event) {
                if (!LGRole.this.crystaliaPlayer.isOnline() || !LGRole.this.crystaliaPlayer.isAlive())
                    return;
                if (event.isCancelled())
                    return;
                LGRole.this.listeLoups.remove(event.getCrystaliaPlayer().getPlayerName());
                if (LGRole.this.isLoup &&
                        event.getKiller() != null && event.getKiller().equals(LGRole.this.crystaliaPlayer)) {
                    LGRole.this.addTempEffect(PotionEffectType.SPEED, 0, 1200, false);
                    LGRole.this.addTempEffect(PotionEffectType.ABSORPTION, 0, 1200, false);
                }
            }

            @EventHandler
            public void onNewLoupEvent(LGNewLoupEvent event) {
                if (!LGRole.this.isLoup)
                    return;
                if (event.getCrystaliaPlayer().equals(LGRole.this.crystaliaPlayer)) {
                    for (CrystaliaPlayer gamePlayer : LGRole.this.main.getPlayerManager().getAliveGamePlayers()) {
                        if (((LGRole)gamePlayer.getRole()).isLoup && !LGRole.this.listeLoups.contains(gamePlayer.getPlayerName()))
                            LGRole.this.listeLoups.add(gamePlayer.getPlayerName());
                    }
                } else {
                    LGRole.this.listeLoups.add(event.getCrystaliaPlayer().getPlayerName());
                }
            }

            @EventHandler
            public void onRevealListeLoups(LGRevealListeLoupsEvent event) {
                if (!LGRole.this.isLoup)
                    return;
                for (CrystaliaPlayer gamePlayer : LGRole.this.main.getPlayerManager().getAliveGamePlayers()) {
                    if (((LGRole)gamePlayer.getRole()).isLoup) {
                        gamePlayer.sendMessage(LGRole.this.gameManager.getGamemodeUhc().getDisplayNameChat() + "§6Vous pouvez désormais accéder à la liste des Loups-Garous de la partie. /lg role pour en savoir plus.");
                        if (!LGRole.this.listeLoups.contains(gamePlayer.getPlayerName()))
                            LGRole.this.listeLoups.add(gamePlayer.getPlayerName());
                    }
                }
            }

            @EventHandler
            public void onSideChange(LGSideChangeEvent event) {
                if (event.getCrystaliaPlayer().equals(LGRole.this.crystaliaPlayer))
                    if (LGRole.this.coupleRole != null) {
                        ((LGRole)LGRole.this.crystaliaPlayer.getRole()).setLgSide(LGSides.COUPLE);
                    } else {
                        ((LGRole)LGRole.this.crystaliaPlayer.getRole()).setLgSide(event.getNewSide());
                    }
                LGRole.this.gameManager.getGamemodeUhc().checkWin();
            }
        };
        this.lguhc = (LGUHC)gameManager.getGamemodeUhc();
        this.selfDisplayModifiers = "";
        this.isLoup = false;
        this.listeLoups = new ArrayList<>();
        this.coupleRole = null;
        this.events = new ArrayList<>();
        this.main.getServer().getPluginManager().registerEvents(this.listener, (Plugin)this.main);
    }

    public String getShortDescription() {
        return "§9Vous êtes §l" + getName() + getSelfDisplayModifiers() + "§9.\n" + getLgSide().getObjectiveDisplay() + "\n" + getPowersDescription();
    }

    public String getFullName() {
        return getName() + getSelfDisplayModifiers();
    }

    public String getFullDescription() {
        return getShortDescription() + "\n" + getListeLoups();
    }

    public List<String> getEvents() {
        return this.events;
    }

    public void addEvent(String event) {
        this.events.add(event);
    }

    public String getListeLoups() {
        StringBuilder liste = new StringBuilder();
        if (this.isLoup && this.lguhc.isListeLoupsRevealed()) {
            liste.append("§4Liste des Loups: §c");
            this.listeLoups.forEach(s -> liste.append(s).append("§4, §c"));
                    liste.replace(liste.lastIndexOf(","), liste.length(), ".");
        }
        return liste.toString();
    }

    public String getPowersDescription() {
        return "Cette description n'existe malheureusement pas encore.";
    }

    public boolean isLoup() {
        return this.isLoup;
    }

    public void setLoup(boolean loup) {
        this.isLoup = loup;
        if (loup) {
            addNightEffect(PotionEffectType.INCREASE_DAMAGE, 0);
            addPermEffect(PotionEffectType.NIGHT_VISION, 0);
            Bukkit.getScheduler().runTaskLaterAsynchronously(this.main, () -> this.main.getServer().getPluginManager().callEvent((Event)new LGNewLoupEvent(this.crystaliaPlayer)), 20L);
        }
    }

    public LGSides getLgSide() {
        return this.lgSide;
    }

    public void setLgSide(LGSides lgSide) {
        this.lgSide = lgSide;
    }

    public String getSelfDisplayModifiers() {
        return this.selfDisplayModifiers;
    }

    public void setSelfDisplayModifiers(String selfDisplayModifiers) {
        this.selfDisplayModifiers = selfDisplayModifiers;
    }
}
