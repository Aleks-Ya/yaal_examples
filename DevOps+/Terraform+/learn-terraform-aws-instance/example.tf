provider "aws" {
  profile = "default"
  region  = "eu-central-1"
}

resource "aws_instance" "example" {
  ami = "ami-076431be05aaf8080"
  instance_type = "t3.micro"
}

resource "aws_eip" "ip" {
    vpc = true
    instance = aws_instance.example.id
}
