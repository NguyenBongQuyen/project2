package jmaster.io.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jmaster.io.demo.dto.PageDTO;
import jmaster.io.demo.dto.ResponseDTO;
import jmaster.io.demo.dto.SearchDTO;
import jmaster.io.demo.dto.StudentDTO;
import jmaster.io.demo.dto.UserDTO;
import jmaster.io.demo.service.StudentService;

//ws: REST
@RestController
@RequestMapping("/student")
public class StudentController {
	@Autowired //DI
	StudentService studentService;
	
	//giả sử không upload file
	@PostMapping("/")
	public ResponseDTO<Void> create(@RequestBody @Valid StudentDTO studentDTO) {
		studentService.create(studentDTO);
		return ResponseDTO.<Void>builder().status(200).msg("ok").build();
	}
	
	@PutMapping("/avatar")
	public ResponseDTO<Void> avatar(@ModelAttribute @Valid UserDTO userDTO) {
		return ResponseDTO.<Void>builder().status(200).msg("ok").build();
	}
	
	@DeleteMapping("/")
	public ResponseDTO<Void> delete(@RequestParam("id") int id) {
		studentService.delete(id);
		return ResponseDTO.<Void>builder().status(200).msg("ok").build();
	}
	
	@GetMapping("/")
	@ResponseStatus(code = HttpStatus.OK)
	public ResponseDTO<StudentDTO> get(@RequestParam("id") int id) {
		return ResponseDTO.<StudentDTO>builder().status(200).data(studentService.getById(id)).build();
	}
	
	@PutMapping("/")
	public ResponseDTO<StudentDTO> edit(@RequestBody @Valid StudentDTO studentDTO) {
		studentService.update(studentDTO);
		return ResponseDTO.<StudentDTO>builder().status(200).data(studentDTO).build();
	}
	
	@PostMapping("/search") //jackson
	public ResponseDTO<PageDTO<List<StudentDTO>>> search(@RequestBody @Valid SearchDTO searchDTO) {
		PageDTO<List<StudentDTO>> pageDTO = studentService.search(searchDTO);
		return ResponseDTO.<PageDTO<List<StudentDTO>>>builder().status(200).data(pageDTO).build();
	}
	
}
