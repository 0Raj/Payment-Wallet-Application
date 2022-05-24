package com.paymentApp.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(InvalidPasswordException.class)
	public ResponseEntity<MyErrorMessage> handleCustomerPasswordException(InvalidPasswordException iun,WebRequest req){
		
		MyErrorMessage message = new MyErrorMessage(LocalDateTime.now(),iun.getMessage(),req.getDescription(false));
		
		return new ResponseEntity<MyErrorMessage>(message,HttpStatus.BAD_REQUEST);
		
	}
	
	//to handle Not found exception
	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<MyErrorMessage> wrongUrl(NoHandlerFoundException nhf, WebRequest req){
		MyErrorMessage err = new MyErrorMessage(LocalDateTime.now(), nhf.getMessage(), req.getDescription(false));
		
		ResponseEntity<MyErrorMessage> responseEntity = new ResponseEntity<>(err, HttpStatus.BAD_GATEWAY);
		
		return responseEntity;
	}
		
	@ExceptionHandler(MethodArgumentNotValidException.class)
		public ResponseEntity<MyErrorMessage> myMANVExceptionHandler(MethodArgumentNotValidException me) {
		
		MyErrorMessage err=new MyErrorMessage(LocalDateTime.now(),"Validation Error",me.getBindingResult().getFieldError().getDefaultMessage());
		
		return new ResponseEntity<>(err,HttpStatus.BAD_REQUEST);
	}
		
	
	@ExceptionHandler(UserAlreadyExistWithMobileNumber.class)
	public ResponseEntity<MyErrorMessage> handleUserAlreadyExistWithMobileNumber(UserAlreadyExistWithMobileNumber uae,WebRequest req){
		
		MyErrorMessage message = new MyErrorMessage(LocalDateTime.now(),uae.getMessage(),req.getDescription(false));
		
		return new ResponseEntity<MyErrorMessage>(message,HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(BeneficiaryAlreadyExists.class)
	public ResponseEntity<MyErrorMessage> handleBeneficiaryAlreadyExists(BeneficiaryAlreadyExists uae,WebRequest req){
		
		MyErrorMessage message = new MyErrorMessage(LocalDateTime.now(),uae.getMessage(),req.getDescription(false));
		
		return new ResponseEntity<MyErrorMessage>(message,HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(InsufficientAmountException.class)
	public ResponseEntity<MyErrorMessage> handleInsufficientAmountInWallet(InsufficientAmountException isa,WebRequest req){
		
		MyErrorMessage message = new MyErrorMessage(LocalDateTime.now(),isa.getMessage(),req.getDescription(false));
		
		return new ResponseEntity<MyErrorMessage>(message,HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(IndexOutOfBoundsException.class)
	public ResponseEntity<MyErrorMessage> handleIndexOutOfBoundException(IndexOutOfBoundsException isa,WebRequest req){
		
		MyErrorMessage message = new MyErrorMessage(LocalDateTime.now(),"Please login first",req.getDescription(false));
		
		return new ResponseEntity<MyErrorMessage>(message,HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(BankAlreadyExistWithAccountNoException.class)
	public ResponseEntity<MyErrorMessage> handleBankAlreadyExistException(BankAlreadyExistWithAccountNoException bae,WebRequest req){
		
		MyErrorMessage message = new MyErrorMessage(LocalDateTime.now(),bae.getMessage(),req.getDescription(false));
		
		return new ResponseEntity<MyErrorMessage>(message,HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<MyErrorMessage> handleDataNotFountException(NotFoundException iun,WebRequest req){
		
		MyErrorMessage message = new MyErrorMessage(LocalDateTime.now(),iun.getMessage(),req.getDescription(false));
		
		return new ResponseEntity<MyErrorMessage>(message,HttpStatus.BAD_REQUEST);
		
	}
	

}
