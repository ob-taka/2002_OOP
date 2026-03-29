package com.arena.model.combatant;

import com.arena.model.effect.ArcaneBlastAtkBuff;
import java.util.List;

public class Wizard extends Player {
    private ArcaneBlastAtkBuff arcaneBuff;

    public Wizard() {
        super("Wizard", 200, 50, 10, 20);
        this.arcaneBuff = null;
    }

    @Override
    public String getSpecialSkillName() { return "Arcane Blast"; }

    @Override
    public String useSpecialSkill(List<Combatant> targets) {
        String result = triggerSpecialEffect(targets);
        this.specialCooldown = 3;
        return result;
    }

    @Override
    public String triggerSpecialEffect(List<Combatant> targets) {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(" uses Arcane Blast on ALL enemies!\n");
        int killCount = 0;

        for (Combatant target : targets) {
            if (!target.isAlive()) continue;
            int damage = Math.max(0, this.getEffectiveAtk() - target.getEffectiveDef());
            if (target.preventsIncomingDamage()) {
                damage = 0;
            }
            target.takeDamage(damage);
            sb.append("  ").append(target.getName()).append(" takes ").append(damage).append(" damage.");
            if (!target.isAlive()) {
                sb.append(" ELIMINATED!");
                killCount++;
            }
            sb.append("\n");
        }

        if (killCount > 0) {
            if (arcaneBuff == null) {
                arcaneBuff = new ArcaneBlastAtkBuff();
                addEffect(arcaneBuff);
            }
            for (int i = 0; i < killCount; i++) {
                arcaneBuff.incrementKills();
            }
            sb.append("  Wizard ATK +").append(killCount * 10)
              .append(" (total bonus: +").append(arcaneBuff.getAtkModifier()).append(")");
        }
        return sb.toString();
    }

    public void clearArcaneBuff() {
        if (arcaneBuff != null) {
            removeEffect(arcaneBuff);
            arcaneBuff = null;
        }
    }
}
