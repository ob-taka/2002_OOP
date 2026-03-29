package com.arena;

import com.arena.engine.GameController;
import com.arena.ui.ConsoleView;
import com.arena.ui.GameView;

public class App {
    public static void main(String[] args) {
        GameView view = new ConsoleView();
        GameController controller = new GameController(view);
        controller.run();
    }
}
