package io.github.avacadowizard120.cameraoverhaulreforged;

import com.mojang.logging.LogUtils;
import io.github.avacadowizard120.cameraoverhaulreforged.common.systems.CameraSystem;
import io.github.avacadowizard120.cameraoverhaulreforged.core.configuration.Configuration;
import io.github.avacadowizard120.cameraoverhaulreforged.common.configuration.ConfigData;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(CameraOverhaulReforged.MODID)
public class CameraOverhaulReforged
{
    public static boolean isBarrelRollLoaded = false;

    // Define mod id in a common place for everything to reference
    public static final String MODID = "cameraoverhaulreforged";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public static CameraOverhaulReforged instance;

    public CameraSystem cameraSystem;
    public ConfigData config;

    public CameraOverhaulReforged()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        MinecraftForge.EVENT_BUS.register(this);
    }

    public void onInitializeClient() {
        config = Configuration.LoadConfig(ConfigData.class, MODID, ConfigData.ConfigVersion);
        cameraSystem = new CameraSystem();
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        public static boolean isBarrelLoaded;

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            if (CameraOverhaulReforged.instance == null)
            {
                CameraOverhaulReforged.instance = new CameraOverhaulReforged();
            }

            isBarrelLoaded = ModList.get().isLoaded("do_a_barrel_roll");

            CameraOverhaulReforged.instance.onInitializeClient();
        }
    }
}