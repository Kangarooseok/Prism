//package prism.infra.job;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.context.event.ApplicationReadyEvent;
//import org.springframework.context.event.EventListener;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class CctvStatusStartupSync {
//    private final CctvStatusSyncJob job;
//
//    @EventListener(ApplicationReadyEvent.class)
//    public void runOnce() { job.sync(); }
//}
