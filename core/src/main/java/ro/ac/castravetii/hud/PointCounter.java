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
    private final Image imageEmpty;
    private final Image image;
    private final Container<Label> label;

    public PointCounter(Drawable emptyImage, Drawable image) {
        this.image = new Image(image);
        this.imageEmpty = new Image(emptyImage);

        Label.LabelStyle style = new Label.LabelStyle(Services.font20, Color.WHITE);
        label = new Container<>(new Label("", style)).align(Align.center).padLeft(35).padBottom(4);
        label.pack();
        label.align(Align.center);

        this.add(this.imageEmpty);
        this.add(label);
    }

    public void update(int value) {
        if (value > 0) {
            this.clearChildren();
            this.add(image);
            this.add(label);
        } else if (value == 0) {
            this.clearChildren();
            this.add(imageEmpty);
            this.add(label);
        }

        label.getActor().setText(value);
    }
}
