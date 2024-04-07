package com.example.habittracker.Structs;


import java.util.ArrayList;
import java.util.Arrays;

public class DataTree {
    private String name;
    private RefItemPath itemPath;
    private ArrayList<DataTree> list = new ArrayList<>();
    public DataTree(){
    }

    public DataTree(RefItemPath itemPath){
        this.itemPath = itemPath;
    }

    public RefItemPath getItemPath(){
        return itemPath;
    }


    public DataTree put(ArrayList<DataTree> dataTrees){
        for(DataTree dataTree: dataTrees){
            list.add(dataTree);
        }
        return this;
    }

    public DataTree put(DataTree ... dataTrees){
        put(new ArrayList<>(Arrays.asList(dataTrees)));
        return this;
    }

    public DataTree(String name){
        this.name = name;
    }



    public DataTree put(String ... strings){
        for(String s: strings){
            DataTree dataTree = new DataTree();
            dataTree.name = s;
            list.add(dataTree);
        }
        return this;
    }

    public ArrayList<DataTree> getList(){
        return (ArrayList<DataTree>) list.clone();
    }

    public void add(String string){
        list.add(new DataTree(string));
    }

    public void add(DataTree tree){
        list.add(tree);
    }

    public String getName(){
        return name;
    }

    public DataTree addTree(){
        DataTree tree = new DataTree();
        list.add(tree);
        return tree;
    }

    public String getString(int index){
        return (list.get(index).name);
    }

    public DataTree getTree(int index){
        return ((DataTree) list.get(index));
    }

    public int indexOf(String value){
        int i = 0;
        for(DataTree tree: list){
            if(tree.name.equals(value))
                return i;
            i++;
        }
        return -1;
    }

    public ArrayList<Integer> indexOf(ArrayList<String> values){
        //System.out.println("index of method: " + values);
        //System.out.println("get index for group: " + values);
        ArrayList<Integer> indexes = new ArrayList<>();
        DataTree temp = this;
        for(int i = 0; i < values.size() - 1; i++){
            System.out.println("temp = " + temp);
            System.out.println("finding index of: " + values.get(i));
            int index = temp.indexOf(values.get(i));
            System.out.println("index = " + index);
            indexes.add(index);
            temp = temp.getTree(index);
        }
        String lastValue = values.get(values.size() - 1);
        //System.out.println("lastValue = " + lastValue);
        //System.out.println("temp = " + temp);
        if(temp.size() > 1){
            indexes.add(temp.indexOf(lastValue));
            indexes.add(-1);
        }else
            indexes.add(-2);

        //System.out.println("indexes: " + indexes);
        return indexes;
    }

    public boolean hasChildren(String name){
        int index = indexOf(name);
        return hasChildren(index);
    }

    public boolean hasChildren(int index){
        if(list.get(index).size() > 0)
            return true;
        return false;
    }

    public ArrayList<DataTree> iterateThroughTreeForTree(ArrayList<DataTree> parentTrees, int treeIndex){
        ArrayList<DataTree> currentTrees = new ArrayList<>();
        for(DataTree tree: parentTrees){
            //System.out.println("index from tree: " + tree.nameAndLength());
            currentTrees.add(tree.getTree(treeIndex));
        }
        return currentTrees;
    }

    public ArrayList<String> iterateThroughTreeForString(ArrayList<DataTree> parentTrees, int treeIndex){
        ArrayList<String> values = new ArrayList<>();
        for(DataTree tree: parentTrees){
            //System.out.println("index from tree: " + tree.nameAndLength());
            values.add(tree.getString(treeIndex));
        }
        return values;
    }

    public ArrayList<DataTree> gatherTreesFromArray(ArrayList<DataTree> currentTrees){
        ArrayList<DataTree> gatheredTrees = new ArrayList<>();
        for(DataTree tree: currentTrees){
            for(int gatherIndex = 0; gatherIndex < tree.size(); gatherIndex++){
                gatheredTrees.add(tree.getTree(gatherIndex));
            }
        }
        return gatheredTrees;
    }

    public ArrayList<String> gatherStringFromArray(ArrayList<DataTree> currentTrees){
        //System.out.println("\ngathering array: " + currentTrees + " \n");
        ArrayList<String> gatheredStrings = new ArrayList<>();
        for(DataTree tree: currentTrees){
            for(int gatherIndex = 0; gatherIndex < tree.size(); gatherIndex++){
                gatheredStrings.add(tree.getString(gatherIndex));
            }
        }
        return gatheredStrings;
    }

    public ArrayList<DataTree> processList(ArrayList<DataTree> parentTrees, int treeIndex){
        ArrayList<DataTree> currentTrees = iterateThroughTreeForTree(parentTrees, treeIndex);
        return gatherTreesFromArray(currentTrees);
    }



