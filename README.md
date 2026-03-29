# SC2002 Turn-Based Combat Arena (Java, OODP + SOLID)

A CLI-based turn-based combat arena game built with Java, demonstrating Object-Oriented Design Principles and SOLID compliance.

## Game Overview

A player selects a character class and fights waves of enemies using Actions, Items, and Status Effects across multiple difficulty levels.

- **Players**: Warrior, Wizard
- **Enemies**: Goblin, Wolf
- **Levels**: Easy, Medium, Hard (with backup spawn waves)
- **Win**: All enemies defeated | **Lose**: Player HP reaches 0

## Architecture

Follows the **Boundary-Control-Entity (BCE)** pattern.

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
| **Strategy** | TurnOrderStrategy, EnemyBehavior — extensible turn ordering and enemy AI |
| **Command** | Action interface + implementations — encapsulate actions as objects |
| **Factory** | CombatantFactory, ItemFactory, LevelFactory — decouple creation from usage |
| **Template Method** | Combatant stat/effect lifecycle — common flow with override points |

## SOLID Principles

- **SRP**: Each class has one responsibility (e.g., Combatant manages stats, BattleEngine orchestrates turns)
- **OCP**: New Action/StatusEffect = new class implementing interface. BattleEngine untouched.
- **LSP**: Warrior/Wizard interchangeable as Player; Goblin/Wolf as Enemy; all as Combatant
- **ISP**: StatusEffect uses Java default methods — implementors override only relevant modifiers
- **DIP**: BattleEngine depends on TurnOrderStrategy, GameView, EnemyBehavior interfaces (abstractions, not concretions)

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
- **Medium**: 1 Goblin + 1 Wolf, backup spawn 2 Wolves
- **Hard**: 2 Goblins, backup spawn 1 Goblin + 2 Wolves

## Implementation Phases

1. **Core Model** — Combatant hierarchy, status effects, items
2. **Actions & Strategies** — Action commands, turn order, enemy behavior
3. **Levels & Factories** — Level definitions, object factories
4. **Battle Engine** — Round loop, win/loss logic, backup spawning
5. **CLI UI** — GameView interface + ConsoleView implementation
6. **Integration & Polish** — End-to-end testing against assignment examples
