package examples;

import java.io.EOFException;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.pcap4j.core.*;
import org.pcap4j.core.PcapHandle.TimestampPrecision;
import org.pcap4j.core.PcapNetworkInterface.PromiscuousMode;
import org.pcap4j.packet.Packet;
import org.pcap4j.util.NifSelector;

//@SuppressWarnings("javadoc")
public class PrintIp4Packets {

    private static final String COUNT_KEY
            = PrintIp4Packets.class.getName() + ".count";
    private static final int COUNT
            = Integer.getInteger(COUNT_KEY, 5);

    private static final String READ_TIMEOUT_KEY
            = PrintIp4Packets.class.getName() + ".readTimeout";
    private static final int READ_TIMEOUT
            = Integer.getInteger(READ_TIMEOUT_KEY, 5000); // [ms]

    private static final String SNAPLEN_KEY
            = PrintIp4Packets.class.getName() + ".snaplen";
    private static final int SNAPLEN
            = Integer.getInteger(SNAPLEN_KEY, 65536); // [bytes]

    private static final String TIMESTAMP_PRECISION_NANO_KEY
            = PrintIp4Packets.class.getName() + ".timestampPrecision.nano";
    private static final boolean TIMESTAMP_PRECISION_NANO
            = Boolean.getBoolean(TIMESTAMP_PRECISION_NANO_KEY);

    private PrintIp4Packets() {
    }

    public static void main(String[] args) throws PcapNativeException, NotOpenException, EOFException, TimeoutException {


        System.out.println(COUNT_KEY + ": " + COUNT);
        System.out.println(READ_TIMEOUT_KEY + ": " + READ_TIMEOUT);
        System.out.println(SNAPLEN_KEY + ": " + SNAPLEN);
        System.out.println(TIMESTAMP_PRECISION_NANO_KEY + ": " + TIMESTAMP_PRECISION_NANO);
        System.out.println("\n");

        PcapNetworkInterface nif;
        try {
            nif = new NifSelector().selectNetworkInterface();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        if (nif == null) {
            return;
        }

        System.out.println(nif.getName() + "(" + nif.getDescription() + ")");

        PcapHandle.Builder phb
                = new PcapHandle.Builder(nif.getName())
                .snaplen(SNAPLEN)
                .promiscuousMode(PromiscuousMode.PROMISCUOUS)
                .timeoutMillis(READ_TIMEOUT);
        if (TIMESTAMP_PRECISION_NANO) {
            phb.timestampPrecision(TimestampPrecision.NANO);
        }
        PcapHandle handle = phb.build();

//        String filter = "ip";
        String filter = "tcp";
        handle.setFilter(
                filter,
                BpfProgram.BpfCompileMode.OPTIMIZE
        );

        //noinspection InfiniteLoopStatement
        while (true) {
            Packet packet = handle.getNextPacketEx();
            Packet payload = packet.getPayload();
            System.out.println(payload);
        }
//        handle.close();
    }
}