package com.arena.model.level;

import com.arena.model.combatant.Enemy;
import java.util.List;

public interface Level {
    String getName();
    int getDifficulty();
    List<Enemy> createInitialWave();
    List<Enemy> createBackupWave();
    boolean hasBackupWave();
}
