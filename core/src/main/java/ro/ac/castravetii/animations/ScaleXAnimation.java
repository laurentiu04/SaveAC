package ro.ac.castravetii.animations;

import ro.ac.castravetii.components.TransformComponent;

public class ScaleXAnimation extends Animation<TransformComponent>{

    private float startValue;
    private float delta;

    public ScaleXAnimation(TransformComponent element, float startValue, float endValue, float duration, CubicBezier bezier) {
        super(element, duration, bezier);

        this.startValue = startValue;
        this.delta = endValue - startValue;
    }

    @Override
    public void updateElement(float alpha) {
        element.scale.x = startValue + delta * alpha;
    }

    public void setValues(float startValue, float endValue) {
        this.startValue = startValue;
        this.delta = endValue - startValue;
    }

    @Override
    public void reset() {
        element.scale.set(1f, 1f);
    }
}
