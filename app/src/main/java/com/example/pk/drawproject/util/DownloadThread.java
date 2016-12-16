package com.example.pk.drawproject.util;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by pk on 14.11.2016.
 */
public class DownloadThread implements Runnable {
    String url;
    String file;

    public DownloadThread(String url, String file) {
        this.url = url;
        this.file = file;
    }

    private void downloadSong(String url, String filepath) {
        BufferedInputStream in = null;
        FileOutputStream fout = null;
        try {
            in = new BufferedInputStream(new URL(url).openStream());
            fout = new FileOutputStream(filepath);

            final byte data[] = new byte[5000];
            int count;
            while ((count = in.read(data, 0, 5000)) != -1) {
                fout.write(data, 0, count);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                fout.flush();
                fout.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void run() {
        downloadSong(url, file);
    }
}