package ro.ac.castravetii.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import ro.ac.castravetii.Services;

public class PointCounter extends Stack {
    private final Image image;
    private final Label label;

    public PointCounter(Drawable drawable) {
        image = new Image(drawable);

        Label.LabelStyle style = new Label.LabelStyle(Services.font20, Color.WHITE);
        label = new Label("", style);
        label.pack();
        label.setAlignment(Align.center);

        this.add(image);
        this.add(new Container<>(label).align(Align.center).padLeft(35).padBottom(4));
    }

    public void update(int value) {
        label.setText(value);
    }
}
