package com.xxmassdeveloper.mpchartexample;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import androidx.core.content.ContextCompat;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.xxmassdeveloper.mpchartexample.custom.RadarMarkerView;
import com.xxmassdeveloper.mpchartexample.notimportant.DemoBase;
import java.util.ArrayList;

public class RadarChartActivity extends DemoBase {

  private RadarChart chart;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.activity_radarchart);

    setTitle("RadarChartActivity");

    chart = findViewById(R.id.chart1);
    chart.setBackgroundColor(Color.rgb(255, 255, 255));

    chart.getDescription().setEnabled(false);
    chart.setDrawWeb(true);

    chart.setWebLineWidth(0f);
    chart.setWebColor(Color.BLACK);
    chart.setWebLineWidthInner(0f);
    chart.setWebColorInner(Color.YELLOW);
    chart.setWebAlpha(255);

    // create a custom MarkerView (extend MarkerView) and specify the layout
    // to use for it
    MarkerView mv = new RadarMarkerView(this, R.layout.radar_markerview);
    mv.setChartView(chart); // For bounds control
    chart.setMarker(mv); // Set the marker to the chart

    setData();

    chart.animateXY(1400, 1400, Easing.EaseInOutQuad);

    XAxis xAxis = chart.getXAxis();
    xAxis.setTypeface(tfLight);
    xAxis.setTextSize(12f);
    xAxis.setYOffset(0f);
    xAxis.setXOffset(0f);

    xAxis.setValueFormatter(new ValueFormatter() {

      private final String[] mActivities = new String[]{"Protein", "Fat", "Carbs"};

      @Override
      public String getFormattedValue(float value) {
        return mActivities[(int) value % mActivities.length];
      }
    });
    xAxis.setTextColor(Color.BLACK);

    YAxis yAxis = chart.getYAxis();
    yAxis.setTypeface(tfLight);
    yAxis.setLabelCount(3, false);
    yAxis.setTextSize(12f);
    yAxis.setAxisMinimum(0f);
    yAxis.setAxisMaximum(100f);
    yAxis.setDrawLabels(false);

    Legend l = chart.getLegend();
    l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
    l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
    l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
    l.setDrawInside(false);
    l.setTypeface(tfLight);
    l.setXEntrySpace(7f);
    l.setYEntrySpace(5f);
    l.setTextColor(Color.BLACK);
  }

  private void setData() {

    float mul = 100;
    float min = 0;
    int cnt = 3;

    ArrayList<RadarEntry> entries1 = new ArrayList<>();
    ArrayList<RadarEntry> entries2 = new ArrayList<>();

    // NOTE: The order of the entries when being added to the entries array determines their position around the center of
    // the chart.
    for (int i = 0; i < cnt; i++) {
      float val1 = (float) (Math.random() * mul) + min;
      entries1.add(new RadarEntry(val1));

      float val2 = (float) (Math.random() * mul) + min;
      entries2.add(new RadarEntry(100));
    }

    RadarDataSet set1 = new RadarDataSet(entries1, "Last Week");
    set1.setColor(Color.rgb(243, 249, 251));
    set1.setFillColor(Color.rgb(243, 249, 251));
    set1.setDrawFilled(true);
    set1.setFillAlpha(85);
    set1.setDrawHighlightCircleEnabled(true);
    set1.setDrawHighlightIndicators(true);

    RadarDataSet set2 = new RadarDataSet(entries2, "This Week");
    set2.setColor(Color.rgb(121, 162, 175));
    set2.setFillColor(Color.rgb(121, 162, 175));
    set2.setDrawFilled(true);
    set2.setFillAlpha(180);
    set2.setDrawHighlightCircleEnabled(true);
    set2.setDrawHighlightIndicators(true);

    ArrayList<IRadarDataSet> sets = new ArrayList<>();
    sets.add(set2);
    sets.add(set1);

    RadarData data = new RadarData(sets);
    data.setValueTypeface(tfLight);
    data.setValueTextSize(8f);
    data.setDrawValues(false);
    data.setValueTextColor(Color.BLACK);

    chart.setData(data);
    chart.invalidate();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.radar, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {

    switch (item.getItemId()) {
      case R.id.viewGithub: {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(
            "https://github.com/PhilJay/MPAndroidChart/blob/master/MPChartExample/src/com/xxmassdeveloper/mpchartexample/RadarChartActivity.java"));
        startActivity(i);
        break;
      }
      case R.id.actionToggleValues: {
        for (IDataSet<?> set : chart.getData().getDataSets()) {
          set.setDrawValues(!set.isDrawValuesEnabled());
        }

        chart.invalidate();
        break;
      }
      case R.id.actionToggleHighlight: {
        if (chart.getData() != null) {
          chart.getData().setHighlightEnabled(!chart.getData().isHighlightEnabled());
          chart.invalidate();
        }
        break;
      }
      case R.id.actionToggleRotate: {
        if (chart.isRotationEnabled()) {
          chart.setRotationEnabled(false);
        } else {
          chart.setRotationEnabled(true);
        }
        chart.invalidate();
        break;
      }
      case R.id.actionToggleFilled: {

        ArrayList<IRadarDataSet> sets = (ArrayList<IRadarDataSet>) chart.getData()
            .getDataSets();

        for (IRadarDataSet set : sets) {
          if (set.isDrawFilledEnabled()) {
            set.setDrawFilled(false);
          } else {
            set.setDrawFilled(true);
          }
        }
        chart.invalidate();
        break;
      }
      case R.id.actionToggleHighlightCircle: {

        ArrayList<IRadarDataSet> sets = (ArrayList<IRadarDataSet>) chart.getData()
            .getDataSets();

        for (IRadarDataSet set : sets) {
          set.setDrawHighlightCircleEnabled(!set.isDrawHighlightCircleEnabled());
        }
        chart.invalidate();
        break;
      }
      case R.id.actionToggleXLabels: {
        chart.getXAxis().setEnabled(!chart.getXAxis().isEnabled());
        chart.notifyDataSetChanged();
        chart.invalidate();
        break;
      }
      case R.id.actionToggleYLabels: {

        chart.getYAxis().setEnabled(!chart.getYAxis().isEnabled());
        chart.invalidate();
        break;
      }
      case R.id.animateX: {
        chart.animateX(1400);
        break;
      }
      case R.id.animateY: {
        chart.animateY(1400);
        break;
      }
      case R.id.animateXY: {
        chart.animateXY(1400, 1400);
        break;
      }
      case R.id.actionToggleSpin: {
        chart.spin(2000, chart.getRotationAngle(), chart.getRotationAngle() + 360,
            Easing.EaseInOutCubic);
        break;
      }
      case R.id.actionSave: {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_GRANTED) {
          saveToGallery();
        } else {
          requestStoragePermission(chart);
        }
        break;
      }
    }
    return true;
  }

  @Override
  protected void saveToGallery() {
    saveToGallery(chart, "RadarChartActivity");
  }
}
