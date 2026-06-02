/*
 * Demo 9: Explicit Aliasing (Challenge 3)
 * Demonstrates the use of '&' to take a reference to a 'mut' variable safely.
 */

let 
    inc = |p| {
        p := !p + 1; 
        ()
    };
    mut a = 2
in
    inc(&a);
    println!("Value of mut a after explicit aliasing via &a:");
    println!(a)
;;