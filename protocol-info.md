The protocol for the system broker is based on simple serialization. broker exposes the following commands:

    1 - send a message to the queue;
    2 - read a message from the queue;



Example of the structure for a notibroker message:

    "1.message" - sending a message to the broker
    "2" - seding a request to the broker

The broker sends the message to the reciever in format:

    "message"
