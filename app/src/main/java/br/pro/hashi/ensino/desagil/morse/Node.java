package br.pro.hashi.ensino.desagil.morse;

public class Node {
    private boolean open;
    private char value;
    private Node left;
    private Node right;

    public Node(char value, Node left, Node right) {
        open = true;
        this.value = value;
        this.left = left;
        this.right = right;
    }

    public void print() {
        if(left != null) {
            left.print();
        }
        if(right != null) {
            right.print();
        }
        System.out.print(" " + value);
    }


    public boolean isOpen() {
        return open;
    }
    public void setOpen(boolean open) {
        this.open = open;
    }
    public char getValue() {
        return value;
    }
    public Node getLeft() {
        return left;
    }
    public Node getRight() {
        return right;
    }
    public void setLeft(Node left){
        this.left = left;
    }
    public void setRight(Node right){
        this.right = right;
    }
}

