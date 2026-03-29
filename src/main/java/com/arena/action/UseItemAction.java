package com.arena.action;

import com.arena.model.combatant.Combatant;
import com.arena.model.combatant.Player;
import com.arena.model.item.Item;
import java.util.List;

public class UseItemAction implements Action {
    private final Item item;

    public UseItemAction(Item item) {
        this.item = item;
    }

    @Override
    public String getName() { return "Item (" + item.getName() + ")"; }

    @Override
    public ActionResult execute(Combatant actor, List<Combatant> targets) {
        if (!(actor instanceof Player)) {
            return new ActionResult("Only players can use items.", false);
        }
        Player player = (Player) actor;
        String result = item.use(player, targets);
        player.getInventory().remove(item);
        return new ActionResult(result, true);
    }

    @Override
    public boolean isAvailable(Combatant actor) {
        if (!(actor instanceof Player)) return false;
        return ((Player) actor).hasItems();
    }

    public Item getItem() { return item; }
}
