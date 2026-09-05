# ✏️ Edit Distance

A Java algorithmic project that implements the **Edit Distance** problem using a recursive approach.

The program compares two strings and calculates the minimum number of single-character operations required to transform one string into the other.

The implementation also records the selected operations and reconstructs the transformation path, including:

* **Insert**
* **Delete**
* **Replace**
* **No operation** when two characters are equal

The current example compares:

```text
str1 = "sex"
str2 = "six"
```

---

# 📌 Overview

**Edit Distance**, commonly known as **Levenshtein Distance**, measures how many elementary operations are required to transform one string into another.

The standard operations are:

```text
Insertion
Deletion
Replacement
```

For example:

```text
sex
```

can be transformed into:

```text
six
```

by replacing:

```text
e → i
```

Therefore:

```text
Edit Distance = 1
```

This project implements this concept from scratch in Java without using an external edit-distance library.

---

# 🎯 Problem Definition

Given two strings:

```text
String A
String B
```

the goal is to determine the minimum number of operations needed to transform `A` into `B`.

For every pair of characters, there are several possibilities.

### 1. Characters Are Equal

If:

```text
A[i] == B[j]
```

no edit operation is required.

The algorithm moves diagonally:

```text
(i, j) → (i + 1, j + 1)
```

and records:

```text
9
```

as the marker for **No Operation**.

---

### 2. Insert

An insertion adds a character to the first string.

The algorithm evaluates:

```java
1 + EditDistance(str1, str2, i, j + 1)
```

and records:

```text
1
```

for an insertion.

---

### 3. Delete

A deletion removes a character from the first string.

The algorithm evaluates:

```java
1 + EditDistance(str1, str2, i + 1, j)
```

and records:

```text
-1
```

for a deletion.

---

### 4. Replace

A replacement changes one character into another.

The algorithm evaluates:

```java
1 + EditDistance(str1, str2, i + 1, j + 1)
```

and records:

```text
2
```

for a replacement.

---

# 🧠 Recursive Approach

The main algorithm is implemented in:

```java
EditDistance()
```

with the following signature:

```java
private static int EditDistance(
    String str1,
    String str2,
    int i,
    int j
)
```

The parameters `i` and `j` represent the current positions in the two strings.

Conceptually:

```text
                 Compare A[i] and B[j]
                           │
                  ┌────────┴────────┐
                  │                 │
              Equal              Different
                  │                 │
                  ▼                 ▼
             Move Diagonal     Try 3 Operations
                                    │
                    ┌───────────────┼───────────────┐
                    ▼               ▼               ▼
                 Insert          Delete           Replace
                    │               │               │
                    └───────────────┼───────────────┘
                                    ▼
                              Choose Minimum
                                    │
                                    ▼
                                  Return
```

---

# 🔄 Recurrence

When the current characters are different, the algorithm calculates three possible costs.

### Insert

```text
1 + EditDistance(i, j + 1)
```

### Delete

```text
1 + EditDistance(i + 1, j)
```

### Replace

```text
1 + EditDistance(i + 1, j + 1)
```

The minimum of these three values is selected.

Mathematically:

```text
D(i,j) =
    min(
        1 + D(i, j+1),       // Insert
        1 + D(i+1, j),       // Delete
        1 + D(i+1, j+1)     // Replace
    )
```

When the characters match:

```text
D(i,j) = D(i+1,j+1)
```

---

# 🏁 Base Cases

The recursion contains two main termination conditions.

## First String Reached the End

When:

```java
i >= n
```

there are still characters remaining in the second string.

Those remaining characters must be inserted.

The algorithm returns:

```java
m - j
```

---

## Second String Reached the End

When:

```java
j >= m
```

there are still characters remaining in the first string.

Those characters must be deleted.

The algorithm returns:

```java
n - i
```

---

# 🗺️ Operation Tracking

An important part of this implementation is the `r` matrix:

```java
static int[][] r = new int[u][u];
```

This matrix is used to record which operation was selected at each position.

The implementation uses the following markers:

| Marker | Operation                       |
| -----: | ------------------------------- |
|    `9` | Characters match / No operation |
|    `1` | Insert                          |
|   `-1` | Delete                          |
|    `2` | Replace                         |

For example:

```text
r[i][j] = 9
```

means:

```text
Current characters are equal
        ↓
Move diagonally
```

while:

```text
r[i][j] = 2
```

means:

```text
Replace current character
        ↓
Move diagonally
```

---

