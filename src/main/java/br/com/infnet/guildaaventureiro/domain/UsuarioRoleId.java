package br.com.infnet.guildaaventureiro.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class UsuarioRoleId implements Serializable {
    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @Column(name = "role_id", nullable = false)
    private Long roleId;
}
