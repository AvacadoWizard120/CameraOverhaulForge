package com.confusingfool.cameraoverhaulneoforge.mixin;

import com.confusingfool.cameraoverhaulneoforge.core.callbacks.CameraUpdateCallback;
import com.confusingfool.cameraoverhaulneoforge.core.callbacks.ModifyCameraTransformCallback;
import com.confusingfool.cameraoverhaulneoforge.core.structures.Transform;
import net.minecraft.client.Camera;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.swing.text.html.parser.Entity;

@Mixin(Camera.class)
public abstract class CameraMixin
{
    @Shadow abstract float getXRot();
    @Shadow abstract float getYRot();
    @Shadow abstract Vec3 getPosition();
    @Shadow abstract void setRotation(float yaw, float pitch);

    @Inject(method = "setup", at = @At("RETURN"))
    private void OnCameraUpdate(BlockGetter pLevel, net.minecraft.world.entity.Entity pEntity, boolean pDetached, boolean pThirdPersonReverse, float pPartialTick, CallbackInfo ci) {
        Transform cameraTransform = new Transform(getPosition(), new Vec3(getXRot(), getYRot(), 0d));

        CameraUpdateCallback.EVENT.Invoker().OnCameraUpdate((Camera) (Object) this, cameraTransform, pPartialTick);

        cameraTransform = ModifyCameraTransformCallback.EVENT.Invoker().ModifyCameraTransform((Camera) (Object) this, cameraTransform);

        setRotation((float) cameraTransform.eulerRot.y, (float) cameraTransform.eulerRot.x);
    }
}
