# Simple-BlackJack
A Simple OOD BlackJack written in Java

## How to Run

Two ways of running this code. First you could run it from the CLI

```shell
# Navgate to the src/ and compile, run.
$ javac BlackJack.java
$ java BlackJack
```

We have tested under:

```shell
openjdk 11.0.3 2019-04-16
OpenJDK Runtime Environment AdoptOpenJDK (build 11.0.3+7)
OpenJDK 64-Bit Server VM AdoptOpenJDK (build 11.0.3+7, mixed mode)

$ javac --version
javac 11.0.3
```

Note: It does not have to be in Java 11. We have developed this game under Java 8 using IntelliJ. The command line
uses a JDK 11 but it should also work for Java 8.

Alternatively, you could use IntelliJ and make sure setup the project correctly:

1. This game is developed via using IntelliJ and Java SDK 1.8 (openJDK)
2. Open the source folder using IntelliJ
3. Click on the file on the menu and select "Project Structure"
4. In the project settings, make sure to use JAVA SDK 1.8, and select the language level to 8.
5. In order to compile the code, you need to have a compile output folder. Normally it could be a folder named out in the same level as src.
6. So create a new folder called out and make sure it is excluded in the project structure
7. Next, click on Run on the menu and try to edit the configuration for running the program
8. Choose Application from the template and make sure you use BlackJack as the main class.
9. Run the code and enjoy the game!

## Guide

Please make sure you only type numbers into the console when asking for inputs.
Input validation is not a focus on this project therefore we have focused on other features in this game.

## Authors

* Fuqing Wang
* Hang Xu

