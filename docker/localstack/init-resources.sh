#!/bin/bash
set -euo pipefail

echo "Creating LocalStack resources..."

awslocal sqs create-queue \
  --queue-name new-companies.fifo \
  --attributes FifoQueue=true,ContentBasedDeduplication=true

echo "LocalStack resources created successfully."
