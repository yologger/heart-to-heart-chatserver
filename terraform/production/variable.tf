# AWS VPC
variable "region" {
  type = string
}
variable "access_key" {
  type = string
}
variable "secret_key" {
  type = string
}

# AWS EKS
variable "cluster_name" {
  type = string
}
variable "cluster_version" {
  type = string
}