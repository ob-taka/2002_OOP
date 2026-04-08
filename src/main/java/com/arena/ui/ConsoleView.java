package com.arena.ui;

import com.arena.action.Action;
import com.arena.engine.BattleResult;
import com.arena.model.combatant.Combatant;
import com.arena.model.combatant.Enemy;
import com.arena.model.combatant.Player;
import com.arena.model.effect.StatusEffect;
import com.arena.model.item.Item;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleView implements GameView {
    private final Scanner scanner;

    public ConsoleView() {
        this.scanner = new Scanner(System.in);
    }

    private int readInt(int min, int max) {
        while (true) {
            System.out.print("> ");
            try {
                int val = Integer.parseInt(scanner.nextLine().trim());
                if (val >= min && val <= max) return val;
                System.out.println("Please enter a number between " + min + " and " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    @Override
    public void showWelcome() {
        System.out.println("========================================");
        System.out.println("   TURN-BASED COMBAT ARENA");
        System.out.println("========================================");
        System.out.println();
        System.out.println("--- Players ---");
        System.out.println("[1] Warrior  | HP:260 ATK:40 DEF:20 SPD:30 | Skill: Shield Bash");
        System.out.println("[2] Wizard   | HP:200 ATK:50 DEF:10 SPD:20 | Skill: Arcane Blast");
        System.out.println();
        System.out.println("--- Enemies ---");
        System.out.println("    Goblin   | HP:55  ATK:35 DEF:15 SPD:25");
        System.out.println("    Wolf     | HP:40  ATK:45 DEF:5  SPD:35");
        System.out.println();
    }

    @Override
    public int promptPlayerClass() {
        System.out.println("Select your class:");
        System.out.println("[1] Warrior");
        System.out.println("[2] Wizard");
        return readInt(1, 2);
    }

    @Override
    public List<String> promptItemSelection(String playerClass) {
        List<String> itemNames = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        itemNames.add("Potion");        labels.add("Potion           - Heal 100 HP");
        itemNames.add("Power Stone");   labels.add("Power Stone      - Free special skill (no cooldown)");
        itemNames.add("Smoke Bomb");    labels.add("Smoke Bomb       - Enemy attacks deal 0 dmg for 2 turns");
        itemNames.add("Poison Potion"); labels.add("Poison Potion    - DoT 3 HP/turn for 3 turns to ALL enemies");
        if ("Warrior".equalsIgnoreCase(playerClass)) {
            itemNames.add("Burst Kill Potion");
            labels.add("Burst Kill Potion- Instantly kill 1 enemy (Warrior only, requires HP>=20, self-stun 1)");
        }
        if ("Wizard".equalsIgnoreCase(playerClass)) {
            itemNames.add("Blind Potion");
            labels.add("Blind Potion     - Stun ALL enemies for 1 turn (Wizard only)");
        }

        System.out.println("\n--- Available Items ---");
        for (int i = 0; i < labels.size(); i++) {
            System.out.println("[" + (i + 1) + "] " + labels.get(i));
        }
        System.out.println();

        String[] selected = new String[2];
        for (int i = 0; i < 2; i++) {
            System.out.println("Select item " + (i + 1) + " of 2 (duplicates allowed):");
            int choice = readInt(1, itemNames.size());
            selected[i] = itemNames.get(choice - 1);
        }
        return List.of(selected);
    }

    @Override
    public int promptDifficulty() {
        System.out.println("\n--- Difficulty ---");
        System.out.println("[1] Easy   - 3 Goblins");
        System.out.println("[2] Medium - 1 Goblin + 1 Wolf | Backup: 2 Wolves");
        System.out.println("[3] Hard   - 2 Goblins | Backup: 1 Goblin + 2 Wolves");
        System.out.println("Select difficulty:");
        return readInt(1, 3);
    }

    @Override
    public void showRoundStart(int round, List<Combatant> turnOrder) {
        System.out.println("\n========== ROUND " + round + " ==========");
        System.out.print("Turn Order: ");
        for (int i = 0; i < turnOrder.size(); i++) {
            Combatant c = turnOrder.get(i);
            if (i > 0) System.out.print(" -> ");
            System.out.print(c.getName() + "(SPD:" + c.getEffectiveSpd() + ")");
        }
        System.out.println();
    }

    @Override
    public void showCombatantStatus(Player player, List<Enemy> enemies) {
        System.out.println("\n--- Status ---");
        System.out.print("  " + player.getName() + " HP: " + player.getCurrentHp() + "/" + player.getMaxHp());
        if (!player.getActiveEffects().isEmpty()) {
            System.out.print(" [");
            for (int i = 0; i < player.getActiveEffects().size(); i++) {
                if (i > 0) System.out.print(", ");
                StatusEffect e = player.getActiveEffects().get(i);
                System.out.print(e.getName());
                if (e.getRemainingTurns() > 0) System.out.print("(" + e.getRemainingTurns() + ")");
            }
            System.out.print("]");
        }
        System.out.print(" | Cooldown: " + player.getSpecialCooldown());
        System.out.println();

        for (Enemy e : enemies) {
            System.out.print("  " + e.getName());
            if (e.isAlive()) {
                System.out.print(" HP: " + e.getCurrentHp() + "/" + e.getMaxHp());
                if (!e.getActiveEffects().isEmpty()) {
                    System.out.print(" [");
                    for (int i = 0; i < e.getActiveEffects().size(); i++) {
                        if (i > 0) System.out.print(", ");
                        StatusEffect eff = e.getActiveEffects().get(i);
                        System.out.print(eff.getName());
                        if (eff.getRemainingTurns() > 0) System.out.print("(" + eff.getRemainingTurns() + ")");
                    }
                    System.out.print("]");
                }
            } else {
                System.out.print(" ELIMINATED");
            }
            System.out.println();
        }
        System.out.println();
    }

    @Override
    public int promptPlayerAction(Player player, List<Action> availableActions) {
        System.out.println("--- " + player.getName() + "'s Turn ---");
        System.out.println("Choose your action:");
        for (int i = 0; i < availableActions.size(); i++) {
            System.out.println("[" + (i + 1) + "] " + availableActions.get(i).getName());
        }
        return readInt(1, availableActions.size()) - 1;
    }

    @Override
    public Combatant promptTargetSelection(List<? extends Combatant> targets) {
        if (targets.size() == 1) return targets.get(0);
        System.out.println("Select target:");
        for (int i = 0; i < targets.size(); i++) {
            Combatant t = targets.get(i);
            System.out.println("[" + (i + 1) + "] " + t.getName()
                    + " (HP: " + t.getCurrentHp() + "/" + t.getMaxHp() + ")");
        }
        int choice = readInt(1, targets.size());
        return targets.get(choice - 1);
    }

    @Override
    public int promptItemChoice(List<Item> items) {
        System.out.println("Select item to use:");
        for (int i = 0; i < items.size(); i++) {
            System.out.println("[" + (i + 1) + "] " + items.get(i).getName());
        }
        return readInt(1, items.size()) - 1;
    }

    @Override
    public void showActionResult(String message) {
        System.out.println("  >> " + message);
    }

    @Override
    public void showStunned(Combatant combatant) {
        System.out.println("  >> " + combatant.getName() + " is STUNNED! Turn skipped.");
    }

    @Override
    public void showBackupSpawn(List<Enemy> backups) {
        System.out.println("\n*** BACKUP SPAWN! ***");
        for (Enemy e : backups) {
            System.out.println("  " + e.getName() + " (HP:" + e.getMaxHp()
                    + " ATK:" + e.getBaseAtk() + " DEF:" + e.getBaseDef()
                    + " SPD:" + e.getBaseSpd() + ") enters combat!");
        }
        System.out.println();
    }

    @Override
    public void showVictory(BattleResult result) {
        System.out.println("\n========================================");
        System.out.println("  VICTORY!");
        System.out.println("  Congratulations, you have defeated all your enemies.");
        System.out.println("========================================");
        Player p = result.getPlayer();
        System.out.println("  Remaining HP: " + p.getCurrentHp() + "/" + p.getMaxHp());
        System.out.println("  Total Rounds: " + result.getTotalRounds());
        System.out.print("  Remaining Items:");
        if (p.getInventory().isEmpty()) {
            System.out.println(" None");
        } else {
            for (Item item : p.getInventory()) {
                System.out.print(" " + item.getName());
            }
            System.out.println();
        }
        System.out.println();
    }

    @Override
    public void showDefeat(BattleResult result) {
        System.out.println("\n========================================");
        System.out.println("  DEFEATED");
        System.out.println("  Don't give up, try again!");
        System.out.println("========================================");
        System.out.println("  Enemies Remaining: " + result.getEnemiesRemaining());
        System.out.println("  Total Rounds Survived: " + result.getTotalRounds());
        System.out.println();
    }

    @Override
    public int promptEndGame() {
        System.out.println("What would you like to do?");
        System.out.println("[1] Replay with same settings");
        System.out.println("[2] Start a new game");
        System.out.println("[3] Exit");
        return readInt(1, 3);
    }

    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }
}
