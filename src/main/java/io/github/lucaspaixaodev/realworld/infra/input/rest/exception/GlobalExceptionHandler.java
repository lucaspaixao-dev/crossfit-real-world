package io.github.lucaspaixaodev.realworld.infra.input.rest.exception;

import io.github.lucaspaixaodev.realworld.domain.exception.BaseException;
import io.github.lucaspaixaodev.realworld.domain.exception.ValidationException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(BaseException.class)
	public ResponseEntity<ProblemDetail> handleBaseException(BaseException exception, HttpServletRequest request) {
		HttpStatus status = resolveStatus(exception);

		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, exception.getMessage());
		problemDetail.setTitle(status == HttpStatus.BAD_REQUEST ? "Validation error" : "Business error");
		problemDetail.setInstance(URI.create(request.getRequestURI()));

		return ResponseEntity.status(status).body(problemDetail);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ProblemDetail> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
			HttpServletRequest request) {
		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
				"Request validation failed");
		problemDetail.setTitle("Invalid request body");
		problemDetail.setInstance(URI.create(request.getRequestURI()));
		problemDetail.setProperty("errors",
				exception.getBindingResult().getFieldErrors().stream().map(this::toErrorItem).toList());

		return ResponseEntity.badRequest().body(problemDetail);
	}

	private HttpStatus resolveStatus(BaseException exception) {
		if (exception instanceof ValidationException) {
			return HttpStatus.BAD_REQUEST;
		}

		return HttpStatus.UNPROCESSABLE_CONTENT;
	}

	private ErrorItem toErrorItem(FieldError fieldError) {
		return new ErrorItem(fieldError.getField(), fieldError.getDefaultMessage(), fieldError.getRejectedValue());
	}

	private record ErrorItem(String field, String message, Object rejectedValue) {
	}

}
