package com.confusingfool.cameraoverhaulneoforge.core.callbacks;

import com.confusingfool.cameraoverhaulneoforge.core.events.Event;
import com.confusingfool.cameraoverhaulneoforge.core.events.EventHelper;
import com.confusingfool.cameraoverhaulneoforge.core.structures.Transform;
import net.minecraft.client.Camera;

public interface ModifyCameraTransformCallback
{
    Event<ModifyCameraTransformCallback> EVENT = EventHelper.CreateEvent(ModifyCameraTransformCallback.class,
            (listeners) -> (camera, transform) -> {
                for (ModifyCameraTransformCallback listener : listeners) {
                    transform = listener.ModifyCameraTransform(camera, transform);
                }

                return transform;
            }
    );

    Transform ModifyCameraTransform(Camera camera, Transform transform);
}
