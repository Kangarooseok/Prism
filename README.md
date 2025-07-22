# 

## Model
www.msaez.io/#/106228306/storming/nmsservice

## Before Running Services
### Make sure there is a Kafka server running
```
cd kafka
docker-compose up
```
- Check the Kafka messages:
```
cd infra
docker-compose exec -it kafka /bin/bash
cd /bin
./kafka-console-consumer --bootstrap-server localhost:9092 --topic
```

## Run the backend micro-services
See the README.md files inside the each microservices directory:

- controller
- notification
- alertsubscription
- healthchecklog
- issue
- cctv
- network


## Run API Gateway (Spring Gateway)
```
cd gateway
mvn spring-boot:run
```

## Test by API
- controller
```
 http :8088/cctvs id="id"locationName="locationName"locationAddress="locationAddress"ipAddress="ipAddress"hlsAddress="hlsAddress"longitude="longitude"latitude="latitude"createdAt="createdAt"updatedAt="updatedAt"status="status"
 http :8088/users id="id"name="name"email="email"role="role"assignedTeam="assignedTeam"createdAt="createdAt"updatedAt="updatedAt"
```
- notification
```
 http :8088/notifications id="id"receiver="receiver"message="message"status="status"createdAt="createdAt"sentAt="sentAt"updatedAt="updatedAt"issued="issued"healthCheckId="healthCheckId"
```
- alertsubscription
```
 http :8088/alertSubscriptions id="id"userId="userId"createdAt="createdAt"isActive="isActive"
```
- healthchecklog
```
 http :8088/healthCheckLogs id="id"cctvId="cctvId"timestamp="timestamp"icmpLatencyMs="icmpLatencyMs"hlsResponseMs="hlsResponseMs"cpuPercent="cpuPercent"memoryPercent="memoryPercent"diskPercent="diskPercent"uptimeSeconds="uptimeSeconds"icmpStatus="icmpStatus"hlsStatus="hlsStatus"resourceStatus="resourceStatus"faultDetected="faultDetected"createdAt="createdAt"
```
- issue
```
 http :8088/issues id="id"cctvId="cctvId"status="status"description="description"createdAt="createdAt"updatedAt="updatedAt"failureTime="failureTime"resolvedAt="resolvedAt"
```
- cctv
```
 http :8088/tvResolutions id="id"cctvId="cctvId"status="status"healthCheckId="healthCheckId"createdAt="createdAt"updatedAt="updatedAt"
```
- network
```
 http :8088/networkActions id="id"cctvId="cctvId"status="status"performedBy="performedBy"result="result"createdAt="createdAt"updatedAt="updatedAt"
```


## Run the frontend
```
cd frontend
npm i
npm run serve
```

## Test by UI
Open a browser to localhost:8088

## Required Utilities

- httpie (alternative for curl / POSTMAN) and network utils
```
sudo apt-get update
sudo apt-get install net-tools
sudo apt install iputils-ping
pip install httpie
```

- kubernetes utilities (kubectl)
```
curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"
sudo install -o root -g root -m 0755 kubectl /usr/local/bin/kubectl
```

- aws cli (aws)
```
curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"
unzip awscliv2.zip
sudo ./aws/install
```

- eksctl 
```
curl --silent --location "https://github.com/weaveworks/eksctl/releases/latest/download/eksctl_$(uname -s)_amd64.tar.gz" | tar xz -C /tmp
sudo mv /tmp/eksctl /usr/local/bin
```
