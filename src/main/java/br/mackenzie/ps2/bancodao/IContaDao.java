package br.mackenzie.ps2.bancodao;

import java.util.List;

public interface IContaDao {
    boolean criar(Conta conta);
    List<Conta> lerTodas();
    Conta buscarPeloNumero(long numero);
    boolean atualizar(Conta conta);
    boolean apagar(Conta conta);
}