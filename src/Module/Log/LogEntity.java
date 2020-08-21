package Module.Log;

import java.io.Serializable;
import java.util.Objects;

public class LogEntity implements Serializable {
    public String url;
    public String processedFile;
    public boolean isProcessing;
    public String status;

    public LogEntity() {
        super();
    }

    public void copy(LogEntity logEntity) {
        processedFile = logEntity.processedFile;
        isProcessing = logEntity.isProcessing;
        status = logEntity.status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogEntity logEntity = (LogEntity) o;
        return Objects.equals(url, logEntity.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }

}
