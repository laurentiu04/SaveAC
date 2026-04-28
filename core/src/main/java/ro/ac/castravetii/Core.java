package ro.ac.castravetii;

import com.badlogic.gdx.Game;
import ro.ac.castravetii.events.GameEventQueue;
import ro.ac.castravetii.screens.MainMenuScreen;

public class Core extends Game {

    @Override
    public void create() {

        GameEventQueue queue = new GameEventQueue();

        Services.init();

        this.setScreen(new MainMenuScreen(this, queue));

    }

    @Override
    public void render() {

        super.render();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
        public void dispose() {
        // Fac dispose la tot ce am creat, ii gen delete() din C
        Services.font20.dispose();
    }

}
