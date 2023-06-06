package jmaster.io.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jmaster.io.demo.entity.Course;

public interface CourseRepo extends JpaRepository<Course, Integer>{
	@Query("SELECT c FROM Course c WHERE c.name LIKE :x")
	Page<Course> searchByName(@Param("x") String name, Pageable pageable);
	
}
