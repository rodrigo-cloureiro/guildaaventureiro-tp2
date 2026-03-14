package br.com.infnet.guildaaventureiro.dto;

public record AventureiroProfileResponse(
        AventureiroResponseDto aventureiro,
        CompanheiroResponse companheiro,
        long totalParticipacoesEmMissoes,
        MissaoResponse missao
) {
}
