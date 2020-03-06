package note.book.server.controller.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class InterpreterRequestDto {

    @NotEmpty
    @Pattern(regexp = "%[a-zA-Z]+\\n[\\W\\w]+")
    private String code;

    @Pattern(regexp = "[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}")
    private String sessionId;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

}
