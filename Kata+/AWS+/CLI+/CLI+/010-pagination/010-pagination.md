# 010-pagination

`aws iam list-policies`
`aws iam list-policies --query 'length(Policies)' --output text`

`aws iam list-policies --query 'length(Policies)' --output text --page-size 200`
`aws iam list-policies --query 'length(Policies)' --output text --no-paginate`
``aws iam list-policies --query 'length(Policies || `[]`)' --output text --max-items 250``

`aws iam list-policies --max-items 50 --no-cli-pager`
