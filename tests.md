
/* tests for L0 step 1 */

let x = 1;
  (x + 1);;

(let x = 1; (x + 1)) * (let x = 2; (x + 3))

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
let z = (let z = 2*y;  z*z);
b || ~ (z < 0)
;;
