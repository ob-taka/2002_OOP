package com.arena.strategy.turn;

import com.arena.model.combatant.Combatant;
import java.util.List;

public interface TurnOrderStrategy {
    List<Combatant> orderTurns(List<Combatant> combatants);
}
