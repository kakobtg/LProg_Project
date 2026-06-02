/*
 * Demo 8: Heap vs Stack Cells (Challenge 2)
 * Demonstrates a well-typed program using both heap (cell) and stack (mut) allocated cells safely.
 */

let 
    global = cell(10);
    side = |l| {
        let mut a = !l;
        let mut s = 0;
        while (a >= 0) {
            s := s + a;
            a := a - 1
        };
        l := s;
        ()
    }
in
    side(global);
    println!("Global heap cell after being mutated by stack calculation:");
    println!(!global)
;;