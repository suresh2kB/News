package com.example.news;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils() {

    }

    public static List<News> fetchNewsData(String requestUrl) {
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the http request", e);
        }
        List<News> news = extractFeatureFromJson(jsonResponse);
        return news;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }


    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";


        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {

                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<News> extractFeatureFromJson(String newsJSON) {

        if (TextUtils.isEmpty(newsJSON)) {
            return null;
        }

        List<News> news = new ArrayList<>();

        try {

            JSONObject baseJsonResponse = new JSONObject(newsJSON);
            //JSONArray newsresults = baseJsonResponse.getJSONArray("response");
            String response = baseJsonResponse.getString("response");
            JSONObject baseJsonResponse1 = new JSONObject(response);
            JSONArray newsresultsArray = baseJsonResponse1.getJSONArray(("results"));
            News news1 = null;
            String authorname;
            for (int i = 0; i < newsresultsArray.length(); i++) {
                JSONObject p = newsresultsArray.getJSONObject(i);
                String webTitle = p.getString("webTitle");
                String sectionId = p.getString("sectionId");
                String webUrl = p.getString("webUrl");
                //JSONArray properties = p.getJSONArray(("tags"));
                /*
                HELLO SIR ACTUALLY I AM NOT UNDERSTANDING WHY ITS SHOWING BLANK SPACE AND NOT SHOWING AUTHOR NAME SO PLEASE HELP ME
                 */
                String author = "";
                if (p.has("tags")) {
                    JSONArray authorArray = p.getJSONArray("tags");
                    if (authorArray != null && authorArray.length() >= 0) {
                        for (int j = 0; j < authorArray.length(); j++) {
                            JSONObject object = authorArray.getJSONObject(j);
                            author = object.getString("webTitle");
                        }
                    }
                } else author = "Author NA";
                //News news1 = new News(webTitle,authorname, sectionId, webUrl);
                news1 = new News(webTitle, author, sectionId, webUrl);
                news.add(news1);

                //JSONObject currentNews = newsresultsArray.getJSONObject(i);

                //String headline = currentNews.getString("type");
                //JSONObject headline = currentNews.getJSONObject("type");

                //String discription = currentNews.getString("sectionId");

                //String url = currentNews.getString("webUrl");

                //String headline = baseJsonResponse.optString("type");

                //String discription = currentNews.getString("sectionId");

                //String url = currentNews.getString("webUrl");

                ///News news1 = new News(type, discription, url);

                //news.add(news1);
            }
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the News JSON results", e);
        }

        return news;

    }

}


