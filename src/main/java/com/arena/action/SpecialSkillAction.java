package com.arena.action;

import com.arena.model.combatant.Combatant;
import com.arena.model.combatant.Player;
import java.util.List;

public class SpecialSkillAction implements Action {

    @Override
    public String getName() { return "SpecialSkill"; }

    @Override
    public ActionResult execute(Combatant actor, List<Combatant> targets) {
        if (!(actor instanceof Player)) {
            return new ActionResult("Only players can use special skills.", false);
        }
        Player player = (Player) actor;
        String result = player.useSpecialSkill(targets);
        return new ActionResult(result, true);
    }

    @Override
    public boolean isAvailable(Combatant actor) {
        if (!(actor instanceof Player)) return false;
        return ((Player) actor).isSpecialAvailable();
    }
}
