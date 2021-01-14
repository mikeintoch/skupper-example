# customer-rest-json project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

# Deploy it to CodeReady Workspaces

[![Contribute](factory-contribute.svg)](https://codeready-openshift-workspaces.apps.cluster-demo-43c4.demo-43c4.example.opentlc.com/factory?url=https://github.com/mikeintoch/skupper-example.git)


## Running the application in dev mode

Moving to customer service folder
```
cd customer-service
```

You can run your application in dev mode that enables live coding using:
```
./mvnw quarkus:dev
```
Application for Dev using h2 in memory database

## For Deploy on OCP
Login to Openshift
Create a project
```
oc new-project backend-services
```
`For production environment the application using PostgreSQL database`

Create a new Postgresql Database with the next configuration
- `Namespace`: backend-services
- `Database Service Name`: customer-database
- `PostgreSQL Connection Username`: customer
- `PostgreSQL Connection Password`: mysecretpassword
- `PostgreSQL Database Name`: customer

## Packaging and running the application on OCP

```
oc project backend-services
mvn clean compile package -DskipTests
```

The output should end with `BUILD SUCCESS`.

Finally, make sure it’s actually done rolling out:

```
oc rollout status -w dc/customer
```
Add labels to Database and service deployment

```
oc label dc/customer app.kubernetes.io/part-of=customer --overwrite && \
oc label dc/customer-database app.kubernetes.io/part-of=customer app.openshift.io/runtime=postgresql --overwrite && \
oc annotate dc/customer app.openshift.io/connects-to=customer-database --overwrite
```