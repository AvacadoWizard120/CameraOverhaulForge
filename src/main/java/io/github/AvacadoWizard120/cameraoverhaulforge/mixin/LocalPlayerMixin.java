package io.github.AvacadoWizard120.cameraoverhaulforge.mixin;

import io.github.AvacadoWizard120.cameraoverhaulforge.Cameraoverhaulforge;
import io.github.AvacadoWizard120.cameraoverhaulforge.configuration.Configuration;
import io.github.AvacadoWizard120.cameraoverhaulforge.systems.ScreenShakes;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin
{
    @Unique
    private static long cameraOverhaulNeoForge$shakeHandle;

    @Shadow
    public abstract boolean isLocalPlayer();

    @Inject(method = "swing", at = @At("RETURN"))
    private void swing(InteractionHand interactionHand, CallbackInfo ci) {
        if (!isLocalPlayer()) return;

        cameraOverhaulNeoForge$shakeHandle = ScreenShakes.recreate(cameraOverhaulNeoForge$shakeHandle);
        var shake = ScreenShakes.get(cameraOverhaulNeoForge$shakeHandle);
        shake.trauma = (float) Configuration.get().general.handSwingTrauma;
        shake.frequency = 0.5f;
        shake.lengthInSeconds = 0.5f;

        Cameraoverhaulforge.camera.notifyOfPlayerAction();
    }
}
