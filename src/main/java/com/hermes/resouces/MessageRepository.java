package com.hermes.resouces;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hermes.model.Message;

@Repository
public interface MessageRepository extends PagingAndSortingRepository<Message, Long> {

	List<Message> findByUsuario(@Param("usuario") String usuario);
	List<Message> findByUsuarioAndLidaFalse(@Param("usuario") String usuario);
}