# 🖨️ Path Reconstruction

The method:

```java
PrintS()
```

is responsible for following the decisions stored in `r`.

Its signature is:

```java
private static void PrintS(
    int[][] r,
    int i,
    int j
)
```

The method interprets each marker and follows the corresponding transition.

### No Operation

```text
9
```

moves to:

```text
(i + 1, j + 1)
```

and prints:

```text
nothing
```

---

### Replace

```text
2
```

moves to:

```text
(i + 1, j + 1)
```

and prints:

```text
Replace
```

---

### Insert

```text
1
```

moves to:

```text
(i, j + 1)
```

and prints:

```text
Insert
```

---

### Delete

```text
-1
```

moves to:

```text
(i + 1, j)
```

and prints:

```text
Delete
```

This allows the program to display the sequence of operations chosen by the recursive algorithm.

---

# 🔍 Example

The project currently initializes:

```java
static String str1 = "sex";
static String str2 = "six";
```

The strings are:

```text
sex
six
```

Comparison:

```text
s = s   → No operation
e ≠ i   → Replace
x = x   → No operation
```

Therefore, the minimum transformation is conceptually:

```text
sex
 ↓
six
```

with:

```text
Replace
```

as the only edit operation.

The resulting edit distance is:

```text
1
```

---

# 📊 Decision Matrix

The program also prints the contents of the `r` matrix:

```java
for (int i = 0; i < u; i++) {
    for (int j = 0; j < u; j++) {
        System.out.print(r[i][j] + " ");
    }
}
```

This makes the internal decisions of the algorithm visible in the console.

The matrix represents the decisions taken while comparing positions of the two strings.

---

# 🧩 Algorithm Flow

The complete process can be summarized as:

```text
             Start
               │
               ▼
          Two Strings
               │
               ▼
       Start at (0, 0)
               │
               ▼
      End of a String?
          /        \
        Yes         No
        │            │
        ▼            ▼
   Handle Base    Compare
     Case         Characters
                      │
              ┌───────┴───────┐
              │               │
            Equal          Different
              │               │
              ▼               ▼
          Move Diagonal   Insert
                           Delete
                           Replace
                              │
                              ▼
                       Choose Minimum
                              │
                              ▼
                       Store Operation
                              │
                              ▼
                         Return Cost
                              │
                              ▼
                      Print Operations
```

---

# 💻 Core Implementation

The central part of the algorithm follows this structure:

```java
// Insert
int q = 1 + EditDistance(str1, str2, i, j + 1);

// Delete
int p = 1 + EditDistance(str1, str2, i + 1, j);

// Replace
int x = 1 + EditDistance(str1, str2, i + 1, j + 1);
```

The minimum value is then selected:

```java
if (p < q & p < x) {
    min = p;
    r[i][j] = -1;
}
else if (q < p & q < x) {
    min = q;
    r[i][j] = 1;
}
else {
    min = x;
    r[i][j] = 2;
}
```

This demonstrates the core decision-making process of the edit-distance algorithm.

---

# 🛠️ Technology Stack

| Technology            | Usage                             |
| --------------------- | --------------------------------- |
| **Java**              | Programming language              |
| **String**            | Input text representation         |
| **2D Array**          | Operation/decision matrix         |
| **Recursion**         | Edit-distance calculation         |
| **Conditional Logic** | Operation selection               |
| **Console Output**    | Displaying results and operations |

---

# 📚 Concepts Demonstrated

This project focuses on fundamental algorithmic concepts.

### Java

* Strings
* Character access with `charAt()`
* Two-dimensional arrays
* Static methods
* Recursion
* Conditional statements
* Loops
* Console output

### Algorithms

* Edit Distance
* Recursive problem decomposition
* Minimum-cost selection
* String comparison
* Operation tracking
* Path reconstruction

---

# ⏱️ Complexity

Let:

```text
n = length of str1
m = length of str2
```

The classic **Levenshtein dynamic-programming solution** can solve the problem in:

```text
O(n × m)
```

However, the current repository implementation is **recursive without memoization**.

When characters differ, it recursively explores up to three subproblems:

```text
Insert
Delete
Replace
```

Because previously calculated states are not cached, the current implementation can have **exponential time complexity in the worst case**.

A practical upper-bound description for the recursive implementation is:

```text
O(3^(n+m))
```

The recursion depth is bounded by approximately:

```text
O(n + m)
```

The `r` matrix itself uses:

```text
O(max(n,m)²)
```

