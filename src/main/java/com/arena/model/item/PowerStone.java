package com.arena.model.item;

import com.arena.model.combatant.Combatant;
import com.arena.model.combatant.Player;
import java.util.List;

public class PowerStone implements Item {
    private boolean consumed = false;

    @Override
    public String getName() { return "Power Stone"; }

    @Override
    public String use(Player user, List<Combatant> targets) {
        consumed = true;
        String result = user.triggerSpecialEffect(targets);
        return user.getName() + " uses Power Stone! Triggering " + user.getSpecialSkillName()
                + " (cooldown unchanged).\n" + result;
    }

    @Override
    public boolean isConsumed() { return consumed; }
}
