/* KO */
/*
 * Demo 4: The Challenge 
 * Demonstrates the static check blocking unshielded mutual recursion.
 * The compiler will abort BEFORE running any code.
 */
let
  safe_val = 100;
  
  // Safe because it's shielded by |n|
  safe_func = |n| if n == 0 { 1 } else { safe_func(n-1) };
  
  // UNSAFE: Mutual cycle directly accessible in the expressions
  ping = pong + 1;
  pong = ping * 2
in
  println! "This will never print because the compiler catches the cycle!";
  println! safe_val
;;