package io.github.avacadowizard120.cameraoverhaulneoforge.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import io.github.avacadowizard120.cameraoverhaulneoforge.core.callbacks.ModifyCameraTransformCallback;
import io.github.avacadowizard120.cameraoverhaulneoforge.core.structures.Transform;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin
{
    @Shadow
    @Final
    private Camera mainCamera;

    @Inject(method = { "bobView", "bobHurt" }, at = @At("HEAD"))
    private void PostCameraUpdate(PoseStack matrix, float p_109140_, CallbackInfo ci) {
        Transform cameraTransform = new Transform(mainCamera.getPosition(), new Vec3(mainCamera.getXRot(), mainCamera.getYRot(), 0d));

        cameraTransform = ModifyCameraTransformCallback.EVENT.Invoker().ModifyCameraTransform(mainCamera, cameraTransform);

        matrix.mulPose(Axis.ZP.rotationDegrees((float)cameraTransform.eulerRot.z));
    }
}
