package ro.ac.castravetii.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
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

        BitmapFont font = new BitmapFont();
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = font;
        buttonStyle.fontColor = Color.WHITE;

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.DARK_GRAY);
        pixmap.fill();
        buttonStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));

        Pixmap downPixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        downPixmap.setColor(Color.LIGHT_GRAY);
        downPixmap.fill();
        buttonStyle.down = new TextureRegionDrawable(new TextureRegion(new Texture(downPixmap)));

        TextButton resumeButton = new TextButton("Resume", buttonStyle);
        TextButton quitButton = new TextButton("Quit", buttonStyle);

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

        table.add(resumeButton).width(200).height(50).padBottom(20).row();
        table.add(quitButton).width(200).height(50);

        stage.addActor(table);
    }


    public Stage getStage() {
        return stage;
    }


    public void dispose() {
        stage.dispose();
    }
}
