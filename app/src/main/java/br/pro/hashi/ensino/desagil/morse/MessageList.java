package br.pro.hashi.ensino.desagil.morse;

import java.util.LinkedList;
import java.util.List;

public class MessageList {

    private List<String> listaMensagem;


    public MessageList() {
        listaMensagem = new LinkedList<>();
        listaMensagem.add("Preciso de água, por favor!");
        listaMensagem.add("Preciso falar com você!");
        listaMensagem.add("Você poderia me ajudar a ir ao banheiro?");
        listaMensagem.add("Estou com dor");
        listaMensagem.add("EMERGÊNCIA!");
    }

    public List<String> getListaMensagem() {
        return listaMensagem;
    }
}
