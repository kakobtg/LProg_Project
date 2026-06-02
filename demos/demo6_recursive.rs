/*
 * Demo 6: Recursive Types (Challenge 1)
 * Demonstrates equi-recursive tree structures and match evaluation.
 */

let
    countNodes = |t| 
        match t {
            #nil(_) => 0,
            else => match t {
                // Assuming the pattern match binds the struct parameters to the local scope
                #node(l, v, r) => 1 + countNodes(l) + countNodes(r),
                else => 0
            }
        };
        
    tree = #node(#node(#nil(()), 5, #nil(())), 10, #node(#nil(()), 15, #nil(())))
in
    println!("Total nodes in the tree:");
    println!(countNodes(tree))
;;