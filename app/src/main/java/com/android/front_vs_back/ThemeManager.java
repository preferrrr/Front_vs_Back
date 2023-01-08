package com.android.front_vs_back;

import androidx.appcompat.app.AppCompatDelegate;

import org.jetbrains.annotations.NotNull;

public class ThemeManager {

    public enum ThemeMode { LIGHT, DARK }

    public static void applyTheme(@NotNull ThemeMode themeMode) {
        switch (themeMode) {
            case LIGHT:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;

            case DARK:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
        }
    }
}
