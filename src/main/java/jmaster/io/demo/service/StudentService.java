package jmaster.io.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import jmaster.io.demo.dto.PageDTO;
import jmaster.io.demo.dto.SearchDTO;
import jmaster.io.demo.dto.StudentDTO;
import jmaster.io.demo.entity.Student;
import jmaster.io.demo.repository.StudentRepo;
import jmaster.io.demo.repository.UserRepo;

public interface StudentService {
	void create(StudentDTO studentDTO);
	
	void update(StudentDTO studentDTO);
	
	void delete(int id);
	
	StudentDTO getById(int id);
	
	PageDTO<List<StudentDTO>> search(SearchDTO searchDTO);
	
}

@Service
class StudentServiceImpl implements StudentService {
	@Autowired
	StudentRepo studentRepo;
	
	@Autowired
	UserRepo userRepo;
	
	@Override
	@Transactional
	public void create(StudentDTO studentDTO) {
		//không dùng casecade
//		User user = new ModelMapper().map(studentDTO.getUser(), User.class);
//		userRepo.save(user);
//		
//		Student student = new Student();
//		student.setUserId(user.getId());
//		student.setStudentCode(studentDTO.getStudentCode());
//		
//		studentRepo.save(student);
		
		//dùng casecade
		Student student = new ModelMapper().map(studentDTO, Student.class);
		studentRepo.save(student);
	}

	@Override
	@Transactional
	public void update(StudentDTO studentDTO) {
		Student student = studentRepo.findById(studentDTO.getUser().getId()).orElseThrow(NoResultException::new);
		
		student.getUser().setName(studentDTO.getUser().getName());
		
		studentRepo.save(student);
	}
	
	private StudentDTO convert(Student student) {
		ModelMapper modelMapper = new ModelMapper();
		
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		return modelMapper.map(student, StudentDTO.class);
	}
	
	@Override
	public StudentDTO getById(int id) {
		//Optional
		Student student = studentRepo.findById(id).orElseThrow(NoResultException::new);
				
		return convert(student);
	}
	
	@Override
	public void delete(int id) {
		studentRepo.deleteById(id);
	}

	@Override
	public PageDTO<List<StudentDTO>> search(SearchDTO searchDTO) {
		Sort sortBy = Sort.by("name").ascending();
		
		if(StringUtils.hasText(searchDTO.getSortedField())) {
			sortBy = Sort.by(searchDTO.getSortedField()).ascending();
		}
		if(searchDTO.getCurrentPage() == null)
			searchDTO.setCurrentPage(0);
		if(searchDTO.getSize() == null)
			searchDTO.setSize(3);
		if (searchDTO.getKeyword() == null)
			searchDTO.setKeyword("");
			
		PageRequest pageRequest = PageRequest.of(searchDTO.getCurrentPage(), searchDTO.getSize(), sortBy);
		
		Page<Student> page = studentRepo.searchByName("%" + searchDTO.getKeyword() + "%", pageRequest);
		
		PageDTO<List<StudentDTO>> pageDTO = new PageDTO<>();
		pageDTO.setTotalPages(page.getTotalPages());
		pageDTO.setTotalElements(page.getTotalElements());
		
		List<StudentDTO> studentDTOs = page.get().map(u -> convert(u)).collect(Collectors.toList());
		
		pageDTO.setData(studentDTOs);
		
		return pageDTO;
	}

}
