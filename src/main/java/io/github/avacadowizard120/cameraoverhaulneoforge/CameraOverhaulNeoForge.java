package io.github.avacadowizard120.cameraoverhaulneoforge;

import com.mojang.logging.LogUtils;
import io.github.avacadowizard120.cameraoverhaulneoforge.common.configuration.ConfigData;
import io.github.avacadowizard120.cameraoverhaulneoforge.common.systems.CameraSystem;
import io.github.avacadowizard120.cameraoverhaulneoforge.core.configuration.Configuration;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(CameraOverhaulNeoForge.MODID)
public class CameraOverhaulNeoForge
{
    public ConfigData config;
    public static CameraOverhaulNeoForge instance;
    public CameraSystem cameraSystem;

    // Define mod id in a common place for everything to reference
    public static final String MODID = "cameraoverhaulneoforge";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public void onInitializeClient()
    {
        config = Configuration.LoadConfig(ConfigData.class, MODID, ConfigData.ConfigVersion);
        cameraSystem = new CameraSystem();
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        public static boolean isBarrelLoaded;
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            if (CameraOverhaulNeoForge.instance == null)
            {
                CameraOverhaulNeoForge.instance = new CameraOverhaulNeoForge();
            }

            isBarrelLoaded = ModList.get().isLoaded("do_a_barrel_roll");
            CameraOverhaulNeoForge.instance.onInitializeClient();
        }
    }
}
