package br.pro.hashi.ensino.desagil.morse;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MorseTree {
    private Node start;

    private static MorseTree instancia = null;
    public static MorseTree getInstancia() {
        if(instancia == null) {
            instancia = new MorseTree();
        }
        return instancia;
    }

    public MorseTree() {
        char[] letras = new char[]{'+', 'e', 't', 'i', 'a', 'n', 'm', 's', 'u', 'r', 'w',
                'd', 'k', 'g', 'o', 'h', 'v', 'f', ' ', 'l', ' ', 'p', 'j',
                'b', 'x', 'c', 'y', 'z', 'q', ' ', ' ', '5', '4', ' ', '3',
                ' ', ' ', ' ', '2', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '1',
                '6', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '7', ' ', ' ', ' ',
                '8', ' ', '9', '0'};

        Node[] nodes = new Node[64];
        for (int i = 0; i < letras.length; i++) {
            if (letras[i] == ' ') {
                nodes[i] = null;
            } else {
                nodes[i] = new Node(letras[i], null, null);
            }

        }

        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i] != null) {
                if (2 * i + 1 < nodes.length) {
                    nodes[i].setLeft(nodes[2 * i + 1]);
                }
                if (2 * i + 2 < nodes.length) {
                    nodes[i].setRight(nodes[2 * i + 2]);
                }
            }
        }
        start = nodes[0];
    }

    public char translate(String code) {
        Node n = start;
        for (int i = 0; i < code.length(); i++) {
            code.charAt(i);
            if (code.charAt(i) == '.') {
                n = n.getLeft();
            } else {
                n = n.getRight();
            }
        }
        return n.getValue();
    }


    public LinkedList<String> getDicionario() {
        ConcurrentLinkedQueue<Node> queue = new ConcurrentLinkedQueue<>();
        start.setOpen(false);
        queue.add(start);


        LinkedList<String> dicionario = new LinkedList<>();

        while (!queue.isEmpty()) {
            Node node = queue.element();
            Node left = node.getLeft();
            Node right = node.getRight();


            if (left != null && left.isOpen()) {
                left.setOpen(false);
                queue.add(left);
            } else if (right != null && right.isOpen()) {
                right.setOpen(false);
                queue.add(right);
            } else {
                Node apagado = queue.remove();

                if (apagado.getValue() != ' ' && apagado != start) {
                    String codigo = "";
                    char apagadovelho = apagado.getValue();
                    while (apagado != start) {
                        if (apagado.getParent().getLeft() == apagado) {
                            codigo = codigo + ".";
                        } else {
                            codigo = codigo + "-";
                        }
                        apagado = apagado.getParent();
                    }
                    String novocodigo = new StringBuilder(codigo).reverse().toString();
                    dicionario.add(novocodigo + "  =  " + apagadovelho);
                }

            }


        }
    return dicionario;
    }
}