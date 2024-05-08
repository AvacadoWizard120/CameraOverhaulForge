package com.confusingfool.cameraoverhaulreforged.common;

import com.confusingfool.cameraoverhaulreforged.common.configuration.ConfigData;
import com.confusingfool.cameraoverhaulreforged.common.systems.CameraSystem;
import com.confusingfool.cameraoverhaulreforged.core.configuration.Configuration;

import java.util.logging.LogManager;
import java.util.logging.Logger;

public class CameraOverhaul
{
    public static final Logger LOGGER = LogManager.getLogManager().getLogger("Camera Overhaul Reforged");
    public static final String Id = "cameraoverhaulreforged";
    public static CameraOverhaul instance;
    public CameraSystem cameraSystem;
    public ConfigData config;

    public void onInitializeClient()
    {
        config = Configuration.LoadConfig(ConfigData.class, Id, ConfigData.ConfigVersion);
        cameraSystem = new CameraSystem();
    }
}
