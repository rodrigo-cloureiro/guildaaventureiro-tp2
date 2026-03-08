package br.com.infnet.guildaaventureiro.domain;

import br.com.infnet.guildaaventureiro.domain.enums.AuditEntryAction;
import io.hypersistence.utils.hibernate.type.basic.Inet;
import io.hypersistence.utils.hibernate.type.basic.PostgreSQLInetType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(
        name = "audit_entries",
        schema = "audit",
        check = @CheckConstraint(
                name = "ck_audit_action",
                constraint = "action IN ('CREATE','UPDATE','DELETE','LOGIN','LOGOUT','EXPORT','IMPORT','ERROR')"
        ),
        indexes = {
                @Index(name = "idx_audit_action", columnList = "action"),
                @Index(name = "idx_audit_entity", columnList = "entity_schema, entity_name, entity_id"),
                @Index(name = "idx_audit_org_time", columnList = "organizacao_id, occurred_at")
        }
)
@Getter
@Setter
public class AuditEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "audit_entries_id")
    @SequenceGenerator(
            name = "audit_entries_id",
            sequenceName = "audit_entries_id_seq",
            schema = "audit",
            allocationSize = 1
    )
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "organizacao_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_audit_org")
    )
    private Organizacao organizacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "actor_user_id",
            nullable = true,
            foreignKey = @ForeignKey(name = "fk_audit_actor_user")
    )
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "actor_api_key_id",
            nullable = true,
            foreignKey = @ForeignKey(name = "fk_audit_actor_api_key")
    )
    private ApiKey apiKey;

    @Enumerated(EnumType.STRING)
    @Column(
            length = 30,
            nullable = false
    )
    private AuditEntryAction action;

    @Column(name = "entity_schema", length = 60, nullable = false)
    private String entitySchema;

    @Column(name = "entity_name", length = 80, nullable = false)
    private String entityName;

    @Column(name = "entity_id", length = 80, nullable = true)
    private String entityId;

    @CreationTimestamp
    @Column(name = "occurred_at", nullable = false)
    private LocalDateTime occurredAt;

    @Type(value = PostgreSQLInetType.class)
    @Column(columnDefinition = "inet")
    private Inet ip;

    @Column(name = "user_agent", length = 255, nullable = true)
    private String userAgent;

    @JdbcTypeCode(value = SqlTypes.JSON)
    @Column(nullable = true, columnDefinition = "jsonb")
    private Map<String, Object> diff;

    @JdbcTypeCode(value = SqlTypes.JSON)
    @Column(nullable = true, columnDefinition = "jsonb")
    private Map<String, Object> metadata;

    @Column(nullable = false)
    private boolean success = true;
}
