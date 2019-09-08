package com.ulicae.cinelog.utils.image;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.ulicae.cinelog.utils.image.ImageCacheDownloader.BASE_URL;

/**
 * CineLog Copyright 2018 Pierre Rognon
 * <p>
 * <p>
 * This file is part of CineLog.
 * CineLog is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * CineLog is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with CineLog. If not, see <https://www.gnu.org/licenses/>.
 */
class CacheImageDownloaderNetworkTask extends AsyncTask<Void, Void, Void> {

    private String imagePath;

    CacheImageDownloaderNetworkTask(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        File fileToWrite = new ImageCacheFinder().getImage(imagePath);
        try {
            FileUtils.copyURLToFile(new URL(BASE_URL + imagePath), fileToWrite);
        } catch (IOException e) {
            //noinspection ResultOfMethodCallIgnored
            fileToWrite.delete();
        }

        return null;
    }
}
