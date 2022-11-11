package com.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import com.demo.entity.User;
import com.demo.web.ResponseDTO;
import com.demo.dto.UseDataDTO;
  
public interface UserService {

	UseDataDTO createUSer(UseDataDTO data);

	UseDataDTO findById(Long id);

	List<UseDataDTO> findAll();

	List<UseDataDTO> findAll(List<User> users);

	List<UseDataDTO> findAllByQuery(String query);

	List<UseDataDTO> findAll(Pageable paging);

	List<UseDataDTO> filterPageSearch(Pageable paging, String query);

	Page<User> getUsersByPagination(int pageNo, int pageSize);

	Page<User> findAllPaginated(int pageNo, int pageSize);

	Page<User> findAllPaginatedAndSorting(int pageNo, int pageSize, String field);

	boolean deleteUserData(Long id);

	List<UseDataDTO> updateUser(User user);

	ResponseDTO countUByUser(UseDataDTO userdata, ResponseDTO response);

	List<UseDataDTO> getResultMap(Iterable<User> listIterable);

	UseDataDTO mapToDTO(User user);

	User mapToEntity(UseDataDTO dto);

	List<UseDataDTO> getAllComments(Pageable paging);

	UseDataDTO getUserDataById(Long id);

	UseDataDTO updateUserData(Long id, UseDataDTO dto);

}
