# Infix to Postfix & Prefix Converter

## Description
A Java program that converts **Infix expressions** into **Postfix (RPN)** and **Prefix (Polish Notation)** using a Stack-based approach.

## Files
| File | Description |
|------|-------------|
| `InfixConverter.java` | Main Java program |
| `PSEUDOCODE.txt` | Step-by-step pseudocode for both algorithms |
| `WORKFLOW.png` | Visual workflow diagram |

## How to Run

### Compile
```bash
javac InfixConverter.java
```

### Run
```bash
java InfixConverter
```

## Sample Output
```
Infix    : (A+B)*C
Postfix  : A B + C *
Prefix   : * + A B C

Infix    : A+B*C
Postfix  : A B C * +
Prefix   : + A * B C
```

## Algorithms Used
- **Infix → Postfix**: Dijkstra's Shunting-Yard Algorithm
- **Infix → Prefix**: Reverse + Postfix + Reverse strategy

## Supported Operators
`+`  `-`  `*`  `/`  `^`  and parentheses `( )`
