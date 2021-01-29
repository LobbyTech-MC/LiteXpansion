package dev.j3fftw.litexpansion.utils;

import dev.j3fftw.extrautils.utils.Utils;

public final class LoreBuilderDynamic {
    public static String powerBuffer(double power) {
        return power(power, "可储存");
    }

    public static String powerPerTick(double power) {
        return power(dev.j3fftw.extrautils.utils.Utils.perTickToPerSecond(power), "/s");
    }

    public static String power(double power, String prefix) {
        return "&8\u21E8 &e\u26A1" + prefix + " &7" + Utils.powerFormatAndFadeDecimals(power) + " J";
    }
}
