package ro.ac.castravetii.animations;

/**
 * Play modes for animations inspired by CSS.
 */
public enum PlayMode {
    /**
     * Loops the animation from <b>0%</b> to <b>100%</b>.
     */
    LOOP,
    /**
     * Loops the animation from <b>100%</b> to <b>0%</b>.
     */
    LOOP_REVERSE,
    /**
     * Loops the animation from <b>0%</b> to <b>100%</b> and back to <b>0%</b>.
     */
    PING_PONG,
    /**
     * Loops the animation from <b>100%</b> to <b>0%</b> and back to <b>100%</b>.
     */
    PING_PONG_REVERSED,
    /**
     * No looping, the animation is played <b>once</b>.
     */
    NORMAL,
    /**
     * No looping, the animation is played <b>once</b> but <b>reversed</b>.
     */
    REVERSE,
    /**
     * No looping, the animation goes from <b>0%</b> to <b>100%</b> and back to <b>0%</b>. So it's a {@code PING_PONG} without looping.
     */
    ALTERNATE,
    /**
     * No looping, the animation goes from <b>100%</b> to <b>0%</b> and back to <b>100%</b>. So it's a {@code PING_PONG_REVERSED} without looping.
     */
    ALTERNATE_REVERSE
}
