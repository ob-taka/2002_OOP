package com.arena.model.item;

import com.arena.model.combatant.Combatant;
import com.arena.model.combatant.Player;
import com.arena.model.combatant.Wizard;
import com.arena.model.effect.StunEffect;
import java.util.List;
import java.util.StringJoiner;

public class BlindPotion implements Item {
    private boolean consumed = false;

    @Override
    public String getName() { return "Blind Potion"; }

    @Override
    public String use(Player user, List<Combatant> targets) {
        if (!(user instanceof Wizard)) {
            return "Only the Wizard can use Blind Potion.";
        }
        StringJoiner names = new StringJoiner(", ");
        for (Combatant t : targets) {
            if (t.isAlive()) {
                t.addEffect(new StunEffect(1));
                names.add(t.getName());
            }
        }
        consumed = true;
        return user.getName() + " hurls Blind Potion! Stunned: " + names + " (1 turn)";
    }

    @Override
    public boolean isConsumed() { return consumed; }
}
