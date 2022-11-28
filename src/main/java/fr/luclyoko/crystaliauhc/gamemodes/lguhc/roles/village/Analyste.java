package fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.village;

import fr.luclyoko.crystaliauhc.game.GameManager;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.lgevents.LGAnalyseEvent;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.lgevents.LGObserveEvent;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.LGRole;
import fr.luclyoko.crystaliauhc.gamemodes.lguhc.roles.LGSides;
import fr.luclyoko.crystaliauhc.players.CrystaliaPlayer;
import fr.luclyoko.crystaliauhc.utils.Effects;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffectType;

public class Analyste extends LGRoleVillage {
    private int leftUses;

    private Map<CrystaliaPlayer, List<PotionEffectType>> observedPlayers;

    private boolean hasUsedAnalyse;

    private List<PotionEffectType> observedEffects;

    Listener analyste;

    public Analyste(GameManager gameManager, CrystaliaPlayer crystaliaPlayer) {
        super(gameManager, crystaliaPlayer);
        this.analyste = new Listener() {
            @EventHandler
            public void onAnalyse(LGAnalyseEvent event) {
                if (event.getAnalyste().equals(Analyste.this.crystaliaPlayer)) {
                    CrystaliaPlayer analysed = event.getAnalysed();
                    List<PotionEffectType> observed = (List<PotionEffectType>)Analyste.this.observedPlayers.get(analysed);
                    StringBuilder sb = new StringBuilder(Analyste.this.gameManager.getGamemodeUhc().getDisplayNameChat() + "§eListe des effets analysés:");
                    for (PotionEffectType activeEffect : observed)
                        sb.append("\n§6").append(Effects.getDisplayName(activeEffect));
                    Analyste.this.crystaliaPlayer.sendMessage(sb.toString());
                    if (!((LGRole)analysed.getRole()).getLgSide().equals(LGSides.VILLAGE))
                        analysed.sendMessage(Analyste.this.gameManager.getGamemodeUhc().getDisplayNameChat() + "§eVous avez été analysé par l'Analyste qui est " + Analyste.this.crystaliaPlayer.getPlayerName() + ".");
                    Analyste.this.hasUsedAnalyse = true;
                    ((LGRole)analysed.getRole()).addEvent("Analysé par " + Analyste.this.crystaliaPlayer.getPlayerName() + " (Analyste)");
                    Analyste.this.addEvent("Analyse de " + analysed.getPlayerName());
                }
            }

            @EventHandler
            public void onObserve(LGObserveEvent event) {
                if (event.getAnalyste().equals(Analyste.this.crystaliaPlayer)) {
                    CrystaliaPlayer observed = event.getObserved();
                    boolean observationResult = false;
                    List<PotionEffectType> observedEff = new ArrayList<>();
                    for (PotionEffectType activeEffect : observed.getRole().getActiveEffects()) {
                        if (Analyste.this.observedEffects.contains(activeEffect)) {
                            observationResult = true;
                            observedEff.add(activeEffect);
                        }
                    }
                    Analyste.this.observedPlayers.put(observed, observedEff);
                    Analyste.this.leftUses--;
                    Analyste.this.crystaliaPlayer.sendMessage(Analyste.this.gameManager.getGamemodeUhc().getDisplayNameChat() + "§eL'analyse du joueur " + observed.getPlayerName() + " a donné un résultat " + (observationResult ? "§a§lpositif" : "§c§lnégatif") + "§e(" + Analyste.this.leftUses + " utilisations restantes).");
                    ((LGRole)observed.getRole()).addEvent("Observé par " + Analyste.this.crystaliaPlayer.getPlayerName() + " (Analyste)");
                    Analyste.this.addEvent("Observation de " + observed.getPlayerName());
                }
            }
        };
        setName("Analyste");
        this.leftUses = 5;
        this.observedPlayers = new HashMap<>();
        this.hasUsedAnalyse = false;
        this.observedEffects = Arrays.asList(new PotionEffectType[] { PotionEffectType.INCREASE_DAMAGE, PotionEffectType.DAMAGE_RESISTANCE, PotionEffectType.WEAKNESS, PotionEffectType.SPEED, PotionEffectType.INVISIBILITY, PotionEffectType.ABSORPTION });
        crystaliaPlayer.sendMessage(gameManager.getGamemodeUhc().getDisplayNameChat() + getShortDescription());
        this.main.getServer().getPluginManager().registerEvents(this.analyste, (Plugin)this.main);
    }

    public String getPowersDescription() {
        return "Vous pouvez, 5 fois dans la partie, observer un joueur avec la commande /lg observer <pseudo>.\nCette observation vous permettra de savoir si ce joueur possède un ou plusieurs effets parmi les suivants: Strength, Resistance, Weakness, Speed, Invisibility et Absorption.\nVous pouvez une fois dans la partie, analyser un joueur précédemment observé avec la commande /lg analyser <pseudo>.\nCette analyse vous permettra de connaître les effets précédemment observés.";
    }

    public int getLeftUses() {
        return this.leftUses;
    }

    public void setLeftUses(int leftUses) {
        this.leftUses = leftUses;
    }

    public boolean hasUsedAnalyse() {
        return this.hasUsedAnalyse;
    }

    public void setHasUsedAnalyse(boolean hasUsedAnalyse) {
        this.hasUsedAnalyse = hasUsedAnalyse;
    }

    public Map<CrystaliaPlayer, List<PotionEffectType>> getObservedPlayers() {
        return this.observedPlayers;
    }

    public void addObservedPlayer(CrystaliaPlayer crystaliaPlayer, List<PotionEffectType> potionEffectTypes) {
        this.observedPlayers.put(crystaliaPlayer, potionEffectTypes);
    }

    public void removeObservedPlayer(CrystaliaPlayer crystaliaPlayer) {
        this.observedPlayers.remove(crystaliaPlayer);
    }
}
