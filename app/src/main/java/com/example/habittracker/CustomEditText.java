package com.example.habittracker;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

public class CustomEditText extends androidx.appcompat.widget.AppCompatEditText implements Widget{
    String text = "null";
    public CustomEditText(Context context) {
        super(context);
        init();
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setValue(String newValue){
        text = newValue;
        this.setText(newValue);
    }


    private void init(){
        setValue("null");
        int MAX_CHARACTERS = 50;

        // Error handling from too many characters
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                text = s.toString();
                onDataChangedListener.run();
                if (s.length() > MAX_CHARACTERS) {
                    setError("Maximum character limit exceeded");
                } else {
                    setError(null);
                }
            }
        });

        // Responding to the user focusing on the EditText
        setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                // EditText is focused
            } else {
                // EditText lost focus
            }
        });

        // Responding to the user changing the text (using TextWatcher)

        // Responding to the user exiting focus from the EditText (using OnFocusChangeListener)

    }

    @Override
    public Widget widgetClone() {
        CustomEditText editText = new CustomEditText(getContext());
        editText.setData(getData());
        return null;
    }
    public Runnable onDataChangedListener = null;
    @Override
    public void setOnDataChangedListener(Runnable runnable) {
        onDataChangedListener = runnable;
    }

    @Override
    public WidgetParams getData(){
        EditTextParams params = new EditTextParams(text);

        return params;
    }

    @Override
    public WidgetValue value(){
        EditTextValue value = new EditTextValue(text);
        return value;
    }

    @Override
    public void setData(WidgetParams params){
        EditTextParams casted = ((EditTextParams) params);
        setValue(casted.text);
    }

    @Override
    public View getView() {
        return this;
    }

    public static class EditTextParams extends WidgetParams{
        public String text;
        public EditTextParams(String text){
            this.widgetClass = "edit text";
            this.text = text;
        }
    }

    public static class EditTextValue extends WidgetValue{
        public String text;
        public EditTextValue(String text){
            this.text = text;
        }
    }
}
