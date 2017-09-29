package com.company.tree;

/**
 * Created by dongweizhao on 16/10/30.
 */
public class BinaryTreeNode<T> {
    private T data;
    private BinaryTreeNode leftTreeNode;
    private BinaryTreeNode rightTreeNode;

    public BinaryTreeNode() {
        data=null;
        leftTreeNode= null;
        rightTreeNode=null;
    }

    public BinaryTreeNode(T data) {
        this.data = data;
        leftTreeNode=null;
        rightTreeNode=null;
    }

    public BinaryTreeNode(T data, BinaryTreeNode leftTreeNode, BinaryTreeNode rightTreeNode) {
        this.data = data;
        this.leftTreeNode = leftTreeNode;
        this.rightTreeNode = rightTreeNode;
    }

    public BinaryTreeNode getLeftTreeNode() {
        return leftTreeNode;
    }

    public void setLeftTreeNode(BinaryTreeNode leftTreeNode) {
        this.leftTreeNode = leftTreeNode;
    }

    public BinaryTreeNode getRightTreeNode() {
        return rightTreeNode;
    }

    public void setRightTreeNode(BinaryTreeNode rightTreeNode) {
        this.rightTreeNode = rightTreeNode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isLeaf(){
        if (this.leftTreeNode==null&&rightTreeNode==null){
            return true;
        }
        return false;
    }

}
