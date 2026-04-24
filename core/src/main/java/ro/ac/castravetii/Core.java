package ro.ac.castravetii;

import com.badlogic.gdx.Game;
import ro.ac.castravetii.screens.MainMenuScreen;
import ro.ac.castravetii.systems.*;

public class Core extends Game {

    @Override
    public void create() {

        // >>>>>>>>>>>>>>>>>>>>>> SETUP INITAL <<<<<<<<<<<<<<<<<<<<<<<< //
        Services.init();

        Services.engine.addSystem(new RenderSystem()); // Render system pentru entitati
        Services.engine.addSystem(new MovementSystem(2));
        Services.engine.addSystem(new AnimationControlSystem());
        Services.engine.addSystem(new HitboxSystem());
        Services.engine.addSystem(new HealthbarSystem());

        // ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ //



        this.setScreen(new MainMenuScreen(this));

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
