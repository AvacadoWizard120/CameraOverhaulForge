package io.github.AvacadoWizard120.CameraOverhaul.configuration;

import com.moandjiezana.toml.*;
import io.github.AvacadoWizard120.CameraOverhaul.CameraOverhaul;
import io.github.AvacadoWizard120.CameraOverhaul.abstractions.TextAbstractions;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.*;
import java.lang.reflect.*;
import java.nio.file.*;

public final class Configuration
{
    private static Path CONFIG_DIR;
    private static Path CONFIG_PATH;

    public static void init(FMLPreInitializationEvent event) {
        // Get config directory using 1.12.2-compatible method
        CONFIG_DIR = event.getModConfigurationDirectory().toPath();
        CONFIG_PATH = CONFIG_DIR.resolve(CameraOverhaul.MODID + ".toml");

        loadConfig();
    }

    private static final String CONFIG_ENTRIES_PREFIX = "cameraoverhaul.config";
    private static final Toml TOML = new Toml();
    private static final ConfigData configDefault = new ConfigData();
    private static ConfigData configCurrent;

    public static ConfigData getDefault() { return configDefault; }
    public static ConfigData get() {
        if (configCurrent == null) loadConfig();
        return configCurrent;
    }

    public static void loadConfig() {
        try {
            Files.createDirectories(CONFIG_DIR);

            if (Files.exists(CONFIG_PATH)) {
                try (BufferedReader reader = Files.newBufferedReader(CONFIG_PATH)) {
                    configCurrent = TOML.read(reader).to(ConfigData.class);
                }
            } else {
                configCurrent = new ConfigData();
            }
        } catch (Exception e) {
            configCurrent = new ConfigData();
            CameraOverhaul.LOGGER.error("Failed to load config file", e);
        }

        saveConfig();
    }

    public static void saveConfig() {
        configCurrent.configVersion = ConfigData.CONFIG_VERSION;

        try {
            Files.createDirectories(CONFIG_DIR);
            try (BufferedWriter writer = Files.newBufferedWriter(CONFIG_PATH,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING)) {

                writeCommentedFieldsToml(writer, configCurrent, configDefault, 0);
            }
        } catch (Exception e) {
            CameraOverhaul.LOGGER.error("Failed to save config file", e);
        }
    }

    public static String getNameKey(String identifier) { return CONFIG_ENTRIES_PREFIX + "." + identifier + ".name"; }
    public static String getDescKey(String identifier) { return CONFIG_ENTRIES_PREFIX + "." + identifier + ".desc"; }

    // Very basic, made for one purpose. | Modified to work with Java 8
    private static void writeCommentedFieldsToml(BufferedWriter writer, Object objCurrent, Object objDefault, int indentation) throws IllegalAccessException, IOException {
        for (Field field : objCurrent.getClass().getFields()) { // Explicit Field type
            if (Modifier.isStatic(field.getModifiers())) continue;

            Class<?> fieldType = field.getType();

            if (!fieldType.isPrimitive() && fieldType != String.class) {
                writer.write("\r\n");
                writer.write("[");
                writer.write(field.getName());
                writer.write("]");
                writer.write("\r\n\r\n");
                writeCommentedFieldsToml(writer, field.get(objCurrent), field.get(objDefault), indentation + 1);
                continue;
            }

            indent(writer, indentation);
            writer.write("# ");
            writer.write(TextAbstractions.getTextValue(getDescKey(field.getName())));
            newLine(writer, indentation);
            writer.write("# Default: ");
            writer.write(field.get(objDefault).toString());
            newLine(writer, indentation);
            writer.write(field.getName());
            writer.write(" = ");
            writer.write(field.get(objCurrent).toString());
            newLine(writer, 0);
        }
    }
    private static void indent(BufferedWriter writer, int indentation) throws IOException {
        for (int i = 0; i < indentation; i++) writer.write("\t");
    }
    private static void newLine(BufferedWriter writer, int indentation) throws IOException {
        writer.write("\r\n");
        indent(writer, indentation);
    }
}
