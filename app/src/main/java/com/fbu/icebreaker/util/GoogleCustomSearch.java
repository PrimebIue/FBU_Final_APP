package com.fbu.icebreaker.util;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.fbu.icebreaker.R;
import com.fbu.icebreaker.subclasses.Hobby;
import com.parse.ParseFile;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class GoogleCustomSearch {

    private static final String TAG = "GoogleCustomSearch";

    private static String result = null;

    private final WorkCounter workCounter = new WorkCounter(2);

    private Integer responseCode = null;

    private Dialog dialog;

    private String responseMessage = "";
    private String hobbyName;
    private Context context;

    private String hobbyDescription = "";
    private List<String> newHobbyTags;
    private ParseFile image = null;

    public void fetchHobbyResources(String searchString, List<String> tags, Context context, Dialog dialog) {
        // Remove spaces from the search string
        this.dialog = dialog;
        this.context = context;
        newHobbyTags = tags;
        hobbyName = searchString;
        searchString = searchString.replace(" ", "+");

        // Generate link for google image search

        Uri.Builder builderImageUrl = new Uri.Builder();
        builderImageUrl.scheme("https")
                .authority("www.googleapis.com")
                .appendPath("customsearch")
                .appendPath("v1")
                .appendQueryParameter("q", searchString)
                .appendQueryParameter("key", context.getString(R.string.google_search_api_key))
                .appendQueryParameter("cx", context.getString(R.string.search_engine_id))
                .appendQueryParameter("alt", "json")
                .appendQueryParameter("searchType", "image");
        String urlImageString = builderImageUrl.build().toString();

        URL urlImage = null;
        try {
            urlImage = new URL(urlImageString);
        } catch (MalformedURLException e) {
            Log.e(TAG, "ERROR converting String to URL " + e.toString());
        }

        // Generate link to get hobby description

        Uri.Builder builderDescriptionUrl = new Uri.Builder();
        builderDescriptionUrl.scheme("https")
                .authority("www.googleapis.com")
                .appendPath("customsearch")
                .appendPath("v1")
                .appendQueryParameter("q", searchString)
                .appendQueryParameter("key", context.getString(R.string.google_search_api_key))
                .appendQueryParameter("cx", context.getString(R.string.search_engine_id))
                .appendQueryParameter("alt", "json");
        String urlString = builderDescriptionUrl.build().toString();
        Log.i("Test", urlString);

        URL urlDescription = null;
        try {
            urlDescription = new URL(urlString);
        } catch (MalformedURLException e) {
            Log.e(TAG, "ERROR converting String to URL " + e.toString());
        }

        GoogleImageSearchAsyncTask imageSearchTask = new GoogleImageSearchAsyncTask();
        GoogleDescriptionSearchAsyncTask descriptionSearchTask = new GoogleDescriptionSearchAsyncTask();

        // Execute google searches
        imageSearchTask.execute(urlImage);
        descriptionSearchTask.execute(urlDescription);
    }

    @SuppressLint("StaticFieldLeak")
    private class GoogleImageSearchAsyncTask extends AsyncTask<URL, Integer, String> {

        protected void onPreExecute() {
            Log.d(TAG, "AsyncTask - onPreExecute");
            // Progress bar would go here
        }

        @Override
        protected String doInBackground(URL... urls) {

            URL url = urls[0];

            // Http connection
            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                Log.e(TAG, "Http connection ERROR " + e.toString());
            }

            try {
                assert conn != null;
                responseCode = conn.getResponseCode();
                responseMessage = conn.getResponseMessage();
            } catch (IOException e) {
                Log.e(TAG, "Http getting response code ERROR " + e.toString());
            }

            try {
                if (responseCode != null && responseCode == 200) {

                    // response OK
                    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;

                    while ((line = rd.readLine()) != null) {
                        sb.append(line).append("\n");
                    }
                    rd.close();
                    conn.disconnect();
                    result = sb.toString();

                } else {

                    // response problem
                    String errorMsg = "Http ERROR response " + responseMessage + "\n" + "Are you online ? " + "\n" + "Make sure to replace in code your own Google API key and Search Engine ID";
                    Log.e(TAG, errorMsg);
                    result = errorMsg;

                }
                return result;
            } catch (IOException e) {
                Log.e(TAG, "Http Response ERROR " + e.toString());
            }
            return null;
        }

        protected void onProgressUpdate(Integer... progress) {
        }

        protected void onPostExecute(String result) {

            String imageUrl = null;
            try {
                JSONObject jsonObject = new JSONObject(result);

                for (int i = 0; i < jsonObject.getJSONArray("items").length(); i++) {

                    if (!jsonObject.getJSONArray("items").getJSONObject(i).getString("displayLink").equals("www.facebook.com")) {
                        imageUrl = jsonObject.getJSONArray("items").getJSONObject(i).getString("link");
                        break;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.i(TAG, "ImageUrl = " + imageUrl);

            AsyncGettingBitmapFromUrl getBitmap = new AsyncGettingBitmapFromUrl();
            getBitmap.execute(imageUrl);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class GoogleDescriptionSearchAsyncTask extends AsyncTask<URL, Integer, String> {

        protected void onPreExecute() {
            Log.d(TAG, "AsyncTask - onPreExecute");
            // Progress bar would go here
        }

        @Override
        protected String doInBackground(URL... urls) {

            URL url = urls[0];
            Log.d(TAG, "AsyncTask - doInBackground, url=" + url);

            // Http connection
            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                Log.e(TAG, "Http connection ERROR " + e.toString());
            }

            try {
                assert conn != null;
                responseCode = conn.getResponseCode();
                responseMessage = conn.getResponseMessage();
            } catch (IOException e) {
                Log.e(TAG, "Http getting response code ERROR " + e.toString());
            }

            Log.d(TAG, "Http response code =" + responseCode + " message=" + responseMessage);

            try {
                if (responseCode != null && responseCode == 200) {

                    // response OK
                    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;

                    while ((line = rd.readLine()) != null) {
                        sb.append(line).append("\n");
                    }
                    rd.close();
                    conn.disconnect();
                    result = sb.toString();

                    Log.d(TAG, "result=" + result);

                    return result;

                } else {

                    // response problem
                    String errorMsg = "Http ERROR response " + responseMessage + "\n" + "Are you online ? " + "\n" + "Make sure to replace in code your own Google API key and Search Engine ID";
                    Log.e(TAG, errorMsg);
                    result = errorMsg;
                    return result;
                }
            } catch (IOException e) {
                Log.e(TAG, "Http Response ERROR " + e.toString());
            }
            return null;
        }

        protected void onProgressUpdate(Integer... progress) {
            Log.d(TAG, "AsyncTask - onProgressUpdate, progress=" + Arrays.toString(progress));
        }

        protected void onPostExecute(String result) {
            Log.d(TAG, "AsyncTask Description - onPostExecute, result=" + result);
            try {
                JSONObject jsonObject = new JSONObject(result);
                hobbyDescription = jsonObject.getJSONArray("items").getJSONObject(0).getString("snippet");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            workCounter.taskFinished();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class AsyncGettingBitmapFromUrl extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {

            Bitmap bitmap;
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(input);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {

            System.out.println("bitmap" + bitmap);

            try {
                if (bitmap != null) {
                    bitmap = getResizedBitmap(bitmap, bitmap.getHeight(), bitmap.getWidth());
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] bitmapBytes = stream.toByteArray();
                    image = new ParseFile("myImage.png", bitmapBytes);
                }
            } catch (NullPointerException e) {
                Log.e(TAG, "Can't get image ", e);
            }
            workCounter.taskFinished();
        }
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        return Bitmap.createBitmap(bm, 0, 0, width, height,
                matrix, false);
    }

    public class WorkCounter {
        private int runningTasks;

        public WorkCounter(int numberOfTasks) {
            this.runningTasks = numberOfTasks;
        }

        // Only call this in onPostExecute! (or add synchronized to method declaration)
        public void taskFinished() {
            if (--runningTasks == 0) {
                saveNewHobby();
            }
        }
    }

    private void saveNewHobby() {

        // Set hobby name
        Hobby hobby = new Hobby();
        hobby.setDescription(hobbyDescription);
        hobby.setName(hobbyName);
        hobby.setTags(newHobbyTags);

        if (image != null)
            hobby.setImage(image);

        if (newHobbyTags.size() != 0) {
            hobby.saveInBackground(e -> {
                if (e != null) {
                    Log.e(TAG, "Error while saving hobby.", e);
                    Toast.makeText(context, R.string.saving_error, Toast.LENGTH_SHORT).show();
                }
                Log.i(TAG, "Hobby save successful");
                dialog.dismiss();
            });
        } else {
            Toast.makeText(context, "Sorry, add at least 1 tag.", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }
    }
}
