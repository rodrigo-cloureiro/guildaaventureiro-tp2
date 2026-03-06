package br.com.infnet.guildaaventureiro.repository;

import br.com.infnet.guildaaventureiro.domain.Usuario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UsuarioRepositoryTest {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void findAllTest() {
        List<Usuario> all = usuarioRepository.findAll();
        all.forEach(System.out::println);
        assertFalse(all.isEmpty());
    }

    @Test
    public void findByOrganizacaoNameTest() {
        List<Usuario> list = usuarioRepository.findByOrganizacaoNomeIgnoreCase("Guilda do Norte");
        list.forEach(u -> System.out.println(u.getNome()));
        assertFalse(list.isEmpty());

        /*
        select
            u1_0.id,
            u1_0.created_at,
            u1_0.email,
            u1_0.nome,
            u1_0.organizacao_id,
            u1_0.senha_hash,
            u1_0.status,
            u1_0.ultimo_login_em,
            u1_0.updated_at
        from
            audit.usuarios u1_0
        left join
            audit.organizacoes o1_0
                on o1_0.id=u1_0.organizacao_id
        where
            upper(o1_0.nome)=upper(?)
        */
    }
}
