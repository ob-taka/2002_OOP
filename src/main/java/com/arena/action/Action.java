package com.arena.action;

import com.arena.model.combatant.Combatant;
import java.util.List;

public interface Action {
    String getName();
    ActionResult execute(Combatant actor, List<Combatant> targets);
    boolean isAvailable(Combatant actor);
}
