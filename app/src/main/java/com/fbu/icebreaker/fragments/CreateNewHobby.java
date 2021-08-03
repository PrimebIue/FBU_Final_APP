package com.fbu.icebreaker.fragments;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

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
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import me.kaede.tagview.OnTagClickListener;
import me.kaede.tagview.Tag;
import me.kaede.tagview.TagView;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class CreateNewHobby extends DialogFragment {

    private static final String TAG = "CreateNewHobby";
    private static final String[] HOBBY_TAGS = new String[]{"Sport", "Endurance", "Art", "Esport", "Caring", "Instrument", "Music", "Extreme", "Handcraft", "Technology", "Building", "Outdoors", "Technical Skill"};

    private final ArrayList<String> newHobbyTags = new ArrayList<String>();
    private final WorkCounter workCounter = new WorkCounter(2);

    private static String result = null;

    private EditText etHobbyName;

    private Button btnAddHobby;
    private Button btnCancelHobbyAdd;

    private TagView tvSelectedTags;
    private TagView tvTagsToSelect;
    private TagView tvTagsToSelect2;
    private TagView tvTagsToSelect3;
    private TagView tvTagsToSelect4;


    private String hobbyDescription = "";
    private String responseMessage = "";

    private Integer responseCode = null;

    private ParseFile image = null;


    public CreateNewHobby() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_new_hobby, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Objects.requireNonNull(getDialog()).getWindow().setLayout(getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics().heightPixels);

        etHobbyName = view.findViewById(R.id.etHobbyName);
        btnCancelHobbyAdd = view.findViewById(R.id.btnCancelHobbyAdd);
        btnAddHobby = view.findViewById(R.id.btnAddHobby);
        tvTagsToSelect = view.findViewById(R.id.tvTagsToSelect);
        tvTagsToSelect2 = view.findViewById(R.id.tvTagsToSelect2);
        tvTagsToSelect3 = view.findViewById(R.id.tvTagsToSelect3);
        tvTagsToSelect4 = view.findViewById(R.id.tvTagsToSelect4);
        tvSelectedTags = view.findViewById(R.id.tvSelectedTags);

        // Had to use multiple TagView because it was impossible to fit the tags without modifying the library
        for (int i = 0; i < HOBBY_TAGS.length; i++) {
            Tag tag = new Tag(HOBBY_TAGS[i]);
            tag.tagTextSize = 12;
            if (i <= 4)
                tvTagsToSelect.addTag(tag);
            else if (i <= 8)
                tvTagsToSelect2.addTag(tag);
            else if (i <= 11)
                tvTagsToSelect3.addTag(tag);
            else
                tvTagsToSelect4.addTag(tag);
        }

        tvTagsToSelect.setOnTagClickListener(new OnTagClickListener() {
            @Override
            public void onTagClick(int i, Tag tag) {
                onAddTagClick(tag);
            }
        });

        tvTagsToSelect2.setOnTagClickListener(new OnTagClickListener() {
            @Override
            public void onTagClick(int i, Tag tag) {
                onAddTagClick(tag);
            }
        });

        tvTagsToSelect3.setOnTagClickListener(new OnTagClickListener() {
            @Override
            public void onTagClick(int i, Tag tag) {
                onAddTagClick(tag);
            }
        });

        tvTagsToSelect4.setOnTagClickListener(new OnTagClickListener() {
            @Override
            public void onTagClick(int i, Tag tag) {
                onAddTagClick(tag);
            }
        });

        tvSelectedTags.setOnTagClickListener(new OnTagClickListener() {
            @Override
            public void onTagClick(int i, Tag tag) {
                tvSelectedTags.remove(i);
                newHobbyTags.remove(i);
            }
        });

        btnCancelHobbyAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Objects.requireNonNull(getDialog()).dismiss();
            }
        });

        btnAddHobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get first image from google search

                String searchString = etHobbyName.getText().toString();
                // Remove spaces from the search string
                searchString = searchString.replace(" ", "+");

                // Generate link for google image search

                Uri.Builder builderImageUrl = new Uri.Builder();
                builderImageUrl.scheme("https")
                        .authority("www.googleapis.com")
                        .appendPath("customsearch")
                        .appendPath("v1")
                        .appendQueryParameter("q", searchString)
                        .appendQueryParameter("key", getString(R.string.google_search_api_key))
                        .appendQueryParameter("cx", getString(R.string.search_engine_id))
                        .appendQueryParameter("alt", "json")
                        .appendQueryParameter("searchType", "image");
                String urlImageString = builderImageUrl.build().toString();

                URL urlImage = null;
                try {
                    urlImage = new URL(urlImageString);
                } catch (MalformedURLException e) {
                    Log.e(TAG, "ERROR converting String to URL " + e.toString());
                }

                GoogleImageSearchAsyncTask imageSearchTask = new GoogleImageSearchAsyncTask();

                // Generate link to get hobby description

                Uri.Builder builderDescriptionUrl = new Uri.Builder();
                builderDescriptionUrl.scheme("https")
                        .authority("www.googleapis.com")
                        .appendPath("customsearch")
                        .appendPath("v1")
                        .appendQueryParameter("q", searchString)
                        .appendQueryParameter("key", getString(R.string.google_search_api_key))
                        .appendQueryParameter("cx", getString(R.string.search_engine_id))
                        .appendQueryParameter("alt", "json");
                String urlString = builderDescriptionUrl.build().toString();
                Log.i("Test", urlString);


                URL urlDescription = null;
                try {
                    urlDescription = new URL(urlString);
                } catch (MalformedURLException e) {
                    Log.e(TAG, "ERROR converting String to URL " + e.toString());
                }

                GoogleDescriptionSearchAsyncTask descriptionSearchTask = new GoogleDescriptionSearchAsyncTask();

                // Execute google searches
                imageSearchTask.execute(urlImage);
                descriptionSearchTask.execute(urlDescription);
            }
        });
    }

    private void saveNewHobby() {

        // Set hobby name
        Hobby hobby = new Hobby();
        hobby.setDescription(hobbyDescription);
        hobby.setName(etHobbyName.getText().toString());
        hobby.setTags(newHobbyTags);

        if (image != null)
            hobby.setImage(image);

        if (newHobbyTags.size() != 0) {
            hobby.saveInBackground(e -> {
                if (e != null) {
                    Log.e(TAG, "Error while saving hobby.", e);
                    Toast.makeText(getContext(), R.string.saving_error, Toast.LENGTH_SHORT).show();
                }
                Log.i(TAG, "Hobby save successful");
                Objects.requireNonNull(getDialog()).dismiss();
            });
        } else {
            Toast.makeText(getContext(), "Sorry, add at least 1 tag.", Toast.LENGTH_SHORT).show();
            Objects.requireNonNull(getDialog()).dismiss();
        }
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

    void onAddTagClick(Tag tag) {
        if (newHobbyTags.size() < 4) {
            String tagName = tag.text;
            newHobbyTags.add(tagName);
            tvSelectedTags.addTag(tag);
        } else {
            Toast.makeText(getContext(), "Sorry, you can only add 4 tags per hobby", Toast.LENGTH_SHORT).show();
        }
    }
}