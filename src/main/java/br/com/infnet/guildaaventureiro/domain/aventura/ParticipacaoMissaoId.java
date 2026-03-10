package br.com.infnet.guildaaventureiro.domain.aventura;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class ParticipacaoMissaoId implements Serializable {
    @Column(name = "missao_id", nullable = false)
    private Long missaoId;

    @Column(name = "aventureiro_id", nullable = false)
    private Long aventureiroId;

    protected ParticipacaoMissaoId() {
    }

    public ParticipacaoMissaoId(Long missaoId, Long aventureiroId) {
        this.missaoId = missaoId;
        this.aventureiroId = aventureiroId;
    }
}
