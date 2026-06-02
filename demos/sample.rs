let m = cell(9);
let v = !m;
v+1;;

let m = cell(0);
m := !m + 1;
m := !m + 1;
m := !m + 1;
m := !m + 1;
m := !m + 1;
!m
;;

let m = cell(9);
let v = !m;
m := v + 1;
v
;;

let m = cell(9);
let v = !m;
m := v + 1;
!m
;;

let c = 0;
let L = 1000;
let m = cell(c);
while (!m>0) {
    m := !m - 1
};
!m
;;

let c = 0;
let L = 1000;
let m = cell(c);
while (!m>0) {
    m := !m - 1
};
!m
;;

/* fails 
type Money = int;
let c:Money = 0;
type Money = bool;
let L:Money = 1000;
let m:cell(int) = cell(c);
while (!m>0) {
    m := !m - 1
};
!m
;;
*/

let c = 0;
let L = 100;
let m = cell(L);
let S = cell(c);
while (!m>0) {
    m := !m - 1;
    S := !S + !m
};
!S
;;

let L = 8;
let m = cell(L);
let fp = cell(0);
let fnxt = cell(1);
while (!m>0) {
    m := !m - 1;
    {
        let t = !fnxt;
        fnxt := !fnxt + !fp;
        fp := t
    }
};
!fnxt
;;


let sigfpe = cell ( |x|x );
let setfpe = |h| { sigfpe := h };
let div = |n| |m| {
      if (m==0) { (!sigfpe) (n) }
      else { n / m }
};
setfpe (|v| { -1 });
(div(4))(2)
;;

let sigfpe = cell ( |x|x );
let setfpe = |h| { sigfpe := h };
let div = |n| |m| {
      if (m==0) { (!sigfpe) (n) }
      else { n / m }
};
setfpe (|v| { -1 });
(div(4))(0)
;;

let knot = cell (|x| {x});
let fact = |n| {
      if (n==0) { 1 }
      else { n * ((!knot)(n - 1)) }
};
knot := fact;
fact (6)
;;

let mkpair =
    |a| |b| { 
        let l = cell(a);
        let r = cell(b);
        |f| { (f(l))(r) }
}; 
let getfst = |p|
    { !(p(|a| |b| { a })) };
let setfst = |p| |v|
    { let target = p(|a| |b| { a }); target := v; () };
let getsnd = |p|
    { !(p(|a| |b| { b })) };
let setsnd = |p| |v|
    { let target = p(|a| |b| { b }); target := v; () };
let x = (mkpair(1))(2);
(setfst(x))(10); 
(setsnd(x))(20); 
(getfst(x)) + (getsnd(x))
;;

let l0 = #none(());
l0;;

let l0 = #some(2);
l0;;

let l0 = #none(());
match l0 {
    #none(x) => 1,
    else => match l0 {
        #some(i) => i,
        else => 1
    }
};;

let l0 = #none(());
match l0 {
    #none(x) => 1,
    else => match l0 {
        #some(i) => i,
        else => 1
    }
};;

let l0 = #none(());
match l0 {
    #none(x) => #some(2),
    else => match l0 {
        #some(i) => #none(()),
        else => #none(())
    }
};;

let flop = |o| {
    match o {
        #none(_) => #some(2),
        else => match o {
            #some(i) => #none(()),
            else => #none(())
        }
    }
};
flop (#none(()))
;;

/* this ones fail to typecheck:

type Opt = enum { #none:(()), #some:(int)};
let l0:Opt = #some(42);
match l0 {
    #none(x) => 1
|   #some(i) => (i && true)  
};;

type Opt = enum { #none:(()), #some:(int)};
let l0:Opt = #some(42);
match l0 {
    #none(x) => 1
|   #some(i) => (i > 2)  
};;

type Opt = enum { #none:(()), #some:(int)};
let l0:Opt = #some(42);
match l0 {
    #none(x) => 1
};;

type Opt = enum { #none:(()), #some:(int)};
let l0:Opt = #some(42);
match l0 {
    #some(x) => x + 1
};;

*/

let hasnext = 
    |l| { match l {
            #CONS(x, r) => true,
            else => false
        }};
hasnext (#NIL(()));;

let hasnext = 
    |l| { match l {
            #CONS(x, r) => true,
            else => false
        }};
let l0 = #CONS(2,#NIL(()));
hasnext (l0);;

/* recursive types */

let Zero = #Z(());
let One = #S(Zero);
let Two = #S(One);
let int2Nat = |n| {
    if (n==0) { #Z(()) } else {#S(int2Nat (n-1))}
};
let add = |n| |m| {
            match n {
                #Z(u) => m,
                else => match n {
                            #S(n0) => #S((add(n0))(m)),
                    else => m
                }
            }
    };
(add(int2Nat(4)))(int2Nat(6));;

let hasnext = 
    |l| { match l {
            #CONS(x,r) => true,
            else => false
        }};

let printlist =
    |l| {
        let l0 = cell(l);
        println!("[");
        while (hasnext(!l0)) {
            match !l0 {
                #CONS(p, r) => {
                    println!(p);         
                    if hasnext(r) {
                        println!(", ");
                        l0 := r;
                        ()
                    } else {
                        println!("]");
                        l0 := r;
                        ()
                    }
                },
                else => ()
            }
        };
        ()
    };

let primes = cell(#NIL(()));

let checkprime =
    |cp| |lp| {
        match lp {
        #NIL(_) => true,
        else => match lp {
            #CONS(p, r) =>
                    if (cp == p*(cp/p))
                        { false }
                            else { (checkprime(cp))(r) },
            else => true
        }
        }
    };

let primesieve =
    |n| { 
          let c = cell(2);
          while (!c < n) {
             let cp = !c;
             if (checkprime(cp))(!primes) {
                primes := #CONS(cp, !primes);
                ()
             } else { () };
             c := cp + 1
          }
    };

primesieve(1000);
printlist(!primes)
;;




/* mut */

let mut x = 10;
x+x;;

let mut x = 10;
x := x + 1;;


let mut x = 10;
let mut y = 5;
x+y;;

let mut x = 10;
let mut y = 5;
x := x + 1;
y := x + y;
x+y;;

/* examples from above, now with mutable lets */
let L = 8;
let mut m = L;
let mut fp = 0;
let mut fnxt = 1;
while (m>0) {
    m := m - 1;
    {
        let t = fnxt;
        fnxt := fnxt + fp;
        fp := t
    }
};
fnxt
;;


/* panic 

let mut x:int = 10;
let f = { let mut p = 0; |z:int| { p + z } };
f (2);; */
