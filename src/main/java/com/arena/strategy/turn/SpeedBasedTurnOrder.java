package com.arena.strategy.turn;

import com.arena.model.combatant.Combatant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SpeedBasedTurnOrder implements TurnOrderStrategy {

    @Override
    public List<Combatant> orderTurns(List<Combatant> combatants) {
        List<Combatant> sorted = new ArrayList<>(combatants);
        sorted.sort(Comparator.comparingInt(Combatant::getEffectiveSpd).reversed());
        return sorted;
    }
}
