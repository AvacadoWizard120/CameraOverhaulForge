package com.confusingfool.cameraoverhaulreforged.common.configuration;

import com.confusingfool.cameraoverhaulreforged.core.configuration.BaseConfigData;

public class ConfigData extends BaseConfigData
{
    public static final int ConfigVersion = 1;

    public boolean enabled = true;

    public float strafingRollFactor = 1.0f;
    public float getStrafingRollFactorWhenFlying = -1.0f;
    public float getStrafingRollFactorWhenSwimming = -1.0f;
    public float yawDeltaRollFactor = 1.0f;

    public float verticalVelocityPitchFactor = 1.0f;
    public float forwardVelocityPitchFactor = 1.0f;

    public float horizontalVelocitySmoothingFactor = 0.8f;
    public float verticalVelocitySmoothingFactor = 0.8f;
    public float yawDeltaSmoothingFactor = 0.8f;
    public float yawDeltaDecayFactor = 0.5f;
}
