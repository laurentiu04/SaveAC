/**
 * Cubic Bézier curve evaluator for animation easing.
 *
 * Models the CSS cubic-bezier(x1, y1, x2, y2) convention:
 *  - P0 = (0, 0) and P3 = (1, 1) are fixed endpoints.
 *  - P1 = (x1, y1) and P2 = (x2, y2) are the control points.
 *
 * Usage:
 *   CubicBezier ease = CubicBezier.EASE_IN_OUT;
 *   float value = ease.evaluate(t);  // t in [0, 1]
 */

// Generat cu Claude AI.

package ro.ac.castravetii.animations;

public class CubicBezier {

    // ── Presets ────────────────────────────────────────────────────────────────

    public static final CubicBezier LINEAR       = new CubicBezier(0.00f, 0.00f, 1.00f, 1.00f);
    public static final CubicBezier EASE         = new CubicBezier(0.25f, 0.10f, 0.25f, 1.00f);
    public static final CubicBezier EASE_IN      = new CubicBezier(0.42f, 0.00f, 1.00f, 1.00f);
    public static final CubicBezier EASE_OUT     = new CubicBezier(0.00f, 0.00f, 0.58f, 1.00f);
    public static final CubicBezier EASE_IN_OUT  = new CubicBezier(0.42f, 0.00f, 0.58f, 1.00f);
    public static final CubicBezier BOUNCE_OUT   = new CubicBezier(0.34f, 1.56f, 0.64f, 1.00f); // overshoot spring

    // ── State ──────────────────────────────────────────────────────────────────

    private final float x1, y1, x2, y2;

    // Newton's method precision
    private static final int    NEWTON_ITERATIONS = 8;
    private static final float  NEWTON_MIN_SLOPE  = 0.001f;

    // Subdivision fallback precision
    private static final int    SUBDIVIDE_STEPS   = 10;

    // Sample table for initial guess (avoids full Newton on every call)
    private static final int TABLE_SIZE = 11;
    private final float[] sampleTable = new float[TABLE_SIZE];

    // ── Constructor ────────────────────────────────────────────────────────────

    public CubicBezier(float x1, float y1, float x2, float y2) {
        if (x1 < 0 || x1 > 1 || x2 < 0 || x2 > 1)
            throw new IllegalArgumentException("Control point X values must be in [0, 1]");
        this.x1 = x1; this.y1 = y1;
        this.x2 = x2; this.y2 = y2;
        buildSampleTable();
    }

    // ── Public API ─────────────────────────────────────────────────────────────

    /**
     * Evaluates the Bézier curve at progress {@code t}.
     *
     * @param t normalised time, clamped to [0, 1]
     * @return eased value in [0, 1] (may overshoot if control points allow it)
     */
    public float evaluate(float t) {
        t = Math.clamp(t, 0f, 1f);
        if (t == 0f || t == 1f) return t;
        // Linear shortcut
        if (x1 == y1 && x2 == y2) return t;
        return calcY(getTForX(t));
    }

    // ── Core math ──────────────────────────────────────────────────────────────

    /** Bézier basis: one component (x or y) given parameter u. */
    private float calcBezier(float u, float a1, float a2) {
        // Expanded form: 3*a1*u*(1-u)^2 + 3*a2*u^2*(1-u) + u^3
        return ((A(a1, a2) * u + B(a1, a2)) * u + C(a1)) * u;
    }

    private float calcY(float u)          { return calcBezier(u, y1, y2); }
    private float calcX(float u)          { return calcBezier(u, x1, x2); }
    private float calcXSlope(float u)     { return 3f * A(x1, x2) * u * u + 2f * B(x1, x2) * u + C(x1); }

    private static float A(float a1, float a2) { return 1f - 3f * a2 + 3f * a1; }
    private static float B(float a1, float a2) { return 3f * a2 - 6f * a1; }
    private static float C(float a1)            { return 3f * a1; }

    /** Precompute evenly-spaced X samples for fast initial guess. */
    private void buildSampleTable() {
        for (int i = 0; i < TABLE_SIZE; i++)
            sampleTable[i] = calcX(i / (float)(TABLE_SIZE - 1));
    }

    /**
     * Inverts X → finds the Bézier parameter u such that calcX(u) ≈ x.
     * Uses a lookup table + Newton refinement, falling back to bisection.
     */
    private float getTForX(float x) {
        // 1. Locate the interval in the sample table.
        int   interval      = 0;
        float intervalWidth = 1f / (TABLE_SIZE - 1);

        for (int i = 1; i < TABLE_SIZE - 1; i++) {
            if (sampleTable[i] <= x) interval = i;
            else break;
        }

        // 2. Linear interpolation for initial guess.
        float dist = (x - sampleTable[interval]) /
                     (sampleTable[interval + 1] - sampleTable[interval]);
        float u = (interval + dist) * intervalWidth;

        // 3. Newton–Raphson refinement (fast path).
        float slope = calcXSlope(u);
        if (slope >= NEWTON_MIN_SLOPE) {
            for (int i = 0; i < NEWTON_ITERATIONS; i++) {
                float error = calcX(u) - x;
                if (Math.abs(error) < 1e-7f) break;
                u -= error / calcXSlope(u);
            }
            return u;
        }

        // 4. Bisection fallback (handles flat tangents).
        float lo = interval * intervalWidth;
        float hi = lo + intervalWidth;
        for (int i = 0; i < SUBDIVIDE_STEPS; i++) {
            float mid   = (lo + hi) / 2f;
            float error = calcX(mid) - x;
            if (Math.abs(error) < 1e-7f) return mid;
            if (error < 0f) lo = mid; else hi = mid;
        }
        return (lo + hi) / 2f;
    }
}
