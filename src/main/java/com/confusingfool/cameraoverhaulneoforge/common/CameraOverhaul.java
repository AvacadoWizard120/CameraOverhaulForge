package com.confusingfool.cameraoverhaulneoforge.common;

import com.confusingfool.cameraoverhaulneoforge.common.configuration.ConfigData;
import com.confusingfool.cameraoverhaulneoforge.common.systems.CameraSystem;
import com.confusingfool.cameraoverhaulneoforge.core.configuration.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CameraOverhaul
{
    public static final Logger LOGGER = LogManager.getLogger("Camera Overhaul NeoForge");
    public static final String Id = "camera_overhaul_neoforge";
    public static CameraOverhaul instance;
    public CameraSystem cameraSystem;
    public ConfigData config;

    public void onInitializeClient() {
        config = Configuration.LoadConfig(ConfigData.class, Id, ConfigData.ConfigVersion);
        cameraSystem = new CameraSystem();
    }
}
