package io.github.AvacadoWizard120.CameraOverhaul.mixins;

import io.github.AvacadoWizard120.CameraOverhaul.CameraOverhaul;
import io.github.AvacadoWizard120.CameraOverhaul.systems.CameraContext;
import io.github.AvacadoWizard120.CameraOverhaul.systems.TimeSystem;
import io.github.AvacadoWizard120.CameraOverhaul.utils.Transform;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.passive.EntityAnimal;
import org.joml.Vector3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public abstract class CameraMixin
{
    @Inject(method = "setupCameraTransform", at = @At("RETURN"))
    private void onCameraUpdate(float partialTicks, int pass, CallbackInfo ci) {
        Entity entity = Minecraft.getMinecraft().getRenderViewEntity();
        if (entity == null) return;

        Entity vehicle = entity.getRidingEntity();
        Entity controlledEntity = vehicle != null ? vehicle : entity;

        CameraContext context = new CameraContext();
        context.isRiding = vehicle != null;
        context.isRidingMount = vehicle instanceof EntityAnimal;
        context.isRidingVehicle = vehicle instanceof EntityBoat || vehicle instanceof EntityMinecart;

        context.velocity = new Vector3d(controlledEntity.motionX, controlledEntity.motionY, controlledEntity.motionZ);
        context.transform = new Transform(
                new Vector3d(controlledEntity.posX, controlledEntity.posY + controlledEntity.getEyeHeight(), controlledEntity.posZ),
                new Vector3d(controlledEntity.rotationPitch, controlledEntity.rotationYaw, 0)
        );

        Minecraft mc = Minecraft.getMinecraft();
        if (mc.gameSettings.thirdPersonView == 0) {
            context.perspective = CameraContext.Perspective.FIRST_PERSON;
        } else {
            context.perspective = (mc.gameSettings.thirdPersonView == 1)
                    ? CameraContext.Perspective.THIRD_PERSON
                    : CameraContext.Perspective.THIRD_PERSON_REVERSE;
        }

        if (controlledEntity instanceof EntityLivingBase) {
            EntityLivingBase living = (EntityLivingBase) controlledEntity;
            context.isFlying = living.isElytraFlying();
            context.isSwimming = living.isInWater() && living.isSprinting();
            context.isSprinting = living.isSprinting();
        }

        // Undo vanilla rotations
        float originalYaw = controlledEntity.rotationYaw;
        float originalPitch = controlledEntity.rotationPitch;

        GlStateManager.rotate(originalYaw + 180.0F, 0.0F, -1.0F, 0.0F);
        GlStateManager.rotate(originalPitch, -1.0F, 0.0F, 0.0F);

        // Update camera system
        TimeSystem.update();
        CameraOverhaul.camera.onCameraUpdate(context, TimeSystem.getDeltaTime());
        CameraOverhaul.camera.modifyCameraTransform(context.transform);

        // Apply new rotations
        GlStateManager.rotate((float) context.transform.eulerRot.z, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate((float) context.transform.eulerRot.x, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate((float) context.transform.eulerRot.y + 180.0F, 0.0F, 1.0F, 0.0F);
    }
}
