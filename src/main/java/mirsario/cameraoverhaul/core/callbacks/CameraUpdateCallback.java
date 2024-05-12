package mirsario.cameraoverhaul.core.callbacks;

import mirsario.cameraoverhaul.core.events.Event;
import mirsario.cameraoverhaul.core.events.EventHelper;
import mirsario.cameraoverhaul.core.structures.Transform;
import net.minecraft.client.Camera;
import net.minecraft.world.entity.Entity;

public interface CameraUpdateCallback {
    Event<CameraUpdateCallback> EVENT = EventHelper.CreateEvent(CameraUpdateCallback.class,
            (listeners) -> (focusedEntity, camera, transform, deltaTime) -> {
                for (CameraUpdateCallback listener : listeners) {
                    listener.OnCameraUpdate(focusedEntity, camera, transform, deltaTime);
                }
            }
    );

    void OnCameraUpdate(Entity focusedEntity, Camera camera, Transform cameraTransform, float deltaTime);
}