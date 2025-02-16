package application;

import entities.Agenda;
import entities.Contato;
import exception.DomainException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) throws Exception{
        Scanner sc = new Scanner(System.in);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Agenda agenda = new Agenda();


        //Criar um menu de opções com switch case (feito)
        //Verificar numeros de telefone duplicados (feito)
        //Fazer um metodo de busca que devolva todos os contatos com o nome digitado(Ex: Todos os contatos "ana")
        //Importar contatos de arquivo e salvar contatos em arquivo


        System.out.println("================= AGENDA TELEFÔNICA POO =================");
        int opt;
        do{
            System.out.println("         MENU                   ");
            System.out.println("1. Adicionar Contato.");
            System.out.println("2. Exibir Contatos.");
            System.out.println("3. Buscar Contato.");
            System.out.println("6. Sair.");
            System.out.print("\nEscolha uma opção: ");
            opt = sc.nextInt();
            sc.nextLine();

            switch(opt) {
                case 1:
                    System.out.println("         Novo Contato         ");
                    System.out.print("Nome: ");
                    String nome = sc.nextLine();

                    int numero = 0;
                    boolean numeroValido = false;

                    while (!numeroValido) {
                        try {
                            System.out.print("Número (xxxxx-xxxx): ");
                            numero = sc.nextInt();
                            sc.nextLine(); // Consumir a quebra de linha

                            // Tentativa de criar um novo contato
                            Contato contato = new Contato(nome, numero, LocalDate.now());
                            agenda.adicionarContato(contato);
                            numeroValido = true; // Sai do loop se não houver exceção

                        } catch (DomainException e) {
                            System.out.println("Erro: " + e.getMessage() + " Tente novamente.");
                        } catch (Exception e) {
                            System.out.println("Entrada inválida! Certifique-se de inserir um número correto.");
                            sc.nextLine(); // Limpar buffer
                        }
                    }
                break;

                case 2:
                    agenda.exibirContatos();
                    break;

                case 3:
                    System.out.println("Nome do contato desejado: ");
                    String nomeBusca = sc.nextLine();

                    Contato contatoEncontrado = agenda.buscarContatos(nomeBusca);

                    if (contatoEncontrado == null) {
                        System.out.println("Contato não encontrado.");
                    } else {
                        System.out.println("Contato encontrado: " + contatoEncontrado);
                        System.out.println("         Opções:                  ");
                        System.out.println("1. Excluir contato.");
                        System.out.println("2. Alterar contato.");
                        System.out.println("3. Bloquear Contato.");
                        System.out.println("4. Sair");
                        System.out.print("Opção: ");
                        int opt2 = sc.nextInt();
                        sc.nextLine();
                        if(opt2 == 1)
                            agenda.removerContato(contatoEncontrado);
                        if(opt2 == 2)
                            alterarContato(contatoEncontrado);
                        if(opt2 == 3)
                            contatoEncontrado.bloquearContato();
                        if(opt2 == 4)
                            break;


                    }
                    break;
            }

        }while(opt != 6);
        sc.close();
    }
    public static void alterarContato(Contato contatoEncontrado){
        Scanner sc = new Scanner(System.in);
        System.out.println("         Edição de Contato       ");
        System.out.println("1. Alterar Nome.");
        System.out.println("2. Alterar Numero.");
        System.out.println("3. Alterar Ambos.");
        System.out.print("Opção: ");
        int opt = sc.nextInt();
        if(opt == 1) {
            System.out.print("Novo Nome: ");
            String NewName = sc.nextLine();
            contatoEncontrado.setNome(NewName);
        }
        if(opt == 2) {
            System.out.print("Novo Numero: ");
            Integer NewNumber = sc.nextInt();
            contatoEncontrado.setNumero(NewNumber);
        }
        if (opt == 3) {
            System.out.print("Novo Nome: ");
            String NewName = sc.nextLine();
            contatoEncontrado.setNome(NewName);
            System.out.print("Novo Numero: ");
            Integer NewNumber = sc.nextInt();
            contatoEncontrado.setNumero(NewNumber);
        }
        else{
            System.out.println("Entrada inválida.");
        }
        sc.close();
    }

}