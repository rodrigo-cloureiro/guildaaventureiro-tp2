package br.com.infnet.guildaaventureiro.repository;

import br.com.infnet.guildaaventureiro.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findByOrganizacaoNome(String organizacaoNome);
}
