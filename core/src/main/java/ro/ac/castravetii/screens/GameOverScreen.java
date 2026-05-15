package ro.ac.castravetii.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import ro.ac.castravetii.Services;
import ro.ac.castravetii.events.GameEventQueue;

public class GameOverScreen extends ScreenAdapter {
    private final Game game;
    private final GameEventQueue queue;
    private Stage stage;
    private final int finalScore;

    public GameOverScreen(Game game, GameEventQueue queue, int score) {
        this.game = game;
        this.queue = queue;
        this.finalScore = score;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);


        // Titlu Game Over
        Label gameOverLabel = new Label("GAME OVER", Services.skin);
        gameOverLabel.setFontScale(2f);

        // Afișare Scor
        Label scoreLabel = new Label("Score: " + finalScore, Services.skin);

        // Buton Retry
        TextButton retryButton = new TextButton("RETRY", Services.skin);
        retryButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game, queue)); // Reîncepe jocul
            }
        });

        // Buton Main Menu
        TextButton menuButton = new TextButton("MAIN MENU", Services.skin);
        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game, queue)); // Întoarcere la meniu
            }
        });

        // Organizarea în tabel
        table.add(gameOverLabel).padBottom(20).row();
        table.add(scoreLabel).padBottom(40).row();
        table.add(retryButton).width(200).height(60).padBottom(10).row();
        table.add(menuButton).width(200).height(60);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1); // Fundal negru pentru Game Over
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
