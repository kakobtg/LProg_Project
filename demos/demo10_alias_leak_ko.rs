/* KO */
/*
 * Demo 10: Memory Leak Prevention (Challenge 3)
 * This code should be rejected by the static typechecker to ensure no memory leaks 
 * or illegal dereferences of dead stack allocations are possible.
 */

let 
    flop = |n| { 
        let mut s = n;
        let p = &s;
        p := !p + s;
        p
    }
in
    println!("This will not print because flop returns an escaped stack reference!");
    println!(!flop(2))
;;