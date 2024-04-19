package com.example.habittracker.Slider;

import android.content.Context;

import com.example.habittracker.Structs.EntryValueTree;
import com.example.habittracker.StaticClasses.GLib;
import com.example.habittracker.Structs.WidgetPath;
import com.example.habittracker.Values.GroupValue;
import com.example.habittracker.Values.WidgetValue;
import com.example.habittracker.structures.HeaderNode;
import com.example.habittracker.Widgets.EntryWidget;
import com.example.habittracker.Structs.EntryWidgetParam;


import java.util.ArrayList;
import java.util.HashMap;

public class TextSlider extends EntryWidget {
    public static final String className = "text slider";
    Context context;
    SliderWithLabels sliderWithLabels;
    public TextSlider(Context context) {
        super(context);
        sliderWithLabels = new SliderWithLabels(context);
        setChild(sliderWithLabels);
        this.context = context;
        init();
    }

    private void init(){
        ArrayList<String> values = new ArrayList<>();
        values.add("morning");
        values.add("afternoon");
        values.add("night");

        ArrayList<Integer> labelIndex = new ArrayList<>();
        for(int i = 0; i < values.size(); i++)
            labelIndex.add(i);

        sliderWithLabels.setValues(values, labelIndex);
    }


    public Runnable onDataChangedListener;
    @Override
    public void setOnDataChangedListener(Runnable runnable) {
        onDataChangedListener = runnable;
    }

    @Override
    protected WidgetValue getEntryValueTreeCustom() {
        return null;
    }

    @Override
    protected void setValueCustom(WidgetValue widgetValueString) {

    }

    @Override
    protected void setParamCustom(EntryWidgetParam param) {

    }


    public static class TextSliderParam extends EntryWidgetParam {
        String selected;
        public TextSliderParam(String name, String selected){
            super(name, TextSlider.className);
            this.selected = selected;
        }

        @Override
        public String hierarchyString(int numTabs) {
            return GLib.tabs(numTabs) + "slider\n";
        }

        @Override
        public HeaderNode createHeaderNode() {
            return null;
        }
    }
}
