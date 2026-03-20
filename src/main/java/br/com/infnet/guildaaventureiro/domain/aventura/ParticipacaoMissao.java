package br.com.infnet.guildaaventureiro.domain.aventura;

import br.com.infnet.guildaaventureiro.domain.aventura.enums.PapelMissao;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(
        name = "participacao_missao",
        schema = "aventura",
        indexes = {
                @Index(name = "idx_participacao_missao_aventureiro", columnList = "aventureiro_id")
        }
)
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ParticipacaoMissao {
    @EmbeddedId
    private final ParticipacaoMissaoId id = new ParticipacaoMissaoId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId(value = "missaoId")
    @JoinColumn(
            name = "missao_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_participacao_missao_missao")
    )
    @EqualsAndHashCode.Include
    private Missao missao;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId(value = "aventureiroId")
    @JoinColumn(
            name = "aventureiro_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_participacao_missao_aventureiro")
    )
    @EqualsAndHashCode.Include
    private Aventureiro aventureiro;

    @Enumerated(EnumType.STRING)
    @Column(name = "papel_missao", nullable = false)
    private PapelMissao papel;

    @Min(value = 0, message = "A recompensa em ouro deve ser maior ou igual a zero")
    @Column(name = "recompensa_em_ouro", nullable = true)
    private Integer recompensaEmOuro = 0;

    @Column(nullable = false)
    private boolean mvp = false;

    @CreationTimestamp
    @Column(name = "data_registro", nullable = false, updatable = false)
    private LocalDateTime dataRegistro;

    protected ParticipacaoMissao() {
    }

    public ParticipacaoMissao(Missao missao, Aventureiro aventureiro, PapelMissao papelMissao) {
        this.missao = missao;
        this.aventureiro = Objects.requireNonNull(aventureiro, "O aventureiro é obrigatório");
        this.papel = Objects.requireNonNull(papelMissao, "O papel do aventureiro na missão é obrigatório");
    }

    void definirMvp() {
        this.mvp = true;
    }

    public void recompensar(int recompensa) {
        if (recompensa <= 0) {
            throw new IllegalArgumentException("Recompensa deve ser um valor positivo");
        }

        this.recompensaEmOuro += recompensa;
    }
}
