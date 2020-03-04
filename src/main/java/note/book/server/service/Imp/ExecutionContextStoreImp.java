package note.book.server.service.Imp;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import note.book.server.service.ExecutionContext;
import note.book.server.service.ExecutionContextStore;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExecutionContextStoreImp implements ExecutionContextStore {

    @Value("${session.expires.in}")
    private int sessionExpiresIn;

    private final Map<String, ExecutionContext> store = new HashMap<>();

    private static final Logger logger = LogManager.getLogger(ExecutionContextStoreImp.class);

    @Override
    public ExecutionContext getContext(String sessionId) {
        logger.info("Calling The execution context for this session");
        ExecutionContext context = this.store.get(sessionId);
        if (context == null) {
            context = new ExecutionContext();
            this.store.put(sessionId, context);
        } else {
            context.setLastAccessed(LocalDateTime.now());
        }
        return context;
    }

    @Override
    public void resetContext(String sessionId) {
        logger.info("Calling the Reset context of this session");
        this.store.put(sessionId, new ExecutionContext());
    }

    @Scheduled(fixedDelay = 60 * 1000)
    @Async
    @Override
    public void cleanUpsessions() {
        List<String> toRemove = new ArrayList<>();
        for (String sessionId : this.store.keySet()) {
            long inactive = ChronoUnit.SECONDS.between(this.store.get(sessionId).getLastAccessed(), LocalDateTime.now());
            if (inactive > sessionExpiresIn) {
                toRemove.add(sessionId);
            }
        }
        toRemove.forEach(this.store::remove);
        logger.info("Sessions cleanup finished, {} sessions deleted.", toRemove.size());
    }

}
