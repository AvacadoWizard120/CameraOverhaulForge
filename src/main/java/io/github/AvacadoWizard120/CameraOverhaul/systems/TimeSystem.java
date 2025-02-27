package io.github.AvacadoWizard120.CameraOverhaul.systems;

public final class TimeSystem
{
    private static long previous;
    private static long current;
    private static double deltaTime;
    private static double accumulatedTime;

    public static double getTime() {
        return accumulatedTime;
    }

    public static double getDeltaTime() {
        return deltaTime;
    }

    public static void update() {
        previous = current;
        current = System.nanoTime();

        if (previous <= 0d) {
            previous = current - 1;
        }

        deltaTime = (current - previous) / 1_000_000_000d;
        accumulatedTime += deltaTime;
    }
}
