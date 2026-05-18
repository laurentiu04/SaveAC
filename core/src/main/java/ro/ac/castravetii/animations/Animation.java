package ro.ac.castravetii.animations;

import java.util.ArrayDeque;

/**
 * An abstract class for creating new types of animations.
 * @param <T> The type of the element to be animated.
 */
abstract class Animation<T> {

    /**
     * Collection of all the active animations. It's used by the {@link AnimationController#update(float delta)} to update the animations.
     */
    public static ArrayDeque<Animation> collection = new ArrayDeque<>();

    protected final T element;
    protected float duration;
    protected float delay = 0f;
    protected final CubicBezier bezier;
    protected boolean playing = false;
    protected float elapsedTime = 0;
    protected FillMode fillMode = FillMode.NONE;
    protected PlayMode playMode = PlayMode.NORMAL;

    public Animation(T element, float duration, CubicBezier bezier) {
        this.element = element;
        this.duration = duration;
        this.bezier = bezier;

        collection.add(this);
    }

    public void play(){
        this.playing = true;
    }

    public void end() {
        this.playing = false;

        if (fillMode == FillMode.NONE) {
            this.reset();
        }

        elapsedTime = 0;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public void setPlayMode(PlayMode mode) {
        this.playMode = mode;
    }

    public void setFillMode(FillMode mode) {
        this.fillMode = mode;
    }

    public void setDelay(float delay) {
        this.delay = delay;

    }

    /**
     * Updates the animated element based on the {@code PlayMode}.
     * @param deltaTime The time passed since last frame in seconds.
     * @see PlayMode
     */
    public void update(float deltaTime){
        elapsedTime += deltaTime;

        if (delay > 0f && elapsedTime < delay ) {
            return;
        }

        float progress = (elapsedTime - delay) / duration;

        if (progress >= 1f) {
            switch (this.playMode){
                case NORMAL, REVERSE, ALTERNATE, ALTERNATE_REVERSE -> {
                    this.end();
                    return;
                }

                case LOOP, LOOP_REVERSE, PING_PONG -> {
                    elapsedTime = delay;
                    progress -= 1f;
                }
            }
        }

        float alpha;

        switch (this.playMode) {
            case NORMAL, LOOP -> {
                alpha = bezier.evaluate(progress);
            }

            case REVERSE, LOOP_REVERSE -> {
                alpha = bezier.evaluate(1f - progress);
            }

            case ALTERNATE, PING_PONG -> {
                alpha = progress <= 0.5f ? bezier.evaluate(progress * 2f) : bezier.evaluate(1f - (progress - 0.5f) / 0.5f);
            }

            case ALTERNATE_REVERSE -> {
                alpha = progress < 0.5f ? bezier.evaluate(1f - progress * 2f) : bezier.evaluate((progress - 0.5f) / 0.5f);
            }

            default -> {
                System.out.println("Default case triggered.");
                this.end();
                return;
            }
        }

        updateElement(alpha);
    }

    /**
     * Override this function to define how the element should be updated.
     * @param alpha The value returned by the bezier curve at any given time in the animation.
     */
    public abstract void updateElement(float alpha);

    /**
     * Override this function to define how the element should be reset.
     */
    public abstract void reset();
}

