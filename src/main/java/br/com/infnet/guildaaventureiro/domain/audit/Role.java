package br.com.infnet.guildaaventureiro.domain.audit;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = "roles",
        schema = "audit",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_roles_nome_por_org", columnNames = {"organizacao_id", "nome"})
        },
        indexes = {
                @Index(name = "idx_roles_org", columnList = "organizacao_id")
        }
)
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roles_id")
    @SequenceGenerator(
            name = "roles_id",
            sequenceName = "roles_id_seq",
            schema = "audit",
            allocationSize = 1
    )
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "organizacao_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_roles_org")
    )
    private Organizacao organizacao;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "role_permissions",
            schema = "audit",
            joinColumns = @JoinColumn(name = "role_id"),
            foreignKey = @ForeignKey(name = "fk_rp_role"),
            inverseJoinColumns = @JoinColumn(name = "permission_id"),
            inverseForeignKey = @ForeignKey(name = "fk_rp_perm")
    )
    private Set<Permission> permissions = new HashSet<>();

    @OneToMany(mappedBy = "role")
    private Set<UsuarioRole> usuarios = new HashSet<>();

    @Column(nullable = false, length = 60)
    private String nome;

    @Column(nullable = true, length = 255)
    private String descricao;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
