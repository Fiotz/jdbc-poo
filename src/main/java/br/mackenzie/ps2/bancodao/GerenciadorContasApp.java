package br.mackenzie.ps2.bancodao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class GerenciadorContasApp {
    public static void main(String[] args) {
        IContaDao dao = new ContaDao();
        Scanner scanner = new Scanner(System.in);

        // Criar a tabela automaticamente se não existir
        try (var conn = ConnectionFactory.getConnection();
             var stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS contas (nro_conta BIGINT PRIMARY KEY, saldo DECIMAL(8,2))");
        } catch (Exception e) {
            System.err.println("Erro ao criar tabela: " + e.getMessage());
        }

        while (true) {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Criar Conta");
            System.out.println("2. Listar Contas");
            System.out.println("3. Atualizar Saldo");
            System.out.println("4. Apagar Conta");
            System.out.println("5. Sair");
            System.out.print("Escolha: ");
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer

            switch (opcao) {
                case 1:
                    System.out.print("Número da conta: ");
                    long numero = scanner.nextLong();
                    System.out.print("Saldo inicial: ");
                    BigDecimal saldo = scanner.nextBigDecimal();
                    dao.criar(new Conta(numero, saldo));
                    System.out.println("Conta criada!");
                    break;
                case 2:
                    List<Conta> contas = dao.lerTodas();
                    System.out.println("--- CONTAS ---");
                    for (Conta c : contas) {
                        System.out.println("Número: " + c.getNumero() + " | Saldo: R$ " + c.getSaldo());
                    }
                    break;
                case 3:
                    System.out.print("Número da conta: ");
                    long numAlt = scanner.nextLong();
                    Conta contaAlt = dao.buscarPeloNumero(numAlt);
                    if (contaAlt != null) {
                        System.out.print("Novo saldo: ");
                        BigDecimal novoSaldo = scanner.nextBigDecimal();
                        contaAlt.setSaldo(novoSaldo);
                        dao.atualizar(contaAlt);
                        System.out.println("Saldo atualizado!");
                    } else {
                        System.out.println("Conta não encontrada.");
                    }
                    break;
                case 4:
                    System.out.print("Número da conta a apagar: ");
                    long numDel = scanner.nextLong();
                    Conta contaDel = dao.buscarPeloNumero(numDel);
                    if (contaDel != null) {
                        dao.apagar(contaDel);
                        System.out.println("Conta apagada!");
                    } else {
                        System.out.println("Conta não encontrada.");
                    }
                    break;
                case 5:
                    System.out.println("Saindo...");
                    scanner.close();
                    System.exit(0);
            }
        }
    }
}