package prism.infra;

import lombok.Getter;
import lombok.Setter;
import prism.domain.cctv.model.Cctv;

@Getter
@Setter
public abstract class AbstractEvent {

    protected String topic;
    protected String eventType;
    protected long timestamp;

    public AbstractEvent(Cctv cctv, String topic) {
        this.topic = topic;
        this.eventType = this.getClass().getSimpleName();
        this.timestamp = System.currentTimeMillis();
    }
}
