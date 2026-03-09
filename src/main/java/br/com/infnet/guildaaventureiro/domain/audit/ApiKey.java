package br.com.infnet.guildaaventureiro.domain.audit;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "api_keys",
        schema = "audit",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_api_keys_nome_por_org",
                        columnNames = {"organizacao_id", "nome"}
                )
        }
)
@Getter
@Setter
public class ApiKey {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "api_keys_id")
    @SequenceGenerator(
            name = "api_keys_id",
            sequenceName = "api_keys_id_seq",
            schema = "audit",
            allocationSize = 1
    )
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "organizacao_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_api_keys_org")
    )
    private Organizacao organizacao;

    @Column(nullable = false, length = 120)
    private String nome;

    @Column(name = "key_hash", nullable = false, length = 255)
    private String keyHash;

    @Column(nullable = false)
    private boolean ativo = true;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "last_used_at")
    private LocalDateTime lastUsedAt;
}
