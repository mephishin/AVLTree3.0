import java.io.*;
import java.util.*;


public class Main {


    public static void main(String args[]) {
        Tree t = new Tree();
        Node root = null;
        while (true) {
            System.out.println("(1) Найти");
            System.out.println("(2) Вставить");
            System.out.println("(3) Удалить");
            System.out.println("(4) Показать");

            try {
                BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
                String s = bufferRead.readLine();

                if (Integer.parseInt(s) == 1) {
                    System.out.print("Значение для поиска: ");
                    System.out.println(t.find(root, Integer.parseInt(bufferRead.readLine())));
                }
                else if (Integer.parseInt(s) == 4) {
                    System.out.println("Последовательность из которой получиится сбалансированное дерево: ");
                    t.makeSequence(root, 1);
                    t.showSequence();

                }
                else if (Integer.parseInt(s) == 2) {
                    System.out.print("Значение для вставки: ");
                    root = t.insert(root, Integer.parseInt(bufferRead.readLine()));
                }
                else if (Integer.parseInt(s) == 3) {
                    System.out.println("Значение для удаления: ");
                    root = t.deleteNode(root, Integer.parseInt(bufferRead.readLine()));
                } else {
                    System.out.println("Такого варианта нет. Попробуте снова!");
                    continue;
                }
                System.out.println(" ");
            }
            catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
}