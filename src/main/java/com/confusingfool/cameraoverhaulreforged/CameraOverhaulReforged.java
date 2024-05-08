package com.confusingfool.cameraoverhaulreforged;

import com.confusingfool.cameraoverhaulreforged.common.CameraOverhaul;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = com.confusingfool.cameraoverhaulreforged.CameraOverhaulReforged.MODID, name = com.confusingfool.cameraoverhaulreforged.CameraOverhaulReforged.NAME, version = com.confusingfool.cameraoverhaulreforged.CameraOverhaulReforged.VERSION)
public class CameraOverhaulReforged
{
    public static final String MODID = "cameraoverhaulreforged";
    public static final String NAME = "Camera Overhaul Reforged";
    public static final String VERSION = "1.0.0";

    @Mod.Instance
    public static CameraOverhaul instance;

    public CameraOverhaulReforged()
    {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    public void onEvent(TickEvent.PlayerTickEvent event)
    {
        if (event.side == Side.CLIENT && event.phase == TickEvent.Phase.START)
        {
            if (instance == null) {
                instance = new CameraOverhaul();
            }

            instance.onInitializeClient();
        }
    }
}
