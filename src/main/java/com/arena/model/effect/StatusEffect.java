package com.arena.model.effect;

public interface StatusEffect {
    String getName();
    int getRemainingTurns();
    void decrementTurn();
    boolean isExpired();

    default int getAtkModifier() { return 0; }
    default int getDefModifier() { return 0; }
    default int getSpdModifier() { return 0; }
    default boolean preventsAction() { return false; }
    default boolean preventsIncomingDamage() { return false; }
    default int getDamagePerTick() { return 0; }
    default int getHealPerTick() { return 0; }
}
