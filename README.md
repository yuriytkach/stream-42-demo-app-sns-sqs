# Demo Project for Online Stream #42 - SNS and SQS
Demo project for online YouTube stream #42 about using SNS and SQS

## Access to Online Stream on YouTube

To get a link to online stream on YouTube please do the following:

- :moneybag: Make any donation to support my volunteering initiative to help Ukrainian Armed Forces by means described on [my website](https://www.yuriytkach.com/volunteer)
- :email: Write me an [email](mailto:me@yuriytkach.com) indicating donation amount and time
- :tv: I will reply with the link to the stream on YouTube.

Thank you in advance for your support! Слава Україні! :ukraine:

## Run the App

First start the localstack container using docker-compose:

```shell
docker-compose up
```

The topic and queues that are created with container start are defined in `scripts/localstack_bootstrap.sh`

Then start the app:
```shell
./gradlew bootRun
```

Now you can submit events to controller and observe the logs:

```shell
curl -X POST localhost:8080/event -H 'Content-Type: application/json' -d '{"id": 1, "name": "Event"}'
```

## Reference Documentation
For further reference, please consider the following sections:

* [Spring Cloud AWS](https://spring.io/projects/spring-cloud-aws)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.1.2/reference/htmlsinge/index.html#web)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
