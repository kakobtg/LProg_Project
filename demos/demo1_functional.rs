/* 
 * Demo 1: Functional Fragment (Level 0)
 * Demonstrates closures, higher-order functions, currying, and recursion.
 */
let 
  // Curried function: takes x, returns a function that takes y
  add = |x| |y| x + y;
  
  // Higher-order function: takes a function 'f' and applies it twice
  apply_twice = |f| |x| f(f(x));
  
  // Recursive factorial
  fact = |n| if n == 0 { 1 } else { n * fact(n - 1) }
in
  println! "--- Currying ---";
  println! (add(5))(10);              // Output: 15
  
  println! "--- Higher Order ---";
  println! (apply_twice(add(10)))(5); // Output: 25
  
  println! "--- Recursion ---";
  println! fact(6)                    // Output: 720
;;