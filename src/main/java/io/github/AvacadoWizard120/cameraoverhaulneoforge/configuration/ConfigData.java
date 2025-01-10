package io.github.AvacadoWizard120.cameraoverhaulneoforge.configuration;

import org.tomlj.TomlTable;

public final class ConfigData {
    public static final class General {
        public boolean enabled = true;
        public boolean enableInThirdPerson = true;
        // Turning Roll
        public double turningRollAccumulation = 1.0;
        public double turningRollIntensity = 1.25;
        public double turningRollSmoothing = 1.0;
        // Sway
        public double cameraSwayIntensity = 0.60;
        public double cameraSwayFrequency = 0.16;
        public double cameraSwayFadeInDelay = 0.15;
        public double cameraSwayFadeInLength = 5.0;
        public double cameraSwayFadeOutLength = 0.75;
        // ScreenShakes
        public double screenShakesMaxIntensity = 2.5;
        public double screenShakesMaxFrequency = 6.0;
        public double explosionTrauma = 1.00;
        public double thunderTrauma = 0.05;
        public double handSwingTrauma = 0.03;
    }

    public static final class Contextual {
        public double strafingRollFactor = 10.0;
        public double forwardVelocityPitchFactor = 7.0;
        public double verticalVelocityPitchFactor = 2.5;
        public double horizontalVelocitySmoothingFactor = 1.0;
        public double verticalVelocitySmoothingFactor = 1.0;
    }

    public static final int CONFIG_VERSION = 2;

    public int configVersion;
    public General general = new General();
    public Contextual walking = new Contextual();
    public Contextual swimming = new Contextual();
    public Contextual flying = new Contextual();
    public Contextual mounts = new Contextual();
    public Contextual vehicles = new Contextual();

    // Default constructor
    public ConfigData() {
        // Flying
        flying.strafingRollFactor *= -1.0;
        // Swimming
        swimming.strafingRollFactor *= -3.0;
        swimming.forwardVelocityPitchFactor *= 3.0;
        swimming.verticalVelocityPitchFactor *= 3.0;
        // Mounts
        mounts.strafingRollFactor *= 2.0;
        vehicles.forwardVelocityPitchFactor *= 0.5;
        // Vehicles
        vehicles.strafingRollFactor *= 0.5;
        vehicles.forwardVelocityPitchFactor *= 0.5;
        vehicles.verticalVelocityPitchFactor *= 2.0;
    }

    // Constructor to load values from TomlTable
    public ConfigData(TomlTable table) {
        if (table != null) {
            this.configVersion = table.getLong("configVersion").intValue();

            // Load General section
            TomlTable generalTable = table.getTable("general");
            if (generalTable != null) {
                this.general.enabled = generalTable.getBoolean("enabled");
                this.general.enableInThirdPerson = generalTable.getBoolean("enableInThirdPerson");
                this.general.turningRollAccumulation = generalTable.getDouble("turningRollAccumulation");
                this.general.turningRollIntensity = generalTable.getDouble("turningRollIntensity");
                this.general.turningRollSmoothing = generalTable.getDouble("turningRollSmoothing");
                this.general.cameraSwayIntensity = generalTable.getDouble("cameraSwayIntensity");
                this.general.cameraSwayFrequency = generalTable.getDouble("cameraSwayFrequency");
                this.general.cameraSwayFadeInDelay = generalTable.getDouble("cameraSwayFadeInDelay");
                this.general.cameraSwayFadeInLength = generalTable.getDouble("cameraSwayFadeInLength");
                this.general.cameraSwayFadeOutLength = generalTable.getDouble("cameraSwayFadeOutLength");
                this.general.screenShakesMaxIntensity = generalTable.getDouble("screenShakesMaxIntensity");
                this.general.screenShakesMaxFrequency = generalTable.getDouble("screenShakesMaxFrequency");
                this.general.explosionTrauma = generalTable.getDouble("explosionTrauma");
                this.general.thunderTrauma = generalTable.getDouble("thunderTrauma");
                this.general.handSwingTrauma = generalTable.getDouble("handSwingTrauma");
            }

            // Load Contextual sections
            loadContextual(table, "walking", this.walking);
            loadContextual(table, "swimming", this.swimming);
            loadContextual(table, "flying", this.flying);
            loadContextual(table, "mounts", this.mounts);
            loadContextual(table, "vehicles", this.vehicles);
        }
    }

    // Helper method to load contextual data
    private void loadContextual(TomlTable table, String section, Contextual context) {
        TomlTable contextTable = table.getTable(section);
        if (contextTable != null) {
            context.strafingRollFactor = contextTable.getDouble("strafingRollFactor");
            context.forwardVelocityPitchFactor = contextTable.getDouble("forwardVelocityPitchFactor");
            context.verticalVelocityPitchFactor = contextTable.getDouble("verticalVelocityPitchFactor");
            context.horizontalVelocitySmoothingFactor = contextTable.getDouble("horizontalVelocitySmoothingFactor");
            context.verticalVelocitySmoothingFactor = contextTable.getDouble("verticalVelocitySmoothingFactor");
        }
    }
}
