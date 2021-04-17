package com.example.capstonewallet.Views.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import androidx.fragment.app.Fragment;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.example.capstonewallet.R;
import java.util.ArrayList;
import java.util.List;

/**
 * The fragment class to create view of chart of historic ether prices in StockNews fragment
 *
 * @author Sam Dodson
 */
public class ChartFragment extends Fragment implements View.OnClickListener {
    private ArrayList<String> prices;
    private ArrayList<String> dates;
    private ProgressBar progressBar;
    private ImageButton closeButton;

    /**
     * The argumented constructor for the ChartFragment class
     * @param prices a list of historic ether prices
     * @param dates a list of dates corresponding the ether prices
     */
    public ChartFragment(ArrayList<String> prices, ArrayList<String> dates) {
        this.prices = prices;
        this.dates = dates;
    }

    /**
     * The method called to initialize view for ChartFragment class
     * @param inflater the layout inflater for chart_fragment.xml
     * @param container the parent view of ChartFragment
     * @param args null bundle
     * @return the view for this fragment class
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle args) {
        View view = inflater.inflate(R.layout.chart_fragment, container, false);

        AnyChartView anyChartView = view.findViewById(R.id.chartContainer);
        progressBar = view.findViewById(R.id.chartProgressBar);
        closeButton = view.findViewById(R.id.closeChartButton);
        closeButton.setOnClickListener(this);

        // Sets chart layout and initializes headings and axes
        Cartesian cartesian = AnyChart.line();
        cartesian.background().fill("#E7E6F1");
        cartesian.background().stroke("5 #15165D");
        cartesian.xAxis(0).labels().enabled(false);
        cartesian.title("Ether Value in USD");
        String title = dates.get(0) + " -- " + dates.get(dates.size() - 1);
        cartesian.xAxis(0).title(title);
        cartesian.yAxis(0).title("$ Value");
        List<DataEntry> seriesData = new ArrayList<>();

        // Data is added to chart
        for(int index = 0; index < dates.size(); index++) {
            seriesData.add(new ValueDataEntry(dates.get(index), Double.parseDouble(prices.get(index))));
        }

        Set set = Set.instantiate();
        set.data(seriesData);
        Mapping mapping = set.mapAs("{ x: 'x', value: 'value' }");
        cartesian.tooltip().titleFormat("{%x}");
        cartesian.tooltip().format("${%value}");
        Line series1 = cartesian.line(mapping);
        anyChartView.setChart(cartesian);

        // Once chart is rendered progress bar becomes invisible
        anyChartView.setOnRenderedListener(new AnyChartView.OnRenderedListener() {
            @Override
            public void onRendered() {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
        return view;
    }

    /**
     * Removes chart when close button is pushed
     * @param v the view for this fragment class
     */
    @Override
    public void onClick(View v) {
        ((NewsFragment)getParentFragment()).popChartFragment();
    }
}
