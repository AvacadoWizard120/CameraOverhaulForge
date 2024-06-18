package io.github.avacadowizard120.cameraoverhaulreforged.core.callbacks;

import io.github.avacadowizard120.cameraoverhaulreforged.core.events.Event;
import io.github.avacadowizard120.cameraoverhaulreforged.core.events.EventHelper;
import io.github.avacadowizard120.cameraoverhaulreforged.core.structures.Transform;
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