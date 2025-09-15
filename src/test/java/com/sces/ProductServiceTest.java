package com.sces;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import com.sces.exception.DuplicateProductNameException;
import com.sces.exception.InvalidProductNameException;
import com.sces.exception.NegativeQuantityException;
import com.sces.model.Product;
import com.sces.repository.InMemoryProductRepository;
import com.sces.repository.ProductRepository;
import com.sces.service.DefaultProductService;
import com.sces.service.ProductService;

class ProductServiceTest {

    private ProductService service;

    @BeforeEach
    void setup() {
        ProductRepository repo = new InMemoryProductRepository();
        service = new DefaultProductService(repo);
    }

    @Test
    @DisplayName("Cria produto válido com ID sequencial iniciando em 1")
    void createValidProduct_assignsSequentialId() {
        Product p1 = service.createProduct("Mouse", "Sem fio", 10);
        Product p2 = service.createProduct("Teclado", "Mecânico", 5);

        assertEquals(1L, p1.getId());
        assertEquals(2L, p2.getId());
        assertEquals("Mouse", p1.getName());
        assertEquals(10, p1.getQuantity());
    }

    @Test
    @DisplayName("Não permite nome duplicado (case-insensitive e com trim)")
    void duplicateName_isRejected_caseInsensitive_trimmed() {
        service.createProduct("Notebook", "14\"", 3);
        assertThrows(DuplicateProductNameException.class,
                () -> service.createProduct("  notebook  ", "Outro", 1));
        assertThrows(DuplicateProductNameException.class,
                () -> service.createProduct("NOTEBOOK", null, 0));
    }

    @Test
    @DisplayName("Quantidade negativa é rejeitada")
    void negativeQuantity_isRejected() {
        assertThrows(NegativeQuantityException.class,
                () -> service.createProduct("Cabo HDMI", "2m", -1));
    }

    @Test
    @DisplayName("Nome inválido (null/blank) é rejeitado")
    void blankName_isRejected() {
        assertThrows(InvalidProductNameException.class,
                () -> service.createProduct("   ", "desc", 0));
        assertThrows(InvalidProductNameException.class,
                () -> service.createProduct(null, "desc", 0));
    }

    @Test
    @DisplayName("Quantidade zero é permitida")
    void zeroQuantity_isAllowed() {
        Product p = service.createProduct("Pendrive", null, 0);
        assertEquals(0, p.getQuantity());
    }

    @Test
    @DisplayName("Listagem ordenada por ID")
    void listSortedById() {
        Product a = service.createProduct("A", null, 1);
        Product c = service.createProduct("C", null, 1);
        Product b = service.createProduct("B", null, 1);
        List<Product> all = service.listAll();
        assertEquals(List.of(a, c, b), all); // IDs 1,2,3
        assertEquals(1L, all.get(0).getId());
        assertEquals(3L, all.get(2).getId());
    }

    @Test
    @DisplayName("toString organizado e informativo")
    void toString_isFormatted() {
        Product p1 = service.createProduct("Webcam", "", 2);
        Product p2 = service.createProduct("Headset", "USB-C", 5);
        assertTrue(p1.toString().contains("Webcam"));
        assertTrue(p1.toString().contains("qtd=2"));
        assertTrue(p2.toString().contains("USB-C"));
        assertTrue(p2.toString().startsWith("#"));
    }
}
