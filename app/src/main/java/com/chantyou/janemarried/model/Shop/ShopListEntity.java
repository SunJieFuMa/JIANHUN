package com.chantyou.janemarried.model.Shop;

import java.io.Serializable;

/**
 * Created by sunfusheng on 16/4/20.
 */
public class ShopListEntity implements Serializable, Comparable<ShopListEntity> {

    private String type; // 风景、动物、植物、建筑
    private String title; // 标题
    private String from; // 来源
    private int rank; // 排名
    private String image_url; // 图片地址

    // 暂无数据属性
    private boolean isNoData = false;
    private int height;

    private String url;//商铺头像
    private int isSpread;//是否显示推广图标(1显示；0不显示)
    private String name;//商铺名称
    private String charge;//业务范围
    private String label;//商铺标签(用,分割，单独显示)
    private long collectCount;//收藏个数
    private long egCount;//案例数量
    private long planCount;//套餐数量


    public ShopListEntity() {
    }

    public ShopListEntity(String type, String title, String from, int rank, String image_url) {
        this.type = type;
        this.title = title;
        this.from = from;
        this.rank = rank;
        this.image_url = image_url;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isNoData() {
        return isNoData;
    }

    public void setNoData(boolean noData) {
        isNoData = noData;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    @Override
    public int compareTo(ShopListEntity another) {
        return this.getRank() - another.getRank();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getIsSpread() {
        return isSpread;
    }

    public void setIsSpread(int isSpread) {
        this.isSpread = isSpread;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public long getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(long collectCount) {
        this.collectCount = collectCount;
    }

    public long getEgCount() {
        return egCount;
    }

    public void setEgCount(long egCount) {
        this.egCount = egCount;
    }

    public long getPlanCount() {
        return planCount;
    }

    public void setPlanCount(long planCount) {
        this.planCount = planCount;
    }
}
