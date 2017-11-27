package com.chantyou.janemarried.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sunfusheng on 16/4/23.
 */
public class FilterTwoImageEntity implements Serializable {

    private ImageTitleEntity type;
    private List<FilterEntity> list;
    private boolean isSelected;
    private FilterEntity selectedFilterEntity;

    public FilterTwoImageEntity() {
    }


    public FilterTwoImageEntity(ImageTitleEntity type, List<FilterEntity> list) {
        this.type = type;
        this.list = list;
    }


    public FilterEntity getSelectedFilterEntity() {
        return selectedFilterEntity;
    }

    public void setSelectedFilterEntity(FilterEntity selectedFilterEntity) {
        this.selectedFilterEntity = selectedFilterEntity;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public List<FilterEntity> getList() {
        return list;
    }

    public void setList(List<FilterEntity> list) {
        this.list = list;
    }


    public ImageTitleEntity getType() {
        return type;
    }

    public void setType(ImageTitleEntity type) {
        this.type = type;
    }
}
