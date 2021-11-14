package com.exformat.tz;

import com.badlogic.gdx.Game;
import com.exformat.tz.screens.LoadingScreen;

public class TestGame extends Game {
    @Override
    public void create() {
        setScreen(new LoadingScreen(this));
    }
}
