package ae.rakbank.eventmanagementservice.sequences;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

public class AlphanumericSequenceGenerator implements IdentifierGenerator {

    private static final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object obj) {
        int currentCount = counter.getAndIncrement();
        String prefix = "EVT";
        return prefix + String.format("%05d", currentCount);
    }
}
