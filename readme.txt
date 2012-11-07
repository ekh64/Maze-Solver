2012 Update:
Application needs to be compiled and run from the command prompt
$ cd [directory containing files]
$ javac Maze.java
$ java Maze

Erica Hyman
ekhyman@gmail.com
April 21, 2008

CSC 172 Project 3:

Description: Nothing is more fun than the simple childhood pastime of solving mazes.
	What could be better than a never ending supply of mazes, guaranteed solvable, saveable
	and 100% enjoyable.
	
This java applet asks for a
width and height and generates a maze of that dimension, completely random, generated using
disjoint sets to ensure the mazes are solvable.
Features:
	- Takes in a user-defined width and height
	- Ability to read a saved maze from a file and reproduce it
	* EXTRA: Mazes displayed in an applet for the best possible maze-solving experience
	
Included Files:
	Maze.java - The main source code, including the user interface and applet code.
	DisjSets.java - The Disjoint Sets class, which is the backbone of the maze generation