#!/usr/bin/env bash

echo "configuring sqs"
echo "==================="
LOCALSTACK_HOST=localhost
AWS_REGION=us-east-1

create_queue() {
    local FIFO=${2:-false}
    local DLQ=${3:-""}

    case "${FIFO}" in
        true)
            echo "creating fifo queue"
            local QUEUE_NAME_TO_CREATE=$1.fifo
            local ATTRIBUTES="FifoQueue=true,ContentBasedDeduplication=true,VisibilityTimeout=30"
            ;;
        false)
            echo "creating standard queue"
            local QUEUE_NAME_TO_CREATE=$1
            case "${DLQ}" in
                "")
                    local ATTRIBUTES="VisibilityTimeout=10"
                    ;;
                *)
                    local ATTRIBUTES="VisibilityTimeout=10,RedrivePolicy='{\"deadLetterTargetArn\":\"${DLQ}\",\"maxReceiveCount\":2}'"
                    ;;
            esac
            ;;
        *)
            echo "FIFO must be true or false"
            exit 1
            ;;
    esac

    awslocal --endpoint-url=http://${LOCALSTACK_HOST}:4566 sqs create-queue \
    --queue-name "${QUEUE_NAME_TO_CREATE}" --region ${AWS_REGION} --attributes "${ATTRIBUTES}"
}

create_queue "dlq"
create_queue "queue1" false "arn:aws:sqs:${AWS_REGION}:000000000000:dlq"
create_queue "queue2" true

awslocal sns create-topic --name "sns-topic" --region ${AWS_REGION}
create_queue "queue-from-sns" false
awslocal sns subscribe --topic-arn "arn:aws:sns:${AWS_REGION}:000000000000:sns-topic" \
--protocol sqs --notification-endpoint "arn:aws:sqs:${AWS_REGION}:000000000000:queue-from-sns" --region ${AWS_REGION}

