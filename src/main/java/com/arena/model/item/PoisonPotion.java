package com.arena.model.item;

import com.arena.model.combatant.Combatant;
import com.arena.model.combatant.Player;
import com.arena.model.effect.DamageOverTimeEffect;
import java.util.List;
import java.util.StringJoiner;

public class PoisonPotion implements Item {
    private static final int DAMAGE_PER_TICK = 3;
    private static final int DURATION = 3;

    private boolean consumed = false;

    @Override
    public String getName() { return "Poison Potion"; }

    @Override
    public String use(Player user, List<Combatant> targets) {
        StringJoiner names = new StringJoiner(", ");
        for (Combatant t : targets) {
            if (t.isAlive()) {
                t.addEffect(new DamageOverTimeEffect(DAMAGE_PER_TICK, DURATION));
                names.add(t.getName());
            }
        }
        consumed = true;
        return user.getName() + " uses Poison Potion! Poisoned: " + names
                + " (-" + DAMAGE_PER_TICK + " HP/turn for " + DURATION + " turns)";
    }

    @Override
    public boolean isConsumed() { return consumed; }
}
