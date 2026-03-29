package com.arena.model.item;

import com.arena.model.combatant.Combatant;
import com.arena.model.combatant.Player;
import java.util.List;

public class Potion implements Item {
    private boolean consumed = false;

    @Override
    public String getName() { return "Potion"; }

    @Override
    public String use(Player user, List<Combatant> targets) {
        int oldHp = user.getCurrentHp();
        user.heal(100);
        consumed = true;
        return user.getName() + " uses Potion! HP: " + oldHp + " -> " + user.getCurrentHp()
                + " (+" + (user.getCurrentHp() - oldHp) + ")";
    }

    @Override
    public boolean isConsumed() { return consumed; }
}
