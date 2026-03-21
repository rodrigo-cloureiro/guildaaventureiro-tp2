package br.com.infnet.guildaaventureiro.dto.relatorio;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDateTime;

public record RelatorioMissaoFiltroRequest(
        @NotNull(message = "A data de início é obrigatória")
        @PastOrPresent(message = "A data de início da missão não pode ser no futuro")
        LocalDateTime inicio,

        @NotNull(message = "A data de término é obrigatória")
        @PastOrPresent(message = "A data de término da missão não pode ser no futuro")
        LocalDateTime termino
) {
}
