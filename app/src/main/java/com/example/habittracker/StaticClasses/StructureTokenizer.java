package com.example.habittracker.StaticClasses;

import com.example.habittracker.MainActivity;
import com.example.habittracker.Structs.CachedStrings.CachedString;
import com.example.habittracker.Structs.CachedStrings.LiteralString;
import com.example.habittracker.Structs.CachedStrings.RefEntryString;
import com.example.habittracker.Structs.EntryWidgetParam;
import com.example.habittracker.Values.GroupValue;
import com.example.habittracker.Values.ListValue;
import com.example.habittracker.Values.WidgetValue;
import com.example.habittracker.Values.WidgetValueString;
import com.example.habittracker.Values.WidgetValueStringPath;
import com.example.habittracker.Widgets.EntryWidgets.CustomEditText;
import com.example.habittracker.Widgets.EntryWidgets.DropDown;
import com.example.habittracker.Widgets.EntryWidgets.EntryDropDown;
import com.example.habittracker.Widgets.GroupWidget;
import com.example.habittracker.Widgets.ListWidgets.ListWidgetMultipleItems;
import com.example.habittracker.Widgets.ListWidgets.ListWidgetSingleItem;
import com.example.habittracker.Widgets.WidgetParams.DropDownParam;
import com.example.habittracker.Widgets.WidgetParams.EditTextParam;
import com.example.habittracker.Widgets.WidgetParams.GroupWidgetParam;
import com.example.habittracker.Widgets.WidgetParams.ListMultiItemParam;
import com.example.habittracker.Widgets.WidgetParams.ListSingleItemParam;
import com.example.habittracker.structures.Entry;
import com.example.habittracker.structures.Structure;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StructureTokenizer {


    public static JSONArray getStructureArrayToken(){
        try {
            return getStructureArrayTokenThrows();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private static JSONArray getStructureArrayTokenThrows() throws JSONException {
        ArrayList<Structure> structures = Dictionary.getStructures();
        JSONArray jsonArray = new JSONArray();
        for(Structure structure: structures){
            JSONObject json = jsonFromStructure(structure);
            jsonArray.put(json);
        }
        return jsonArray;
    }

    public static JSONObject jsonFromStructure(Structure structure) throws JSONException{
        JSONObject json = new JSONObject();
        json.put("name", structure.getCachedName().getString());
        json.put("id", structure.getId());
        json.put("widget params", structure.getWidgetParam().getJSON());
        JSONArray entriesJSON = new JSONArray();
        for(Entry entry: structure.getEntries()){
            JSONObject entryJSON = new JSONObject();
            entryJSON.put("id", entry.getId());
            entryJSON.put("value", entry.getGroupValue().getJSON());
        }
        return json;
    }


    public static WidgetValue getWidgetValue(JSONObject jsonObject) throws JSONException{
        String className = jsonObject.getString(WidgetValue.classNameKey);
        switch(className){
            case GroupValue.className->{
                return GroupValue.getFromJSON(jsonObject);
            }
            case ListValue.className->{
                return ListValue.getFromJSON(jsonObject);
            }
            case WidgetValueString.className->{
                return WidgetValueString.getFromJSON(jsonObject);
            }
            case WidgetValueStringPath.className->{
                return WidgetValueStringPath.getFromJSON(jsonObject);
            }
        }
        MainActivity.log("couldn't find cached string class: " + className);
        throw new RuntimeException();
    }


    public static CachedString getCachedString(JSONObject jsonObject) throws JSONException{
        String className = jsonObject.getString(CachedString.typeOfClassKey);
        switch(className){
            case LiteralString.className->{
                return LiteralString.getFromJSON(jsonObject);
            }
            case RefEntryString.className->{
                return RefEntryString.getFromJSON(jsonObject);
            }
        }
        MainActivity.log("couldn't find cached string class: " + className);
        throw new RuntimeException();
    }

    public static EntryWidgetParam getWidgetParam(JSONObject jsonObject) throws JSONException{
        String className = jsonObject.getString("className");
        switch(className){
            case DropDown.className->{
                return DropDownParam.getFromJSON(jsonObject);
            }
            case CustomEditText.className->{
                return EditTextParam.getFromJSON(jsonObject);
            }
            case ListWidgetSingleItem.className->{
                return ListSingleItemParam.getFromJSON(jsonObject);
            }
            case ListWidgetMultipleItems.className->{
                return ListMultiItemParam.getFromJSON(jsonObject);
            }
            case GroupWidget.className->{
                return GroupWidgetParam.getFromJSON(jsonObject);
            }
        }
        MainActivity.log("couldn't find cached string class: " + className);
        throw new RuntimeException();

    }
}
