package com.br.games.domain.exception;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ResourceExceptionHandler extends ResponseEntityExceptionHandler{
	
	public static final String MSG_ERRO_GENERICA_USUARIO_FINAL = "Ocorreu um erro interno inesperado no sistema. Tente novamente e se "
			+ "o problema persistir, entre em contato com o administrador do sistema.";
	
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		// TODO Auto-generated method stub
		ProblemType problemType= ProblemType.RESOURCE_NOT_FOUND;
		String detail= String.format("o recurso %s, que você tentou acessar não existe",ex.getRequestURL());
		Problem problem = createProblemBuilder(status, problemType, detail).userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
				.build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Object> resourceNotFound(ResourceNotFoundException e){
		ProblemType problemType= ProblemType.RESOURCE_NOT_FOUND;
		HttpStatus status = HttpStatus.NOT_FOUND;
		
		Problem problem = createProblemBuilder(status, problemType, e.getMessage()).userMessage(e.getMessage()).build();
		return ResponseEntity.status(status).body(problem);
	}
	@ExceptionHandler(ValidationException.class)
	private ResponseEntity<StandardError> validationException(ValidationException e, HttpServletRequest request){
		String error= "BAD REQUEST";
		HttpStatus status= HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	
	
	
	@Autowired
	private MessageSource messageSource;
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		// TODO Auto-generated method stub
		
		ProblemType problemType = ProblemType.INVALID_DATA;
		String detail= "Um ou mais campos estão com dados inválidos. Faça o preenchimento correto e tente novamente";
		
		BindingResult bindingResult= ex.getBindingResult();
		
		List<Problem.Object> problemObjects = bindingResult.getAllErrors()
				.stream()
				.map(objectError -> {
					String message= messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
					String name= objectError.getObjectName();
					
					if(objectError instanceof FieldError) {
						name=((FieldError)objectError).getField();
					}
					return Problem.Object.builder().name(name).userMessage(message).build();
				}).collect(Collectors.toList());
				
						
		Problem problem = createProblemBuilder(status, problemType, detail).userMessage(detail).objects(problemObjects).build();

		return handleExceptionInternal(ex,problem, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		// TODO Auto-generated method stub
		if(body==null) {
			body= Problem.builder().timestamp(LocalDateTime.now())
					.title(status.getReasonPhrase()).status(status.value()).userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL).build();
		}else if(body instanceof String) {
			body = Problem.builder().timestamp(LocalDateTime.now()).title((String) body).status(status.value()).userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL).build();
		}
	
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
	
	
	private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType,String detail){
		return Problem.builder().timestamp(LocalDateTime.now()).status(status.value()).type(problemType.getUri()).title(problemType.getTitle()).detail(detail);
	}
}
