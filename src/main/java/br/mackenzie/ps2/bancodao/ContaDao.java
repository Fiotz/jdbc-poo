package br.mackenzie.ps2.bancodao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContaDao implements IContaDao {

    public ContaDao() {
        // Criar a tabela automaticamente se não existir
        try (var conn = ConnectionFactory.getConnection();
                var stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS contas (nro_conta BIGINT PRIMARY KEY, saldo DECIMAL(8,2))");
        } catch (Exception e) {
            System.err.println("Erro ao criar tabela: " + e.getMessage());
        }
    }

    @Override
    public boolean criar(Conta conta) {
        return false;
    }

    @Override
    public List<Conta> lerTodas() {
        List<Conta> contas = new ArrayList<>();
        return contas;
    }
    

    @Override
    public Conta buscarPeloNumero(long numero) {
        return null;
    }

    @Override
    public boolean atualizar(Conta conta) {
        return false;
    }

    @Override
    public boolean apagar(Conta conta) {
        return false;
    }
}