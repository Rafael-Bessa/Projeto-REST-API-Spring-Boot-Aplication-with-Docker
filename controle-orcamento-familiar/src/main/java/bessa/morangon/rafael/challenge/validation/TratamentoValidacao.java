package bessa.morangon.rafael.challenge.validation;

import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

@RestControllerAdvice
public class TratamentoValidacao {

	@Autowired
	private MessageSource messageSource;

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ErroFormularioDto> tratamento(MethodArgumentNotValidException e) {

		List<ErroFormularioDto> errosDto = new ArrayList<>();

		List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();

		fieldErrors.forEach(erro -> {
			String mensagem = messageSource.getMessage(erro, LocaleContextHolder.getLocale());
			ErroFormularioDto erros = new ErroFormularioDto(erro.getField(), mensagem);
			errosDto.add(erros);
		});

		return errosDto;
	}

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(InvalidFormatException.class)
	public String tratamentoCategoria(InvalidFormatException e) {

		return e.getOriginalMessage().substring(88);

	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(DateTimeException.class)
	public String tratamentoDateTime(DateTimeException e) {
		
		return e.getMessage();
		
	}

}
