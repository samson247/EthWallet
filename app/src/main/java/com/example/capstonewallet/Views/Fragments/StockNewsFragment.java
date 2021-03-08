package com.example.capstonewallet.Views.Fragments;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import com.loopj.android.http.*;

import com.example.capstonewallet.Models.Clients.ChartClient;
import com.example.capstonewallet.Models.Clients.EtherPriceClient;
import com.example.capstonewallet.Models.Clients.NewsClient;
import com.example.capstonewallet.R;
import com.example.capstonewallet.Views.Adapters.ContactsAdapter;
import com.example.capstonewallet.Views.Adapters.StockNewsAdapter;
import com.example.capstonewallet.databinding.StockNewsFragmentBinding;
import com.example.capstonewallet.viewmodels.StockNewsViewModel;

import java.lang.reflect.Array;
import java.util.ArrayList;

//import cz.msebera.android.httpclient.entity.mime.Header;

public class StockNewsFragment extends Fragment implements View.OnClickListener {
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
    StockNewsViewModel stockNewsViewModel;
    private ArrayList<String> prices;
    private ArrayList<String> dates;
    private FragmentManager fragmentManager;
    private TextView chartButton;
    private Spinner unitOptions;
    private EditText ethAmount;
    private TextView usdValue;

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
        stockNewsViewModel = new StockNewsViewModel();


        setupStockNewsRecyclerView(view);

        // Add elements to recycler view
        EtherPriceClient client = new EtherPriceClient();
        try {
            Thread thread = new Thread()
            {
                public void run() {
                    try {
                        usdValue = (TextView) view.findViewById(R.id.amountUSD);
                        stockNewsViewModel.startPriceService();
                        usdValue.setText(stockNewsViewModel.getPrice());
                        stockNewsViewModel.getChartData();
                        prices = stockNewsViewModel.getChartPrices();
                        dates = stockNewsViewModel.getChartDates();
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

        chartButton = view.findViewById(R.id.chartIcon);
        chartButton.setOnClickListener(this::onClick);
        unitOptions = view.findViewById(R.id.units);
        //unitOptions.getSelectedItem().toString();

        ethAmount = view.findViewById(R.id.amountValue);
        ethAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                usdValue.setTextColor(getResources().getColor(R.color.navy, null));
                if(s.length() == 0) {
                    usdValue.setText("");
                }
                else if(s.length() > 0 && s.length() < 10) {
                    String result = stockNewsViewModel.convertToUsd(ethAmount.getText().toString(), unitOptions.getSelectedItem().toString());
                    usdValue.setText(result);
                }
                else {
                    usdValue.setText("Error");
                    usdValue.setTextColor(Color.RED);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        return view;
    }

    private void setupStockNewsRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.news_recycler);

        newsText = stockNewsViewModel.startNewsService();

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
           /* Thread thread2 = new Thread()
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
            thread2.start();*/

            Log.d("yo123", "Url: " + urlToImage);
            //newsImage.add(Bitmap.createScaledBitmap(image, 40, 40, false));
        }
    }

    public void addChartFragment() {
        fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ChartFragment fragment = new ChartFragment(prices, dates);
        fragmentTransaction.add(R.id.chartContainer, fragment, null);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == chartButton.getId()) {
            addChartFragment();
        }
    }
}