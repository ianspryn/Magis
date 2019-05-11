package com.magis.app.internet;

import com.magis.app.Main;
import com.magis.app.UI.Alert;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class InternetConnector {
    public static void checkInternet() {
        try {
            final URL url = new URL("http://www.google.com");
            final URLConnection conn = url.openConnection();
            conn.connect();
            conn.getInputStream().close();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            Alert.showAlert("No Internet Connection!", "Magis can still be accessed, but without internet, no images will be visible");
        }
    }
}
