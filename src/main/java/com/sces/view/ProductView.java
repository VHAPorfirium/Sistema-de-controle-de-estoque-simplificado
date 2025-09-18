package com.sces.view;

import java.util.List;
import com.sces.model.Product;

public final class ProductView {
    private ProductView() {}

    public static String toTable(List<Product> products) {
        // Larguras mínimas baseadas nos rótulos
        int wId = "ID".length();
        int wName = "NOME".length();
        int wDesc = "DESCRIÇÃO".length();
        int wQty = "QTD".length();

        for (Product p : products) {
            wId = Math.max(wId, String.valueOf(p.getId()).length());
            wName = Math.max(wName, safe(p.getName()).length());
            String d = normalizeDesc(p.getDescription());
            wDesc = Math.max(wDesc, d.length());
            wQty = Math.max(wQty, String.valueOf(p.getQuantity()).length());
        }

        String header = String.format("%" + wId + "s | %-" + wName + "s | %-" + wDesc + "s | %" + wQty + "s",
                "ID", "NOME", "DESCRIÇÃO", "QTD");
        String sep = "-".repeat(header.length());

        StringBuilder sb = new StringBuilder(header).append("\n").append(sep);
        for (Product p : products) {
            String line = String.format(
                    "%" + wId + "d | %-" + wName + "s | %-" + wDesc + "s | %" + wQty + "d",
                    p.getId(),
                    safe(p.getName()),
                    normalizeDesc(p.getDescription()),
                    p.getQuantity()
            );
            sb.append("\n").append(line);
        }
        return sb.toString();
    }

    private static String safe(String s) { return s == null ? "" : s; }

    private static String normalizeDesc(String d) {
        // Mostra "—" quando nula/vazia, para cumprir "exibir descrição"
        return (d == null || d.isBlank()) ? "—" : d;
    }
}