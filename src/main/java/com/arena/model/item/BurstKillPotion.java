package com.arena.model.item;

import com.arena.model.combatant.Combatant;
import com.arena.model.combatant.Player;
import com.arena.model.combatant.Warrior;
import com.arena.model.effect.StunEffect;
import java.util.List;

public class BurstKillPotion implements Item {
    private static final int MIN_HP = 20;

    private boolean consumed = false;

    @Override
    public String getName() { return "Burst Kill Potion"; }

    @Override
    public String use(Player user, List<Combatant> targets) {
        if (!(user instanceof Warrior)) {
            return "Only the Warrior can use Burst Kill Potion.";
        }
        if (user.getCurrentHp() < MIN_HP) {
            return user.getName() + " is too weak (<" + MIN_HP + " HP) to use Burst Kill Potion.";
        }
        Combatant target = null;
        for (Combatant t : targets) {
            if (t.isAlive()) { target = t; break; }
        }
        if (target == null) {
            return "No valid target for Burst Kill Potion.";
        }
        int dmg = target.getCurrentHp();
        target.takeDamage(dmg);
        // Note: PDF mentions "lose 20xp"; no XP system in codebase, so skipped intentionally.
        // Duration 2 because tickEffects runs at the end of this turn and decrements once.
        user.addEffect(new StunEffect(2));
        consumed = true;
        return user.getName() + " unleashes Burst Kill on " + target.getName()
                + " for " + dmg + " damage! " + target.getName() + " is ELIMINATED. "
                + user.getName() + " cannot move next round.";
    }

    @Override
    public boolean isConsumed() { return consumed; }
}
