/* 
 * Demo 2: Imperative Fragment (Level 1)
 * Demonstrates loops, mutable state, and closures capturing state (factories).
 */
let 
  make_counter = |start| {
    let count = cell(start) in
      |step| {
        count := !count + step;
        !count
      }
  };
  
  // Create two independent stateful counters
  c1 = make_counter(0);
  c2 = make_counter(100)
in
  println! "--- Counter 1 ---";
  println! c1(5);  // Output: 5
  println! c1(5);  // Output: 10
  
  println! "--- Counter 2 ---";
  println! c2(10); // Output: 110
  println! c2(10); // Output: 120
  
  println! "--- While Loop State ---";
  let total = cell(0); i = cell(1) in
    while !i <= 5 {
      total := !total + !i;
      i := !i + 1
    };
    println! !total // Output: 15
;;