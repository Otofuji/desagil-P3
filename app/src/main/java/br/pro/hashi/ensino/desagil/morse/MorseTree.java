package br.pro.hashi.ensino.desagil.morse;

public class MorseTree {
    private Node start;

    public MorseTree(){
        char [] letras = new char[] {'+', 'e', 't', 'i','a', 'n', 'm','s','u','r','w',
                                    'd','k','g', 'o', 'h', 'v','f',' ','l', ' ','p','j',
                                    'b','x','c','y','z','q',' ', ' ','5','4',' ','3',
                                    ' ',' ',' ','2',' ',' ',' ',' ',' ',' ',' ','1',
                                    '6',' ',' ',' ',' ',' ',' ',' ','7',' ',' ',' ',
                                    '8',' ','9','0'};

        Node[] nodes = new Node[64];
        for(int i = 0; i< letras.length; i++) {
            if (letras[i] == ' ') {
                nodes[i] = null;
            } else {
                nodes[i] = new Node(letras[i], null, null);
            }

        }

        for(int i = 0; i< nodes.length; i++) {
            if(nodes[i]!=null) {
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
        for(int i =0; i<code.length(); i++){
            code.charAt(i);
            if(code.charAt(i)=='.'){
                n = n.getLeft();
            }
            else{
                n = n.getRight();
            }
        }
        return n.getValue();
    }
}