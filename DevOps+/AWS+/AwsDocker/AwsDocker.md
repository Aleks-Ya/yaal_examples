# Aws Docker

## aws-cli/aws-cli
Image: https://gallery.ecr.aws/aws-cli/aws-cli
Run: `docker run --rm -it public.ecr.aws/aws-cli/aws-cli --version`
Alias: `alias awsd='docker run --rm -ti -v ~/.aws-docker:/root/.aws -v $(pwd):/aws public.ecr.aws/aws-cli/aws-cli'`
Configure: `awsd configure`
List S3 buckets: `awsd s3 ls`

## bitnami/aws-cli
Image: https://gallery.ecr.aws/bitnami/aws-cli
Run: `docker run bitnami/aws-cli:latest`
