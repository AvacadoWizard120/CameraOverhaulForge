// Copyright 2020-2025 Mirsario & Contributors.
// Released under the GNU General Public License 3.0.

package io.github.AvacadoWizard120.CameraOverhaul.systems;

import io.github.AvacadoWizard120.CameraOverhaul.utils.Transform;
import io.github.AvacadoWizard120.CameraOverhaul.utils.VectorUtils;
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
        Vector2d temp = VectorUtils.rotate(new Vector2d(velocity.x, velocity.z), 360d - transform.eulerRot.y);
        return new Vector3d(temp.x, velocity.y, temp.y);
    }
}
