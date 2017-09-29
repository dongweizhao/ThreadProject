package com.company.tree;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by dongweizhao on 16/10/30.
 */
public class BinaryTree {
    private BinaryTreeNode root;

    public BinaryTree() {
        root = null;
    }

    public BinaryTree(BinaryTreeNode root) {
        this.root = root;
    }

    public boolean isEmpty() {
        if (root == null)
            return true;
        return false;
    }

    public void Visit(BinaryTreeNode root) {
        if (root != null) {
            System.out.println("root.data = [" + root.getData() + "]");
        }
    }


    /**
     * 节点个数
     *
     * @param root
     * @return
     */
    public int getNodeCount(BinaryTreeNode root) {
        int count = 0;
        if (root == null)
            return count;
        if (root.getLeftTreeNode() != null) {
            count++;
        }
        if (root.getRightTreeNode() != null)
            count++;

        count += getNodeCount(root.getLeftTreeNode()) + getNodeCount(root.getRightTreeNode()) + 1;
        return count;
    }

    public int getChildren(BinaryTreeNode root) {
        int cout = 0;
        //儿子
        BinaryTreeNode childern = root.getLeftTreeNode();
        if (childern != null) {
            cout++;
            cout += getChildren(childern);
        }
        return cout;
    }

    public void NPreOrder(BinaryTreeNode root) {
        if (root == null) return;
        BlockingDeque<BinaryTreeNode> deque=new LinkedBlockingDeque<BinaryTreeNode>();
        BinaryTreeNode pointer = root;
        while (pointer != null) {
            if (pointer!=null) {
                Visit(pointer);
                if (pointer.getLeftTreeNode() != null) {
                     deque.add(pointer.getLeftTreeNode());
                }
                pointer=pointer.getRightTreeNode();

            }else {
                pointer=deque.peek();
                deque.remove();
            }
        }

    }


    /**
     * 节点深度
     *
     * @param root
     * @return
     */
    public int getDepth(BinaryTreeNode root) {
        if (root == null) return 0;
        int leftDepth = 0, rightDepth = 0;
        if (root.getLeftTreeNode() != null) {
            leftDepth += getDepth(root.getLeftTreeNode()) + 1;
        }
        if (root.getRightTreeNode() != null) {
            rightDepth += getDepth(root.getRightTreeNode()) + 1;
        }

        if (leftDepth > rightDepth) {
            return leftDepth;
        }

        return rightDepth;
    }

    public static void main(String[] args) {
        BinaryTreeNode binaryTreeNode1_1__1_1_left = new BinaryTreeNode();

        BinaryTreeNode binaryTreeNode1_1__1_left = new BinaryTreeNode();
        binaryTreeNode1_1__1_left.setLeftTreeNode(binaryTreeNode1_1__1_1_left);
        BinaryTreeNode binaryTreeNode1_1_1_right = new BinaryTreeNode();

        BinaryTreeNode binaryTreeNode1_1_left = new BinaryTreeNode();
        binaryTreeNode1_1_left.setLeftTreeNode(binaryTreeNode1_1__1_left);
        BinaryTreeNode binaryTreeNode1_1_right = new BinaryTreeNode();
        binaryTreeNode1_1_right.setRightTreeNode(binaryTreeNode1_1_1_right);

        BinaryTreeNode binaryTreeNode1_left = new BinaryTreeNode();
        binaryTreeNode1_left.setLeftTreeNode(binaryTreeNode1_1_left);
        BinaryTreeNode binaryTreeNode1_right = new BinaryTreeNode();
        binaryTreeNode1_right.setRightTreeNode(binaryTreeNode1_1_right);

        BinaryTreeNode binaryTreeNode = new BinaryTreeNode();
        binaryTreeNode.setLeftTreeNode(binaryTreeNode1_left);
        binaryTreeNode.setRightTreeNode(binaryTreeNode1_right);

        BinaryTree binaryTree = new BinaryTree(binaryTreeNode);
        System.out.println("count = [" + binaryTree.getNodeCount(binaryTreeNode) + "]");

        System.out.println("depth:" + binaryTree.getDepth(binaryTreeNode));
    }
}
