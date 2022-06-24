package examples;

import org.junit.jupiter.api.Test;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.core.Pcaps;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyIterable;
import static org.hamcrest.Matchers.not;

public class GetAllNetworkInterfaces {
    @Test
    void allNifs() throws PcapNativeException {
        List<PcapNetworkInterface> allDevs = Pcaps.findAllDevs();
        assertThat(allDevs, not(emptyIterable()));
        allDevs.forEach(System.out::println);
    }
}