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

(LOOP)
@24576
D = M
@LOOP
D; JEQ
@8192
D = A
@count
M = D
(BLACK_LOOP)
@count
D = M
M = M-1
@16383
A = D + A
M = -1
@count
D = M
@BLACK_LOOP
D; JGT
(BLACK_PGM_END)
@24576
D = M
@WHITE
D; JEQ
@BLACK_PGM_END
0; JEQ
(WHITE)
@8192
D = A
@count
M = D
(WHITE_LOOP)
@count
D = M
M = M - 1
@16383
A = D + A
M = 0
@count
D = M
@WHITE_LOOP
D; JGT
(WHITE_PGM_END)
@24576
D = M
@LOOP
D; JEQ
@WHITE_PGM_END
0; JEQ