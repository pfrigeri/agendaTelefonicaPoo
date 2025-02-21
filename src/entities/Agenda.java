package entities;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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

    public void salvarContatos(String path) throws Exception {

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(path + "\\contatosAgenda.txt"))){

            for(Contato c : contatos){
                bw.write(c.getNome()+","+c.getNumero());
                bw.newLine();
            }
        }
        catch(IOException e){
            System.out.println("Erro: " + e.getMessage());
        }
        catch (Exception e) {
            System.out.println("Entrada inválida! Verifique se o arquivo tem a formatação csv.\n" + e.getMessage());
        }
    }

    public List<Contato> buscarContatos(String nomeBusca){
        int comeco = 0, fim = contatos.size() -1;

        List<Contato> resultados = new ArrayList<>();

        while(comeco <= fim){
            int meio = (comeco + fim) / 2;
            Contato chute = contatos.get(meio);


            //Verificar se há mais algum contato com o parametro da bsuca (ex: 'ana')
            int comparacao = chute.getNome().substring(0,nomeBusca.length()).compareToIgnoreCase(nomeBusca);

            if (comparacao == 0) {
                int esquerda = meio -1, direita = meio +1;
                resultados.add(chute);

                while(esquerda >= 0 && contatos.get(esquerda).getNome().startsWith(nomeBusca)){
                    resultados.add(contatos.get(esquerda));
                    esquerda--;
                }

                while(direita <= fim && contatos.get(direita).getNome().startsWith(nomeBusca)){
                    resultados.add(contatos.get(direita));
                    direita++;
                }
                return resultados;

            } else if (comparacao > 0) {
                fim = meio - 1;
            } else {
                comeco = meio + 1;
            }
        }
        return new ArrayList<>(); // Retorna null se não encontrar o contato
    }

    public void validarNumeroUnico(Integer numeroNovo) throws DomainException {
        for(Contato c : contatos){
            if(c.getNumero().equals(numeroNovo)){
                throw new DomainException("Número de Contato já existente.");
            }
        }
    }




}
