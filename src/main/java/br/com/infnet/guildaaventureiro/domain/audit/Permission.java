package br.com.infnet.guildaaventureiro.domain.audit;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = "permissions",
        schema = "audit",
        uniqueConstraints = {
                @UniqueConstraint(name = "permissions_code_key", columnNames = {"code"})
        }
)
@Getter
@Setter
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permissions_id")
    @SequenceGenerator(
            name = "permissions_id",
            sequenceName = "permissions_id_seq",
            schema = "audit",
            allocationSize = 1
    )
    private Long id;

    @Column(nullable = false, length = 80, unique = true)
    private String code;

    @Column(nullable = false, length = 255)
    private String descricao;

    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles = new HashSet<>();
}
