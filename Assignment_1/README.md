### Machine 1:
    javac *.java

    rmic AddServerImpl

    rmiregistry

    java AddServer

### Machine 2:
    javac *.java

    java AddClient 127.0.0.1 (Server machine's IP address) 5 8


### For sharing files over different machines:
    scp AddClient.java ubuntu@192.168.1.10:/home/ubuntu/Desktop/

  
