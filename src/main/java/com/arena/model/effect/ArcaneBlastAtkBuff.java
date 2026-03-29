package com.arena.model.effect;

public class ArcaneBlastAtkBuff implements StatusEffect {
    private int killCount;

    public ArcaneBlastAtkBuff() {
        this.killCount = 0;
    }

    public void incrementKills() { killCount++; }

    public int getKillCount() { return killCount; }

    @Override
    public String getName() { return "Arcane Blast ATK Buff"; }

    @Override
    public int getRemainingTurns() { return -1; }

    @Override
    public void decrementTurn() { /* permanent until end of level */ }

    @Override
    public boolean isExpired() { return false; }

    @Override
    public int getAtkModifier() { return 10 * killCount; }
}
