public class Node {
    private Node left, right; //потомки
    private int height = 1; // высота ноды, которая будет обновляться в ходе программы
    private int value; // значение ноды

    public Node (int val) { // конструктор ноды
        this.value = val;
    }

    @Override
    public String toString() {
        return "Node{" +
                "l=" + left +
                ", r=" + right +
                ", h=" + height +
                ", v=" + value +
                '}';
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
