package com.hermes.controllers;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hermes.model.Message;
import com.hermes.resouces.MessageRepository;

@RestController
public class MessageController {

	private final Logger log = Logger.getLogger(MessageController.class.getSimpleName());
	
	@Autowired
	private MessageRepository rep;
	
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	@RequestMapping(path="/test")
	public ResponseEntity<?> test()
	{
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(path = "/messages/all/", produces = {"application/json"})
	public ResponseEntity<?> list()
	{
		List<Message> messages = (List<Message>) rep.findAll();
		
		if (null == messages)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(messages, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	@RequestMapping("/{userId}/messages/all/")
	public ResponseEntity<?> listByUser(@PathVariable String userId)
	{
		List<Message> messages = rep.findByUsuario(userId);
		
		if (null == messages)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(messages, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	@RequestMapping("/{userId}/messages/news/")
	public ResponseEntity<?> listByUserNews(@PathVariable String userId)
	{
		List<Message> messages = rep.findByUsuarioAndLidaFalse(userId);
		
		if (null == messages)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(messages, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(path = "/messages/", method = RequestMethod.POST, produces = {"application/json"})
	public ResponseEntity<?> add(@RequestBody Message message)
	{
		if (null == message || null == message.getMessage() || null == message.getUsuario())
		{
			return ResponseEntity.badRequest().body("A mensagem não pode ser vazia.");
		}
		
		Message created = rep.save(message);
		
		return new ResponseEntity<>(created, HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(path = "/messages/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable String id)
	{
		if (null == id || id.isEmpty())
		{
			return ResponseEntity.badRequest().body("Deve ser passado uma 'id' para exclusão.");
		}
		
		try
		{
			rep.delete(Long.parseLong(id));
		
			return new ResponseEntity<>(HttpStatus.OK);
		
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	@RequestMapping(path = "/messages/{id}", method = RequestMethod.GET, produces = {"application/json"})
	public ResponseEntity<?> findById(@PathVariable String id)
	{
		if (null == id || id.isEmpty())
		{
			return ResponseEntity.badRequest().body("Deve ser passado uma 'id' para pesquisa.");
		}
		
		
		Message message = rep.findOne(Long.parseLong(id));
		
		if (null == message)
		{
			return ResponseEntity.noContent().build();
		} 
		
		return ResponseEntity.ok(message);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(path = "/messages/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@RequestBody Message message)
	{
		if (null == message || null == message.getMessage() || null == message.getUsuario())
		{
			return ResponseEntity.badRequest().body("A mensagem não pode ser vazia.");
		}
		
		rep.save(message);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	@RequestMapping(path = "/messages/{id}/read", method = RequestMethod.PUT)
	public ResponseEntity<?> read(@RequestBody Message message)
	{
		if (null == message || null == message.getMessage() || null == message.getUsuario())
		{
			return ResponseEntity.badRequest().body("A mensagem não pode ser vazia.");
		}
		message.read();
		rep.save(message);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(path = "/messages", method = RequestMethod.GET)
	public Page<Message> listAllByPage(Pageable pageable)
	{
		return rep.findAll(pageable);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping("/{userId}/messages")
	public Page<Message> listByUserPageable(@PathVariable String userId, Pageable pageable)
	{
		return rep.findByUsuario(userId, pageable);
	}
	
}

