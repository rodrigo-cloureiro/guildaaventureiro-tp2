package br.com.infnet.guildaaventureiro.dto;

import java.util.List;

public record PagedResponseDto<T>(
        int page,
        int size,
        int total,
        int totalPages,
        List<T> content
) {
}
