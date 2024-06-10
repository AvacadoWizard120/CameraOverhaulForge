package com.confusingfool.cameraoverhaulneoforge.core.callbacks;

import com.confusingfool.cameraoverhaulneoforge.core.events.Event;
import com.confusingfool.cameraoverhaulneoforge.core.events.EventHelper;
import com.confusingfool.cameraoverhaulneoforge.core.structures.Transform;
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
