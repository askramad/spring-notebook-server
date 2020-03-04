package note.book.server.controller;

import java.util.UUID;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import note.book.server.controller.dto.InterpreterRequestDto;
import note.book.server.controller.dto.InterpreterResponseDto;
import note.book.server.exception.InterpreterException;
import note.book.server.service.InterpreterService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class InterpreterController {

    private static final Logger logger = LogManager.getLogger(InterpreterController.class);

    private final InterpreterService interpreterService;

    @Autowired
    public InterpreterController(InterpreterService interpreterService) {
        this.interpreterService = interpreterService;
    }

    @GetMapping("/status")
    public String status() {
        return "Alive!";
    }

    @PostMapping("/execute")
    public InterpreterResponseDto execute(@Valid @RequestBody InterpreterRequestDto request) {
        String code = request.getCode().substring(request.getCode().indexOf('\n') + 1);
        String lang = request.getCode().substring(1, request.getCode().indexOf('\n'));
        String sessionId = request.getSessionId();
        logger.info("code : {},lang:{}", code, lang);
        InterpreterResponseDto response = new InterpreterResponseDto();
        if (sessionId == null) {
            sessionId = UUID.randomUUID().toString();
            response.setSessionId(sessionId);
        }
        try {
            response.setResult(interpreterService.execute(lang, code, sessionId));
            response.setSuccess(true);
        } catch (InterpreterException e) {
            response.setResult(e.getMessage());
            response.setSuccess(false);
        }
        return response;
    }
}
