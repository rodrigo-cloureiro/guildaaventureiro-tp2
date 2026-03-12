package br.com.infnet.guildaaventureiro.dto;

import br.com.infnet.guildaaventureiro.domain.aventura.enums.AventureiroClasse;

public record AventureiroResponseDto(
        Long id,
        String nome,
        AventureiroClasse classe,
        int nivel,
        boolean ativo
) {
}
