package com.example.habittracker.Structs;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class RefItemPath implements Iterable<CachedString>{
    private ArrayList<CachedString> path;
    public RefItemPath(ArrayList<CachedString> path){
        this.path = path;
    }

    public RefItemPath(CachedString key){
        path = new ArrayList<>();
        path.add(key);
    }

    public ArrayList<CachedString> getPath(){
        return path;
    }

    public CachedString getLast(){
        return path.get(path.size() - 1);
    }

    @Override
    public boolean equals(Object object){
        if(object instanceof RefItemPath){
            RefItemPath item = (RefItemPath) object;
            if(item.path.equals(path))
                return true;
        }
        return false;
    }

    public String toString(){
        return path.toString();
    }

    public ItemPath getItemPath() {
        ArrayList<String> stringPath = new ArrayList<>();
        for(CachedString cachedString: path)
            stringPath.add(cachedString.getString());
        return new ItemPath(stringPath);
    }

    @NonNull
    @Override
    public Iterator<CachedString> iterator() {
        return path.iterator();
    }

    @Override
    public void forEach(@NonNull Consumer<? super CachedString> action) {
        Iterable.super.forEach(action);
        path.forEach(action);
    }

    @NonNull
    @Override
    public Spliterator<CachedString> spliterator() {
        return path.spliterator();
    }
}