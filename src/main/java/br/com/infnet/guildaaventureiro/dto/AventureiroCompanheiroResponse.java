package br.com.infnet.guildaaventureiro.dto;

import br.com.infnet.guildaaventureiro.dto.companheiro.CompanheiroResponse;

public record AventureiroCompanheiroResponse(
        String nome,
        CompanheiroResponse companheiro
) {
}
