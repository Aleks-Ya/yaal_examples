# AWS CDK CLI

Install: `npm i -g aws-cdk`

Help: `cdk --help`
Help about a command: `cdk bootstrap --help`

Initialize a Java project in current dir: `cdk init app --language java`
Suppress the telemetry collection notice: `cdk acknowledge 34892`
Bootstrap the Environment (create needed roles in the Account): `cdk bootstrap`

List all stacks in the app: `cdk list`
Print template for a stack: `cdk synth CreateBucketStack`

Deploy a stack: `cdk deploy CreateBucketStack`
Detect a drift (manual changes) of a stack: `cdk drift CreateBucketStack`
Show the diff (what will be modified on deploy) with deployed stack: `cdk diff CreateBucketStack`
Destroy a stack: `cdk destroy CreateBucketStack`
