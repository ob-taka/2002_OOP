package com.arena.model.combatant;

import com.arena.model.effect.StunEffect;
import java.util.List;

public class Warrior extends Player {

    public Warrior() {
        super("Warrior", 260, 40, 20, 30);
    }

    @Override
    public String getSpecialSkillName() { return "Shield Bash"; }

    @Override
    public String useSpecialSkill(List<Combatant> targets) {
        String result = triggerSpecialEffect(targets);
        this.specialCooldown = 3;
        return result;
    }

    @Override
    public String triggerSpecialEffect(List<Combatant> targets) {
        if (targets.isEmpty()) return "No target for Shield Bash.";
        Combatant target = targets.get(0);
        int damage = Math.max(0, this.getEffectiveAtk() - target.getEffectiveDef());
        if (target.preventsIncomingDamage()) {
            damage = 0;
        }
        target.takeDamage(damage);
        target.addEffect(new StunEffect(2));
        return name + " uses Shield Bash on " + target.getName()
                + "! Deals " + damage + " damage. " + target.getName() + " is STUNNED!";
    }
}
