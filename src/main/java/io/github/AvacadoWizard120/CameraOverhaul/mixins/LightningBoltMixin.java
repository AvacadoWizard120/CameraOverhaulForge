package io.github.AvacadoWizard120.CameraOverhaul.mixins;

import io.github.AvacadoWizard120.CameraOverhaul.configuration.Configuration;
import io.github.AvacadoWizard120.CameraOverhaul.systems.ScreenShakes;
import net.minecraft.entity.effect.EntityLightningBolt;
import org.joml.Vector3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityLightningBolt.class)
public abstract class LightningBoltMixin
{
    @Inject(method = "onUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/effect/EntityLightningBolt;setDead()V"))
    private void onStrike(CallbackInfo ci) {
        EntityLightningBolt bolt = (EntityLightningBolt)(Object)this;

        Vector3d pos = new Vector3d(bolt.posX, bolt.posY, bolt.posZ);

        ScreenShakes.Slot explosion = ScreenShakes.createDirect();
        explosion.position.set(pos.x, pos.y, pos.z);
        explosion.radius = 16f;
        explosion.trauma = (float)Configuration.get().general.explosionTrauma;
        explosion.lengthInSeconds = 3f;

        ScreenShakes.Slot thunder = ScreenShakes.createDirect();
        thunder.position.set(pos.x, pos.y, pos.z);
        thunder.radius = 192f;
        thunder.trauma = (float)Configuration.get().general.thunderTrauma;
        thunder.frequency = 0.5f;
        thunder.lengthInSeconds = 7f;
    }
}
