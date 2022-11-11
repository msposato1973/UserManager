package com.demo.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.dto.UseDataDTO;
//import com.demo.AppConstants;
import com.demo.entity.User;
import com.demo.service.UserService;
import com.demo.service.UserServiceImpl;
 



@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1")
public class DemoController  {

	private static final String templateFull = "%s!  %s!";
	private static final String templateLog = "Method:   %s!  -  detail :  %s!";

	private static final Logger LOGGER = LoggerFactory.getLogger(DemoController.class);


	@Autowired
	private final UserService userService;

	public DemoController( UserServiceImpl userService) {
		this.userService = userService;
	}


	@PostMapping("/userData")
    public ResponseEntity<?> addUser(@RequestBody UseDataDTO data) {

		LOGGER.debug(String.format(templateLog, "addUser ", data.toString()));
		UseDataDTO response =  null ;
		if(!countUserData(
				data.getFirstName(),data.getLastName(), data.getEmailId()
		).getBody().getExist()) {
			response =   userService.createUSer(data)  ;
		} else {
			 response = data ;
		}
 
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@GetMapping("/countUserData")
    public ResponseEntity<ResponseDTO>  countUserData(
            @RequestParam(value = "email",  required = true) String email,
            @RequestParam(value = "firstName", required = true) String firstName,
            @RequestParam(value = "lastName" , required = true) String lastName
          ) {
		 
		UseDataDTO data =  new  UseDataDTO(firstName,lastName,email) ;
		LOGGER.debug(String.format(templateLog, "countUserData ", data.toString()));
		ResponseDTO  response =  new ResponseDTO();
		response =  userService.countUByUser(data, response) ;
		return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
	}
	
 
	// get employee by id rest api
	@GetMapping("/userData/{id}")
	public ResponseEntity<UseDataDTO> getUseDataDTOById(@PathVariable Long id) {
		UseDataDTO userData = new UseDataDTO();
		LOGGER.debug(String.format(templateLog, "getUseDataDTOById ", id.toString()));

		userData = userService.findById(id);

		return ResponseEntity.ok(userData);
	}


	// get employee by id rest api
	@GetMapping("/users")
	public APIResponse<?>  getAll() {

		

		List<UseDataDTO> listEntity = userService.findAll() ;
		LOGGER.debug(String.format(templateLog, "getAll ", listEntity.size()));

		//Page<UseDataDTO> allUseDataDTO = new PageImpl<>(listEntity);
		Integer total = userService.findAll().size();
		  
		return new APIResponse(listEntity.size(), listEntity, total);
	}




	@GetMapping("/usersPage")
    public APIResponse<?> getAllUseDataDTO(
                        @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) Integer pageNo,
                        @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) Integer pageSize,
                        @RequestParam(value = "query", defaultValue = AppConstants.DEFAULT_QUERY, required = false) String query,
                        @RequestParam(value = "sordBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sordBy
    		)  {

		LOGGER.debug(String.format(templateLog, "/usersPage -> getAllUseDataDTO ", PageRequest.of(pageNo, pageSize).toString()));
		
		Integer total = 0;
		Page<User>  allUser = null;

		Pageable paging= PageRequest.of(pageNo, pageSize);
		List<UseDataDTO> listEntity = (!query.equalsIgnoreCase("") && query!=null) ? userService.filterPageSearch(paging, query) : new ArrayList<>();
		
		if(listEntity.isEmpty() || listEntity==null) {
			listEntity = userService.findAll() ;
		}
			
		allUser =  userService.findAllPaginated(pageNo,pageSize);
        LOGGER.debug(String.format(templateLog, "getAllUseDataDTO -> Page<User> : ", allUser.get().count()));
		
		total =(!listEntity.isEmpty())? listEntity.size() : allUser.getSize();
		 
		return new APIResponse(listEntity.size(), sortingBy(sordBy, listEntity), total);
    }


	


	private Page<UseDataDTO> mapTOPageDTO(Page<User>  allUser){
		List<UseDataDTO> result = new ArrayList<>();
		allUser.getContent().forEach((user)->{
			 result.add(new UseDataDTO(
						user.getId(),
						user.getFirstName(),
						user.getLastName(),
						user.getEmail()));
		});

		return new PageImpl<>(result);
	}



	@DeleteMapping("/delUserData/{id}")
	public ResponseEntity<Map<String, Boolean>> delUserData(@PathVariable Long id) {

		LOGGER.debug(String.format(templateLog, "delUserData ", id));
		boolean deleted = false;

		deleted =  userService.deleteUserData(id);
		Map<String, Boolean> response = new HashMap<>();

		response.put("deleted", deleted);

		return  ResponseEntity.ok(response);
	}


	// get employee by id rest api
	@GetMapping("/userById/{id}")
	public ResponseEntity<UseDataDTO> getUserbyId(@PathVariable Long id) {

		UseDataDTO userData = new UseDataDTO();
		LOGGER.debug(String.format(templateLog, "getUseDataDTOById ", id.toString()));

		userData = userService.findById(id);

		return ResponseEntity.ok(userData);
	}


	@PutMapping("/updateUserData/{id}")
	public ResponseEntity<UseDataDTO> updateUserDataById(@PathVariable Long id,@RequestBody UseDataDTO userData) {

		LOGGER.debug(String.format(templateLog, "getUserDataById ", id));
		UseDataDTO userDataDto = userService.updateUserData(id, userData);
		return  ResponseEntity.ok(userDataDto);
	}


	private String getTemplateFull(UseDataDTO response) {
		return String.format(templateFull, response.getFirstName(), response.getLastName());
	}


	private List<UseDataDTO> sortingBy(String propertieNme, List<UseDataDTO> list) {
		List<UseDataDTO> sortedUseDataDTO = null;
		
		if (propertieNme.equalsIgnoreCase("EmailId"))
			sortedUseDataDTO = list.stream().sorted( (f1, f2)->{
				return f1.getEmailId().compareTo(f2.getEmailId());
			}).collect(Collectors.toList());
		
		if (propertieNme.equalsIgnoreCase("FirstName"))
			sortedUseDataDTO = list.stream().sorted( (f1, f2)->{
				return f1.getFirstName().compareTo(f2.getFirstName());
			}).collect(Collectors.toList());
		
		if (propertieNme.equalsIgnoreCase("LastName"))
			sortedUseDataDTO = list.stream().sorted( (f1, f2)->{
				return f1.getLastName().compareTo(f2.getLastName());
			}).collect(Collectors.toList());
		
		if (propertieNme.equalsIgnoreCase("id"))
			sortedUseDataDTO = list.stream().sorted( (f1, f2)->{
				return f1.getId().compareTo(f2.getId());
			}).collect(Collectors.toList());
		
		 

		return (propertieNme.trim().equals("")) ? list : sortedUseDataDTO;
	}
}
