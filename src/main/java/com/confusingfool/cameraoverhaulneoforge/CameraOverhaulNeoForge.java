package com.confusingfool.cameraoverhaulneoforge;

import com.confusingfool.cameraoverhaulneoforge.common.CameraOverhaul;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(CameraOverhaulNeoForge.MODID)
public class CameraOverhaulNeoForge
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "camera_overhaul_neoforge";

    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public CameraOverhaulNeoForge(IEventBus modEventBus)
    {
        modEventBus.addListener(ClientModEvents::clientSetup);
    }

    @Mod.EventBusSubscriber(modid = CameraOverhaulNeoForge.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        public static boolean isBarrelLoaded;
        @SubscribeEvent
        public static void clientSetup(FMLClientSetupEvent event)
        {
            if (CameraOverhaul.instance == null) {
                CameraOverhaul.instance = new CameraOverhaul();
            }

            isBarrelLoaded = ModList.get().isLoaded("do_a_barrel_roll");

            CameraOverhaul.instance.onInitializeClient();
        }
    }
}
