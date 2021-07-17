package htmlunit.vtb;

import java.util.List;

class AgreementData {
    private final List<String> agreements;

    AgreementData(List<String> agreements) {
        this.agreements = agreements;
    }

    public List<String> getAgreements() {
        return agreements;
    }
}
