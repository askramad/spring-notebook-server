package note.book.server.service;

public interface ExecutionContextStore {

    ExecutionContext getContext(String sessionId);

    void resetContext(String sessionId);

    void cleanUpsessions();
}
