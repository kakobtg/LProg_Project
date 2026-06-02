/*
 * Demo 7: Stack Allocated Mutable Variables (Challenge 2)
 * Demonstrates 'let mut', L-value / R-value mode, and implicit dereferencing.
 */

let 
    main = |l| {
        let mut a = l;
        let mut s = 0;
        while (a >= 0) {
            s := s + a;
            a := a - 1
        };
        s
    }
in
    println!("Result of loop with stack-allocated muts:");
    println!(main(10))
;;