package com.example.capstonewallet.Views.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.capstonewallet.Models.Clients.EtherPriceClient;
import com.example.capstonewallet.Models.Clients.NewsClient;
import com.example.capstonewallet.R;
import com.example.capstonewallet.Views.Activities.WalletView;
import com.example.capstonewallet.Views.Adapters.NewsAdapter;
import com.example.capstonewallet.viewmodels.NewsViewModel;
import java.util.ArrayList;

/**
 * Fragment class for news section of app, displays articles, stock price,
 * and chart of historic prices
 */
public class NewsFragment extends Fragment implements View.OnClickListener {
    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    private ArrayList<ArrayList<String>> articleData = new ArrayList<>();
    private NewsViewModel newsViewModel;
    private ArrayList<String> prices;
    private ArrayList<String> dates;
    private TextView chartButton;
    private Spinner unitOptions;
    private EditText ethAmount;
    private TextView usdValue;
    private FragmentContainerView chartContainer;
    private ChartFragment chartFragment;

    /**
     * Initializes values and creates view for fragment
     * @param inflater inflates layout of fragment
     * @param container parent view group of fragment
     * @param args empty bundle of arguments
     * @return view of fragment class
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle args) {
        View view = inflater.inflate(R.layout.news_fragment, container, false);
        newsViewModel = new NewsViewModel();

        // Retrieves article data for recycler view
        if(recyclerView == null) {
            setupStockNewsRecyclerView(view);
        }

        usdValue = (TextView) view.findViewById(R.id.amountUSD);
        /*// Gets price of ether
        EtherPriceClient client = new EtherPriceClient();
        try {
            Thread thread = new Thread() {
                public void run() {
                    try {
                        usdValue = (TextView) view.findViewById(R.id.amountUSD);
                        newsViewModel.startPriceService();
                        usdValue.setText(newsViewModel.getPrice());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            thread.start();
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        chartButton = view.findViewById(R.id.chartIcon);
        chartButton.setOnClickListener(this);
        chartContainer = view.findViewById(R.id.chartContainer);

        unitOptions = view.findViewById(R.id.units);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.unitOptions, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        unitOptions.setAdapter(adapter);
        unitOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // When unit is changed different conversion is required
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(ethAmount.getText().toString().length() > 0 && !ethAmount.getText().toString().equals(".")) {
                    String result = newsViewModel.convertToUsd(ethAmount.getText().toString(),
                            unitOptions.getSelectedItem().toString());
                    usdValue.setText(result);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ethAmount = view.findViewById(R.id.amountValue);
        ethAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            // When text is changed an updated conversion value is found
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                usdValue.setTextColor(getResources().getColor(R.color.navy, null));
                if(s.length() == 0) {
                    usdValue.setText("");
                }
                else if(s.length() > 0 && s.length() < 10 && !ethAmount.getText().toString().equals(".")) {
                    String amount = ethAmount.getText().toString();
                    if(amount.charAt(0) == '.') {
                        String zero = "0";
                        amount = zero.concat(amount);
                    }
                    else if(ethAmount.getText().toString().charAt(ethAmount.getText().toString().length() - 1) == '.')
                    {
                        String zero = "0";
                        amount = amount.concat(zero);
                    }
                    String result = newsViewModel.convertToUsd(amount, unitOptions.getSelectedItem().toString());
                    usdValue.setText(result);
                }
                else {
                    usdValue.setText("Error");
                    usdValue.setTextColor(Color.RED);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        ((WalletView)getActivity()).hideLoadingScreen();
        ((WalletView)getActivity()).showTopFragment();
        return view;
    }

    /**
     * Sets up the recycler view and adds article data
     * @param view the recycler view to add data to
     */
    private void setupStockNewsRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.news_recycler);

        articleData = ((WalletView)getActivity()).getWalletViewModel()
                .getArticles();

        if(articleData == null) {
            NewsClient.ArticleData[] articles = ((WalletView) getActivity()).getWalletViewModel()
                    .getArticleData();
            if(articles != null) {
                    newsViewModel.parseArticleData(articles);
                    articleData = newsViewModel.getArticleData();
                    ((WalletView) getActivity()).getWalletViewModel().setArticles(articleData);
                    Log.d("yo123", "empty");
            }
            else {
                    articleData = newsViewModel.startNewsService();
                    Log.d("yo123", "fromnews");
            }
        }
        adapter = new NewsAdapter(getContext(), articleData.get(0), articleData.get(1));
        adapter.setHasStableIds(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onStart() {
        super.onStart();
        // Gets price of ether
        String price = ((WalletView) getActivity()).getWalletViewModel().getPrice();
        if(price != null) {
            usdValue.setText(price);
            newsViewModel.setPrice(price);
        }
        else {
            EtherPriceClient client = new EtherPriceClient();
            try {
                Thread thread = new Thread() {
                    public void run() {
                        try {
                            newsViewModel.startPriceService();
                            String price = newsViewModel.getPrice();
                            usdValue.setText(price);
                            ((WalletView) getActivity()).getWalletViewModel().setPrice(price);
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
        }

        ArrayList<ArrayList<String>> chartData = ((WalletView) getActivity()).getWalletViewModel().getChartData();
        if(chartData == null) {
            Log.d("yo123", "running chart client.........");
            getChartData();
        }
        else {
            prices = chartData.get(0);
            dates = chartData.get(1);
        }
    }

    /**
     * Displays the chart fragment
     */
    public void addChartFragment() {
        // If chart hasn't yet been created
        if(chartFragment == null) {
            FragmentManager fragmentManager = getChildFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            chartFragment = new ChartFragment(prices, dates);
            fragmentTransaction.add(R.id.chartContainer, chartFragment, null)
                    .addToBackStack("chart");
            fragmentTransaction.commit();
        }
        else if(!chartContainer.isShown()){
            // If chart has already been created it is displayed again
            chartContainer.setVisibility(View.VISIBLE);
            getChildFragmentManager().beginTransaction()
                    .add(R.id.chartContainer, chartFragment, null)
                    .addToBackStack("chart").commit();
        }
    }

    /**
     * Removes chart fragment from view and pops from back stack
     */
    public void popChartFragment() {
        getChildFragmentManager().popBackStack();
        chartContainer.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    /**
     * Handles click events in this fragment
     * @param v the view that generated the click event
     */
    @Override
    public void onClick(View v) {
        // Displays chart after initializing it
        if(v.getId() == chartButton.getId()) {
            recyclerView.setVisibility(View.INVISIBLE);
            chartContainer.setBackground(getResources().getDrawable(R.color.navy, null));
            if(prices == null || dates == null) {
                getChartData();
            }
            addChartFragment();
        }
    }

    /**
     * Gets data to display in chart
     */
    public void getChartData() {
        Thread thread = new Thread() {
            public void run() {
                try {
                    newsViewModel.getChartData();
                    prices = newsViewModel.getChartPrices();
                    dates = newsViewModel.getChartDates();
                    ArrayList<ArrayList<String>> chartData = new ArrayList<>();
                    chartData.add(prices);
                    chartData.add(dates);
                    ((WalletView) getActivity()).getWalletViewModel().setChartData(chartData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}