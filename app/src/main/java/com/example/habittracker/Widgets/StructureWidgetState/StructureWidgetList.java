package com.example.habittracker.Widgets.StructureWidgetState;

import android.content.Context;
import android.view.View;

import com.example.habittracker.Structs.EntryWidgetParam;
import com.example.habittracker.Widgets.GroupWidget;
import com.example.habittracker.Widgets.ListWidget;
import com.example.habittracker.Widgets.StructureWidget;
import com.example.habittracker.Widgets.Widget;

import java.util.ArrayList;

public class StructureWidgetList implements Widget {
    private Context context;
    private GroupWidget groupWidget;
    private ArrayList<StructureWidget> structureWidgets = new ArrayList<>();
    private GroupWidget parent;
    public StructureWidgetList(Context context, GroupWidget parent) {
        this.context = context;
        this.parent = parent;
        groupWidget = new GroupWidget(context);
        parent.addWidget(groupWidget);
        init();
    }

    private void init(){
        groupWidget.addButton(view -> {
            addStructureWidget();
        });
    }

    public StructureWidget addStructureWidget(){
        StructureWidget structureWidget = new StructureWidget(context);
        structureWidgets.add(structureWidget);
        groupWidget.addWidget(structureWidget);
        return structureWidget;
    }

    @Override
    public void setOnDataChangedListener(Runnable runnable) {

    }

    @Override
    public EntryWidgetParam getParam() {
        return new ListWidget.ListParam(null, (GroupWidget.GroupWidgetParam) groupWidget.getParam(), new ArrayList<>());
    }

    @Override
    public void setParam(EntryWidgetParam params) {
        ListWidget.ListParam listParam = (ListWidget.ListParam) params;
        ArrayList<EntryWidgetParam> children = listParam.cloneableWidget.params;
        for(EntryWidgetParam entryWidgetParam: children){
            StructureWidget structureWidget = addStructureWidget();
            structureWidget.setParam(entryWidgetParam);
        }
    }

    @Override
    public View getView() {
        return null;
    }
}
