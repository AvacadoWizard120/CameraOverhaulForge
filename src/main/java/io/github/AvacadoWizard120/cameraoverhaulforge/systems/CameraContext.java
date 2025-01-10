package io.github.AvacadoWizard120.cameraoverhaulforge.systems;

import io.github.AvacadoWizard120.cameraoverhaulforge.utils.Transform;
import io.github.AvacadoWizard120.cameraoverhaulforge.utils.VectorUtils;
import org.joml.Vector2d;
import org.joml.Vector3d;

public class CameraContext
{
    public enum Perspective {
        FIRST_PERSON,
        THIRD_PERSON,
        THIRD_PERSON_REVERSE,
    }

    public boolean isSwimming;
    public boolean isFlying;
    public boolean isSprinting;
    public boolean isRiding;
    public boolean isRidingMount;
    public boolean isRidingVehicle;
    public Vector3d velocity;
    public Perspective perspective;
    public Transform transform = new Transform();

    public Vector3d getForwardRelativeVelocity() {
        var temp = VectorUtils.rotate(new Vector2d(velocity.x, velocity.z), 360d - transform.eulerRot.y);
        return new Vector3d(temp.x, velocity.y, temp.y);
    }
}
