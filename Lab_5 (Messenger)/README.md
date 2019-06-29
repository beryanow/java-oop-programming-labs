# Information
## Client
Type `/help` to get list of available commands.\
Press `Up arrow key` to scroll through history.
## Server
Logger writes messages in `logs.txt` and `stdout`. 
# Compile and build jar
## Compile jar
```
$ ant jar
```
Jar archive will be built in `build/jar/chat.jar`.
## Run jar
To launch Serializable version of client 
```
$ java -jar build/jar/chat.jar client serial
```
To launch XML version of client
```
$ java -jar build/jar/chat.jar client xml
```
To launch Serializable version of server 
```
$ java -jar build/jar/chat.jar server serial
```
To launch XML version of server 
```
$ java -jar build/jar/chat.jar server xml
```