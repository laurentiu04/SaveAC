package ro.ac.castravetii.animations;

import ro.ac.castravetii.components.TextureComponent;

/**
 * An animation class to animate the opacity of a {@code TextureComponent}.
 */
public class OpacityAnimation extends Animation<TextureComponent> {

    /**
     * The start value of the animation.
     */
    private final float startValue;

    /**
     *
     */
    private final float diff;

    public OpacityAnimation(TextureComponent element, float startValue, float endValue, float duration, CubicBezier bezier) {
        super(element, duration, bezier);

        this.startValue = startValue;

        if (endValue > 1f) {
            System.out.println("[OpacityAnimation@]: End value greater than 1f!");
            Thread.dumpStack();
            this.diff = 1f - startValue;
        } else
            this.diff = endValue - startValue;
    }

    @Override
    public void updateElement(float alpha) {
        element.opacity = startValue + diff * alpha;
    }

    @Override
    public void reset() {
        element.opacity = startValue;
    }
}
