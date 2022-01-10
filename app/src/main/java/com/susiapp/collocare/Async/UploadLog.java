package com.susiapp.collocare.Async;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.susiapp.collocare.Models.Globals;
import com.susiapp.collocare.Models.HttpHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UploadLog extends AsyncTask<String, Integer, String> {

    Globals globals = Globals.getInstance();
    private String username;

    public UploadLog(String username) {
        this.username = username;
    }

    @Override
    protected String doInBackground(String... strings) {

        String sourceFileUri = globals.rootPath+"log_"+globals.getUsername()+".txt";

        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri);

        String response;

        try {
            String upLoadServerUri = "http://websusi.000webhostapp.com/collocare/json/upload_log.php";

            // open a URL connection to the Servlet
            FileInputStream fileInputStream = new FileInputStream(
                    sourceFile);
            URL url = new URL(upLoadServerUri);

            // Open a HTTP connection to the URL
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true); // Allow Inputs
            conn.setDoOutput(true); // Allow Outputs
            conn.setUseCaches(false); // Don't use a Cached Copy
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("ENCTYPE",
                    "multipart/form-data");
            conn.setRequestProperty("Content-Type",
                    "multipart/form-data;boundary=" + boundary);
            conn.setRequestProperty("bill", sourceFileUri);

            dos = new DataOutputStream(conn.getOutputStream());

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"bill\";filename=\""
                    + sourceFileUri + "\"" + lineEnd);

            dos.writeBytes(lineEnd);

            // create a buffer of maximum size
            bytesAvailable = fileInputStream.available();

            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

            // read file and write it into form...
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0) {

                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math
                        .min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0,
                        bufferSize);

            }

            // send multipart form data necesssary after file
            // data...
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens
                    + lineEnd);

            // Responses from the server (code and message)
//            serverResponseCode = conn.getResponseCode();
            String serverResponseMessage = conn
                    .getResponseMessage();

//            if (serverResponseCode == 200) {

                // messageText.setText(msg);
                //Toast.makeText(ctx, "File Upload Complete.",
                //      Toast.LENGTH_SHORT).show();

                // recursiveDelete(mDirectory1);

//            }

            // close the streams //
            fileInputStream.close();
            dos.flush();
            dos.close();

            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = convertStreamToString(in);

            JSONObject object = new JSONObject(response);
            Log.e("", object.getString("result"));
            return object.getString("result");

        } catch (Exception e) {
            e.printStackTrace();
            return "failed";
        }


    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

}
