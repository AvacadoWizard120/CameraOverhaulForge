package io.github.AvacadoWizard120.cameraoverhaulforge.configuration;

import io.github.AvacadoWizard120.cameraoverhaulforge.Cameraoverhaulforge;
import io.github.AvacadoWizard120.cameraoverhaulforge.utils.TextUtil;
import net.minecraftforge.fml.loading.FMLPaths;
import org.tomlj.Toml;
import org.tomlj.TomlParseResult;
import org.tomlj.TomlTable;

import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;

public final class Configuration
{
    private static final Path CONFIG_DIR = FMLPaths.CONFIGDIR.get();
    private static final Path CONFIG_PATH = CONFIG_DIR.resolve(Cameraoverhaulforge.MODID + ".toml");
    private static final String CONFIG_ENTRIES_PREFIX = "cameraoverhaulforge.config";
    private static final ConfigData configDefault = new ConfigData();
    private static ConfigData configCurrent;

    public static ConfigData getDefault() { return configDefault; }

    public static ConfigData get() {
        if (configCurrent == null) loadConfig();
        return configCurrent;
    }

    public static void loadConfig() {
        var file = CONFIG_PATH.toFile();

        try {
            Files.createDirectories(CONFIG_DIR);

            if (file.exists()) {
                // Correct parsing of the TOML file using Toml.parse()
                TomlParseResult parseResult = Toml.parse((ReadableByteChannel) file);
                // Access the specific section if needed
                TomlTable configTable = parseResult.getTable("cameraoverhaulforge.config");

                // Manually load values from the TomlTable
                if (configTable != null) {
                    configCurrent = new ConfigData(configTable);
                } else {
                    configCurrent = new ConfigData(); // Default if the section is missing
                }
            } else {
                configCurrent = new ConfigData(); // Default config if file does not exist
            }
        } catch (Exception e) {
            configCurrent = new ConfigData(); // Fallback to default config
            Cameraoverhaulforge.LOGGER.error("Failed to load config file", e);
        }

        saveConfig();
    }

    public static void saveConfig() {
        configCurrent.configVersion = ConfigData.CONFIG_VERSION;

        try (BufferedWriter writer = Files.newBufferedWriter(CONFIG_PATH)) {
            writeCommentedFieldsToml(writer, configCurrent, configDefault, 0);
        } catch (Exception e) {
            Cameraoverhaulforge.LOGGER.error("Failed to save config file", e);
        }
    }

    public static String getNameKey(String identifier) { return CONFIG_ENTRIES_PREFIX + "." + identifier + ".name"; }
    public static String getDescKey(String identifier) { return CONFIG_ENTRIES_PREFIX + "." + identifier + ".desc"; }

    private static void writeCommentedFieldsToml(BufferedWriter writer, Object objCurrent, Object objDefault, int indentation) throws IllegalAccessException, IOException {
        for (var field : objCurrent.getClass().getFields()) {
            if (Modifier.isStatic(field.getModifiers())) continue;

            if (!field.getType().isPrimitive() && field.getType() != String.class) {
                writer.write("\r\n");
                writer.write("[" + field.getName() + "]");
                writer.write("\r\n");
                writer.write("\r\n");
                writeCommentedFieldsToml(writer, field.get(objCurrent), field.get(objDefault), indentation + 1);
                continue;
            }

            indent(writer, indentation);
            writer.write("# ");
            writer.write(TextUtil.getTextValue(getDescKey(field.getName())));
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
