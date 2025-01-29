package io.github.AvacadoWizard120.CameraOverhaul.utils;

import org.joml.Vector3d;

public class Transform
{
    public Vector3d position;
    public Vector3d eulerRot;

    public Transform() {
        this.position = new Vector3d(0, 0, 0);
        this.eulerRot = new Vector3d(0, 0, 0);
    }

    public Transform(Vector3d position, Vector3d eulerRot) {
        this.position = position;
        this.eulerRot = eulerRot;
    }
}
