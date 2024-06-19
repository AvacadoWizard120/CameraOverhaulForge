package io.github.avacadowizard120.cameraoverhaulneoforge.core.callbacks;

import io.github.avacadowizard120.cameraoverhaulneoforge.core.events.Event;
import io.github.avacadowizard120.cameraoverhaulneoforge.core.events.EventHelper;
import io.github.avacadowizard120.cameraoverhaulneoforge.core.structures.Transform;
import net.minecraft.client.Camera;
import net.minecraft.world.entity.Entity;

public interface CameraUpdateCallback
{
    Event<CameraUpdateCallback> EVENT = EventHelper.CreateEvent(CameraUpdateCallback.class,
            (listeners) -> (focusedEntity, camera, transform, deltaTime) -> {
                for (CameraUpdateCallback listener : listeners) {
                    listener.OnCameraUpdate(focusedEntity, camera, transform, deltaTime);
                }
            }
    );

    void OnCameraUpdate(Entity focusedEntity, Camera camera, Transform cameraTransform, float deltaTime);
}
