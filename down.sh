#!/bin/bash
set -e

docker-compose down -v --remove-orphans

set +e
DOCKER_CONTAINER_IDS=$(docker ps -a | grep "dev\|none\|test-vp\|peer[0-9]-" | awk '{print $1}')
DOCKER_IMAGE_IDS=$(docker images | grep "dev\|none\|test-vp\|peer[0-9]-" | awk '{print $3}')
docker rm -f $DOCKER_CONTAINER_IDS
docker rmi -f $DOCKER_IMAGE_IDS
set -e