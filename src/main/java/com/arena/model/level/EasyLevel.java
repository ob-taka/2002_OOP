package com.arena.model.level;

import com.arena.model.combatant.Enemy;
import com.arena.model.combatant.Goblin;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EasyLevel implements Level {

    @Override
    public String getName() { return "Easy"; }

    @Override
    public int getDifficulty() { return 1; }

    @Override
    public List<Enemy> createInitialWave() {
        return Arrays.asList(new Goblin("Goblin A"), new Goblin("Goblin B"), new Goblin("Goblin C"));
    }

    @Override
    public List<Enemy> createBackupWave() { return Collections.emptyList(); }

    @Override
    public boolean hasBackupWave() { return false; }
}
