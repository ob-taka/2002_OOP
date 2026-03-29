package com.arena.model.level;

import com.arena.model.combatant.Enemy;
import com.arena.model.combatant.Goblin;
import com.arena.model.combatant.Wolf;
import java.util.Arrays;
import java.util.List;

public class MediumLevel implements Level {

    @Override
    public String getName() { return "Medium"; }

    @Override
    public int getDifficulty() { return 2; }

    @Override
    public List<Enemy> createInitialWave() {
        return Arrays.asList(new Goblin("Goblin"), new Wolf("Wolf"));
    }

    @Override
    public List<Enemy> createBackupWave() {
        return Arrays.asList(new Wolf("Wolf A"), new Wolf("Wolf B"));
    }

    @Override
    public boolean hasBackupWave() { return true; }
}
