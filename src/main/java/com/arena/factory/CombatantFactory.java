package com.arena.factory;

import com.arena.model.combatant.*;

public class CombatantFactory {

    public static Player createPlayer(String type) {
        switch (type.toLowerCase()) {
            case "warrior": return new Warrior();
            case "wizard": return new Wizard();
            default: throw new IllegalArgumentException("Unknown player type: " + type);
        }
    }

    public static Enemy createEnemy(String type) {
        switch (type.toLowerCase()) {
            case "goblin": return new Goblin();
            case "wolf": return new Wolf();
            default: throw new IllegalArgumentException("Unknown enemy type: " + type);
        }
    }
}
