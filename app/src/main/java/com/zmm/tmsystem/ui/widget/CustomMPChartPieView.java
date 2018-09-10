package com.zmm.tmsystem.ui.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.MPPointF;
import com.zmm.tmsystem.R;

import java.util.ArrayList;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/9/1
 * Email:65489469@qq.com
 */
public class CustomMPChartPieView extends RelativeLayout {


    private PieChart mChart;

    public CustomMPChartPieView(Context context) {
        this(context,null);
    }

    public CustomMPChartPieView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomMPChartPieView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = View.inflate(getContext(), R.layout.item_chart_custom, this);
        mChart = (PieChart) view.findViewById(R.id.pie_chart);


        mChart.setUsePercentValues(true);
        mChart.getDescription().setEnabled(false);
        mChart.setExtraOffsets(5, 10, 5, 5);

        mChart.setDragDecelerationFrictionCoef(0.95f);

//        mChart.setCenterText("男女\n比例");

        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(Color.WHITE);

        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(1);

        mChart.setHoleRadius(25f);
        mChart.setTransparentCircleRadius(50f);

        mChart.setDrawCenterText(true);

        mChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(true);
        mChart.setHighlightPerTapEnabled(true);


        //标记 是否显示
        Legend l = mChart.getLegend();
        l.setEnabled(true);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(true);
        //标记 x，y轴距离
        l.setXEntrySpace(10f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // entry label styling  内部文字颜色和大小
        mChart.setEntryLabelColor(Color.WHITE);
        mChart.setEntryLabelTextSize(15f);

    }

    public void setData(ArrayList<PieEntry> entries, ArrayList<Integer> colors,String label) {

        mChart.setCenterText(label);


        if(entries == null && entries.size() == 0){
            mChart.setNoDataText("");
        }

        PieDataSet dataSet = new PieDataSet(entries, "");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        //颜色
        dataSet.setColors(colors);
        dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        //内部 百分比颜色和大小
        data.setValueTextSize(15f);
        data.setValueTextColor(Color.WHITE);
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();
    }
}
