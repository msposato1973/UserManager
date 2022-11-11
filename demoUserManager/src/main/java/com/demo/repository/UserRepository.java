package com.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.demo.entity.User;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long>{ 
	
	Page<User> findByfirstNameContaining (String firstName, Pageable pageable);
	
	Page<User> findAll(Pageable pageable);
    
	
	@Query(value = "select u.* from userdata u where (u.first_name like %:keyword%) ", nativeQuery = true)
    List<User> filterPageSearchFirstname(@Param("keyword") String keyword);
	
	@Query(value = "select u.* from userdata u where (u.email like %:keyword%) ", nativeQuery = true)
    List<User> filterPageSearchEmailId(@Param("keyword") String keyword);
	
	@Query(value = "select u.* from userdata u where (u.last_name like %:keyword%) ", nativeQuery = true)
    List<User> filterPageSearchLastName(@Param("keyword") String keyword);
	
	
	@Query(value = "select u.* from userdata u where u.email = :email_id and u.last_name = :lastName", nativeQuery = true)
	 List<User> findUserNamedParams(@Param("email") String emailId, @Param("lastName") String lastName);
	
	 
}
