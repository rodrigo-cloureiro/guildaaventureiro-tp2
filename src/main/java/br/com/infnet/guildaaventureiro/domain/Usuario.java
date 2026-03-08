package br.com.infnet.guildaaventureiro.domain;

import br.com.infnet.guildaaventureiro.domain.enums.UsuarioStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = "usuarios",
        schema = "audit",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_usuarios_email_por_org",
                        columnNames = {"organizacao_id", "email"}
                )
        },
        check = @CheckConstraint(
                name = "ck_usuarios_status",
                constraint = "status IN ('ATIVO', 'BLOQUEADO', 'PENDENTE')"
        ),
        indexes = {
                @Index(name = "idx_usuarios_email", columnList = "email"),
                @Index(name = "idx_usuarios_org", columnList = "organizacao_id")
        }
)
@Getter
@Setter
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuarios_id")
    @SequenceGenerator(
            name = "usuarios_id",
            sequenceName = "usuarios_id_seq",
            schema = "audit",
            allocationSize = 1
    )
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "organizacao_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_usuarios_org")
    )
    private Organizacao organizacao;

    @OneToMany(mappedBy = "usuario")
    private Set<UsuarioRole> roles = new HashSet<>();

    @Column(
            length = 120,
            nullable = false
    )
    private String nome;

    @Column(
            length = 180,
            nullable = false
    )
    private String email;

    @Column(
            name = "senha_hash",
            length = 255,
            nullable = false
    )
    private String senhaHash;

    @Enumerated(EnumType.STRING)
    @Column(
            length = 30,
            nullable = false
    )
    private UsuarioStatus status;

    @Column(
            name = "ultimo_login_em",
            nullable = true
    )
    private LocalDateTime ultimoLoginEm;

    @CreationTimestamp
    @Column(
            name = "created_at",
            nullable = false,
            updatable = false
    )
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(
            name = "updated_at",
            nullable = false
    )
    private LocalDateTime updatedAt;

    protected Usuario() {
    }

    public Usuario(String nome, String email, String senhaHash, UsuarioStatus status) {
        this.nome = nome;
        this.email = email;
        this.senhaHash = senhaHash;
        this.status = status;
    }

    public void definirOrganizacao(Organizacao organizacao) {
        this.organizacao = organizacao;
    }
}
