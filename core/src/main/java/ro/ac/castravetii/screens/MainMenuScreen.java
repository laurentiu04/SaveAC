package ro.ac.castravetii.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import ro.ac.castravetii.Services;
import ro.ac.castravetii.events.GameEventQueue;

public class MainMenuScreen extends ScreenAdapter {

    private final Game game;
    private Stage stage;
    private final GameEventQueue queue;

    private Music music;

    public MainMenuScreen(Game game, GameEventQueue queue) {
        this.game = game;
        this.queue = queue;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        music = Gdx.audio.newMusic(
            Gdx.files.internal("music/realgone.mp3")
        );

        music.setLooping(true);
        music.setVolume(1f);
        music.play();

        Table table = new Table(); // Table to center the elements
        table.setFillParent(true); // Filling full screen
        stage.addActor(table);

        Image titleImage = new Image(Services.skin.getDrawable("Logo")); // Game name

        TextButton playButton = new TextButton("PLAY", Services.skin); // Play button
        playButton.setSize(170, 70);

        TextButton exitButton = new TextButton("QUIT", Services.skin); // Exit button
        exitButton.setSize(170, 70);

        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Going from MainMenuScreen to GameScreen
                music.stop(); //opresc muzica

                game.setScreen(new GameScreen(game, queue));
            }
        });

        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit(); // Exit app
            }
        });

        titleImage.pack();

        table.pad(50);
        table.add(titleImage).width(titleImage.getImageWidth()*2).height(titleImage.getImageHeight()*2).space(50).grow().top().row(); // Adding title
        VerticalGroup buttons = new VerticalGroup();
        buttons.space(10);
//        buttons.setFillParent(true);
        buttons.align(Align.right | Align.center);
        buttons.addActor(new Container<>(playButton).width(170).height(70)); // Adding play button
        buttons.addActor(new Container<>(exitButton).width(170).height(70));// Adding exit button
        table.add(buttons).center();
//        stage.setDebugAll(true);
    }

    @Override
    public void render(float delta) {
        // Set background on light blue
        Gdx.gl.glClearColor(0.53f, 0.81f, 0.92f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update and draw UI
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
