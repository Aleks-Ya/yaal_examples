# AWS CodeBuild

List build projects: `aws codebuild list-projects`
Show build project details: `aws codebuild batch-get-projects --names project1`

Generate a skeleton of a CLI file: `aws codebuild create-project --generate-cli-skeleton > project.json`
Create a project from a CLI file: `aws codebuild create-project --cli-input-json file://project.json`
Check build status: `aws codebuild batch-get-builds --ids project1:2525a87f-88ce-41c2-bc20-6ca99fc4af8e`
Delete a build project: `aws codebuild delete-project --name project1`
Get build ID by project name: `aws codebuild list-builds-for-project --project-name project1 --query 'ids[0]' --output text`
