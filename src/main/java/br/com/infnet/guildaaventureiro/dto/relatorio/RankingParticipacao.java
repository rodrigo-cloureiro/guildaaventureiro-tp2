package br.com.infnet.guildaaventureiro.dto.relatorio;

public record RankingParticipacao(
        String nome,
        Long participacoes,
        Long recompensaRecebida,
        Long destaquesObtidos
) {
}
