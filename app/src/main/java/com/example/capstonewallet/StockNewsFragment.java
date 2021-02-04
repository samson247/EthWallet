package com.example.capstonewallet;

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

import java.util.ArrayList;

//import cz.msebera.android.httpclient.entity.mime.Header;

public class StockNewsFragment extends Fragment {
    RecyclerView recyclerView;
    StockNewsAdapter adapter;
    ArrayList<String> newsText = new ArrayList<>();
    NewsClient client;
    NewsClient.ArticleData [] articles;
    RecyclerView.RecycledViewPool viewPool;
    StockNewsFragmentBinding binding;
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

        setTransactionText();
        adapter = new StockNewsAdapter(getContext(), this.newsText);
        adapter.setHasStableIds(true);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void setTransactionText() throws InterruptedException {
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
        for(int i = 0; i < 19; i++) {
            title = articles[i].getTitle();
            newsText.add(title);
        }
    }
}