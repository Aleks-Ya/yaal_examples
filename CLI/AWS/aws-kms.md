# AWS KMS CLI

List keys: `aws kms list-keys`
List key aliases: `aws kms list-aliases`
Show details about a key: 
 - By key id: `aws kms describe-key --output json --key-id 1a479585-ab47-4f2d-b740-e7b6b9f55b76`
 - By key alias: `aws kms describe-key --output json --key-id alias/aws/s3`
Create a customer managed key: `aws kms create-key --description "My key 1" --key-usage ENCRYPT_DECRYPT`
Create an alias for a key: `aws kms create-alias --alias-name alias/my-key-1 --target-key-id 105f6e75-ce4f-459a-946b-f63f4bbc3fa2`
Encrypt text: `aws kms encrypt --output json --key-id alias/my-key-1 --plaintext "My secret data"`
Decrypt text: `aws kms decrypt --ciphertext-blob AQICAHgU2wFpiP36EFW6iiM+A6tI0aW7PQzRkgdqzSSSJb7h7AFp7H3xO09lo8mhvSTdyvdAAAAAZzBlBgkqhkiG9w0BBwagWDBWAgEAMFEGCSqGSIb3DQEHATAeBglghkgBZQMEAS4wEQQMGdrEP/hB+38S2k20AgEQgCRDM2V5MPimjVVDuBxVHlgiK5mFiKku0JPjQqdVH+kZZEsVZTA=`
