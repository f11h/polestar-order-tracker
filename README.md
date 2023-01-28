# Polestar Order Tracker

Automatically track the detailed OrderInformation of you Polestar order!<br />
Get notifications via E-Mail if anything happens regarding your future car!

It checks every 6 hours if the OrderModel of the Polestar GRAPH API of your order has changed. If there are any changes
it will send you an email with a detailed report of what has changed.

The service uses an in-memory database to keep track of the "old" state of the order. On startup the OrderModel will be fetched
and stored date will be used to detect the changes. It makes no sense to run this program just for a short time. I suggest
to run it as a daemon on a server.

## Disclaimer

Polestar has not officially released the used APIs for consumers/ 3rd party apps. That's why I cannot guarantee that the usage
of this service is allowed nor that the APIs will provide the required features in the future.

## Contribution

This is an OpenSource project. If you want to participate just clone the repository, do your changes and create a PullRequests.

## Build

To build the project on your own **Java 17+** is required.
Open a shell within the project directory and execute the following command:

```bash
mvnw install
```

You can find the resulting ```polestar-order-tracker-X.X.X.jar``` in the ```target``` directory.

## Configure & Run

### Direct

Create a copy of the file ```application.yml``` from ```sample-conf``` directory and place it next to the
```polestar-order-tracker-X.X.X.jar``` which you have build on your own or downloaded
from [Releases](https://github.com/f11h/polestar-order-tracker/releases) page.

Adjust the values in this file according to your needs (Setup E-Mail Server and your Polestar order details)

| Property                                           | Description                                                                                                                                                                                                                                      | Possible Values/ Example             |
|----------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------|
| polestar-order-tracker.order-configs[]enabled      | Enable this order to keep track of it                                                                                                                                                                                                            | true, false                          |
| polestar-order-tracker.order-configs[]notify-email | Provide a list of E-Mail recipient, which will get notified about changes                                                                                                                                                                        | mail@example.org                     | 
| polestar-order-tracker.order-configs[]order-id     | The UUID of your Polestar Order. Open your order via the Web-Portal. You will see an URL like ```https://www.polestar.com/de/order/424641db-6692-49a8-aa10-2fe970516404``` in your Browsers address bar. We need the ID from the end of the url. | 424641db-6692-49a8-aa10-2fe970516404 |
| polestar-order-tracker.order-configs[]username     | Your E-Mail of your Polestar ID                                                                                                                                                                                                                  | mail@example.org                     |
| polestar-order-tracker.order-configs[]password     | Your Password of your Polestar ID                                                                                                                                                                                                                | sup3rS3cur3                          |

Just start the service with the following command

```bash
java -jar polestar-order-tracker-X.X.X.jar
```

### Docker

The service can also started as Docker container. Therefore the configuration should be done via environment variables:

| ENV                                               | Description                                                                                                                                                                                                                                      | Possible Values/ Example             |
|---------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------|
| SPRING_MAIL_HOST                                  | SMTP Server Host                                                                                                                                                                                                                                 | smtp.example.org                     |
| SPRING_MAIL_PORT                                  | SMTP Server Port                                                                                                                                                                                                                                 | 587                                  |
| SPRING_MAIL_USERNAME                              | SMTP Username                                                                                                                                                                                                                                    | mail@example.org                     |
| SPRING_MAIL_PASSWORD                              | SMTP User Password                                                                                                                                                                                                                               | sup3rS3cur3                          |
| POLESTARORDERTRACKER_ORDERCONFIGS_0_ENABLED       | Enable this order to keep track of it                                                                                                                                                                                                            | true, false                          |
| POLESTARORDERTRACKER_ORDERCONFIGS_0_NOTIFYEMAIL_0 | Provide a E-Mail recipient, which will get notified about changes                                                                                                                                                                                | mail@example.org                     | 
| POLESTARORDERTRACKER_ORDERCONFIGS_0_ORDERID       | The UUID of your Polestar Order. Open your order via the Web-Portal. You will see an URL like ```https://www.polestar.com/de/order/424641db-6692-49a8-aa10-2fe970516404``` in your Browsers address bar. We need the ID from the end of the url. | 424641db-6692-49a8-aa10-2fe970516404 |
| POLESTARORDERTRACKER_ORDERCONFIGS_0_USERNAME      | Your E-Mail of your Polestar ID                                                                                                                                                                                                                  | mail@example.org                     |
| POLESTARORDERTRACKER_ORDERCONFIGS_0_PASSWORD      | Your Password of your Polestar ID                                                                                                                                                                                                                | sup3rS3cur3                          |

