### Terminal 1:
    idlj -fall ReverseModule.idl
    
    javac *.java ReverseModule/*.java
    
    orbd -ORBInitialPort 8000

### Terminal 2:
    java ReverseServer -ORBInitialPort 8000 -ORBInitialHostlocalhost

### Terminal 3:
    java ReverseClient -ORBInitialPort 8000 -ORBInitialHostlocalhost 
