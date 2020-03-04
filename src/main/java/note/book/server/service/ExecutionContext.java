package note.book.server.service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;

import org.graalvm.polyglot.Context;

public class ExecutionContext {

    private Context context;

    private LocalDateTime lastAccessed;

    private ByteArrayOutputStream outputStream;

    public ExecutionContext() {
        this.lastAccessed = LocalDateTime.now();
        this.outputStream = new ByteArrayOutputStream();
        this.context = Context.newBuilder().out(this.outputStream).err(this.outputStream).build();
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public LocalDateTime getLastAccessed() {
        return lastAccessed;
    }

    public void setLastAccessed(LocalDateTime lastAccessed) {
        this.lastAccessed = lastAccessed;
    }

    public ByteArrayOutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(ByteArrayOutputStream outputStream) {
        this.outputStream = outputStream;
    }

}
