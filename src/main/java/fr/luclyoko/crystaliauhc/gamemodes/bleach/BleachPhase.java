package fr.luclyoko.crystaliauhc.gamemodes.bleach;

public enum BleachPhase {
    BEGINNING(1),
    HUECO_MUNDO(2),
    FINAL_ASSAULT(3);

    private final int phaseNumber;

    BleachPhase(int phaseNumber) {
        this.phaseNumber = phaseNumber;
    }
}
