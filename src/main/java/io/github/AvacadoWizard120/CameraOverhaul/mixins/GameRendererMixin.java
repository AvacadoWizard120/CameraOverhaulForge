package io.github.AvacadoWizard120.CameraOverhaul.mixins;

import io.github.AvacadoWizard120.CameraOverhaul.CameraOverhaul;
import io.github.AvacadoWizard120.CameraOverhaul.abstractions.MathAbstractions;
import io.github.AvacadoWizard120.CameraOverhaul.utils.Transform;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.Entity;
import org.joml.Vector3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public abstract class GameRendererMixin
{
    @Inject(method = "setupCameraTransform", at = @At("HEAD"))
    private void onCameraUpdate(float partialTicks, int pass, CallbackInfo ci) {
        Entity entity = Minecraft.getMinecraft().getRenderViewEntity();

        if (entity == null) return;

        Transform cameraTransform = new Transform(
                new Vector3d(
                        entity.prevPosX + (entity.posX - entity.prevPosX) * partialTicks,
                        entity.prevPosY + (entity.posY - entity.prevPosY) * partialTicks + entity.getEyeHeight(),
                        entity.prevPosZ + (entity.posZ - entity.prevPosZ) * partialTicks
                ),
                new Vector3d(
                        entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks,
                        entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks,
                        0
                )
        );

        CameraOverhaul.camera.modifyCameraTransform(cameraTransform);

        MathAbstractions.rotateMatrixByAxis(
                (float) cameraTransform.eulerRot.z,
                0f, 0f, 1f // Z-axis
        );
    }
}
