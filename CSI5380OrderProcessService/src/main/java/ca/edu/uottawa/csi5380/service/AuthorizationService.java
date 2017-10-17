package ca.edu.uottawa.csi5380.service;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class AuthorizationService {

    private static final Logger LOGGER = LogManager.getLogger(AuthorizationService.class);

    private final AtomicLong counter = new AtomicLong();

    public boolean authorizeOrder() {
        // Reject every 5th request
        LOGGER.info("Order #: " + (counter.get()+1));
        return counter.incrementAndGet() % 5 != 0;
    }

}
