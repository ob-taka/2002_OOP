# SC2002 Turn-Based Combat Arena

A CLI-based turn-based combat arena game built with Java, demonstrating Object-Oriented Design Principles (OODP) and SOLID compliance for the SC2002 module.

## Introduction

In this game, a player selects a character class (Warrior or Wizard) and fights through waves of enemies using a combination of basic attacks, special skills, items, and status effects. The game features multiple difficulty levels, each with unique enemy compositions and backup spawn mechanics that increase the challenge as the battle progresses.

- **Players**: Warrior, Wizard
- **Enemies**: Goblin, Wolf
- **Difficulty Levels**: Easy, Medium, Hard (with backup spawn waves)
- **Win Condition**: All enemies defeated
- **Lose Condition**: Player HP reaches 0

## How to Launch

### Prerequisites

- Java JDK 17 or above installed
- Terminal / Command Prompt

### Compile and Run

From the project root directory:

```bash
# Compile all source files
javac -d out src/main/java/com/arena/**/*.java src/main/java/com/arena/*.java

# Run the game
java -cp out com.arena.App
```

### Using an IDE (IntelliJ / Eclipse / VS Code)

1. Open the project folder in your IDE.
2. Mark `src/main/java` as the sources root.
3. Run `com.arena.App` (the `main` method in `App.java`).

## Design Architecture

The project follows the **Boundary-Control-Entity (BCE)** architectural pattern, separating concerns into three layers:

| Layer | Package | Responsibility |
|-------|---------|----------------|
| **Boundary** | `ui/` | Handles all user I/O (`ConsoleView`) through the `GameView` interface |
| **Control** | `engine/` | Orchestrates game flow, battle rounds, turn ordering, and win/loss logic |
| **Entity** | `model/` | Represents domain objects — combatants, items, status effects, levels |

Supporting packages:
- `action/` — Encapsulates player and enemy actions as command objects
- `strategy/` — Pluggable algorithms for turn ordering and enemy AI
- `factory/` — Centralizes object creation for combatants, items, and levels

### Source Structure

```
src/main/java/com/arena/
├── App.java                              // Entry point
├── model/
│   ├── combatant/                        // Entity: Combatant hierarchy
│   │   ├── Combatant.java                // Abstract base (HP, ATK, DEF, SPD, effects)
│   │   ├── Player.java                   // Abstract (inventory, cooldown)
│   │   ├── Warrior.java                  // HP:260 ATK:40 DEF:20 SPD:30, Shield Bash
│   │   ├── Wizard.java                   // HP:200 ATK:50 DEF:10 SPD:20, Arcane Blast
│   │   ├── Enemy.java                    // Abstract (EnemyBehavior strategy)
│   │   ├── Goblin.java                   // HP:55 ATK:35 DEF:15 SPD:25
│   │   └── Wolf.java                     // HP:40 ATK:45 DEF:5 SPD:35
│   ├── effect/                           // Entity: Status effects
│   │   ├── StatusEffect.java             // Interface (default methods for ISP)
│   │   ├── StunEffect.java
│   │   ├── DefendEffect.java
│   │   ├── SmokeBombEffect.java
│   │   └── ArcaneBlastAtkBuff.java
│   ├── item/                             // Entity: Items
│   │   ├── Item.java                     // Interface
│   │   ├── Potion.java                   // Heal 100 HP
│   │   ├── PowerStone.java              // Free special skill (no cooldown change)
│   │   └── SmokeBomb.java               // Invulnerability current + next turn
│   └── level/                            // Entity: Level definitions
│       ├── Level.java                    // Interface
│       ├── EasyLevel.java               // 3 Goblins
│       ├── MediumLevel.java             // 1G+1W, backup 2W
│       └── HardLevel.java              // 2G, backup 1G+2W
├── action/                               // Command pattern: Actions
│   ├── Action.java                       // Interface
│   ├── ActionResult.java                // Result data class
│   ├── BasicAttack.java                 // dmg = max(0, ATK-DEF)
│   ├── DefendAction.java               // +10 DEF for 2 turns
│   ├── UseItemAction.java
│   └── SpecialSkillAction.java
├── engine/                               // Control layer
│   ├── BattleContext.java               // Shared battle state
│   ├── BattleResult.java               // End-of-battle stats
│   ├── BattleEngine.java               // Core: round loop, win/loss, backup spawn
│   ├── TurnManager.java                // Turn ordering + round tracking
│   └── GameController.java             // Game flow: screens, replay loop
├── strategy/                             // Strategy pattern
│   ├── turn/
│   │   ├── TurnOrderStrategy.java       // Interface
│   │   └── SpeedBasedTurnOrder.java    // Sort by SPD descending
│   └── enemy/
│       ├── EnemyBehavior.java           // Interface
│       └── BasicAttackBehavior.java     // Always BasicAttack player
├── factory/
│   ├── CombatantFactory.java
│   ├── ItemFactory.java
│   └── LevelFactory.java
└── ui/                                   // Boundary layer
    ├── GameView.java                    // Interface (DIP: engine depends on this)
    └── ConsoleView.java                 // CLI implementation (Scanner + System.out)
```

## Design Patterns

| Pattern | Usage |
|---------|-------|
| **Strategy** | `TurnOrderStrategy`, `EnemyBehavior` — pluggable turn ordering and enemy AI |
| **Command** | `Action` interface + implementations — encapsulate player/enemy actions as objects |
| **Factory** | `CombatantFactory`, `ItemFactory`, `LevelFactory` — decouple object creation from usage |
| **Template Method** | `Combatant` stat/effect lifecycle — common flow with class-specific override points |

## SOLID Principles

| Principle | How It Is Applied |
|-----------|-------------------|
| **SRP** | Each class has one responsibility (e.g., `Combatant` manages stats, `BattleEngine` orchestrates turns) |
| **OCP** | New actions or effects = new class implementing the interface. `BattleEngine` remains untouched |
| **LSP** | `Warrior`/`Wizard` are interchangeable as `Player`; `Goblin`/`Wolf` as `Enemy`; all as `Combatant` |
| **ISP** | `StatusEffect` uses Java default methods — implementors override only the modifiers they need |
| **DIP** | `BattleEngine` depends on abstractions (`TurnOrderStrategy`, `GameView`, `EnemyBehavior`), not concrete classes |

## Game Mechanics

### Combatants

| Character | HP | ATK | DEF | SPD | Special |
|-----------|-----|-----|-----|-----|---------|
| Warrior | 260 | 40 | 20 | 30 | Shield Bash: BasicAttack dmg + stun (2 turns) |
| Wizard | 200 | 50 | 10 | 20 | Arcane Blast: BasicAttack dmg to ALL enemies, +10 ATK per kill |
| Goblin | 55 | 35 | 15 | 25 | BasicAttack only |
| Wolf | 40 | 45 | 5 | 35 | BasicAttack only |

### Actions (one per turn)

- **BasicAttack**: `dmg = max(0, ATK - target DEF)`
- **Defend**: +10 DEF for current + next round
- **Item**: Use a consumable item
- **SpecialSkill**: Class-specific ability, 3-turn cooldown

### Items (2 chosen at game start, single-use)

- **Potion**: Heal 100 HP (capped at max)
- **Power Stone**: Trigger special skill without affecting cooldown
- **Smoke Bomb**: Enemy attacks deal 0 damage for current + next turn

### Difficulty Levels

- **Easy**: 3 Goblins
- **Medium**: 1 Goblin + 1 Wolf, backup spawn of 2 Wolves
- **Hard**: 2 Goblins, backup spawn of 1 Goblin + 2 Wolves
