package io.github.AvacadoWizard120.cameraoverhaulforge.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import io.github.AvacadoWizard120.cameraoverhaulforge.Cameraoverhaulforge;
import io.github.AvacadoWizard120.cameraoverhaulforge.utils.Transform;
import io.github.AvacadoWizard120.cameraoverhaulforge.utils.VectorUtils;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.GameRenderer;
import org.joml.Vector3d;
import org.joml.Vector3f;
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

    @Inject(method = "bobHurt", at = @At("HEAD"))
    private void postCameraUpdate(PoseStack matrices, float f, CallbackInfo ci) {
        Transform cameraTransform = new Transform(
                VectorUtils.toJoml(mainCamera.getPosition()),
                new Vector3d(mainCamera.getXRot(), mainCamera.getYRot(), 0)
        );

        Cameraoverhaulforge.camera.modifyCameraTransform(cameraTransform);

        matrices.mulPose(Axis.of(new Vector3f(0f,0f,1f)).rotationDegrees((float) cameraTransform.eulerRot.z));
    }
}
