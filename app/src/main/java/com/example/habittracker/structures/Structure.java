package com.example.habittracker.structures;

import com.example.habittracker.MainActivity;
import com.example.habittracker.StaticClasses.Dictionary;
import com.example.habittracker.StaticClasses.EnumLoop;
import com.example.habittracker.Structs.CachedStrings.ArrayString;
import com.example.habittracker.Structs.CachedStrings.CachedString;
import com.example.habittracker.Structs.CachedStrings.LiteralString;
import com.example.habittracker.Structs.EntryWidgetParam;
import com.example.habittracker.Values.BaseWidgetValue;
import com.example.habittracker.Values.GroupValue;
import com.example.habittracker.Widgets.WidgetParams.GroupWidgetParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Structure {
    private static int structureKeyCount = 0;
    private int idCount = 0;
    private Integer id;
    private String name;
    private GroupWidgetParam widgetParam;
    private String type;
    private HashMap<Integer, Entry> entries;
    private HashMap<WidgetId, Header.WidgetInfo> widgetMap;


    public Structure(String name, GroupWidgetParam widgetParam, String type){
        initVariables(name, widgetParam, type, null);
    }



    public Structure(String name, GroupWidgetParam widgetParam, String type,
                     ArrayList<Entry> entries){
        initVariables(name, widgetParam, type, entries);
    }
    public Structure(String type){
        initVariables(null, null, type, null);
    }

    public void initVariables(String name, GroupWidgetParam widgetParam, String type,
                              ArrayList<Entry> entries){
        this.id = null;
        this.name = name;
        this.widgetParam = widgetParam;
        this.type = type;
        this.entries = new HashMap();
        widgetParam.setStructure(this);
        if(entries == null)
            this.entries = new HashMap<>();
        else{
            for(Entry data: entries)
                insertOldEntry(data);
        }


        //MainActivity.log("log param while init structure: \n" + widgetParam.hierarchyString());

        init();
    }

    private void init(){
        Header header = new Header(widgetParam, this);
        widgetMap = header.widgetMap;
    }

    public void createId(){
        MainActivity.log("set new key for: " + getCachedName());
        if(id != null)
            throw new RuntimeException("tried to set new key of structure twice");
        this.id = structureKeyCount;
        structureKeyCount++;
    }

    private void insertOldEntry(Entry entry){
        entries.put(entry.getId(), entry);
    }

    public void addEntry(GroupValue entryData){
        Set<Integer> entryIdSet = entries.keySet();
        while(entryIdSet.contains(idCount))
            idCount++;
        Entry entry = new Entry(entryData, idCount, this);
        idCount++;
        entries.put(entry.getId(), entry);
    }

    public void setData(Entry entryToEdit, GroupValue data){
        int entryId = entryToEdit.getId();
        Entry found = null;
        for(Entry entry: entries.values()){
            if(entry.getId() == entryId){
                found = entry;
                break;
            }
        }
        Entry newEntry = new Entry(data, entryId, this);
        entries.put(entryId, newEntry);
    }



    public ArrayList<Entry> getEntryList(){
        return new ArrayList<>(entries.values());
    }

    public ArrayList<GroupValue> getData(){
        ArrayList<GroupValue> entryValueTrees = new ArrayList<>();
        for(Entry entry: entries.values())
            entryValueTrees.add(entry.getGroupValue());
        return entryValueTrees;
    }

    public ArrayList<Entry> getEntries(){
        return new ArrayList<>(entries.values());
    }



    public CachedString getCachedName(){
        return new LiteralString(name);
    }

    public String getType(){
        return type;
    }

    public Header.WidgetInfo getWidgetInfo(WidgetId widgetId){
        return widgetMap.get(widgetId);
    }



    public GroupWidgetParam getWidgetParam(){
        return widgetParam;
    }

    public boolean isSpreadsheet() {
        if(type.equals(Dictionary.category))
            return true;
        return false;
    }

    public static boolean isSpreadsheet(String type){
        if(type.equals(Dictionary.category))
            return true;
        return false;
    }

    public boolean equals(Object object){
        if(object instanceof Structure){
            Structure other = (Structure) object;
            if(other.getId().equals(id))
                return true;
        }
        return false;
    }

    public Entry getEntry(int entryId) {
        return entries.get(entryId);
    }

    public Integer getId() {
        if(id == null){
            MainActivity.log("structure: " + getCachedName() +", has no id");
            throw new RuntimeException();
        }

        return id;
    }

    public String toString(){
        return "<structure> " + getCachedName().getString() + ", id: " + getId();
    }

    public EntryWidgetParam getWidgetParamFromId(WidgetId widgetId) {
        return widgetMap.get(widgetId).getEntryWidgetParam();
    }

    protected CachedString getEntryName(Entry entry) {
        MainActivity.log("getting entry name");

        ArrayList<WidgetId> importantWidgetList = getImportantWidgets();
        ArrayList<CachedString> arrayStringList = new ArrayList<>();
        MainActivity.log("important widgets: " + importantWidgetList);
        for(WidgetId widgetId: importantWidgetList){

            ArrayList<BaseWidgetValue> widgetValueList = entry.getGroupValue().getValuesFromWidgetPath(getWidgetInfo(widgetId).getWidgetPath());
            ArrayString string = new ArrayString(EnumLoop.makeList(widgetValueList, (widgetValue)->widgetValue.getDisplayCachedString()));
            MainActivity.log("getting values from widgetId: " + widgetValueList);
            arrayStringList.add(string);
        }
        return new ArrayString(arrayStringList);
    }

    public ArrayList<WidgetId> getImportantWidgets(){
        ArrayList<WidgetId> result = new ArrayList<>();
        for(WidgetId widgetId: widgetMap.keySet()){
            if(widgetId.getWidgetParam().isUniqueAttribute)
                result.add(widgetId);
        }
        return result;
    }

    public ArrayList<WidgetId> getWidgetIdList() {
        return new ArrayList<>(widgetMap.keySet());
    }
}
