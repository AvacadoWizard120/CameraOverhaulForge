package io.github.AvacadoWizard120.cameraoverhaulneoforge.mixin;

import io.github.AvacadoWizard120.cameraoverhaulneoforge.configuration.Configuration;
import io.github.AvacadoWizard120.cameraoverhaulneoforge.systems.ScreenShakes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundExplodePacket;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public abstract class ExplosionMixin
{
    @Unique
    Minecraft cameraOverhaulNeoForge$minecraft = Minecraft.getInstance();

    @Inject(method = "handleExplosion", at = @At("RETURN"))
    private void finalizeExplosion(ClientboundExplodePacket p_105012_, CallbackInfo ci) {
        Vec3 vec3 = p_105012_.center();
        assert cameraOverhaulNeoForge$minecraft.player != null;
        Vec3 playerPos = new Vec3(cameraOverhaulNeoForge$minecraft.player.getX(), cameraOverhaulNeoForge$minecraft.player.getY(), cameraOverhaulNeoForge$minecraft.player.getZ());

        double distanceFromExplosion = playerPos.distanceTo(vec3);
        float scaledDistance = (float) (distanceFromExplosion * 10f);
        float effectiveDistance = Math.min(scaledDistance, 128f);

        var shake = ScreenShakes.createDirect();
        shake.position.set(vec3.x, vec3.y, vec3.z);
        shake.radius = effectiveDistance;
        shake.trauma = (float) Configuration.get().general.explosionTrauma;
        shake.lengthInSeconds = 2f;
    }
}
