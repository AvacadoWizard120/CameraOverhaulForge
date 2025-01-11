package io.github.AvacadoWizard120.cameraoverhaulforge;

import com.mojang.logging.LogUtils;
import io.github.AvacadoWizard120.cameraoverhaulforge.systems.CameraSystem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(Cameraoverhaulforge.MODID)
public class Cameraoverhaulforge
{
    public static final String MODID = "cameraoverhaulforge";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static Cameraoverhaulforge instance;

    public static CameraSystem camera;

    public Cameraoverhaulforge(FMLJavaModLoadingContext context) {
        MinecraftForge.EVENT_BUS.register(this);
        instance = this;
    }

    public void onInitializeClient() {
        LOGGER.info("Initializing client...");
        camera = new CameraSystem();
        LOGGER.info("Camera system initialized: {}", camera);
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            LOGGER.info("Client setup event triggered!");
            if (instance != null) {
                instance.onInitializeClient();
            } else {
                LOGGER.error("Instance of Cameraoverhaulforge is null during client setup!");
            }
        }
    }
}
