---To run---
Chess.jar is in the lib folder. This single file contains everything necessary to run the program. On Windows you can simply double-click on this to run it, providing that you have the Java runtime environment installed.
To run on Linux:
Make Chess.jar executable: chmod 755 Chess.jar
Then: ./Chess.jar

----To compile----
If you have ant installed, you can compile and run by typing:
ant compile run
You can create an executable jar by typing:
ant jar

If you don't have ant, you can compile by going into the Chess folder and typing:
javac -sourcepath src -d bin src/control/GameController.java
Make sure that the contents of Chess/images are in the Chess/bin folder.
Then to run, type:
java -classpath bin control.GameController


If you wish to develop with this code, I should warn you that I wrote it when I was fairly new to Java and object orientated programming. Thus I have committed sins such as having large classes with too many methods. Also I did not know about the MVC pattern at the time. In the language of MVC, GameController.java is the controller and ChessBoard.java is the model and view.
