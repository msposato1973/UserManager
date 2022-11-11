package com.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.demo.dto.UseDataDTO;
import com.demo.entity.User;
import com.demo.exception.ResourceNotFoundException;
import com.demo.repository.UserRepository;
import com.demo.web.ResponseDTO;
@Service
public class UserServiceImpl implements UserService{

	
	@Autowired
	private  UserRepository userRepository ;
	

	public UseDataDTO createUSer(UseDataDTO data) {
		 User userEntity = new User();
		 userEntity = mapToEntity(data) ;
		 
		 
		 userRepository.save(userEntity);
		
		 return mapToDTO(userEntity) ;
	}

	public UseDataDTO findById(Long id) {
		User userEntity =this.userRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("User not found"+id)
        );
		 
		return mapToDTO(userEntity);
	}  
	
	
	public List<UseDataDTO> findAll() {
		 Iterable<User>  iterable =  userRepository.findAll();
		 return getResultMap(iterable);
	}  
	
	public List<UseDataDTO> findAll(List<User> users) {
 
		 List<UseDataDTO> result = new ArrayList<UseDataDTO>();
		 
		 (users).forEach(x -> {
		        result.add(mapToDTO(x));
		 });
		 
		  
		 return result;
	}  
	
	public List<UseDataDTO> findAllByQuery(String query) {
		 
		 List<UseDataDTO> result = new ArrayList<UseDataDTO>();
		 
		 findAll().forEach(x ->  {
			 if(x.getFirstName().startsWith(query) || 
			    x.getLastName().startsWith(query) ||
			    x.getEmailId().contains(query)
			   )
		        result.add((x));
		 });
		 
				 return result;
	}  
	
	
	public List<UseDataDTO> findAll(Pageable paging) {
	  
		 
		 Page<User> pagedResult = userRepository.findAll(paging);
		 List<User> users = pagedResult.getContent();
		  
		 return  findAll(users);
	}  
	
	 
	public List<UseDataDTO> filterPageSearch(Pageable paging, String query) {
		List<User> users = userRepository.filterPageSearchFirstname(query);
		//List<User> users = pagedResult.getContent();
		 
		return  findAll(users);
	}
	 
    public Page<User> getUsersByPagination(int pageNo, int pageSize) {

        //create pagerequest object
        
        //pass it to repos
        Page<User> pagingUser = userRepository.findAll(PageRequest.of(pageNo, pageSize));
      
        return pagingUser;
    }
    
	
    public Page<User> findAllPaginated(int pageNo, int pageSize) {
       
    	Page<User> usersPage = getUsersByPagination(pageNo, pageSize);
       
    	 return  usersPage;
    }
    
    public Page<User> findAllPaginatedAndSorting(int pageNo, int pageSize, String field) {
        
    	 Page<User> pagingUser = userRepository.findAll(PageRequest.of(pageNo, pageSize).withSort(Sort.by(field)));
       
    	 return  pagingUser;
    }
	
	
	public boolean deleteUserData(Long id) { 
		 
		if(this.findById(id)!=null){
			 User useEntity = userRepository.findById(id).get();
			 userRepository.deleteById(useEntity.getId());
		 	 return true;
		}else {
			return false;
		}
		
	} 
	
	public List<UseDataDTO> updateUser(User user) {
	 	 User useEntity = userRepository.findById(user.getId()).get();
		 userRepository.save(user);
	 	 return findAll();
	}  
	 
	
	public ResponseDTO countUByUser(UseDataDTO userdata, ResponseDTO response) {
		List<User> useEntity =  userRepository.findAll().stream()
								.filter(x-> x.getEmail().equalsIgnoreCase(userdata.getEmailId()) &&  
										x.getFirstName().equalsIgnoreCase(userdata.getFirstName()) && 
										x.getLastName().equalsIgnoreCase(userdata.getLastName()))
								.collect(Collectors.toList());
		response.setCount(useEntity.size());		
		response.setExist((useEntity.size()>0));
		return response;
	}
     
    
 // convert entity to DTO  
    public List<UseDataDTO> getResultMap(Iterable<User> listIterable ){
    	
    	List<User> actualList = StreamSupport.stream(listIterable.spliterator(), false)
    			  .collect(Collectors.toList());
    	
    	 List<UseDataDTO> result = new ArrayList<UseDataDTO>();
    	 listIterable.forEach(x -> {
		        result.add(mapToDTO(x));
		 });
        return result;
    }

	public UseDataDTO mapToDTO(User user) {
		// TODO Auto-generated method stub
		//(String id, String firstName, String lastName, String emailId) {
		return new UseDataDTO(
				user.getId(), 
				user.getFirstName(), 
				user.getLastName(), 
				user.getEmail());
		 
	  
	}
	
	public User mapToEntity(UseDataDTO dto) {
		User useEntity = new User();
		useEntity.setId(dto.getId());
		useEntity.setFirstName(dto.getFirstName());
		useEntity.setLastName(dto.getLastName());
		useEntity.setEmail(dto.getEmailId());
		return useEntity;
		 
	  
	}
	
	public List<UseDataDTO> getAllComments(Pageable paging){
        
        Iterable<User> useEntitys =  userRepository.findAll(paging);
        List<UseDataDTO> commentsDTO = new ArrayList<>();

        useEntitys.forEach( useEntity -> {
        	 
            commentsDTO.add(mapToDTO(useEntity));
        });

        return commentsDTO;
    }

	public UseDataDTO getUserDataById(Long id) {
		User useEntity = userRepository.findById(id).get();
		return mapToDTO(useEntity);
	}
	
	public UseDataDTO updateUserData(Long id,UseDataDTO dto){
		User newUser = mapToEntity(dto);
		return mapToDTO(updateUser(newUser, id));
	}

	private User updateUser(User newUser, Long id){
        return this.userRepository.findById(id)
                .map(user -> {
                 
                    user.setFirstName(newUser.getFirstName());
                    user.setLastName(newUser.getLastName());
                    user.setEmail(newUser.getEmail());
                 
                    return this.userRepository.save(user);
                })
                .orElseGet(()->{
                   newUser.setId(id);
                   return this.userRepository.save(newUser);
                });
    }

 
}
