package com.arena.engine;

import com.arena.model.combatant.Player;

public class BattleResult {
    private final boolean playerWon;
    private final int totalRounds;
    private final Player player;
    private final int enemiesRemaining;

    public BattleResult(boolean playerWon, int totalRounds, Player player, int enemiesRemaining) {
        this.playerWon = playerWon;
        this.totalRounds = totalRounds;
        this.player = player;
        this.enemiesRemaining = enemiesRemaining;
    }

    public boolean isPlayerWon() { return playerWon; }
    public int getTotalRounds() { return totalRounds; }
    public Player getPlayer() { return player; }
    public int getEnemiesRemaining() { return enemiesRemaining; }
}
