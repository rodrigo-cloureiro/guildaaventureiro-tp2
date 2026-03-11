package br.com.infnet.guildaaventureiro.domain.aventura;

import br.com.infnet.guildaaventureiro.domain.audit.Organizacao;
import br.com.infnet.guildaaventureiro.domain.aventura.enums.NivelPerigoMissao;
import br.com.infnet.guildaaventureiro.domain.aventura.enums.PapelMissao;
import br.com.infnet.guildaaventureiro.domain.aventura.enums.StatusMissao;
import br.com.infnet.guildaaventureiro.exception.aventura.AventureiroInativoException;
import br.com.infnet.guildaaventureiro.exception.aventura.MissaoNaoAceitaParticipantesException;
import br.com.infnet.guildaaventureiro.exception.aventura.OrganizacaoInvalidaException;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(
        name = "missoes",
        schema = "aventura",
        indexes = {
                @Index(name = "idx_missoes_nivel_status", columnList = "nivel_perigo, status")
        }
)
@Getter
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
    private final Set<ParticipacaoMissao> participacoesEmMissoes = new HashSet<>();

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

    public Missao(Organizacao organizacao, String titulo, NivelPerigoMissao nivelPerigo) {
        this.organizacao = Objects.requireNonNull(organizacao, "A organização é obrigatória");
        this.titulo = Objects.requireNonNull(titulo, "O título é obrigatório");
        this.nivelPerigo = Objects.requireNonNull(nivelPerigo, "O nível do perigo é obrigatório");
        this.status = StatusMissao.PLANEJADA;
    }

    public Set<ParticipacaoMissao> getParticipacoesEmMissoes() {
        return Collections.unmodifiableSet(this.participacoesEmMissoes);
    }

    public void iniciarMissao() {
        if (this.status != StatusMissao.PLANEJADA) {
            throw new IllegalArgumentException("Não é possível iniciar missões que não estejam planejadas");
        }

        this.status = StatusMissao.EM_ANDAMENTO;
        this.dataInicio = LocalDateTime.now();
    }

    public void concluirMissao() {
        if (this.status != StatusMissao.EM_ANDAMENTO) {
            throw new IllegalArgumentException("A missão precisa estar em andamento para ser concluída");
        }

        this.status = StatusMissao.CONCLUIDA;
        this.dataTermino = LocalDateTime.now();
    }

    public void cancelarMissao() {
        if (this.status == StatusMissao.CONCLUIDA) {
            throw new IllegalArgumentException("Não é possível cancelar missões concluídas");
        }

        this.status = StatusMissao.CANCELADA;
    }

    public void adicionarAventureiro(
            Aventureiro aventureiro,
            PapelMissao papelMissao,
            Integer recompensaEmOuro
    ) { // TODO Implementar DTO
        verificarOrganizacao(aventureiro);
        verificarStatus();
        verificarInatividadeAventureiro(aventureiro);

        ParticipacaoMissao participacaoMissao = new ParticipacaoMissao(papelMissao, recompensaEmOuro);
        participacaoMissao.associar(this, aventureiro);
    }

    void adicionarParticipacao(ParticipacaoMissao participacaoMissao) {
        this.participacoesEmMissoes.add(Objects.requireNonNull(participacaoMissao));
    }

    private void verificarStatus() {
        if (this.status != StatusMissao.PLANEJADA) {
            throw new MissaoNaoAceitaParticipantesException(
                    "Não é possível adicionar aventureiros quando a missão está " + this.status.name()
            );
        }
    }

    private void verificarInatividadeAventureiro(Aventureiro aventureiro) {
        if (!aventureiro.isAtivo()) {
            throw new AventureiroInativoException("Aventureiros inativos não podem ser associados");
        }
    }

    private void verificarOrganizacao(Aventureiro aventureiro) {
        if (!Objects.equals(this.organizacao.getId(), aventureiro.getOrganizacao().getId())) {
            throw new OrganizacaoInvalidaException("O aventureiro não pertence a organização");
        }
    }
}
