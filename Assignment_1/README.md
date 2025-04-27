### Machine 1:
    javac *.java

    rmic AddServerImpl

    rmiregistry

    java AddServer

### Machine 2:
    javac *.java

    java AddClient 127.0.0.1 (Server machine's IP address) 5 8
  
