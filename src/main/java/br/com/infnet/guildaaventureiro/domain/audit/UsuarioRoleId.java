package br.com.infnet.guildaaventureiro.domain.audit;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
public class UsuarioRoleId implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @Column(name = "role_id", nullable = false)
    private Long roleId;
}
