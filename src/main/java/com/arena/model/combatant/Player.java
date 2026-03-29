package com.arena.model.combatant;

import com.arena.model.item.Item;
import java.util.ArrayList;
import java.util.List;

public abstract class Player extends Combatant {
    protected List<Item> inventory;
    protected int specialCooldown;

    protected Player(String name, int hp, int atk, int def, int spd) {
        super(name, hp, atk, def, spd);
        this.inventory = new ArrayList<>();
        this.specialCooldown = 0;
    }

    public abstract String getSpecialSkillName();
    public abstract String useSpecialSkill(List<Combatant> targets);
    public abstract String triggerSpecialEffect(List<Combatant> targets);

    public int getSpecialCooldown() { return specialCooldown; }

    public void setCooldown(int cooldown) { this.specialCooldown = cooldown; }

    public void decrementCooldown() {
        if (specialCooldown > 0) specialCooldown--;
    }

    public boolean isSpecialAvailable() { return specialCooldown == 0; }

    public List<Item> getInventory() { return inventory; }

    public void addItem(Item item) { inventory.add(item); }

    public boolean hasItems() { return !inventory.isEmpty(); }
}
