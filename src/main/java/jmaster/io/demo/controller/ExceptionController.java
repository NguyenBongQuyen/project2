package jmaster.io.demo.controller;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jmaster.io.demo.dto.ResponseDTO;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ExceptionController {
//	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@ExceptionHandler(NoResultException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public ResponseDTO<String> notFound(NoResultException e) {
		log.info("INFO", e);
//		return ResponseDTO.<String>builder().status(404).msg("No Data").build() ;
		ResponseDTO<String> responseDTO = new ResponseDTO<>(400, "No Data");
//		responseDTO.setStatus(400);
//		responseDTO.setMsg("No Data");
		return responseDTO;
	}
	
	@ExceptionHandler(BindException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST) //HTTP STATUS CODE
	public ResponseDTO<String> badRequest(BindException e) {
		log.info("bad request");
		
		List<ObjectError> errors = e.getBindingResult().getAllErrors();
		
		String msg = "";
		for (ObjectError err : errors) {
			FieldError fieldError = (FieldError) err;
			
			msg += fieldError.getField() + ":" + err.getDefaultMessage() + ";";
		}
		return ResponseDTO.<String>builder().status(400).msg(msg).build();
	}
	
	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	@ResponseStatus(code = HttpStatus.CONFLICT)
	public ResponseDTO<String> duplicate(Exception e) {
		log.info("INFO", e);
		return ResponseDTO.<String>builder().status(409).msg("Duplicated Data").build() ;
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(code = HttpStatus.FORBIDDEN) 
	public ResponseDTO<String> accessDeny(Exception e) {
		log.info("INFO", e);
		return ResponseDTO.<String>builder().status(403).msg("Access Deny").build(); 
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED) 
	public ResponseDTO<String> unauthorized(Exception e) {
		log.info("INFO", e);
		return ResponseDTO.<String>builder().status(401).msg(e.getMessage()).build(); 
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseDTO<String> err(Exception e) {
		log.error("INFO", e);
		return ResponseDTO.<String>builder().status(500).msg("Duplicated Data").build() ;
	}
	
}
