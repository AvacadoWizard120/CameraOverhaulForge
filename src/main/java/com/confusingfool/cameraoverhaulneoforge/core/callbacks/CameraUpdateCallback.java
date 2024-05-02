package com.confusingfool.cameraoverhaulneoforge.core.callbacks;

import com.confusingfool.cameraoverhaulneoforge.core.events.Event;
import com.confusingfool.cameraoverhaulneoforge.core.events.EventHelper;
import com.confusingfool.cameraoverhaulneoforge.core.structures.Transform;
import net.minecraft.client.Camera;

public interface CameraUpdateCallback
{
    Event<CameraUpdateCallback> EVENT = EventHelper.CreateEvent(CameraUpdateCallback.class,
            (listeners) -> (camera, transform, deltaTime) -> {
                for (CameraUpdateCallback listener : listeners) {
                    listener.OnCameraUpdate(camera, transform, deltaTime);
                }
            }
    );

    void OnCameraUpdate(Camera camera, Transform cameraTransform, float deltaTime);
}
