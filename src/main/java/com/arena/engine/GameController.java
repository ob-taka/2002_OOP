package com.arena.engine;

import com.arena.factory.ItemFactory;
import com.arena.factory.LevelFactory;
import com.arena.model.combatant.Player;
import com.arena.model.combatant.Warrior;
import com.arena.model.combatant.Wizard;
import com.arena.model.level.Level;
import com.arena.strategy.turn.SpeedBasedTurnOrder;
import com.arena.ui.GameView;
import java.util.List;

public class GameController {
    private final GameView view;

    public GameController(GameView view) {
        this.view = view;
    }

    public void run() {
        boolean running = true;
        while (running) {
            view.showWelcome();

            int playerClass = view.promptPlayerClass();
            Player player = createPlayer(playerClass);
            view.showMessage("\nYou selected: " + player.getName());

            List<String> items = view.promptItemSelection(player.getName());
            for (String itemName : items) {
                player.addItem(ItemFactory.createItem(itemName));
            }
            view.showMessage("Items: " + items);

            int difficulty = view.promptDifficulty();
            Level level = LevelFactory.createLevel(difficulty);
            view.showMessage("\nDifficulty: " + level.getName());
            view.showMessage("Enemies: " + level.createInitialWave().size() + " initial"
                    + (level.hasBackupWave() ? " + backup wave" : ""));
            view.showMessage("\n--- Battle Start! ---");

            running = runGameLoop(playerClass, items, difficulty);
        }
        view.showMessage("Thanks for playing!");
    }

    private boolean runGameLoop(int playerClass, List<String> items, int difficulty) {
        while (true) {
            Player player = createPlayer(playerClass);
            for (String itemName : items) {
                player.addItem(ItemFactory.createItem(itemName));
            }
            Level level = LevelFactory.createLevel(difficulty);
            BattleContext context = new BattleContext(player, level);
            BattleEngine engine = new BattleEngine(new SpeedBasedTurnOrder(), view);
            BattleResult result = engine.runBattle(player, context);

            if (result.isPlayerWon()) {
                view.showVictory(result);
            } else {
                view.showDefeat(result);
            }

            int choice = view.promptEndGame();
            switch (choice) {
                case 1: // Replay same settings
                    continue;
                case 2: // New game
                    return true;
                case 3: // Exit
                    return false;
            }
        }
    }

    private Player createPlayer(int classChoice) {
        switch (classChoice) {
            case 1: return new Warrior();
            case 2: return new Wizard();
            default: return new Warrior();
        }
    }
}
