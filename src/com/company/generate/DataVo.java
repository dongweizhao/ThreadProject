package com.company.generate;

/**
 * 数据对象
 * Created by dongweizhao on 17/6/24.
 */
public class DataVo {
    private int node;//数据节点
    private String data;

    public DataVo(int node, String data) {
        this.node = node;
        this.data = data;
    }

    public int getNode() {
        return node;
    }

    public void setNode(int node) {
        this.node = node;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