    //get values from group indexes. The indexes are the key for the location
    public ArrayList<String> getFromIndexes(ArrayList<Integer> indexes){
        //System.out.println("get values from indexes");
        //System.out.println(this.hierarchy());
        //System.out.println("indexes = " + indexes);
        int lastIsTreeNum = indexes.get
                (indexes.size() - 1);
        boolean lastIsTree = false;
        if(lastIsTreeNum == -2)
            lastIsTree = true;
        //System.out.println("getting value of data tree from indexes");
        //System.out.println(this.hierarchy() + "\n\n");
        ArrayList<DataTree> parentTrees = new ArrayList<>();
        parentTrees.add(this);

        for(int i = 0; i < indexes.size() - 2; i++){
            int treeIndex = indexes.get(i);
            //System.out.println("("+i+")current tree index: " + treeIndex);
            parentTrees = processList(parentTrees, treeIndex);
            //System.out.println(parentTrees.get(0).hierarchy());
        }
        int lastIndex = indexes.get(indexes.size() - 2);
        DataTree testTree = parentTrees.get(0);
        ArrayList<String> values;
        if(lastIsTree){
            ArrayList<DataTree> lastTrees = iterateThroughTreeForTree(parentTrees, lastIndex);
            values = gatherStringFromArray(lastTrees);
        }else
            values = iterateThroughTreeForString(parentTrees, lastIndex);
        //System.out.println("values = " + values);
        return values;
    }

    public int size() {
        return list.size();
    }



    public ArrayList<ArrayList<String>> entryGroupValues(ArrayList<ArrayList<Integer>> indexes){
        ArrayList<ArrayList<String>> values = new ArrayList<>();
        for(ArrayList<Integer> index: indexes){
            values.add(getFromIndexes(index));
        }
        return values;
    }


    public static DataTree convert(Object[] objects){
        DataTree tree = new DataTree();
        for(Object obj: objects){
            if(obj instanceof String){
                tree.add(((String) obj));
                continue;
            }
            if(obj instanceof Object[]){
                tree.add(convert(((Object[]) obj)));
                continue;
            }
        }
        return tree;
    }

    public static ArrayList<DataTree> convert(Object[][] objects){
        ArrayList<DataTree> trees = new ArrayList<>();
        for(Object[] array: objects)
            trees.add(convert(array));
        return trees;
    }

    public static DataTree convertHeader(Object[] header){
        return convertHeader(null, header);
    }

    private static DataTree convertHeader(String name, Object[] header){
        //System.out.println("converting header");
        //System.out.println("header.length = " + header.length);
        DataTree tree = new DataTree(name);
        for(Object obj: header){
            if(obj instanceof String){
                tree.add(((String) obj));
                continue;
            }
            if(obj instanceof KeyPair){
                tree.add(convertHeader(((KeyPair) obj)));
                continue;
            }
        }
        //System.out.println("tree = \n" + tree.hierarchy());
        //System.out.println("converting header finished");
        return tree;
    }

    public static DataTree convertHeader(KeyPair pair){
        return convertHeader(pair.getKey(), (Object[])pair.getValue());
    }

    public static ArrayList<ItemPath> gatherItems(DataTree tree){
        ArrayList<ItemPath> items = new ArrayList<>();
        ArrayList<String> stack = new ArrayList<>();
        gatherItems(items, tree, stack);
        return items;
    }

    public static void gatherItems(ArrayList<ItemPath> items, DataTree tree, ArrayList<String> stack){
        for(int i = 0; i < tree.list.size(); i++){
            ArrayList<String> copy = (ArrayList<String>)stack.clone();
            if(tree.hasChildren(i)){
                DataTree newTree = tree.getTree(i);
                copy.add(newTree.name);
                gatherItems(items, newTree, copy);
            }else{
                String item = tree.getString(i);
                copy.add(item);
                items.add(new ItemPath(copy));
            }
        }
    }

    public String nameAndLength(){
        return "{ " + name + ", " + list.size() + " }";
    }

    public String toString(){
        return "{ DataTree " + name + ", " + list.size() + " }";

    }

    public String hierarchy(){
        return hierarchy(0);
    }

    private String hierarchy(int tabs){
        String tab = "\t";
        String tabString = "";
        for(int i = 0; i < tabs; i++){
            tabString += tab;
        }
        String result = "";
        result += tabString + nameAndLength() + "\n";
        for(Object obj: list){
            if( obj instanceof String){
                result += tabString + tab + obj.toString() + "\n";
                continue;
            }
            if(obj instanceof DataTree){
                result += ((DataTree) obj).hierarchy(tabs + 1);
            }
        }
        return result;
    }


}
