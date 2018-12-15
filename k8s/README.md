 # Kubernetes Deployment
## Helm Install
```helm init --service-account tiller```

```kubectl create serviceaccount --namespace kube-system tiller```

```kubectl create clusterrolebinding tiller-cluster-rule --clusterrole=cluster-admin --serviceaccount=kube-system:tiller```

```kubectl patch deploy --namespace kube-system tiller-deploy -p '{"spec":{"template":{"spec":{"serviceAccount":"tiller"}}}}'```

```helm repo add bitnami https://charts.bitnami.com```

## Startup
* Install MySQL:

  ```helm install --name microdb --set mysqlRootPassword=7layer,mysqlDatabase=microservicedb stable/mysql```

* Install Kafka:

  ```helm install --name microbus bitnami/kafka```

* Install NginX Ingress Controller:

  ```helm install stable/nginx-ingress --name nginx --namespace kube-system --set rbac.create=true --set controller.replicaCount=1```

* Install OAuth2 authorization server:

  ```kubectl create -f oauth2.yml```

* Install Configuration server:

  ```kubectl create -f config.yml```

* Install the apps:

  ```kubectl create -f subscription.yml```

  ```kubectl create -f billing.yml```

* Install API Gateway:

  ```kubectl create -f apigw.yml```

* Install Ingress for the API Gateway service and OAuth2 server:

  ```kubectl create -f apigw-ingress.yml```

## Test it!
Retrieve the Ingress Controller's external IP:

```kubectl get service nginx-nginx-ingress-controller -n kube-system -o jsonpath='{.status.loadBalancer.ingress[].ip}'```

Modify the tests/microservices.postman_environment.json to have:
* ```apigw``` to have value of ```<Ingress-IP>/api```
* ```oauth2server``` to have value of ```<Ingress-IP>```

Run the tests:

```newman run Microservices.postman_collection.json -ke microservices.postman_environment.json```
