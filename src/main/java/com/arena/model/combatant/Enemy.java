package com.arena.model.combatant;

import com.arena.strategy.enemy.EnemyBehavior;

public abstract class Enemy extends Combatant {
    protected EnemyBehavior behavior;

    protected Enemy(String name, int hp, int atk, int def, int spd, EnemyBehavior behavior) {
        super(name, hp, atk, def, spd);
        this.behavior = behavior;
    }

    public EnemyBehavior getBehavior() { return behavior; }

    public void setBehavior(EnemyBehavior behavior) { this.behavior = behavior; }
}
