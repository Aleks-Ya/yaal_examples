package examples;

import org.junit.jupiter.api.Test;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.Pcaps;

import static org.assertj.core.api.Assertions.assertThat;

class GetAllNetworkInterfacesTest {
    @Test
    void allNifs() throws PcapNativeException {
        var allDevs = Pcaps.findAllDevs();
        assertThat(allDevs).isNotEmpty();
        allDevs.forEach(System.out::println);
    }
}