package br.com.infnet.guildaaventureiro.dto.aventureiro;

import br.com.infnet.guildaaventureiro.dto.companheiro.CompanheiroResponse;
import br.com.infnet.guildaaventureiro.dto.missao.MissaoMinimalResponse;

public record AventureiroProfileResponse(
        AventureiroResponse aventureiro,
        CompanheiroResponse companheiro,
        long totalParticipacoesEmMissoes,
        MissaoMinimalResponse missao
) {
}
