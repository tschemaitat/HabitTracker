package com.example.habittracker.Inflatables;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.habittracker.BorderView;
import com.example.habittracker.CustomTextInputLayout;
import com.example.habittracker.DataTree;
import com.example.habittracker.GLib;
import com.example.habittracker.MainActivity;
import com.example.habittracker.R;
import com.example.habittracker.Structs.EntryWidgetParam;
import com.example.habittracker.Widgets.CustomEditText;
import com.example.habittracker.Widgets.DropDown;
import com.example.habittracker.Widgets.GroupWidget;
import com.example.habittracker.Widgets.ListWidget;
import com.example.habittracker.Widgets.StructureWidget;
import com.google.android.material.textfield.TextInputLayout;

public class TestPage implements Inflatable {
    private LinearLayout linearLayout;
    private Context context;

    public TestPage(Context context){
        this.context = context;
        linearLayout = MainActivity.createVerticalLayout();
        //setupTestLayout();
        testDataTreeValueExport();
        //testNewEditText();
    }

    @Override
    public View getView() {
        return linearLayout;
    }

    @Override
    public void onRemoved() {

    }

    @Override
    public void onOpened() {

    }


    public void setupTestLayout(){
        StructureWidget structureWidget = new StructureWidget(context);
        structureWidget.setOnDataChangedListener(()->{});
        linearLayout.addView(structureWidget.getView());

        CustomEditText editText = new CustomEditText(context);
        linearLayout.addView(editText.getView());
    }

    public void testDataTreeValueExport(){
        GroupWidget.GroupWidgetParam groupWidgetParam = new GroupWidget.GroupWidgetParam(null, new EntryWidgetParam[]{
                new CustomEditText.EditTextParam("show", "null"),
                new DropDown.StaticDropDownParameters("genre", new String[]{
                        "comedy",
                        "romance"
                }),
                new ListWidget.ListParam("tropes", new EntryWidgetParam[]{
                        new DropDown.StaticDropDownParameters("trope", new String[]{
                                "isekai",
                                "philosophers",
                                "working together",
                                "video games"
                        }),
                        new CustomEditText.EditTextParam("trope description", "null")
                })

        });
        GroupWidget groupWidget = (GroupWidget) GLib.inflateWidget(context, groupWidgetParam);
        groupWidget.setMargin(20, 10);

        linearLayout.addView(groupWidget.getView());
        groupWidget.setOnDataChangedListener(()->{
            DataTree tree = groupWidget.getDataTree();
            System.out.println("data changes tree: \n" + tree.hierarchy());
        });
    }

    public void testNewEditText(){
        View view = MainActivity.mainActivity.getLayoutInflater().inflate(R.layout.edit_text_with_border, linearLayout);
        TextInputLayout textInputLayout = view.findViewById(R.id.editTextLayout);
        EditText editText = textInputLayout.getEditText();
        //editText.setFocusable(false);
        //editText.setFocusableInTouchMode(false); // to prevent focus when touched
        editText.setCursorVisible(false); // to hide the cursor
        editText.setKeyListener(null);
        editText.requestFocus();


        ConstraintLayout constraintLayout = new ConstraintLayout(context);
        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(-1, 400);
        constraintLayout.setLayoutParams(linearLayoutParams);
        linearLayout.addView(constraintLayout);
        TextView textView = new TextView(context);
        textView.setText("hello");
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(-1, -1);
        BorderView borderWithName = new BorderView(context, constraintLayout);
        //constraintLayout.addView(borderWithName);
        constraintLayout.addView(textView);

        //borderWithName.setLayoutParams(layoutParams);
        //borderWithName.setMinimumHeight(100);

        linearLayout.addView(new CustomTextInputLayout(context));
    }
}
