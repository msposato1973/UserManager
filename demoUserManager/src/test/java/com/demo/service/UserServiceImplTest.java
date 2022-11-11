package com.demo.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.demo.repository.UserRepository;
import com.demo.service.*;

import com.demo.dto.UseDataDTO;
 
import com.demo.entity.User;

@SpringBootTest
class UserServiceImplTest {
	
	@Autowired
	private  UserServiceImpl userService;
	
	@MockBean
	private  UserRepository repository;
	
	@BeforeEach
	void setUp()  {
		
		Iterable<User>  iterable = Arrays.asList(new User(new Long("1"), "Massimo", "Sposato", "ms@gmail.com"), 
												 new User(new Long("2"), "Mario", "Esposito", "me@gmail.com")
									);
		Mockito.when(repository.findAll()).thenReturn((List<User>) iterable);
		
		
		User userEntity = new User(new Long("1"), "Massimo", "Sposato", "ms@gmail.com");
		Mockito.when(repository.findById(new Long(1))).thenReturn(Optional.of(userEntity));
	}
	

	@Test
	@DisplayName("Get all data from valid user service")
	public void testWhenFound() {
		 
		List<UseDataDTO> listUserData = userService.findAll();
		
		assertEquals(2, listUserData.size());
		assertEquals( 1 , listUserData.get(0).getId() );
		assertEquals("Massimo", listUserData.get(0).getFirstName() );
		assertEquals("Sposato", listUserData.get(0).getLastName() );
		assertEquals("ms@gmail.com", listUserData.get(0).getEmailId() );
		 
	}
	
	@Test
	@DisplayName("Get all data from valid user service by query")
	public void testFindAllByQuery() {
		String query = "Mar";
		List<UseDataDTO> listUserData = userService.findAllByQuery(query);
		UseDataDTO useData = listUserData.get(0);
		
		assertEquals(1, listUserData.size());
		assertEquals(2 , useData.getId() );
		assertEquals("Mario", useData.getFirstName() );
		assertEquals("Esposito", useData.getLastName() );
		assertEquals("me@gmail.com", useData.getEmailId() );
		 
		 
	}
	
	@Test
	@DisplayName("Get all data from valid user service by id")
	public void testFindById() {
	
		UseDataDTO useData  = userService.findById(new Long(1));
		 
		
	
		assertEquals(1 , useData.getId() );
		assertEquals("Massimo", useData.getFirstName() );
		assertEquals("Sposato", useData.getLastName() );
		assertEquals("ms@gmail.com", useData.getEmailId() );
		 
		 
	}

}
