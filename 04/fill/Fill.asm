// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm
//CONTRIBUTERS: ANDREW STODDARD AND KENNETH WILSON

// Runs an infinite loop that listens to the keyboard input.
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel;
// the screen should remain fully black as long as the key is pressed.
// When no key is pressed, the program clears the screen, i.e. writes
// "white" in every pixel;
// the screen should remain fully clear as long as no key is pressed.

// Put your code here.
<<<<<<< HEAD
@8191
D=A
@count
M=D
(LOOP_START)
@count
D=M
M=M-1
@16384
A=D+A
M=-1
@count
D=M
@LOOP_START
D;JGT
(PGM_END)
@PGM_END
0;JMP
=======
>>>>>>> 1798f8f66564bcffd3dbe10222000ecf6048d081
