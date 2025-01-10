package io.github.AvacadoWizard120.cameraoverhaulforge.mixin;

import io.github.AvacadoWizard120.cameraoverhaulforge.configuration.Configuration;
import io.github.AvacadoWizard120.cameraoverhaulforge.systems.ScreenShakes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LightningBolt.class)
public abstract class LightningBoltMixin
{
    @Inject(method = "spawnFire", at = @At("RETURN"))
    private void spawnFire(int num, CallbackInfo ci) {
        var entity = (Entity)(Object)this;
        var pos = entity.position();

        var explosion = ScreenShakes.createDirect();
        explosion.position.set(pos.x, pos.y, pos.z);
        explosion.radius = 16f;
        explosion.trauma = (float) Configuration.get().general.explosionTrauma;
        explosion.lengthInSeconds = 3f;

        var thunder = ScreenShakes.createDirect();
        thunder.position.set(pos.x, pos.y, pos.z);
        thunder.radius = 192f;
        thunder.trauma = (float)Configuration.get().general.thunderTrauma;
        thunder.frequency = 0.5f;
        thunder.lengthInSeconds = 7f;
    }
}
