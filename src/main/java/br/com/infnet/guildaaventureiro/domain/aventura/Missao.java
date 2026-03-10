package br.com.infnet.guildaaventureiro.domain.aventura;

import br.com.infnet.guildaaventureiro.domain.audit.Organizacao;
import br.com.infnet.guildaaventureiro.domain.aventura.enums.NivelPerigoMissao;
import br.com.infnet.guildaaventureiro.domain.aventura.enums.PapelMissao;
import br.com.infnet.guildaaventureiro.domain.aventura.enums.StatusMissao;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = "missoes",
        schema = "aventura",
        uniqueConstraints = {},
        indexes = {
                @Index(name = "idx_missoes_nivel_status", columnList = "nivel_perigo, status")
        }
)
@Getter
@Setter
public class Missao {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "missoes_id")
    @SequenceGenerator(
            name = "missoes_id",
            sequenceName = "missoes_id_seq",
            schema = "aventura",
            allocationSize = 1
    )
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "organizacao_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_missoes_org")
    )
    private Organizacao organizacao;

    @OneToMany(
            mappedBy = "missao",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<ParticipacaoMissao> participacoesEmMissoes = new HashSet<>();

    @Column(length = 150, nullable = false)
    private String titulo;

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_perigo", nullable = false)
    private NivelPerigoMissao nivelPerigo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusMissao status;

    @CreationTimestamp
    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_inicio", nullable = true, updatable = true)
    private LocalDateTime dataInicio;

    @Column(name = "data_termino", nullable = true, updatable = true)
    private LocalDateTime dataTermino;

    protected Missao() {
    }

    public Missao(String titulo, NivelPerigoMissao nivelPerigo, StatusMissao status) {
        this.titulo = titulo;
        this.nivelPerigo = nivelPerigo;
        this.status = status;
    }

    public void definirDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public void definirDataTermino(LocalDateTime dataTermino) {
        this.dataTermino = dataTermino;
    }

    public void adicionarAventureiro(
            Aventureiro aventureiro,
            PapelMissao papelMissao,
            Integer recompensaEmOuro
    ) { // TODO Implementar DTO
        // TODO A missão deve estar em estado compatível para aceitar participantes.
        // TODO Um aventureiro inativo não pode ser associado.
        // TODO Apenas aventureiros da mesma organização podem participar.

        ParticipacaoMissao participacaoMissao = new ParticipacaoMissao(papelMissao, recompensaEmOuro);

        participacaoMissao.definirMissao(this);
        participacaoMissao.definirAventureiro(aventureiro);

        this.participacoesEmMissoes.add(participacaoMissao);
        aventureiro.entrarEmMissao(participacaoMissao);
    }
}
