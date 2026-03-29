package com.arena.engine;

import com.arena.model.combatant.Enemy;
import com.arena.model.combatant.Player;
import com.arena.model.level.Level;
import java.util.ArrayList;
import java.util.List;

public class BattleContext {
    private final Player player;
    private final Level level;
    private final List<Enemy> activeEnemies;
    private int currentRound;
    private boolean backupSpawned;

    public BattleContext(Player player, Level level) {
        this.player = player;
        this.level = level;
        this.activeEnemies = new ArrayList<>(level.createInitialWave());
        this.currentRound = 0;
        this.backupSpawned = false;
    }

    public Player getPlayer() { return player; }
    public Level getLevel() { return level; }
    public List<Enemy> getActiveEnemies() { return activeEnemies; }
    public int getCurrentRound() { return currentRound; }
    public boolean isBackupSpawned() { return backupSpawned; }

    public void incrementRound() { currentRound++; }

    public List<Enemy> getAliveEnemies() {
        List<Enemy> alive = new ArrayList<>();
        for (Enemy e : activeEnemies) {
            if (e.isAlive()) alive.add(e);
        }
        return alive;
    }

    public boolean allEnemiesDead() {
        return activeEnemies.stream().noneMatch(Enemy::isAlive);
    }

    public boolean shouldSpawnBackup() {
        return !backupSpawned && level.hasBackupWave() && allEnemiesDead();
    }

    public List<Enemy> spawnBackup() {
        List<Enemy> backup = level.createBackupWave();
        activeEnemies.addAll(backup);
        backupSpawned = true;
        return backup;
    }
}
