package ro.ac.castravetii.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import ro.ac.castravetii.events.GameEventQueue;

public class MainMenuScreen implements Screen {

    private final Game game;
    private Stage stage;
    private Skin skin; // Adăugat pentru design-ul butoanelor
    private final GameEventQueue queue;

    public MainMenuScreen(Game game, GameEventQueue queue) {
        this.game = game;
        this.queue = queue;
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f));
        Gdx.input.setInputProcessor(stage);

        createBasicSkin();

        Table table = new Table(); // Table to center the elements
        table.setFillParent(true); // Filling full screen
        stage.addActor(table);
        Label titleLabel = new Label("SaveAC", skin); // Game name
        TextButton playButton = new TextButton("Joaca", skin); // Play button
        TextButton exitButton = new TextButton("Iesire", skin); // Exit button

        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Going from MainMenuScreen to GameScreen
                game.setScreen(new GameScreen(game, queue));
            }
        });

        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit(); // Exit app
            }
        });


        table.add(titleLabel).padBottom(30).row(); // Adding title
        table.add(playButton).width(150).height(40).padBottom(10).row(); // Adding play button
        table.add(exitButton).width(150).height(40); // Adding exit button
    }

    private void createBasicSkin() {
        skin = new Skin();

        BitmapFont font = new BitmapFont();
        skin.add("default", font);

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888); // Creating a pixmap
        pixmap.setColor(Color.WHITE); // Set to white
        pixmap.fill(); // filling pixmap with white
        skin.add("background", new Texture(pixmap)); // background has white color

        // Label style
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.BLACK;
        skin.add("default", labelStyle);

        // Buttons style
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("background", Color.DARK_GRAY);
        textButtonStyle.down = skin.newDrawable("background", Color.LIGHT_GRAY);
        textButtonStyle.font = font;
        skin.add("default", textButtonStyle);
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
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

    @Override
    public void dispose() {
        stage.dispose();
        if (skin != null) skin.dispose(); // Cleaning memory
    }
}
