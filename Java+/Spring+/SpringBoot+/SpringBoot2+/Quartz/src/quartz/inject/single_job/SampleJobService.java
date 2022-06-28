package quartz.inject.single_job;

import org.springframework.stereotype.Service;

@Service
class SampleJobService {
    private int invocationCounter = 0;

    void executeSampleJob() {
        invocationCounter++;
    }

    int getInvocationCounter() {
        return invocationCounter;
    }
}
