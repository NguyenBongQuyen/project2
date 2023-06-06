package jmaster.io.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jmaster.io.demo.dto.CourseDTO;
import jmaster.io.demo.dto.PageDTO;
import jmaster.io.demo.dto.ResponseDTO;
import jmaster.io.demo.dto.SearchDTO;
import jmaster.io.demo.service.CourseService;

//ws: REST
@RestController
@RequestMapping("/course")
public class CourseController {
	@Autowired //DI
	CourseService courseService;
	
	@PostMapping("/")
	public ResponseDTO<Void> create(@RequestBody @Valid CourseDTO courseDTO) {
		courseService.create(courseDTO);
		return ResponseDTO.<Void>builder().status(200).msg("ok").build();
	}
	
	@DeleteMapping("/")
	public ResponseDTO<Void> delete(@RequestParam("id") int id) {
		courseService.delete(id);
		return ResponseDTO.<Void>builder().status(200).msg("ok").build();
	}
	
	@GetMapping("/") 
	public ResponseDTO<CourseDTO> get(@RequestParam("id") int id) {
		return ResponseDTO.<CourseDTO>builder().status(200).data(courseService.getById(id)).build();
	}
	
	@PutMapping("/")
	public ResponseDTO<Void> edit(@RequestBody @Valid CourseDTO courseDTO) {
		courseService.update(courseDTO);
		return ResponseDTO.<Void>builder().status(200).build();
	}
	
	@PostMapping("/search")
	public ResponseDTO<PageDTO<List<CourseDTO>>> search(@RequestBody @Valid SearchDTO searchDTO) {
		PageDTO<List<CourseDTO>> pageDTO = courseService.search(searchDTO);
		return ResponseDTO.<PageDTO<List<CourseDTO>>>builder().status(200).data(pageDTO).build();
	}
	
}
