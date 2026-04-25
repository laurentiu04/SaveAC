package ro.ac.castravetii;

import com.badlogic.gdx.Game;
import ro.ac.castravetii.events.GameEventQueue;
import ro.ac.castravetii.screens.GameScreen;
import ro.ac.castravetii.systems.*;

public class Core extends Game {

    @Override
    public void create() {

        GameEventQueue queue = new GameEventQueue();

        // ---------------- SETUP INITAL ---------------- //
        Services.init();

        Services.engine.addSystem(new RenderSystem()); // Render system pentru entitati
        Services.engine.addSystem(new MovementSystem(2));
        Services.engine.addSystem(new AnimationControlSystem());
        Services.engine.addSystem(new HitboxSystem());
        Services.engine.addSystem(new HealthbarSystem());
        Services.engine.addSystem(new LevelSystem(queue));
        // ----------------------------------------------- //



        this.setScreen(new GameScreen(this, queue));

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
