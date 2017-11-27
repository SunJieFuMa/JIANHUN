package com.chantyou.janemarried.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sunfusheng on 16/4/23.
 */
public class FilterData implements Serializable {

    private List<FilterEntity> region;//地区
    private List<FilterEntity> category;//类型
    private List<FilterEntity> sorts;//排序
    private List<FilterEntity> filters;
    private List<FilterTwoImageEntity> imageCategory;



    public List<FilterEntity> getSorts() {
        return sorts;
    }

    public void setSorts(List<FilterEntity> sorts) {
        this.sorts = sorts;
    }

    public List<FilterEntity> getFilters() {
        return filters;
    }

    public void setFilters(List<FilterEntity> filters) {
        this.filters = filters;
    }

    public List<FilterTwoImageEntity> getImageCategory() {
        return imageCategory;
    }

    public void setImageCategory(List<FilterTwoImageEntity> imageCategory) {
        this.imageCategory = imageCategory;
    }

    public List<FilterEntity> getCategory() {
        return category;
    }

    public void setCategory(List<FilterEntity> category) {
        this.category = category;
    }

    public List<FilterEntity> getRegion() {
        return region;
    }

    public void setRegion(List<FilterEntity> region) {
        this.region = region;
    }
}
