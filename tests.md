
/* tests for L0 step 1 */

let x = 1;
  (x + 1);;

(let x = 1; (x + 1)) * (let x = 2; (x + 3))
;;

let x = 1;
  let y = 2;
  (x + y)
;;

let x = 2;
let z = x+2;
let k = (let x = z+2; x*x);
k+k;;

let y = 1;
let b = (y > 0) && (y <= 20);
let z = (let z_inner = 2*y;  z_inner*z_inner);
b || ((z < 0) == false)
;;

/* tests for Phase 2: Static Type Checker Edge Cases */

/* 1. Match Branch Unification Mismatch (Should fail statically) */
let l0 = #some(42);
match l0 {
    #some(x) => x + 1,
    else => false
}
;;

/* 2. L-Value Assignment Type Mismatch (Should fail statically) */
let mut counter = 10;
counter := counter + 1;
counter := true;
counter
;;

/* 3. Nested Scope Memory Leak Violation (Should fail statically) */
let safe_val = 10;
let escaping_ref = {
    let mut local_var = 99;
    &local_var
};
!escaping_ref
;;
