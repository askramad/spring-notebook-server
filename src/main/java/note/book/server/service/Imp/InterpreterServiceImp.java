package note.book.server.service.Imp;

import java.util.Timer;
import java.util.TimerTask;

import org.graalvm.polyglot.PolyglotException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import note.book.server.exception.InterpreterException;
import note.book.server.exception.NotSupportedLanguageException;
import note.book.server.service.ExecutionContext;
import note.book.server.service.InterpreterService;

@Service
public class InterpreterServiceImp implements InterpreterService {

    private final ExecutionContextStoreImp store;

    @Autowired
    public InterpreterServiceImp(ExecutionContextStoreImp store) {
        this.store = store;
    }

    @Value("${execution.time.out}")
    private int executionTimeOut;

    @Override
    public String execute(String lang, String code, String sessionId) {
        ExecutionContext context = this.store.getContext(sessionId);
        Timer timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                context.getContext().close(true);
                store.resetContext(sessionId);
            }
        }, executionTimeOut * 1000);
        String output;
        try {
            context.getContext().eval(lang, code);
        } catch (PolyglotException e) {
            throw new InterpreterException(e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new NotSupportedLanguageException(e.getMessage());
        }
        timer.cancel();
        output = context.getOutputStream().toString();
        context.getOutputStream().reset();
        return output;
    }

}
