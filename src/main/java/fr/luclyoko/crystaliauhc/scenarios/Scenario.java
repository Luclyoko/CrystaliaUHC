package fr.luclyoko.crystaliauhc.scenarios;

import fr.luclyoko.crystaliauhc.Main;
import fr.luclyoko.crystaliauhc.guis.GuiBuilder;

import javax.annotation.Nullable;

public interface Scenario {

    Main main = Main.getInstance();

    String getName();

    boolean isEnabled();

    void setEnabled(boolean isEnabled);

    @Nullable Class<? extends GuiBuilder> getConfigGui();
}
