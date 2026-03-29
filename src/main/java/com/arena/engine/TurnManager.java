package com.arena.engine;

import com.arena.model.combatant.Combatant;
import com.arena.strategy.turn.TurnOrderStrategy;
import java.util.List;
import java.util.stream.Collectors;

public class TurnManager {
    private final TurnOrderStrategy strategy;

    public TurnManager(TurnOrderStrategy strategy) {
        this.strategy = strategy;
    }

    public List<Combatant> determineTurnOrder(List<Combatant> combatants) {
        List<Combatant> alive = combatants.stream()
                .filter(Combatant::isAlive)
                .collect(Collectors.toList());
        return strategy.orderTurns(alive);
    }
}
