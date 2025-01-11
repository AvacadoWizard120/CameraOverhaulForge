package io.github.AvacadoWizard120.cameraoverhaulneoforge;

import com.mojang.logging.LogUtils;
import io.github.AvacadoWizard120.cameraoverhaulneoforge.systems.CameraSystem;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import org.slf4j.Logger;

@Mod(Cameraoverhaulneoforge.MODID)
public class Cameraoverhaulneoforge
{
    public static final String MODID = "cameraoverhaulneoforge";

    public static final Logger LOGGER = LogUtils.getLogger();

    public static Cameraoverhaulneoforge instance;

    public static CameraSystem camera;

    public Cameraoverhaulneoforge()
    {
        instance = this;
    }

    public void onInitializeClient()
    {
        LOGGER.info("Initializing client...");
        camera = new CameraSystem();
        LOGGER.info("Camera system initialized: {}", camera);
    }

    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            LOGGER.info("Client setup event triggered!");
            if (instance != null)
            {
                instance.onInitializeClient();
            } else {
                LOGGER.error("Instance of Cameraoverhaulneoforge is null during client setup!");
            }
        }
    }
}
