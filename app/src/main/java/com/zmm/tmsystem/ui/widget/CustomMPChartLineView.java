package com.zmm.tmsystem.ui.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.zmm.tmsystem.R;

import java.util.ArrayList;
import java.util.List;

import static com.github.mikephil.charting.components.Legend.LegendPosition.ABOVE_CHART_LEFT;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/9/1
 * Email:65489469@qq.com
 */
public class CustomMPChartLineView extends RelativeLayout {


    private LineChart mChart;

    public CustomMPChartLineView(Context context) {
        this(context, null);
    }

    public CustomMPChartLineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomMPChartLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = View.inflate(getContext(), R.layout.item_line_chart_custom, this);
        mChart = (LineChart) view.findViewById(R.id.line_chart);


        mChart.setTouchEnabled(false);//设置是否可触摸

//        mChart.setNoDataText("当前数据为空");//设置当 chart 为空时显示的描述文字
        mChart.setNoDataText("");//设置当 chart 为空时显示的描述文字

        mChart.getDescription().setEnabled(false);//隐藏右下角描述

        //setChartData下面的"圈数" 的设置
        Legend legend = mChart.getLegend();
        legend.setEnabled(true);//设置Legend启用或禁用。 如果禁用， Legend 将不会被绘制。
        legend.setFormSize(30f);
        legend.setForm(Legend.LegendForm.NONE);//样式，无
        legend.setPosition(ABOVE_CHART_LEFT);
        legend.setXOffset(-30f);
        legend.setYOffset(10f);


        XAxis xAxis = mChart.getXAxis();
        xAxis.setEnabled(false);//设置轴启用或禁用

        //Y轴   getAxisLeft
        YAxis yAxis = mChart.getAxisLeft();
        yAxis.setEnabled(true);
        yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        yAxis.setTextSize(8);
        yAxis.setTextColor(this.getResources().getColor(R.color.chart_text));
//        yAxis.setAxisMinimum(0);
//        yAxis.setAxisMaximum(120);
        yAxis.setLabelCount(5, false);
        yAxis.setAxisLineWidth(2f);
        yAxis.setDrawGridLines(true);//是否展示网格线


        //右侧，一般不用，设为false即可
        mChart.getAxisRight().setEnabled(false);

    }


    public void setChartData(ArrayList<Entry> entries, int minValue, String title, int level) {


        mChart.invalidate();
        mChart.notifyDataSetChanged();

        if (level >= 7) {
            mChart.getAxisLeft().setAxisMaximum(150);
        } else {
            mChart.getAxisLeft().setAxisMaximum(120);
        }

        if (minValue > 0 && minValue < 20) {
            mChart.getAxisLeft().setAxisMinimum(0);

        } else if (minValue > 20 && minValue < 40) {
            mChart.getAxisLeft().setAxisMinimum(20);

        } else if (minValue > 40 && minValue < 60) {
            mChart.getAxisLeft().setAxisMinimum(40);

        } else if (minValue > 60 && minValue < 80) {
            mChart.getAxisLeft().setAxisMinimum(60);

        } else if (minValue > 80) {
            mChart.getAxisLeft().setAxisMinimum(80);

        }


        LineDataSet lineDataSet;

        if (mChart.getData() != null && mChart.getData().getDataSetCount() > 0) {

            lineDataSet = (LineDataSet) mChart.getData().getDataSetByIndex(0);

            lineDataSet.setValues(entries);

            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            //曲线标题
            lineDataSet = new LineDataSet(entries, title);
            //绘制曲线颜色
            lineDataSet.setColor(this.getResources().getColor(R.color.lable_blue));
            lineDataSet.setLineWidth(1f);
            //是否绘制阴影
            lineDataSet.setDrawFilled(false);

            LineData data = new LineData(lineDataSet);
            data.setValueTextColor(Color.WHITE);
            data.setValueTextSize(9f);

            mChart.setData(data);
        }


        //去除折线图上的小圆圈
        List<ILineDataSet> sets = mChart.getData().getDataSets();

        for (ILineDataSet iSet : sets) {

            LineDataSet set = (LineDataSet) iSet;
//            set.setDrawValues(false);
//            set.setDrawCircles(false);
//            set.setMode(LineDataSet.Mode.LINEAR);

            set.setDrawValues(false);
            set.setDrawCircles(true);
            set.setCircleColor(this.getResources().getColor(R.color.colorAccent));
            set.setMode(LineDataSet.Mode.LINEAR);
        }

    }


}
