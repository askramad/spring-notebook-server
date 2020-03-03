package note.book.server.service;

import note.book.server.exception.InterpreterException;

public interface InterpreterService {

    String execute(String lang, String code, String sessionId) throws InterpreterException;

}
