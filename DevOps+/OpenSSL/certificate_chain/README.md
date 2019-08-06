# The Certification Chain exercise

## Goal
### Goals
1. Create 3 certificates: root, intermediate and client.
2. The client certificate should be signed by the intermediate certificate.
3. The intermediate certificate should be signed by the root certificate.

### Outcome
1. Root CA: 
    1. Key pair
    2. Certificate
2. Intermediate CA:
    1. Key pair
    2. Certificate
    3. Certification chain
3. Client:
    1. Key pair
    2. Certificate
    3. Certification chain
4. Commands:
    1. Generate key pairs
    2. Generate CSRs
    3. Sign CSRs
    4. Verify certification path
5. Information about properties of:
    1. Key pairs
    2. Certificates