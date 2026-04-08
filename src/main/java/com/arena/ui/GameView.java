package com.arena.ui;

import com.arena.action.Action;
import com.arena.engine.BattleResult;
import com.arena.model.combatant.Combatant;
import com.arena.model.combatant.Enemy;
import com.arena.model.combatant.Player;
import com.arena.model.item.Item;
import java.util.List;

public interface GameView {
    void showWelcome();
    int promptPlayerClass();
    List<String> promptItemSelection(String playerClass);
    int promptDifficulty();

    void showRoundStart(int round, List<Combatant> turnOrder);
    void showCombatantStatus(Player player, List<Enemy> enemies);
    int promptPlayerAction(Player player, List<Action> availableActions);
    Combatant promptTargetSelection(List<? extends Combatant> targets);
    int promptItemChoice(List<Item> items);
    void showActionResult(String message);
    void showStunned(Combatant combatant);
    void showBackupSpawn(List<Enemy> backups);

    void showVictory(BattleResult result);
    void showDefeat(BattleResult result);
    int promptEndGame();

    void showMessage(String message);
}
