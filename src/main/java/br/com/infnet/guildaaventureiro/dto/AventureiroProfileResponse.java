package br.com.infnet.guildaaventureiro.dto;

public record AventureiroProfileResponse(
        AventureiroResponse aventureiro,
        CompanheiroResponse companheiro,
        long totalParticipacoesEmMissoes,
        MissaoResponse missao
) {
}
