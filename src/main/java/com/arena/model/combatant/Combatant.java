package com.arena.model.combatant;

import com.arena.model.effect.StatusEffect;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Combatant {
    protected String name;
    protected int maxHp;
    protected int currentHp;
    protected int baseAtk;
    protected int baseDef;
    protected int baseSpd;
    protected List<StatusEffect> activeEffects;

    protected Combatant(String name, int hp, int atk, int def, int spd) {
        this.name = name;
        this.maxHp = hp;
        this.currentHp = hp;
        this.baseAtk = atk;
        this.baseDef = def;
        this.baseSpd = spd;
        this.activeEffects = new ArrayList<>();
    }

    public String getName() { return name; }
    public int getMaxHp() { return maxHp; }
    public int getCurrentHp() { return currentHp; }
    public int getBaseAtk() { return baseAtk; }
    public int getBaseDef() { return baseDef; }
    public int getBaseSpd() { return baseSpd; }

    public int getEffectiveAtk() {
        int modifier = activeEffects.stream().mapToInt(StatusEffect::getAtkModifier).sum();
        return baseAtk + modifier;
    }

    public int getEffectiveDef() {
        int modifier = activeEffects.stream().mapToInt(StatusEffect::getDefModifier).sum();
        return baseDef + modifier;
    }

    public int getEffectiveSpd() {
        int modifier = activeEffects.stream().mapToInt(StatusEffect::getSpdModifier).sum();
        return baseSpd + modifier;
    }

    public void takeDamage(int damage) {
        this.currentHp = Math.max(0, this.currentHp - damage);
    }

    public void heal(int amount) {
        this.currentHp = Math.min(this.maxHp, this.currentHp + amount);
    }

    public boolean isAlive() { return currentHp > 0; }

    public boolean isStunned() {
        return activeEffects.stream().anyMatch(StatusEffect::preventsAction);
    }

    public boolean preventsIncomingDamage() {
        return activeEffects.stream().anyMatch(StatusEffect::preventsIncomingDamage);
    }

    public void addEffect(StatusEffect effect) {
        activeEffects.add(effect);
    }

    public void removeEffect(StatusEffect effect) {
        activeEffects.remove(effect);
    }

    public void removeEffectsByName(String name) {
        activeEffects.removeIf(e -> e.getName().equals(name));
    }

    public List<StatusEffect> getActiveEffects() {
        return activeEffects;
    }

    public void tickEffects() {
        Iterator<StatusEffect> it = activeEffects.iterator();
        while (it.hasNext()) {
            StatusEffect effect = it.next();
            effect.decrementTurn();
            if (effect.isExpired()) {
                it.remove();
            }
        }
    }

    public void clearAllEffects() {
        activeEffects.clear();
    }

    @Override
    public String toString() {
        return name + " (HP: " + currentHp + "/" + maxHp + ")";
    }
}
