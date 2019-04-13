# Teaching-HEIGVD-RES-2019-Labo-SMTP

##Author Erwan Moreira, Remy Vuagniaux

## Objectives

## Installation

In the repo there is a bin/ directory that contains the executable file. To use the client and send pranks you need run the executable file with the command :
    java -jar PrankClient.jar
But be careful to execute the file in a directory where the conf.properties, prank.txt and victims.txt files are in.

### conf.properties

To configure the client you need to modify the conf.properties file.
In this file there are 9 keywords that you can update:

address= the adresse or the hostname of the server smtp that will receve the prank
port= the right port of the server smtp
subjectBalise= in the file prank.txt the subject of the mail is indicate by the subject Balise

exemple:

subjectBalise=S:

In the prank.txt file the subject is bellow the balise S:

S:
the subject of the mail

textBalise= in the file prank.txt the text of the mail is indicate by the text Balise

exemple:

textBalise=T:

In the prank.txt file the text is bellow the balise T:

T:
the text of the mail

separator= in the file prank.txt each mail is separate by a separator that can be set here

exemple:

mail1
separator
mail2

nbPersonGroup= indicate the number of people in group that will receve the same prank

note: the first person of the group will be the false sender

cc= place a mail adresse here if you want someone in cc

Some server smtp need a login to send mail, like mailtrap.io
With the 2 next parametres you can precise your login and the password.

login= the hash of the login
password= the hash of the password

### prank.txt

This file contains the prank that will be send to the victims. A prank include a subject and a text each indicate by a balise. Each prank is separate by a separator.

exemple:

subject: // the subject balise
Dad Jokes // the subject
prank: // the text balise
\1. Did you hear about the restaurant on the moon? Great food, no atmosphere.
\2. What do you call a fake noodle? An Impasta.
\3. How many apples grow on a tree? All of them.
\4. Want to hear a joke about paper? Nevermind it's tearable.
\5. I just watched a program about beavers. It was the best dam program I've ever seen.
Xx420xX // the separator
subject:
Dad Jokes
prank:
\1. Why did the coffee file a police report? It got mugged.
\2. How does a penguin build it's house? Igloos it together.
\3. Dad, did you get a haircut? No I got them all cut.
\4. What do you call a Mexican who has lost his car? Carlos.
\5. Dad, can you put my shoes on? No, I don't think they'll fit me

### victims.txt

This file contains the victims of the pranks. The number of victims that will receve the same prank(in the same group) is setable in the conf.properties. The first person in the group is the sender.

exemple:

group of 3:

bob.dilan@gmail.com //sender
henri.des@gmail.com //victim 1
btrvc.fbe@gmail.com //victim 2
ggman@outlook.com //sender of the second group
aa@a.com //victim 1
batman@gmail.com //victim 2
jesus@nazaret.com // need more people to create a third group

### Test with a mock server

You can test the prankclient on a mockserver (mockmock) there are 2 way of doing this:

#### Run the mock server directly

you can run the mock server by executing the MockMock.jar in the bin directory with the command:

    java -jar MockMock.jar -p 25000 -h 8080

then the server will listening smtp request on the port 25000. so you need to set the conf.properties like this:

address=localhost
port=25000

this will send the mail to your localhost on the port 25000

then use the command:

    java -jar PrankClient.jar

this will send the mail to the mock server.

to see the inbox of the server and see if there is any mail that have been send, you can use your browser to see the webpage of the mock server.

enter the url : localhost:8080.

#### Run the mock server using docker

in the dockers directory there is a docker file and a executable of the mockmock server

If you are using docker, then go in this directory and open a terminal.

then enter the command:

    docker build --tag "mockmock" .

This will create a new image that contains the mockmock server.

then if you are using docker-machine you need to run your container like this:

    docker run -p 8080:8080 -p 25000:25000 mockmock

this command run a container and bind is port 8080 and 25000 with the docker-machine, allowing a connection from your machine.

if you are using linux there is no need to bind the port, so you can simply enter the command:

    docker run mockmock

to run the container.

Now you need to know the ip address of your server.

With docker-machine you need to know the docker-machine address. So you need to enter the command:

    docker-machine ip

Otherwise you need to know the container address. Enter this command:

    docker inspect -f '{{.Name}} - {{.NetworkSettings.IPAddress }}' $(docker ps -q)

Now you need to update the conf.properties file.

set the next balise like this:

address= the address of the server or the docker-machine
port=25000

then use the command:

    java -jar PrankClient.jar

this will send the mail to the mock server.

to see the inbox of the server and see if there is any mail that have been send, you can use your browser to see the webpage of the mock server.

enter the url : "the address of the server or the docker-machine":8080.

## References

* [MockMock server](<https://github.com/tweakers/MockMock>) on GitHub
* The [mailtrap](<https://mailtrap.io/>) online service for testing SMTP
* The [SMTP RFC](<https://tools.ietf.org/html/rfc5321#appendix-D>), and in particular the [example scenario](<https://tools.ietf.org/html/rfc5321#appendix-D>)
* Testing SMTP with TLS: `openssl s_client -connect smtp.mailtrap.io:2525 -starttls smtp -crl`
