package com.arena.model.combatant;

import com.arena.strategy.enemy.BasicAttackBehavior;

public class Goblin extends Enemy {
    public Goblin() {
        super("Goblin", 55, 35, 15, 25, new BasicAttackBehavior());
    }

    public Goblin(String name) {
        super(name, 55, 35, 15, 25, new BasicAttackBehavior());
    }
}
