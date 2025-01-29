package io.github.AvacadoWizard120.CameraOverhaul.abstractions;

import net.minecraft.client.renderer.GlStateManager;
import org.joml.Vector3f;

public final class MathAbstractions
{
    public static void rotateMatrixByAxis(double axisX, double axisY, double axisZ, double rotation) {
        Vector3f axis = new Vector3f((float)axisX, (float)axisY, (float)axisZ);
        GlStateManager.rotate((float)rotation, axis.x, axis.y, axis.z);
    }
}
