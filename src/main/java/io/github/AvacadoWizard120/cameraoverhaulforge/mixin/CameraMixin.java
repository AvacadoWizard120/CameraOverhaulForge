package io.github.AvacadoWizard120.cameraoverhaulforge.mixin;

import io.github.AvacadoWizard120.cameraoverhaulforge.Cameraoverhaulforge;
import io.github.AvacadoWizard120.cameraoverhaulforge.systems.CameraContext;
import io.github.AvacadoWizard120.cameraoverhaulforge.systems.TimeSystem;
import io.github.AvacadoWizard120.cameraoverhaulforge.utils.Transform;
import io.github.AvacadoWizard120.cameraoverhaulforge.utils.VectorUtils;
import net.minecraft.client.Camera;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.vehicle.VehicleEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public abstract class CameraMixin
{
    @Shadow
    public abstract float getXRot();

    @Shadow
    public abstract float getYRot();

    @Shadow
    public abstract Vec3 getPosition();

    @Shadow
    protected abstract void setRotation(float yaw, float pitch);

    @Inject(method = "setup", at = @At("RETURN"))
    private void onCameraUpdate(BlockGetter area, Entity entity, boolean thirdPerson, boolean inverseView, float tickDelta, CallbackInfo ci) {
        var system = Cameraoverhaulforge.camera;
        var vehicle = entity.getVehicle();
        var controlledEntity = vehicle != null ? vehicle : entity;

        var context = new CameraContext();
        context.isRiding = vehicle != null;
        context.isRidingMount = vehicle instanceof Animal;
        //? if >=1.20.3
        context.isRidingVehicle = vehicle instanceof VehicleEntity;
        //? if <1.20.3
        /*context.isRidingVehicle = vehicle instanceof Boat || vehicle instanceof AbstractMinecart;*/

        context.velocity = VectorUtils.toJoml(controlledEntity.getDeltaMovement());
        context.transform = new Transform(
                VectorUtils.toJoml(getPosition()),
                new Vector3d(getXRot(), getYRot(), 0)
        );
        context.perspective = (thirdPerson
                ? (inverseView ? CameraContext.Perspective.THIRD_PERSON_REVERSE : CameraContext.Perspective.THIRD_PERSON)
                : CameraContext.Perspective.FIRST_PERSON
        );

        if (entity instanceof LivingEntity) {
            context.isFlying = ((LivingEntity) entity).isFallFlying();
            context.isSwimming = entity.isSwimming();
            context.isSprinting = entity.isSprinting();
        }

        TimeSystem.update();
        system.onCameraUpdate(context, TimeSystem.getDeltaTime());
        system.modifyCameraTransform(context.transform);

        setRotation((float) context.transform.eulerRot.y, (float) context.transform.eulerRot.x);
    }
}
