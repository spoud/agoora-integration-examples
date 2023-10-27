# GraphQL API to communicate with Agoora

See https://github.com/spoud/agoora-integration-examples/tree/master/graphql-example for the code.

## Introduction

This is a short guide, which can serve as an example, in order to communicate with the Agoora service in an API.
Please remember that Agoora is constantly evolving its service by introducing new functionalities, therefore the data 
structure can change in time. Therefore, always base yourself on the official documentation to make sure you are up 
to date with the latest modifications. Also, this is just an overview of a couple of examples. We will not focus on 
the various types of queries, as they are all well documented elsewhere. Ask Spoud for the current version documentation when needed.

This will serve as a proof of concept, not to demonstrate best practice when it comes to coding.

The technologies used in the first part are Java with Quarkus Framework and Maven

## Compilation

```
./mvnw clean package
```

## Running

### Create an organization

If you're not part of any organization, you can easily create one. For this go in *Admin* and then *Organization*.

### Create an agent

You can use your personal user as login, but we strongly recommend creating an Agent instead. You can see the agent
as a technical user that can be used by applications. For this just go in the *Admin* panel and then *Agent*.

**Note: The agent needs to have the right permissions. For this example it needs to be set to the path of the organizatio
in order to list groups and create users**

### Configure your environment
Copy the .env example file

```
cp .env.example .env
```

You can now open it and replace the organization and user credentials with the one that you just created.

### Running quarkus in dev mode

```
./mvnw quarkus:dev
```

This application should create a user group called `test-group` and assign any users of the organization to it. 

## GraphQL User interface

As a user you can easily access the GraphQL UI. Just add `/graphql` to the hostname. For example: https://app.agoora.com/graphql

### Getting the authentication token

The easiest way to get an access token is to go on the web page, enter developer mode and copy the `Authorization` header. 
See image below:

![](https://github.com/spoud/agoora-integration-examples/raw/master/graphql-example/images/dev-tool-token.png)

Once you have the token, you can include it in the Http Header in the UI. See image below:
![](https://github.com/spoud/agoora-integration-examples/raw/master/graphql-example/images/token-in-ui.png)

/!\ Be aware that the access token is very limited in time. You cannot use it for too long. At some point you will get
error (be aware that the UI is not really explicit...). You can just repeat the process of getting the token.

Once in the UI you can play around with the API. Normally the autocompletion should help you.

You can for example try to get your info by entering:
```graphql
query{
  currentUser{
    id
    firstName
    lastName
    organizations
  }
}
```
