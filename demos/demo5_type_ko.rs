/* KO */
/*
 * Demo 5: Runtime Type Error
 * Demonstrates strict runtime typing. The program runs normally until 
 * the exact moment the type violation occurs.
 */
let 
  valid_math = 10 + 20;
  x = cell(5);
  not_a_cell = 100
in
  println! "--- Execution starts normally ---";
  println! valid_math;
  
  println! "--- Attempting invalid mutation ---";
  // This will crash with InterpreterError because 'not_a_cell' is not a VCell!
  not_a_cell := 50;
  println! "This will never print."
;;