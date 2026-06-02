package com.cinosphere.config;

import java.io.File;

/**
 * Single source of truth for all file storage paths.
 *
 * The old servlets scattered this constant across five files:
 *   System.getProperty("user.home") + File.separator + "webassets" + File.separator + "poster"
 *
 * This class replicates that root so existing files on disk still work
 * after migration, but gives every service/controller one import to change
 * instead of hunting strings.
 *
 * Directory layout (all under WEBASSETS_ROOT):
 *
 *   ~/webassets/
 *     poster/        ← movie poster images     (served at /uploads/movies/poster/*)
 *     background/    ← movie background images (served at /uploads/movies/background/*)
 *     profile/       ← user profile photos     (served at /uploads/profiles/*)
 *     icon/          ← UI icons                (served at /uploads/icons/*)
 *     logo/          ← logo images             (served at /uploads/logos/*)
 */
public final class FileStorageConfig {

    private FileStorageConfig() {}

    /** Root directory — same location the old servlets used, so existing files are found. */
    public static final String WEBASSETS_ROOT =
            System.getProperty("user.home") + File.separator + "webAssets";

    public static final String POSTER_DIR     = WEBASSETS_ROOT + File.separator + "poster";
    public static final String BACKGROUND_DIR = WEBASSETS_ROOT + File.separator + "background";
    public static final String PROFILE_DIR    = WEBASSETS_ROOT + File.separator + "profile";
    public static final String ICON_DIR       = WEBASSETS_ROOT + File.separator + "icon";
    public static final String LOGO_DIR       = WEBASSETS_ROOT + File.separator + "logo";

    /** Default filenames used as fallbacks when a specific file is missing. */
    public static final String DEFAULT_IMAGE  = "default";
}