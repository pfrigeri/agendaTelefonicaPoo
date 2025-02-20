package entities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import enums.Status;
import exception.DomainException;

public class Contato implements Comparable<Contato> { //Permite a comparação do nome dos Contatos
    private String nome;
    private Integer numero;
    private LocalDate dataCriacao;
    private Status status;

    public static final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Contato() {
    }

    public Contato(String nome, Integer numero) throws DomainException {
        this.nome = nome;
        if(numero < 100000000 || numero > 999999999){
            throw new DomainException("O Número de telefone deve possuir 9 digitos.");
        }
        this.numero = numero;
        this.dataCriacao = LocalDate.now();
        this.status = Status.PERMITIDO;

    }

    public Contato(String nome, Integer numero, LocalDate dataCriacao) throws DomainException {
        this.nome = nome;
        if(numero < 100000000 || numero > 999999999){
            throw new DomainException("O Número de telefone deve possuir 9 digitos.");
        }
        this.numero = numero;
        this.dataCriacao = dataCriacao;
        this.status = Status.PERMITIDO;

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getDataCriacao() {
        return dataCriacao.format(fmt);
    }

    @Override
    public String toString(){
        return "Nome: " + nome + "\n" +
                "Número: " +numero + "\n"+
                "Criado em: " + dataCriacao.format(fmt) + "\n" +
                "Status: " + status + "\n";
    }

    @Override
    public int compareTo(Contato outro){
        return this.nome.compareToIgnoreCase(outro.getNome());
    }

    //Mudança para que este metodo sirva para bloquear e desbloquear
    public void blockUnblockContato(){
        if(this.status == Status.PERMITIDO) {
            this.status = Status.BLOQUEADO;
        }
        else{
            this.status = Status.PERMITIDO;
        }
    }


}
