package com.arena.action;

import com.arena.model.combatant.Combatant;
import java.util.List;

public class BasicAttack implements Action {

    @Override
    public String getName() { return "BasicAttack"; }

    @Override
    public ActionResult execute(Combatant actor, List<Combatant> targets) {
        if (targets.isEmpty()) return new ActionResult("No target available.", false);
        Combatant target = targets.get(0);
        int damage = Math.max(0, actor.getEffectiveAtk() - target.getEffectiveDef());
        if (target.preventsIncomingDamage()) {
            damage = 0;
        }
        target.takeDamage(damage);
        String msg = actor.getName() + " attacks " + target.getName()
                + "! Damage: " + damage
                + " (ATK:" + actor.getEffectiveAtk() + " - DEF:" + target.getEffectiveDef() + ")"
                + " | " + target.getName() + " HP: " + (target.getCurrentHp() + damage) + " -> " + target.getCurrentHp();
        if (!target.isAlive()) {
            msg += " | " + target.getName() + " ELIMINATED!";
        }
        return new ActionResult(msg, true);
    }

    @Override
    public boolean isAvailable(Combatant actor) { return true; }
}
