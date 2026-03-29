package com.arena.factory;

import com.arena.model.item.*;

public class ItemFactory {

    public static Item createItem(String type) {
        switch (type.toLowerCase()) {
            case "potion": return new Potion();
            case "power stone": return new PowerStone();
            case "smoke bomb": return new SmokeBomb();
            default: throw new IllegalArgumentException("Unknown item type: " + type);
        }
    }
}
