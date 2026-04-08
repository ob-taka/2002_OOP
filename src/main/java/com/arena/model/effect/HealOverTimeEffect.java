package com.arena.model.effect;

public class HealOverTimeEffect implements StatusEffect {
    private final String name;
    private final int healPerTick;
    private int remainingTurns;

    public HealOverTimeEffect(int healPerTick, int duration) {
        this("Regeneration", healPerTick, duration);
    }

    public HealOverTimeEffect(String name, int healPerTick, int duration) {
        this.name = name;
        this.healPerTick = healPerTick;
        this.remainingTurns = duration;
    }

    @Override
    public String getName() { return name; }

    @Override
    public int getRemainingTurns() { return remainingTurns; }

    @Override
    public void decrementTurn() { remainingTurns--; }

    @Override
    public boolean isExpired() { return remainingTurns <= 0; }

    @Override
    public int getHealPerTick() { return healPerTick; }
}
