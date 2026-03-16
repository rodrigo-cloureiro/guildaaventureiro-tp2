package br.com.infnet.guildaaventureiro.dto.missao;

import br.com.infnet.guildaaventureiro.dto.aventureiro.AventureiroMissaoResponse;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.util.List;

public record MissaoDetailedResponse(
        @JsonUnwrapped
        MissaoResponse missao,
        List<AventureiroMissaoResponse> participantes
) {
}
