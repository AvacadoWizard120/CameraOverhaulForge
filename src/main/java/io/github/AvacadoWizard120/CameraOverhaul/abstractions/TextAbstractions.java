// Copyright 2020-2025 Mirsario & Contributors.
// Released under the GNU General Public License 3.0.

package io.github.AvacadoWizard120.CameraOverhaul.abstractions;

import net.minecraft.util.text.TextComponentTranslation;

public final class TextAbstractions
{
    // Focusing on 1.12.2

    public static TextComponentTranslation createText(String key) {
        return new TextComponentTranslation(key);
    }

    public static String getTextValue(String key) {
        return createText(key).getUnformattedText();
    }

    public static String getText(String key) {
        return getTextValue(key);
    }
}