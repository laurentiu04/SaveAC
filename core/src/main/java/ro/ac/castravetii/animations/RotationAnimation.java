package ro.ac.castravetii.animations;

import ro.ac.castravetii.components.TransformComponent;

public class RotationAnimation extends Animation<TransformComponent> {

    private final float startValue;
    private final float diff;

    public RotationAnimation(TransformComponent element, float startValue, float endValue, float duration, CubicBezier bezier) {
        super(element, duration, bezier);

        this.startValue = element.rotation + startValue;
        float endVal = element.rotation + endValue;

        this.diff = endVal - startValue;
    }

    @Override
    public void updateElement(float alpha) {
        element.rotation = startValue + diff * alpha;
    }

    @Override
    public void reset() {
        element.rotation = startValue;
    }
}
