package br.com.infnet.guildaaventureiro.dto;

import br.com.infnet.guildaaventureiro.dto.missao.MissaoMinimalResponse;

public record AventureiroProfileResponse(
        AventureiroResponse aventureiro,
        CompanheiroResponse companheiro,
        long totalParticipacoesEmMissoes,
        MissaoMinimalResponse missao
) {
}
