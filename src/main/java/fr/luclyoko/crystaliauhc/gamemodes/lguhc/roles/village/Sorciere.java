package fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.village;

import fr.luclyoko.crystaliauhc.Main;
import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.gamemodes.customevents.GamePlayerDeathEvent;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.lgevents.LGSorciereRezEvent;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.LGRole;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import fr.luclyoko.crystaliauhc.utils.ItemBuilder;
import java.util.ArrayList;
import java.util.List;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionType;

public class Sorciere extends LGRoleVillage {
    private boolean hasUsedRez;

    private List<CrystaliaPlayer> canRezPlayer;

    Listener sorciere;

    public Sorciere(GameManager gameManager, CrystaliaPlayer crystaliaPlayer) {
        super(gameManager, crystaliaPlayer);
        this.sorciere = new Listener() {
            @EventHandler
            public void onPlayerDeath(GamePlayerDeathEvent event) {
                Bukkit.getScheduler().runTaskLater((Plugin)Sorciere.this.main, () -> {
                    if (!Sorciere.this.hasUsedRez && !event.getCrystaliaPlayer().isAlive() && !event.getCrystaliaPlayer().equals(Sorciere.this.crystaliaPlayer)) {
                        Sorciere.this.crystaliaPlayer.sendMessage("§aLe joueur " + event.getCrystaliaPlayer().getPlayerName() + " est mort. Vous avez 5 secondes pour le ressusciter.");
                        TextComponent msg = new TextComponent("§2§l[Ressuciter]");
                                msg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/lg rez " + event.getCrystaliaPlayer().getPlayerName()));
                        Sorciere.this.crystaliaPlayer.getPlayer().spigot().sendMessage((BaseComponent)msg);
                        Sorciere.this.canRezPlayer.add(event.getCrystaliaPlayer());
                        Bukkit.getScheduler().runTaskLater((Plugin)Sorciere.this.main, () -> Sorciere.this.canRezPlayer.remove(event.getCrystaliaPlayer()), 100L);
                    }
                }, 100L);
            }

            @EventHandler
            public void onSorciereRez(LGSorciereRezEvent event) {
                if (event.getSorciere().equals(Sorciere.this.crystaliaPlayer)) {
                    CrystaliaPlayer rez = event.getRez();
                    LGRole rezRole = (LGRole)rez.getRole();
                    rezRole.respawn(false);
                    Sorciere.this.canRezPlayer.remove(rez);
                    Sorciere.this.setHasUsedRez(true);
                    Sorciere.this.crystaliaPlayer.sendMessage(Sorciere.this.gameManager.getGamemodeUhc().getDisplayNameChat() + "§aVous venez de ramener " + rez.getPlayerName() + " à la vie.");
                    rez.sendMessage(Sorciere.this.gameManager.getGamemodeUhc().getDisplayNameChat() + "§aLa Sorcière a décidé de vous ressusciter.");
                    rezRole.addEvent("Ressuscité par " + Sorciere.this.crystaliaPlayer.getPlayerName() + " (Sorcière)");
                    Sorciere.this.addEvent("Résurrection de " + rez.getPlayerName());
                }
            }
        };
        setName("Sorcière");
        this.hasUsedRez = false;
        this.canRezPlayer = new ArrayList<>();
        crystaliaPlayer.giveItem((new ItemBuilder(Material.POTION)).setPotionMeta(PotionType.INSTANT_HEAL, true).toItemStack());
        crystaliaPlayer.giveItem((new ItemBuilder(Material.POTION)).setPotionMeta(PotionType.REGEN, true).toItemStack());
        crystaliaPlayer.giveItem((new ItemBuilder(Material.POTION)).setPotionMeta(PotionType.INSTANT_DAMAGE, true).setAmount(2).toItemStack());
        crystaliaPlayer.giveItem((new ItemBuilder(Material.POTION)).setPotionMeta(PotionType.STRENGTH, true).toItemStack());
        this.main.getServer().getPluginManager().registerEvents(this.sorciere, (Plugin)this.main);
    }

    public String getPowersDescription() {
        return "Vous disposez de 5 potions splash (1 Instant Health, 1 Regeneration, 2 Instant Damage, 1 Strength).\nUne fois dans la partie, vous pouvez ressusciter un joueur mort. Vous ne pouvez pas utiliser ce pouvoir sur vous-même.";
    }

    public boolean hasUsedRez() {
        return this.hasUsedRez;
    }

    public void setHasUsedRez(boolean hasUsedRez) {
        this.hasUsedRez = hasUsedRez;
    }

    public List<CrystaliaPlayer> getCanRezPlayer() {
        return this.canRezPlayer;
    }
}
