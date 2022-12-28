import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tree {

    public static String str = "";

    private int height(Node N) {
        if (N == null)
            return 0;
        return N.getHeight();
    }

    public Node find(Node node, int value) {
        while (true) {
            if (node == null) {
                return null;
            } else if (value == node.getValue()) {
                return node;
            } else if (value > node.getValue()) {
                node = node.getRight();
            } else {
                node = node.getLeft();
            }
        }
    }

    public Node insert(Node node, int value) {
        // данный кусок кода отвечает за базовый рекурсивный алгоритм вставки узла, когда в рекурсии мы дойдем до пустой ноды-потомка
        // рекурсия схлопнется и нам вернется вставленная нода
        if (node == null) {
            return (new Node(value));
        }
        if (node.getValue() == value) {
            return node;
        } else if (value < node.getValue())
            node.setLeft(insert(node.getLeft(), value));
        else
            node.setRight(insert(node.getRight(), value));

        // после вставки ноды обновляется высота ее предка
        node.setHeight(Math.max(height(node.getLeft()), height(node.getRight())) + 1);

        // применяется функция проверки сбалансированности узла
        int balance = getBalance(node);

        // далее рассматривается несколько случаев баланса, если баланс равен 0, то ничего, соответсвенно не происходит,
        // если баланс больше 1, то это значит, что левый потомок имеет большую высоты чем правый
        // если баланс меньше -1, то это значит, что правый потомк имеет большую высоты чем левый

        // Используем правое малое вращение
        if (balance > 1 && value < node.getLeft().getValue())
            return rightRotate(node);

        // Используем левое малое вращение
        if (balance < -1 && value > node.getRight().getValue())
            return leftRotate(node);

        // Большой левый поворот
        if (balance > 1 && value > node.getLeft().getValue()) {
            node.setLeft(leftRotate(node.getLeft()));
            return rightRotate(node);
        }

        // Большой правый поворот
        if (balance < -1 && value < node.getRight().getValue()) {
            node.setRight(rightRotate(node.getRight()));
            return leftRotate(node);
        }

        // Возвращаем ноду
        return node;
    }

    private Node rightRotate(Node y) { //малый правый поворт
        Node x = y.getLeft();
        Node T2 = x.getRight();

        // Крутим
        x.setRight(y);
        y.setLeft(T2);

        // Обновляем высоты
        y.setHeight(Math.max(height(y.getLeft()), height(y.getRight())) + 1);
        x.setHeight(Math.max(height(x.getLeft()), height(x.getRight())) + 1);

        // Возвращаем новый узел
        return x;
    }

    private Node leftRotate(Node x) { //малый левый поворот
        Node y = x.getRight();
        Node T2 = y.getLeft();

        // Меняем узлы местами
        y.setLeft(x);
        x.setRight(T2);

        // Обновляем высоты
        x.setHeight(Math.max(height(x.getLeft()), height(x.getRight())) + 1);
        y.setHeight(Math.max(height(y.getLeft()), height(y.getRight())) + 1);

        // Возвращаем новый узел
        return y;
    }

    // Функция вычисляющая значение баланса
    private int getBalance(Node N) {
        if (N == null)
            return 0;
        return height(N.getLeft()) - height(N.getRight());
    }

    private Node minValueNode(Node node) { //функци поиска минимального значения отнсительно заданной ноды
        Node current = node;
        while (current.getLeft() != null)
            current = current.getLeft();
        return current;
    }

    public Node deleteNode(Node root, int value) {
        // Вначале реализуем базовый алгоритм удаления узла

        if (root == null)
            return root;

        // Если удаляемое значение меньше текущего узла, то идем влево
        // Если наоборот, то вправо
        if (value < root.getValue())
            root.setLeft(deleteNode(root.getLeft(), value));


        else if (value > root.getValue())
            root.setRight(deleteNode(root.getRight(), value));

            // если значение текущего узла и удаляемой ноды совпадает, то мы нашли удаляемое значение
            // далее рассматривается несколько случаев
        else {
            // если у текущего узла нет детей либо один ребенок
            if ((root.getLeft() == null) || (root.getRight() == null)) {

                Node temp;
                if (root.getLeft() != null)
                    temp = root.getLeft();
                else
                    temp = root.getRight();

                // детей нет
                if (temp == null) {
                    temp = root;
                    root = null;
                } else // один ребенок
                    root = temp; //удаляемому значению присваивается значение наименьшего предка

                temp = null; //наименьший потомок "удаляется"
            } else {
                // нода с двумя детьми, берем минимальное значение правом поддереве
                Node temp = minValueNode(root.getRight());

                // сохраняем значение минимального предка в правом поддереве в удаляемую ноду
                root.setValue(temp.getValue());

                // удаляем миниамального предка из правого поддерева
                root.setRight(deleteNode(root.getRight(), temp.getValue()));
            }
        }

        // Если дерево не имеет узлов, то возвращаем это дерево
        if (root == null)
            return root;

        // Далее обновим значение высоты текущей ноды
        root.setHeight(Math.max(height(root.getLeft()), height(root.getRight())) + 1);

        // Далее проведем операцию балансировки (при необходимости, узнав ее фактор баланса)
        int balance = getBalance(root);

        // Так же, как и в операции вставки здесь есть 4 варианта.

        // Малое левое
        if (balance > 1 && getBalance(root.getLeft()) >= 0)
            return rightRotate(root);

        // Большое левое
        if (balance > 1 && getBalance(root.getLeft()) < 0) {
            root.setLeft(leftRotate(root.getLeft()));
            return rightRotate(root);
        }

        // Малое правое
        if (balance < -1 && getBalance(root.getRight()) <= 0)
            return leftRotate(root);

        // Большое правое
        if (balance < -1 && getBalance(root.getRight()) > 0) {
            root.setRight(rightRotate(root.getRight()));
            return leftRotate(root);
        }

        return root;
    }

    public void makeSequence(Node node, int param) {
        if (node != null) {
            str += node.getValue() + "(" + param + ") ";
        }
        if (node.getRight() != null) {
            makeSequence(node.getRight(), param + 1);
        }
        if (node.getLeft() != null) {
            makeSequence(node.getLeft(), param + 1);
        }
    }

    public void showSequence() {
        String[] new_Str = str.split(" ");
        boolean isSorted = false;
        String buf;
        while (!isSorted) {
            isSorted = true;
            for (int i = 0; i < new_Str.length - 1; i++) {
                if (Integer.parseInt(String.valueOf(new_Str[i].charAt(2))) > Integer.parseInt(String.valueOf(new_Str[i + 1].charAt(2)))) {
                    isSorted = false;

                    buf = new_Str[i];
                    new_Str[i] = new_Str[i + 1];
                    new_Str[i + 1] = buf;
                }
            }
        }
        System.out.println(Arrays.toString(new_Str));
        str = "";
    }
}