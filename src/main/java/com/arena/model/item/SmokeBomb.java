package com.arena.model.item;

import com.arena.model.combatant.Combatant;
import com.arena.model.combatant.Player;
import com.arena.model.effect.SmokeBombEffect;
import java.util.List;

public class SmokeBomb implements Item {
    private boolean consumed = false;

    @Override
    public String getName() { return "Smoke Bomb"; }

    @Override
    public String use(Player user, List<Combatant> targets) {
        consumed = true;
        user.addEffect(new SmokeBombEffect(2));
        return user.getName() + " uses Smoke Bomb! Enemy attacks deal 0 damage for 2 turns.";
    }

    @Override
    public boolean isConsumed() { return consumed; }
}
