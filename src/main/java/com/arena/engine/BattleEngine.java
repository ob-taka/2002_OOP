package com.arena.engine;

import com.arena.action.*;
import com.arena.model.combatant.*;
import com.arena.model.item.Item;
import com.arena.strategy.turn.TurnOrderStrategy;
import com.arena.ui.GameView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BattleEngine {
    private final TurnOrderStrategy turnOrderStrategy;
    private final GameView view;

    public BattleEngine(TurnOrderStrategy turnOrderStrategy, GameView view) {
        this.turnOrderStrategy = turnOrderStrategy;
        this.view = view;
    }

    public BattleResult runBattle(Player player, BattleContext context) {
        TurnManager turnManager = new TurnManager(turnOrderStrategy);

        while (true) {
            context.incrementRound();
            List<Combatant> allCombatants = buildCombatantList(player, context);
            List<Combatant> turnOrder = turnManager.determineTurnOrder(allCombatants);

            view.showRoundStart(context.getCurrentRound(), turnOrder);
            view.showCombatantStatus(player, context.getActiveEnemies());

            for (Combatant combatant : turnOrder) {
                if (!combatant.isAlive()) continue;

                if (combatant.isStunned()) {
                    view.showStunned(combatant);
                    combatant.tickEffects();
                    if (combatant instanceof Player) {
                        ((Player) combatant).decrementCooldown();
                    }
                    continue;
                }

                if (combatant instanceof Player) {
                    handlePlayerTurn((Player) combatant, context);
                } else if (combatant instanceof Enemy) {
                    handleEnemyTurn((Enemy) combatant, player);
                }

                combatant.tickEffects();
                if (combatant instanceof Player) {
                    ((Player) combatant).decrementCooldown();
                }

                if (!player.isAlive()) {
                    return new BattleResult(false, context.getCurrentRound(), player,
                            (int) context.getAliveEnemies().size());
                }

                if (context.allEnemiesDead()) {
                    if (context.shouldSpawnBackup()) {
                        List<Enemy> backup = context.spawnBackup();
                        view.showBackupSpawn(backup);
                    } else {
                        return new BattleResult(true, context.getCurrentRound(), player, 0);
                    }
                }
            }
        }
    }

    private void handlePlayerTurn(Player player, BattleContext context) {
        List<Action> actions = buildAvailableActions(player);
        int actionIdx = view.promptPlayerAction(player, actions);
        Action chosen = actions.get(actionIdx);

        List<Combatant> targets;

        if (chosen instanceof UseItemAction) {
            int itemIdx = view.promptItemChoice(player.getInventory());
            Item selectedItem = player.getInventory().get(itemIdx);
            chosen = new UseItemAction(selectedItem);
            targets = resolveItemTargets(player, selectedItem, context);
        } else if (chosen instanceof DefendAction) {
            targets = Collections.singletonList(player);
        } else {
            targets = resolveEnemyTargets(chosen, player, context);
        }

        ActionResult result = chosen.execute(player, targets);
        view.showActionResult(result.getMessage());
    }

    private void handleEnemyTurn(Enemy enemy, Player player) {
        Action action = enemy.getBehavior().decideAction(enemy, Collections.singletonList(player));
        Combatant target = enemy.getBehavior().selectTarget(enemy, Collections.singletonList(player));
        if (target == null || !target.isAlive()) return;

        ActionResult result = action.execute(enemy, Collections.singletonList(target));
        view.showActionResult(result.getMessage());
    }

    private List<Action> buildAvailableActions(Player player) {
        List<Action> actions = new ArrayList<>();
        actions.add(new BasicAttack());
        actions.add(new DefendAction());
        if (player.hasItems()) {
            actions.add(new UseItemAction(player.getInventory().get(0)));
        }
        if (player.isSpecialAvailable()) {
            actions.add(new SpecialSkillAction());
        }
        return actions;
    }

    private List<Combatant> resolveEnemyTargets(Action action, Player player, BattleContext context) {
        List<Enemy> alive = context.getAliveEnemies();
        if (alive.isEmpty()) return Collections.emptyList();

        if (action instanceof SpecialSkillAction && player instanceof Wizard) {
            return new ArrayList<>(alive);
        }

        Combatant target = view.promptTargetSelection(alive);
        return Collections.singletonList(target);
    }

    private List<Combatant> resolveItemTargets(Player player, Item item, BattleContext context) {
        String name = item.getName().toLowerCase();
        if (name.equals("potion") || name.equals("smoke bomb")) {
            return Collections.singletonList(player);
        }
        if (name.equals("poison potion")) {
            return new ArrayList<>(context.getAliveEnemies());
        }
        // Power Stone targets enemies
        if (name.equals("power stone") && player instanceof Wizard) {
            return new ArrayList<>(context.getAliveEnemies());
        }
        List<Enemy> alive = context.getAliveEnemies();
        if (alive.isEmpty()) return Collections.emptyList();
        Combatant target = view.promptTargetSelection(alive);
        return Collections.singletonList(target);
    }

    private List<Combatant> buildCombatantList(Player player, BattleContext context) {
        List<Combatant> all = new ArrayList<>();
        all.add(player);
        all.addAll(context.getActiveEnemies());
        return all;
    }
}
