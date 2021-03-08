package com.example.capstonewallet.Views.Fragments;

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

public class ChartFragment extends Fragment {
    private ArrayList<String> prices;
    private ArrayList<String> dates;

    public ChartFragment(ArrayList<String> prices, ArrayList<String> dates) {
        this.prices = prices;
        this.dates = dates;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle args) {
        View view = inflater.inflate(R.layout.chart_fragment, container, false);

        AnyChartView anyChartView = view.findViewById(R.id.chartContainer);
        //anyChartView.setProgressBar(findViewById(R.id.progress_bar));

        Cartesian cartesian = AnyChart.line();
        //cartesian.tooltip();

        List<DataEntry> seriesData = new ArrayList<>();
        //seriesData.add(new ValueDataEntry("2-12-19", 2));
        //seriesData.add(new ValueDataEntry("2-12-20", 10));
        //seriesData.add(new ValueDataEntry("2-12-21", 5));
        //seriesData.add(new ValueDataEntry("2-12-22", 7));

        for(int index = 0; index < dates.size(); index++) {
            seriesData.add(new ValueDataEntry(dates.get(index), Double.parseDouble(prices.get(index))));
        }

        Set set = Set.instantiate();
        set.data(seriesData);
        Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");
        Line series1 = cartesian.line(series1Mapping);
        anyChartView.setChart(cartesian);

        return view;
    }
}
