package com.example.habittracker.Widgets.ListWidgets;

import com.example.habittracker.structurePack.ListItemId;

import com.example.habittracker.defaultImportPackage.ArrayList;

public class SingleItemIdProvider implements ListItemIdProvider{
    private ListItemId listItemId;
    private ListItemIdProvider listItemIdParent;
    public SingleItemIdProvider(ListItemId listItemId){
        this.listItemId = listItemId;
    }

    @Override
    public ListItemId getListItemId() {
        return listItemId;
    }

    @Override
    public ArrayList<ListItemId> getListItemIdList() {
        ArrayList<ListItemId> result = listItemIdParent.getListItemIdList();
        result.add(listItemId);
        return result;
    }

    @Override
    public void setParentListItemIdProvider(ListItemIdProvider listItemIdProvider) {
        this.listItemIdParent = listItemIdProvider;
    }
}
