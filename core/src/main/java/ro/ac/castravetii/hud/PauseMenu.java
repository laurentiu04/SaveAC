package ro.ac.castravetii.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ray3k.tenpatch.TenPatchDrawable;
import ro.ac.castravetii.Services;
import ro.ac.castravetii.screens.GameScreen;

/**
 * Am creat meniul de pauza cu 2 butoane : RESUME si QUIT
 */

public class PauseMenu {
    private final Stage stage;
    private final GameScreen gameScreen;

    public PauseMenu(GameScreen screen) {
        this.gameScreen = screen;
        this.stage = new Stage(new ScreenViewport());

        Button resumeButton = new Button(Services.skin, "resume");
        Button quitButton = new Button(Services.skin, "exit");

        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameScreen.resumeGame();
            }
        });

        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        Texture pixel = new Texture(pixmap);
        pixmap.dispose();

        Image overlay = new Image(pixel);
        overlay.setFillParent(true);
        overlay.setColor(0, 0, 0, 0.3f);

        Table table = new Table();
        table.center();
        table.setFillParent(true);
        stage.addActor(overlay);

        table.add(resumeButton).padBottom(20).row();
        table.add(quitButton);

        stage.addActor(table);
    }


    public Stage getStage() {
        return stage;
    }


    public void dispose() {
        stage.dispose();
    }
}
