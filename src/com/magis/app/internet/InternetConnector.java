package com.magis.app.internet;

import com.magis.app.UI.Alert;
import java.io.*;

public class InternetConnector {
    public static void checkInternet() throws IOException, InterruptedException {
        Process process = java.lang.Runtime.getRuntime().exec("ping www.google.com");
        int x = process.waitFor();
        if(x != 0) {
            Alert internetAlert = new Alert();
            internetAlert.showAlert("No Internet Connection!", "The Tutor can still be accessed, but without internet, images will not be shown in lessons");
        }
    }
}
