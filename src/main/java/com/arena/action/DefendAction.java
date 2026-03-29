package com.arena.action;

import com.arena.model.combatant.Combatant;
import com.arena.model.effect.DefendEffect;
import java.util.List;

public class DefendAction implements Action {

    @Override
    public String getName() { return "Defend"; }

    @Override
    public ActionResult execute(Combatant actor, List<Combatant> targets) {
        actor.removeEffectsByName("Defend");
        actor.addEffect(new DefendEffect(2));
        String msg = actor.getName() + " defends! DEF increased by 10 for 2 turns."
                + " (DEF: " + (actor.getBaseDef()) + " -> " + actor.getEffectiveDef() + ")";
        return new ActionResult(msg, true);
    }

    @Override
    public boolean isAvailable(Combatant actor) { return true; }
}
