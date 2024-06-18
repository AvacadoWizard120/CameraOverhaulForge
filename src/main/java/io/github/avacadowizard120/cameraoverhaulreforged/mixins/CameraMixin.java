package io.github.avacadowizard120.cameraoverhaulreforged.mixins;

import io.github.avacadowizard120.cameraoverhaulreforged.core.callbacks.CameraUpdateCallback;
import io.github.avacadowizard120.cameraoverhaulreforged.core.callbacks.ModifyCameraTransformCallback;
import io.github.avacadowizard120.cameraoverhaulreforged.core.structures.Transform;
import net.minecraft.client.Camera;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.phys.Vec3;
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
    private void OnCameraUpdate(BlockGetter pLevel, Entity focusedEntity, boolean pDetached, boolean pThirdPersonReverse, float tickDelta, CallbackInfo ci) {
        Transform cameraTransform = new Transform(getPosition(), new Vec3(getXRot(), getYRot(), 0d));

        CameraUpdateCallback.EVENT.Invoker().OnCameraUpdate(focusedEntity, (Camera)(Object)this, cameraTransform, tickDelta);

        cameraTransform = ModifyCameraTransformCallback.EVENT.Invoker().ModifyCameraTransform((Camera) (Object) this, cameraTransform);

        setRotation((float) cameraTransform.eulerRot.y, (float) cameraTransform.eulerRot.x);
    }
}