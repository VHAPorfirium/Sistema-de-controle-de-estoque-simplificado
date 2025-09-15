package com.sces.main;

import java.util.List;
import java.util.Scanner;

import com.sces.exception.DuplicateProductNameException;
import com.sces.exception.InvalidProductNameException;
import com.sces.exception.NegativeQuantityException;
import com.sces.model.Product;
import com.sces.repository.InMemoryProductRepository;
import com.sces.service.DefaultProductService;
import com.sces.service.ProductService;

public class App {

    public static void main(String[] args) {
        ProductService service = new DefaultProductService(new InMemoryProductRepository());
        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("=== Inventário (CLI) ===");
            while (true) {
                System.out.println("\n1) Cadastrar produto  2) Listar  0) Sair");
                System.out.print("> ");
                String op = sc.nextLine().trim();
                if ("0".equals(op))
                    break;

                switch (op) {
                    case "1" -> {
                        try {
                            System.out.print("Nome: ");
                            String name = sc.nextLine();

                            System.out.print("Descrição (opcional): ");
                            String desc = sc.nextLine();

                            System.out.print("Quantidade inicial: ");
                            int qty = Integer.parseInt(sc.nextLine().trim());

                            Product p = service.createProduct(name, desc, qty);
                            System.out.println("OK! Criado: " + p);
                        } catch (NumberFormatException nfe) {
                            System.out.println("Quantidade inválida. Use número inteiro.");
                        } catch (InvalidProductNameException | NegativeQuantityException
                                | DuplicateProductNameException ex) {
                            System.out.println("Erro: " + ex.getMessage());
                        } catch (Exception ex) {
                            System.out.println("Erro inesperado: " + ex.getMessage());
                        }
                    }
                    case "2" -> {
                        List<Product> products = service.listAll();
                        if (products.isEmpty()) {
                            System.out.println("(vazio)");
                        } else {
                            products.forEach(System.out::println);
                        }
                    }
                    default -> System.out.println("Opção inválida.");
                }
            }
            System.out.println("Encerrado.");
        }
    }
}
