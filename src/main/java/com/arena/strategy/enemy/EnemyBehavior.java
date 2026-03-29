package com.arena.strategy.enemy;

import com.arena.action.Action;
import com.arena.model.combatant.Combatant;
import java.util.List;

public interface EnemyBehavior {
    Action decideAction(Combatant self, List<Combatant> opponents);
    Combatant selectTarget(Combatant self, List<Combatant> opponents);
}
