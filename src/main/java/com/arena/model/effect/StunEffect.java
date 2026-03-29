package com.arena.model.effect;

public class StunEffect implements StatusEffect {
    private int remainingTurns;

    public StunEffect(int duration) {
        this.remainingTurns = duration;
    }

    @Override
    public String getName() { return "Stun"; }

    @Override
    public int getRemainingTurns() { return remainingTurns; }

    @Override
    public void decrementTurn() { remainingTurns--; }

    @Override
    public boolean isExpired() { return remainingTurns <= 0; }

    @Override
    public boolean preventsAction() { return true; }
}
