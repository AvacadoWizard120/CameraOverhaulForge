package io.github.AvacadoWizard120.cameraoverhaulforge.utils;

import net.minecraft.network.chat.Component;

public final class TextUtil
{
    // This is the equivelent to the TextAbstractions class in Mirsario's original fabric mod. I'm not fw abstractions.

    public static Component createText(String key) {
        return Component.translatable(key);
    }

    public static String getTextValue(String key) { return TextUtil.createText(key).getString(); }

    public static net.minecraft.network.chat.Component getText(String key) { return TextUtil.createText(key); }
}
