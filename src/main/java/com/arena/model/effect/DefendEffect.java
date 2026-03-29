package com.arena.model.effect;

public class DefendEffect implements StatusEffect {
    private int remainingTurns;

    public DefendEffect(int duration) {
        this.remainingTurns = duration;
    }

    @Override
    public String getName() { return "Defend"; }

    @Override
    public int getRemainingTurns() { return remainingTurns; }

    @Override
    public void decrementTurn() { remainingTurns--; }

    @Override
    public boolean isExpired() { return remainingTurns <= 0; }

    @Override
    public int getDefModifier() { return 10; }
}
