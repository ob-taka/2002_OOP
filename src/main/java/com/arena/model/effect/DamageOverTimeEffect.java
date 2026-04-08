package com.arena.model.effect;

public class DamageOverTimeEffect implements StatusEffect {
    private final String name;
    private final int damagePerTick;
    private int remainingTurns;

    public DamageOverTimeEffect(int damagePerTick, int duration) {
        this("Poison", damagePerTick, duration);
    }

    public DamageOverTimeEffect(String name, int damagePerTick, int duration) {
        this.name = name;
        this.damagePerTick = damagePerTick;
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
    public int getDamagePerTick() { return damagePerTick; }
}
