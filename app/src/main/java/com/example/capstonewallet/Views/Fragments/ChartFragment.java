package com.example.capstonewallet.Views.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;
import com.example.capstonewallet.Models.Clients.EtherPriceClient;
import com.example.capstonewallet.R;
import com.example.capstonewallet.viewmodels.StockNewsViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * The fragment class to create view of chart of historic ether prices in StockNews fragment
 *
 * @author Sam Dodson
 */
public class ChartFragment extends Fragment {
    private ArrayList<String> prices;
    private ArrayList<String> dates;

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
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle args) {
        View view = inflater.inflate(R.layout.chart_fragment, container, false);

        AnyChartView anyChartView = view.findViewById(R.id.chartContainer);
        //anyChartView.setProgressBar(findViewById(R.id.progress_bar));

        Cartesian cartesian = AnyChart.line();
        //(getResources().getDrawable(R.color.navy, null)
        cartesian.background().fill("#E7E6F1");
        cartesian.background().stroke("5 #15165D");
        cartesian.xAxis(0).labels().enabled(false);
        cartesian.title("Ether Value in USD");
        cartesian.xAxis(0).title("10/12/20 - 3/12/21");
        cartesian.yAxis(0).title("$ Value");
        //cartesian.tooltip();

        List<DataEntry> seriesData = new ArrayList<>();

        for(int index = 0; index < dates.size(); index++) {
            seriesData.add(new ValueDataEntry(dates.get(index), Double.parseDouble(prices.get(index))));
        }

        Set set = Set.instantiate();
        set.data(seriesData);
        Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");
        Line series1 = cartesian.line(series1Mapping);
        anyChartView.setChart(cartesian);
        //anyChartView.setBackground(getResources().getDrawable(R.drawable.border_5, null));
        //anyChartView.getBackground().setColorFilter();

        return view;
    }
}
