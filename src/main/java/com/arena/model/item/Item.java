package com.arena.model.item;

import com.arena.model.combatant.Combatant;
import com.arena.model.combatant.Player;
import java.util.List;

public interface Item {
    String getName();
    String use(Player user, List<Combatant> targets);
    boolean isConsumed();
}
