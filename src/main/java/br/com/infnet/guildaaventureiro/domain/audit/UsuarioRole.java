package br.com.infnet.guildaaventureiro.domain.audit;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "user_roles",
        schema = "audit",
        indexes = {
                @Index(name = "idx_user_roles_role", columnList = "role_id"),
                @Index(name = "idx_user_roles_user", columnList = "usuario_id")
        }
)
@Getter
@Setter
public class UsuarioRole {
    @EmbeddedId
    private UsuarioRoleId usuarioRoleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId(value = "usuarioId")
    @JoinColumn(
            name = "usuario_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_ur_user")
    )
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId(value = "roleId")
    @JoinColumn(
            name = "role_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_ur_role")
    )
    private Role role;

    @CreationTimestamp
    @Column(name = "granted_at", nullable = false)
    private LocalDateTime grantedAt;
}
