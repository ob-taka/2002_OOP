package com.arena.model.effect;

public class SmokeBombEffect implements StatusEffect {
    private int remainingTurns;

    public SmokeBombEffect(int duration) {
        this.remainingTurns = duration;
    }

    @Override
    public String getName() { return "Smoke Bomb"; }

    @Override
    public int getRemainingTurns() { return remainingTurns; }

    @Override
    public void decrementTurn() { remainingTurns--; }

    @Override
    public boolean isExpired() { return remainingTurns <= 0; }

    @Override
    public boolean preventsIncomingDamage() { return true; }
}
