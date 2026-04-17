package ro.ac.castravetii;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;

public class AttributeWidget extends Stack{
    Label lvl;
    Image icon;

    public AttributeWidget(Drawable drawable) {
        this.icon = new Image(drawable);

        Label.LabelStyle style = new Label.LabelStyle(Services.font15, Color.WHITE);
        lvl = new Label("20", style);

        this.add(icon);
        this.add(new Container<>(lvl).align(Align.bottom).padBottom(4).padLeft(2));
    }
}
