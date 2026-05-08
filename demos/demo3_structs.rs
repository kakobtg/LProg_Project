/* 
 * Demo 3: Struct Tuples (Level 2)
 * Demonstrates recursive pattern matching over a custom binary tree.
 */
let evaluate_tree = |node| {
  match node {
    #Add(left, right) => evaluate_tree(left) + evaluate_tree(right),
    else => match node {
      #Mul(left, right) => evaluate_tree(left) * evaluate_tree(right),
      else => match node {
        #Val(v) => v,
        else => 0
      }
    }
  }
} in
  // Represents the math equation: (5 * 2) + (10 * 3)
  let my_tree = #Add(
    #Mul(#Val(5), #Val(2)), 
    #Mul(#Val(10), #Val(3))
  ) in
    println! "--- Tree Evaluation ---";
    println! evaluate_tree(my_tree) // Should output 40
;;