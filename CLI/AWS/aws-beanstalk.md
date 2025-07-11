# AWS BeansTalk CLI

## Application
List applications: `aws elasticbeanstalk describe-applications`
Create an application: `aws elasticbeanstalk create-application --application-name app1`

## Environment
List environments: `aws elasticbeanstalk describe-environments`
Show details about an environment: `aws elasticbeanstalk describe-environments --environment-name env1`
Create an environment: `aws elasticbeanstalk create-environment --application-name app1`
Terminate environment by id: `aws elasticbeanstalk terminate-environment --environment-id e-yywqfjewm9`
Terminate environment by id (force): `aws elasticbeanstalk terminate-environment --environment-id e-yywqfjewm9 --force-terminate`
List solution stacks: `aws elasticbeanstalk list-available-solution-stacks`
