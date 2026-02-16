# Aws Docker

## aws-cli/aws-cli
Image: https://gallery.ecr.aws/aws-cli/aws-cli

### Run
Show AWS CLI version: `docker run --rm -it public.ecr.aws/aws-cli/aws-cli --version`

Use host's profiles (from `~/.aws`):
1. Alias: `alias awsd='docker run --rm -ti -v ~/.aws:/root/.aws:ro public.ecr.aws/aws-cli/aws-cli'`
2. Use: `awsd sts get-caller-identity`

Use new profiles (from `~/.aws-docker`):
1. Alias: `alias awsd='docker run --rm -ti -v ~/.aws-docker:/root/.aws public.ecr.aws/aws-cli/aws-cli'`
2. Configure: `awsd configure`
3. Use: `awsd sts get-caller-identity`

## bitnami/aws-cli
Image: https://gallery.ecr.aws/bitnami/aws-cli
Run: `docker run bitnami/aws-cli:latest`
