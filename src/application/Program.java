package application;

import entities.Agenda;
import entities.Contato;
import exception.DomainException;

import java.io.*;
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
            System.out.println("4. Importar Contatos.");
            System.out.println("5. Salvar Agenda.");
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
                            sc.nextLine();

                            // Tentativa de criar um novo contato
                            Contato contato = new Contato(nome, numero);
                            agenda.adicionarContato(contato);
                            numeroValido = true; // Sai do loop se não houver exceção

                        } catch (DomainException e) {
                            System.out.println("Erro: " + e.getMessage() + " Tente novamente.");
                        } catch (Exception e) {
                            System.out.println("Entrada inválida! Certifique-se de inserir um número correto.");
                            sc.nextLine();
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
                            contatoEncontrado.blockUnblockContato();
                        if(opt2 == 4)
                            break;
                    }
                    break;

                //alterar a forma de validação para (Boolean) que apenas ignore Contatos já salvos e não interrompa o fluxo de leitura do arquivo.
                case 4:
                    System.out.println("Digite o caminho do arquivo: ");
                    String strpath = sc.next();

                    try(BufferedReader br = new BufferedReader(new FileReader(strpath))){
                        String line = br.readLine();
                        while(line != null){
                            String[] l = line.split(",");
                            if(l.length < 3) {
                                agenda.adicionarContato(new Contato(l[0], Integer.parseInt(l[1])));
                            }
                            else if(l.length == 3 && !l[2].isEmpty()){
                                Contato contato = new Contato(l[0], Integer.parseInt(l[1]), LocalDate.parse(l[2],fmt));
                            }
                            line = br.readLine();
                        }
                    }
                    catch(IOException e){
                        System.out.println("Erro: " + e.getMessage());
                    } catch (DomainException e) {
                        System.out.println("Erro: " + e.getMessage() + " Tente novamente.");
                    } catch (Exception e) {
                        System.out.println("Entrada inválida! Verifique se o arquivo tem a formatação csv.\n" + e.getMessage());
                        sc.nextLine();
                    }

                case 5:
                    System.out.println("Digite o caminho do arquivo: ");
                    strpath = sc.next();

                    agenda.salvarContatos(strpath);
                    break;
            }

        }while(opt != 6);
        sc.close();
    }

    //Criar metodo adicionar contato ?

    //Delegar metodo de alteração para Agenda
    public static void alterarContato(Contato contatoEncontrado){
        Scanner sc = new Scanner(System.in);
        System.out.println("         Edição de Contato       ");
        System.out.println("1. Alterar Nome.");
        System.out.println("2. Alterar Numero.");
        System.out.println("3. Alterar Ambos.");
        System.out.print("Opção: ");
        int opt = sc.nextInt();
        sc.nextLine();

        switch(opt) {
            case 1:
                System.out.print("\nNovo Nome: ");
                String NewName = sc.nextLine();
                contatoEncontrado.setNome(NewName);
                System.out.println("Nome alterado com sucesso!");
                break;

            case 2:
                System.out.print("\nNovo Numero: ");
                Integer NewNumber = sc.nextInt();
                sc.nextLine();
                contatoEncontrado.setNumero(NewNumber);
                System.out.println("Numero alterado com sucesso!");
                break;

            case 3:
                System.out.print("\nNovo Nome: ");
                NewName = sc.nextLine();
                contatoEncontrado.setNome(NewName);
                System.out.print("\nNovo Numero: ");
                NewNumber = sc.nextInt();
                sc.nextLine();
                contatoEncontrado.setNumero(NewNumber);
                System.out.println("Dados alterados com sucesso!");
                break;

        }
    }

}