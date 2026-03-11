package br.com.infnet.guildaaventureiro.domain.aventura;

import br.com.infnet.guildaaventureiro.domain.audit.Organizacao;
import br.com.infnet.guildaaventureiro.domain.audit.Usuario;
import br.com.infnet.guildaaventureiro.domain.aventura.enums.AventureiroClasse;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(
        name = "aventureiros",
        schema = "aventura",
        /*check = {
                @CheckConstraint(
                        name = "ck_aventureiros_classe",
                        constraint = "classe IN ('GUERREIRO', 'MAGO', 'ARQUEIRO', 'CLERIGO', 'LADINO')"
                ),
                @CheckConstraint(name = "ck_aventureiros_nivel", constraint = "nivel >= 1")
        },*/
        indexes = {
                @Index(
                        name = "idx_aventureiros_org_classe_nivel",
                        columnList = "organizacao_id, classe, nivel"
                ),
        }
)
@Getter
public class Aventureiro {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "aventureiros_id")
    @SequenceGenerator(
            name = "aventureiros_id",
            sequenceName = "aventureiros_id_seq",
            schema = "aventura",
            allocationSize = 1
    )
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "organizacao_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_aventureiros_org")
    )
    private Organizacao organizacao;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "usuario_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_aventureiros_usuario")
    )
    @NotNull(message = "O usuário é obrigatório")
    private Usuario usuario;

    @OneToOne(
            mappedBy = "aventureiro",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Companheiro companheiro;

    @OneToMany(
            mappedBy = "aventureiro",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private final Set<ParticipacaoMissao> participacoesEmMissoes = new HashSet<>();

    @Column(length = 120, nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AventureiroClasse classe;

    @Min(value = 1, message = "O nível deve ser maior ou igual a 1")
    @Column(nullable = false)
    private int nivel;

    @Column(nullable = false)
    private boolean ativo = true;

    @CreationTimestamp
    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @UpdateTimestamp
    @Column(name = "data_atualizacao", nullable = false)
    private LocalDateTime dataAtualizacao;

    protected Aventureiro() {
    }

    public Aventureiro(
            Organizacao organizacao,
            Usuario usuario,
            String nome,
            AventureiroClasse classe,
            int nivel
    ) {
        this.organizacao = Objects.requireNonNull(organizacao, "A organização é obrigatória");
        this.usuario = Objects.requireNonNull(usuario, "O usuário é obrigatório");
        this.nome = nome;
        this.classe = classe;
        this.nivel = nivel;
    }

    public void encerrarVinculo() {
        this.ativo = false;
    }

    public void recrutar() {
        this.ativo = true;
    }

    public void definirCompanheiro(Companheiro companheiro) {
        companheiro.definirAventureiro(this);
        this.companheiro = companheiro;
    }

    public void removerCompanheiro() {
        this.companheiro = null;
    }

    void entrarEmMissao(ParticipacaoMissao participacaoMissao) {
        this.participacoesEmMissoes.add(participacaoMissao);
    }
}
