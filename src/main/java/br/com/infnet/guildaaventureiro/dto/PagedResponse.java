package br.com.infnet.guildaaventureiro.dto;

import java.util.List;

public record PagedResponse<T>(
        int page,
        int size,
        int total,
        int totalPages,
        List<T> content
) {
}
