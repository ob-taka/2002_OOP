package com.arena.model.combatant;

import com.arena.strategy.enemy.BasicAttackBehavior;

public class Wolf extends Enemy {
    public Wolf() {
        super("Wolf", 40, 45, 5, 35, new BasicAttackBehavior());
    }

    public Wolf(String name) {
        super(name, 40, 45, 5, 35, new BasicAttackBehavior());
    }
}
