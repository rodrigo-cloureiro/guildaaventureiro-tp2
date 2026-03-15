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
    @EqualsAndHashCode.Include
    private final ParticipacaoMissaoId id = new ParticipacaoMissaoId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId(value = "missaoId")
    @JoinColumn(
            name = "missao_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_participacao_missao_missao")
    )
    private Missao missao;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId(value = "aventureiroId")
    @JoinColumn(
            name = "aventureiro_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_participacao_missao_aventureiro")
    )
    private Aventureiro aventureiro;

    @Enumerated(EnumType.STRING)
    @Column(name = "papel_missao", nullable = false)
    private PapelMissao papel;

    @Min(value = 0, message = "A recompensa em ouro deve ser maior ou igual a zero")
    @Column(name = "recompensa_em_ouro", nullable = true)
    private Integer recompensaEmOuro;

    @Column(nullable = false)
    private boolean mvp = false;

    @CreationTimestamp
    @Column(name = "data_registro", nullable = false, updatable = false)
    private LocalDateTime dataRegistro;

    protected ParticipacaoMissao() {
    }

    public ParticipacaoMissao(PapelMissao papelMissao, Integer recompensaEmOuro) {
        this.papel = Objects.requireNonNull(papelMissao, "O papel do aventureiro na missão é obrigatório");
        this.recompensaEmOuro = recompensaEmOuro;
    }

    public void definirMvp() {
        this.mvp = true;
    }

    public void associar(Missao missao, Aventureiro aventureiro) {
        this.missao = Objects.requireNonNull(missao);
        this.aventureiro = Objects.requireNonNull(aventureiro);
        missao.adicionarParticipacao(this);
        aventureiro.entrarEmMissao(this);
    }
}
