package br.mackenzie.ps2.bancodao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class GerenciadorContasApp {
    public static void main(String[] args) {
        IContaDao contaDao = new ContaDao();
        ITitularDao titularDao = new TitularDao();
        Scanner scanner = new Scanner(System.in);

        // Garante que a tabela contas exista
        try (var conn = ConnectionFactory.getConnection();
             var stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS contas (nro_conta BIGINT PRIMARY KEY, saldo DECIMAL(8,2))");
        } catch (Exception e) {
            System.err.println("Erro ao criar tabela contas: " + e.getMessage());
        }

        int opcaoPrincipal = 0;
        while (opcaoPrincipal != 3) {
            System.out.println("\n=== SISTEMA BANCÁRIO ===");
            System.out.println("1. Gerenciar Contas");
            System.out.println("2. Gerenciar Titulares");
            System.out.println("3. Sair");
            System.out.print("Escolha uma opção: ");
            opcaoPrincipal = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer

            if (opcaoPrincipal == 1) {
                menuContas(scanner, contaDao);
            } else if (opcaoPrincipal == 2) {
                menuTitulares(scanner, titularDao);
            } else if (opcaoPrincipal == 3) {
                System.out.println("Saindo do sistema...");
            } else {
                System.out.println("Opção inválida.");
            }
        }
        scanner.close();
    }

    // --- LÓGICA DE CONTAS ---
    private static void menuContas(Scanner scanner, IContaDao dao) {
        int opcao = 0;
        while (opcao != 5) {
            System.out.println("\n--- MENU CONTAS ---");
            System.out.println("1. Criar Conta | 2. Listar | 3. Atualizar Saldo | 4. Apagar | 5. Voltar");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

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
            }
        }
    }

    // --- LÓGICA DE TITULARES ---
    private static void menuTitulares(Scanner scanner, ITitularDao dao) {
        int opcao = 0;
        while (opcao != 5) {
            System.out.println("\n--- MENU TITULARES ---");
            System.out.println("1. Criar Titular | 2. Listar | 3. Atualizar | 4. Apagar | 5. Voltar");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.print("Número do Titular: ");
                    long numero = scanner.nextLong();
                    scanner.nextLine(); // Limpar buffer
                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();
                    System.out.print("RG: ");
                    String rg = scanner.nextLine();
                    System.out.print("CPF: ");
                    String cpf = scanner.nextLine();
                    dao.criar(new Titular(numero, nome, rg, cpf));
                    System.out.println("Titular criado!");
                    break;
                case 2:
                    List<Titular> titulares = dao.lerTodos();
                    System.out.println("--- TITULARES ---");
                    for (Titular t : titulares) {
                        System.out.println("Número: " + t.getNroTitular() + 
                                           " | Nome: " + t.getNome() + 
                                           " | RG: " + t.getRg() + 
                                           " | CPF: " + t.getCpf());
                    }
                    break;
                case 3:
                    System.out.print("Número do Titular a alterar: ");
                    long numAlt = scanner.nextLong();
                    scanner.nextLine();
                    Titular titularAlt = dao.buscarPeloNumero(numAlt);
                    if (titularAlt != null) {
                        System.out.print("Novo Nome: ");
                        titularAlt.setNome(scanner.nextLine());
                        System.out.print("Novo RG: ");
                        titularAlt.setRg(scanner.nextLine());
                        System.out.print("Novo CPF: ");
                        titularAlt.setCpf(scanner.nextLine());
                        dao.atualizar(titularAlt);
                        System.out.println("Titular atualizado!");
                    } else {
                        System.out.println("Titular não encontrado.");
                    }
                    break;
                case 4:
                    System.out.print("Número do Titular a apagar: ");
                    long numDel = scanner.nextLong();
                    Titular titularDel = dao.buscarPeloNumero(numDel);
                    if (titularDel != null) {
                        dao.apagar(titularDel);
                        System.out.println("Titular apagado!");
                    } else {
                        System.out.println("Titular não encontrado.");
                    }
                    break;
            }
        }
    }
}