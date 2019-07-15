#!/bin/bash

## steps to extend vm memory when default memory config is not sufficient
# minikube config set memory 8192
# minikube config set cpus 2
# minkube delete

minikube stop && minikube start
eval $(minikube docker-env)

# build images so k8s can find them in local registry
docker build web/. -t soa/music-rec-web
docker build search/. -t soa/music-rec-search
docker build charts/. -t soa/music-rec-charts
docker build images/. -t soa/music-rec-images

# apply all deployment/service-yaml-files in kubernetes directory
kubectl apply -f kubernetes/

FRONTEND_URL="$(minikube service frontend --url)"
echo "Web frontend can be reached via: $FRONTEND_URL"
echo "Example: $FRONTEND_URL/?title=Bullets&artist=Kaytranada"
