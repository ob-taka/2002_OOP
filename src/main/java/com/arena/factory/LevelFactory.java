package com.arena.factory;

import com.arena.model.level.*;

public class LevelFactory {

    public static Level createLevel(int difficulty) {
        switch (difficulty) {
            case 1: return new EasyLevel();
            case 2: return new MediumLevel();
            case 3: return new HardLevel();
            default: throw new IllegalArgumentException("Unknown difficulty: " + difficulty);
        }
    }
}
