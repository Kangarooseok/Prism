package prism.domain;

import java.util.*;
import lombok.*;
import prism.domain.*;
import prism.infra.AbstractEvent;

@Data
@ToString
public class HlsFailureDetected extends AbstractEvent {

    private Long id;
}
