package io.github.avacadowizard120.cameraoverhaulneoforge.common.systems;

import io.github.avacadowizard120.cameraoverhaulneoforge.CameraOverhaulNeoForge;
import io.github.avacadowizard120.cameraoverhaulneoforge.common.configuration.ConfigData;
import io.github.avacadowizard120.cameraoverhaulneoforge.core.callbacks.CameraUpdateCallback;
import io.github.avacadowizard120.cameraoverhaulneoforge.core.callbacks.ModifyCameraTransformCallback;
import io.github.avacadowizard120.cameraoverhaulneoforge.core.structures.Transform;
import io.github.avacadowizard120.cameraoverhaulneoforge.core.utils.MathUtils;
import io.github.avacadowizard120.cameraoverhaulneoforge.core.utils.Vec2fUtils;
import net.minecraft.client.Camera;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class CameraSystem implements CameraUpdateCallback, ModifyCameraTransformCallback
{
    private static double prevForwardVelocityPitchOffset;
    private static double prevVerticalVelocityPitchOffset;
    private static double prevStrafingRollOffset;
    private static double prevCameraYaw;
    private static double yawDeltaRollOffset;
    private static double yawDeltaRollTargetOffset;
    private static final Transform offsetTransform = new Transform();

    public CameraSystem() {
        CameraUpdateCallback.EVENT.Register(this);
        ModifyCameraTransformCallback.EVENT.Register(this);

        CameraOverhaulNeoForge.LOGGER.info("CameraOverhaul - CameraSystem is ready.");
    }

    @Override
    public void OnCameraUpdate(Entity focusedEntity, Camera camera, Transform cameraTransform, float deltaTime) {
        boolean isFlying = false;
        boolean isSwimming = false;

        if (focusedEntity instanceof Player player)
        {
            isFlying = player.isFallFlying();
            isSwimming = player.isSwimming();
        }

        //Reset the offset transform
        offsetTransform.position = new Vec3(0d, 0d, 0d);
        offsetTransform.eulerRot = new Vec3(0d, 0d, 0d);

        ConfigData config = CameraOverhaulNeoForge.instance.config;

        if (!config.enabled) {
            return;
        }

        float strafingRollFactorToUse = config.strafingRollFactor;

        if (isFlying) {
            strafingRollFactorToUse = config.getStrafingRollFactorWhenFlying;
        } else if (isSwimming)
        {
            strafingRollFactorToUse = config.getGetStrafingRollFactorWhenSwimming;
        }

        Vec3 velocity = camera.getEntity().getDeltaMovement();
        Vec2 relativeXZVelocity = Vec2fUtils.Rotate(new Vec2((float) velocity.x, (float) velocity.z), 360f - (float) cameraTransform.eulerRot.y);

        if (strafingRollFactorToUse == config.getStrafingRollFactorWhenFlying && !CameraOverhaulNeoForge.ClientModEvents.isBarrelLoaded)
        {
            //X
            VerticalVelocityPitchOffset(cameraTransform, velocity, relativeXZVelocity, deltaTime, config.verticalVelocityPitchFactor, config.verticalVelocitySmoothingFactor);
            ForwardVelocityPitchOffset(cameraTransform, velocity, relativeXZVelocity, deltaTime, config.forwardVelocityPitchFactor, config.horizontalVelocitySmoothingFactor);
            //Z
            YawDeltaRollOffset(cameraTransform, velocity, relativeXZVelocity, deltaTime, config.yawDeltaRollFactor * 1.25f, config.yawDeltaSmoothingFactor, config.yawDeltaDecayFactor);
            StrafingRollOffset(cameraTransform, velocity, relativeXZVelocity, deltaTime, strafingRollFactorToUse, config.horizontalVelocitySmoothingFactor);
        } else if (strafingRollFactorToUse != config.getStrafingRollFactorWhenFlying) {
            //X
            VerticalVelocityPitchOffset(cameraTransform, velocity, relativeXZVelocity, deltaTime, config.verticalVelocityPitchFactor, config.verticalVelocitySmoothingFactor);
            ForwardVelocityPitchOffset(cameraTransform, velocity, relativeXZVelocity, deltaTime, config.forwardVelocityPitchFactor, config.horizontalVelocitySmoothingFactor);
            //Z
            YawDeltaRollOffset(cameraTransform, velocity, relativeXZVelocity, deltaTime, config.yawDeltaRollFactor * 1.25f, config.yawDeltaSmoothingFactor, config.yawDeltaDecayFactor);
            StrafingRollOffset(cameraTransform, velocity, relativeXZVelocity, deltaTime, strafingRollFactorToUse, config.horizontalVelocitySmoothingFactor);
        }

        prevCameraYaw = cameraTransform.eulerRot.y;
    }

    @Override
    public Transform ModifyCameraTransform(Camera camera, Transform transform) {
        return new Transform(
                transform.position.add(offsetTransform.position),
                transform.eulerRot.add(offsetTransform.eulerRot)
        );
    }

    private void VerticalVelocityPitchOffset(Transform inputTransform, Vec3 velocity, Vec2 relativeXZVelocity, double deltaTime, float intensity, float smoothing) {
        double verticalVelocityPitchOffset = velocity.y * 2.75d;

        if (velocity.y < 0f) {
            verticalVelocityPitchOffset *= 2.25d;
        }

        prevVerticalVelocityPitchOffset = verticalVelocityPitchOffset = MathUtils.Damp(prevVerticalVelocityPitchOffset, verticalVelocityPitchOffset, smoothing, deltaTime);

        CameraSystem.offsetTransform.eulerRot = CameraSystem.offsetTransform.eulerRot.add(verticalVelocityPitchOffset * intensity, 0d, 0d);
    }

    private void ForwardVelocityPitchOffset(Transform inputTransform, Vec3 velocity, Vec2 relativeXZVelocity, double deltaTime, float intensity, float smoothing) {
        double forwardVelocityPitchOffset = relativeXZVelocity.y * 5d;

        prevForwardVelocityPitchOffset = forwardVelocityPitchOffset = MathUtils.Damp(prevForwardVelocityPitchOffset, forwardVelocityPitchOffset, smoothing, deltaTime);

        CameraSystem.offsetTransform.eulerRot = CameraSystem.offsetTransform.eulerRot.add(forwardVelocityPitchOffset * intensity, 0d, 0d);
    }

    private void YawDeltaRollOffset(Transform inputTransform, Vec3 velocity, Vec2 relativeXZVelocity, double deltaTime, float intensity, float offsetSmoothing, float decaySmoothing) {
        double yawDelta = prevCameraYaw - inputTransform.eulerRot.y;

        if (yawDelta > 180) {
            yawDelta = 360 - yawDelta;
        } else if (yawDelta < -180) {
            yawDelta = -360 - yawDelta;
        }

        yawDeltaRollTargetOffset += yawDelta * 0.07d;
        yawDeltaRollOffset = MathUtils.Damp(yawDeltaRollOffset, yawDeltaRollTargetOffset, offsetSmoothing, deltaTime);

        CameraSystem.offsetTransform.eulerRot = CameraSystem.offsetTransform.eulerRot.add(0d, 0d, yawDeltaRollOffset * intensity);

        yawDeltaRollTargetOffset = MathUtils.Damp(yawDeltaRollTargetOffset, 0d, decaySmoothing, deltaTime);
    }

    private void StrafingRollOffset(Transform inputTransform, Vec3 velocity, Vec2 relativeXZVelocity, double deltaTime, float intensity, float smoothing) {
        double strafingRollOffset = -relativeXZVelocity.x * 15d;

        prevStrafingRollOffset = strafingRollOffset = MathUtils.Damp(prevStrafingRollOffset, strafingRollOffset, smoothing, deltaTime);

        CameraSystem.offsetTransform.eulerRot = CameraSystem.offsetTransform.eulerRot.add(0d, 0d, strafingRollOffset * intensity);
    }
}
