package com.example.habittracker.Structs;

import com.example.habittracker.StaticClasses.StringMap;

import java.util.ArrayList;

public class ItemPath {
    private ArrayList<Integer> path;
    public ItemPath(ArrayList<Integer> path){
        this.path = path;
    }

    public ItemPath(Integer name){
        path = new ArrayList<>();
        path.add(name);
    }

    public ArrayList<Integer> getKeyPath(){
        return path;
    }

    public ArrayList<String> getStringPath(){
        ArrayList<String> strings = new ArrayList<>();
        for(Integer integer: path)
            strings.add(StringMap.get(integer));
        return strings;
    }

    public String getName(){
        return StringMap.get(getKey());
    }

    public Integer getKey(){
        return path.get(path.size() - 1);
    }
    @Override
    public boolean equals(Object object){
        if(object instanceof ItemPath){
            ItemPath item = (ItemPath) object;
            if(item.path.equals(path))
                return true;
        }
        return false;
    }

    public String toString(){
        return path.toString();
    }
}
