package io.github.AvacadoWizard120.cameraoverhaulforge.utils;

public final class MathUtils
{
    // Clamp

    public static float clamp(float value, float min, float max) {
        return value < min ? min : (Math.min(value, max));
    }
    public static double clamp(double value, double min, double max) {
        return value < min ? min : (Math.min(value, max));
    }
    public static float clamp01(float value) {
        return value < 0f ? 0f : (Math.min(value, 1f));
    }
    public static double clamp01(double value) {
        return value < 0d ? 0d : (Math.min(value, 1d));
    }

    // Lerp

    public static float lerp(float a, float b, float time) {
        return a + (b - a) * clamp01(time);
    }
    public static double lerp(double a, double b, double time) {
        return a + (b - a) * clamp01(time);
    }

    // Framerate-agnostic smoothing.
    // https://www.rorydriscoll.com/2016/03/07/frame-rate-independent-damping-using-lerp

    public static float damp(float source, float destination, float smoothing, float dt) {
        return lerp(source, destination, 1f - (float)Math.pow(smoothing * smoothing, dt));
    }
    public static double damp(double source, double destination, double smoothing, double dt) {
        return lerp(source, destination, 1d - Math.pow(smoothing * smoothing, dt));
    }

    // Step towards

    public static double stepTowards(double current, double target, double step) {
        if (current < target) {
            return Math.min(current + step, target);
        } else if (current > target) {
            return Math.max(current - step, target);
        }

        return current;
    }
}
