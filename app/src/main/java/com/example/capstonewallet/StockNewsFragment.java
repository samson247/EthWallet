package com.example.capstonewallet;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import com.loopj.android.http.*;

import com.example.capstonewallet.databinding.StockNewsFragmentBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

//import cz.msebera.android.httpclient.entity.mime.Header;

public class StockNewsFragment extends Fragment {
    RecyclerView recyclerView;
    StockNewsAdapter adapter;
    ArrayList<String> newsText = new ArrayList<>();
    ArrayList<Bitmap> newsImage = new ArrayList<>();
    NewsClient client;
    NewsClient.ArticleData [] articles;
    RecyclerView.RecycledViewPool viewPool;
    StockNewsFragmentBinding binding;
    Bitmap image;
    String urlToImage;
    /*
    public LoginFragment getInstance(Context context) {
        //super(R.layout.create_account);
        //walletModel = new WalletModel(context);
        return this;
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle args) {
        View view = inflater.inflate(R.layout.stock_news_fragment, container, false);
        Log.d("yo123", "oncreateview");

        try {
            setupStockNewsRecyclerView(view);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Add elements to recycler view
        EtherPriceClient client = new EtherPriceClient();
        try {
            Thread thread = new Thread()
            {
                public void run() {
                    try {
                        TextView usdValue = (TextView) view.findViewById(R.id.amountUSD);
                        String price = client.getPrice();
                        usdValue.setText(price);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            thread.start();
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    private void setupStockNewsRecyclerView(View view) throws InterruptedException {
        recyclerView = view.findViewById(R.id.news_recycler);

        setNewsText();
        adapter = new StockNewsAdapter(getContext(), this.newsText, this.newsImage);
        adapter.setHasStableIds(true);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void setNewsText() throws InterruptedException {
        //newsText.add("Placeholder");
       // newsText.add("Placeholder");
       // newsText.add("Placeholder");
       // newsText.add("Placeholder");
       // newsText.add("Placeholder");
       // newsText.add("Placeholder");
       // newsText.add("Placeholder");
       // newsText.add("Placeholder");
        client = new NewsClient();
        articles = new NewsClient.ArticleData[20];
        Thread thread = new Thread()
        {
            public void run() {
                try {
                    articles = client.getArticles();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
        thread.join();


        String title;
        //String urlToImage;
        for(int i = 0; i < 19; i++) {
            title = articles[i].getTitle();
            newsText.add(title);

            urlToImage = articles[i].getImageUrl();

            // Fix multithreading here
            Thread thread2 = new Thread()
            {
                public void run() {
                    try {
                        image = null;
                        URL urlConnection = new URL(urlToImage);
                        HttpURLConnection connection = (HttpURLConnection) urlConnection
                                .openConnection();
                        connection.setDoInput(true);
                        connection.connect();
                        InputStream input = connection.getInputStream();
                        image = BitmapFactory.decodeStream(input);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            thread2.start();

            Log.d("yo123", "Url: " + urlToImage);
            newsImage.add(Bitmap.createScaledBitmap(image, 40, 40, false));
        }
    }
}