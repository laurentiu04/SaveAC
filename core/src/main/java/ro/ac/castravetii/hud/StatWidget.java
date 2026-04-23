package ro.ac.castravetii.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import ro.ac.castravetii.Services;

public class StatWidget extends Stack{
    private final Label lvl;
    private final Image icon;
    private final Image upgradeButton;

    public StatWidget(Drawable icon, Drawable button) {
        this.icon = new Image(icon);
        this.upgradeButton = new Image(button);
        this.upgradeButton.pack();

        Label.LabelStyle style = new Label.LabelStyle(Services.font15, Color.WHITE);
        lvl = new Label("20", style);

        this.add(this.icon);
        this.add(
            new Container<>(upgradeButton)
            .align(Align.top + Align.center)
            .padTop(-upgradeButton.getImageHeight()/2)
        );
        this.add(new Container<>(lvl).align(Align.bottom).padBottom(2).padLeft(2));
    }

    public void update(int level) {
        lvl.setText(level);
    }
}
