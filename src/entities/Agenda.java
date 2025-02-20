package entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import entities.Contato;
import exception.DomainException;

public class Agenda {
    private List<Contato> contatos;

    public Agenda() {
        this.contatos = new ArrayList<>();
    }

    public void adicionarContato(Contato contato) throws DomainException{
       validarNumeroUnico(contato.getNumero());//Impede numeros repetidos de serem salvos
       contatos.add(contato);
       System.out.println("Contato Salvo com Sucesso !");
       Collections.sort(contatos); //Ordena os contatos automaticamente
    }

    public void removerContato(Contato contato){
        contatos.remove(contato);
        System.out.println("Contato Excluído com Sucesso !");
        Collections.sort(contatos);
    }


    public void exibirContatos(){
        if(contatos.isEmpty()){
            System.out.println();
            System.out.println("A Agenda está vazia no momento.");
            System.out.println();
            return;
        }
        else{
            for(Contato c : contatos ){
                System.out.println("==================================\n");
                System.out.println(c + "\n");
            }
        }
    }

    public Contato buscarContatos(String nomeBusca){
        int comeco = 0, fim = contatos.size() -1;
        while(comeco <= fim){
            int meio = (comeco + fim) / 2;
            Contato chute = contatos.get(meio);


            int comparacao = chute.getNome().substring(0,nomeBusca.length()).compareTo(nomeBusca);

            if (comparacao == 0) {
                return chute;
            } else if (comparacao > 0) {
                fim = meio - 1;
            } else {
                comeco = meio + 1;
            }
        }
        return null; // Retorna null se não encontrar o contato
    }

    public void validarNumeroUnico(Integer numeroNovo) throws DomainException {
        for(Contato c : contatos){
            if(c.getNumero().equals(numeroNovo)){
                throw new DomainException("Número de Contato já existente.");
            }
        }
    }




}