space in the current implementation.

---

# ⚠️ Important Implementation Note

Although the project creates a matrix:

```java
static int[][] r
```

this matrix is **not used as a memoization table for edit-distance costs**.

Instead, it records the selected operation:

```text
9  → No operation
1  → Insert
-1 → Delete
2  → Replace
```

Therefore, this implementation should be described as a:

> **Recursive Edit Distance implementation with operation/path tracking**

rather than a memoized Dynamic Programming implementation.

A future optimized version could store the result of every `(i, j)` state and reuse it.

---

# 🔧 Possible Improvements

Several improvements could make the implementation more efficient and reusable.

* [ ] Add memoization to avoid recalculating the same `(i, j)` states
* [ ] Implement the standard `O(n × m)` dynamic-programming solution
* [ ] Separate the cost matrix from the operation matrix
* [ ] Replace magic markers (`9`, `2`, `1`, `-1`) with named constants or an enum
* [ ] Accept strings from user input
* [ ] Support arbitrary string lengths
* [ ] Add automated test cases
* [ ] Handle empty strings explicitly
* [ ] Improve tie-breaking between operations
* [ ] Make the algorithm reusable through a dedicated class
* [ ] Print the actual character-level transformation
* [ ] Compare recursive and dynamic-programming implementations

---

# 🧪 Suggested Test Cases

The current program uses:

```text
sex
six
```

Additional useful test cases include:

### Identical Strings

```text
kitten
kitten
```

Expected distance:

```text
0
```

### One Replacement

```text
cat
bat
```

Expected distance:

```text
1
```

### One Insertion

```text
cat
cart
```

Expected distance:

```text
1
```

### One Deletion

```text
cart
cat
```

Expected distance:

```text
1
```

### Completely Different Strings

```text
abc
xyz
```

The algorithm must determine the minimum combination of replacements, insertions, and deletions.

---

# 📁 Project Structure

The repository currently has a compact IntelliJ/Java structure:

```text
edit-distance/
│
├── .idea/
│
├── out/
│   └── production/
│       └── EditDistance/
│
├── src/
│   └── Main.java
│
├── EditDistance.iml
│
└── README.md
```

The main implementation is contained in:

```text
src/Main.java
```

The repository currently contains **3 commits**.

---

# 🚀 Getting Started

## Prerequisites

You need:

* Java JDK
* IntelliJ IDEA or another Java IDE

---

## Clone the Repository

```bash
git clone https://github.com/Sobhankhedry/edit-distance.git
```

Navigate into the project:

```bash
cd edit-distance
```

Open the project in IntelliJ IDEA.

Run:

```text
src/Main.java
```

The program will:

1. Calculate the edit distance.
2. Print the result.
3. Print the internal operation matrix.
4. Reconstruct and print the selected operations.

---

# 🎯 Learning Objectives

The main objectives of this project are:

* Understanding the Edit Distance problem
* Practicing recursive algorithms
* Understanding insertion, deletion, and replacement
* Breaking a problem into smaller subproblems
* Comparing multiple possible operations
* Selecting the minimum-cost operation
* Recording algorithmic decisions
* Reconstructing an operation sequence
* Understanding the difference between recursion and memoized dynamic programming

---

# 📌 Project Status

**Status:** Educational / Algorithmic Project

This repository is a compact implementation of the Edit Distance problem in Java.

The main focus is on understanding the recursive formulation of the problem and tracking the operations required to transform one string into another.

It is primarily intended as an **algorithmic learning exercise**, rather than a production-ready string-processing library.

---

# 👨‍💻 Author

**Sobhan Khedry**

Computer Engineering Graduate Student
Backend Development Enthusiast

GitHub: [@Sobhankhedry](https://github.com/Sobhankhedry)

---

# ⭐ Key Takeaways

The project demonstrates how the Edit Distance problem can be decomposed into three possible operations:

```text
                 Different Characters
                         │
          ┌──────────────┼──────────────┐
          ▼              ▼              ▼
       Insert          Delete         Replace
          │              │              │
          └──────────────┼──────────────┘
                         ▼
                  Choose Minimum
                         │
                         ▼
                 Store Operation
                         │
                         ▼
                Reconstruct Path
```

For matching characters:

```text
Character Match
      │
      ▼
No Operation
      │
      ▼
Move Diagonally
```

The repository provides a straightforward implementation for understanding the core mechanics of **Levenshtein Edit Distance, recursive optimization, operation selection, and path reconstruction**.
