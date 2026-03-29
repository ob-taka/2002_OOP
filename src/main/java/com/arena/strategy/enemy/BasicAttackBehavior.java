package com.arena.strategy.enemy;

import com.arena.action.Action;
import com.arena.action.BasicAttack;
import com.arena.model.combatant.Combatant;
import java.util.List;

public class BasicAttackBehavior implements EnemyBehavior {

    @Override
    public Action decideAction(Combatant self, List<Combatant> opponents) {
        return new BasicAttack();
    }

    @Override
    public Combatant selectTarget(Combatant self, List<Combatant> opponents) {
        return opponents.stream()
                .filter(Combatant::isAlive)
                .findFirst()
                .orElse(null);
    }
}
