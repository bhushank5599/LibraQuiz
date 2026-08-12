import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axiosClient from '../../api/axiosClient';
import { Clock, CheckCircle2, XCircle, AlertTriangle, ShieldCheck, ArrowLeft, RefreshCw, Bot, Sparkles, BrainCircuit } from 'lucide-react';

const TakeQuizPage = () => {
  const { quizId } = useParams();
  const navigate = useNavigate();

  const [quiz, setQuiz] = useState(null);
  const [questions, setQuestions] = useState([]);
  const [selectedAnswers, setSelectedAnswers] = useState({}); // { questionIdx: selectedOptionIdx }
  const [timeLeft, setTimeLeft] = useState(1800); // Default 30 mins
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [submittedResult, setSubmittedResult] = useState(null);
  const [isLoading, setIsLoading] = useState(true);

  // Complete 100 MCQ Question Pool to ensure questions ALWAYS render
  const questionPool = [
    // Java (20 Questions)
    { id: 1, questionText: "What is the default value of a boolean primitive variable in Java?", topic: "Java", difficultyLevel: "EASY", options: [{ text: "true", isCorrect: false }, { text: "false", isCorrect: true }, { text: "null", isCorrect: false }, { text: "0", isCorrect: false }] },
    { id: 2, questionText: "Which keyword is used to prevent a class from being subclassed in Java?", topic: "Java", difficultyLevel: "EASY", options: [{ text: "static", isCorrect: false }, { text: "abstract", isCorrect: false }, { text: "final", isCorrect: true }, { text: "private", isCorrect: false }] },
    { id: 3, questionText: "Which package is automatically imported in every Java program?", topic: "Java", difficultyLevel: "EASY", options: [{ text: "java.util", isCorrect: false }, { text: "java.io", isCorrect: false }, { text: "java.net", isCorrect: false }, { text: "java.lang", isCorrect: true }] },
    { id: 4, questionText: "What is the size of an int variable in Java?", topic: "Java", difficultyLevel: "EASY", options: [{ text: "4 Bytes (32 bits)", isCorrect: true }, { text: "2 Bytes", isCorrect: false }, { text: "8 Bytes", isCorrect: false }, { text: "1 Byte", isCorrect: false }] },
    { id: 5, questionText: "Which method is the entry point for execution in any standalone Java application?", topic: "Java", difficultyLevel: "EASY", options: [{ text: "public void start()", isCorrect: false }, { text: "public static void main(String[] args)", isCorrect: true }, { text: "public void main()", isCorrect: false }, { text: "static void run()", isCorrect: false }] },
    { id: 6, questionText: "What is the average time complexity of HashMap.get(key) operation in Java?", topic: "Java", difficultyLevel: "MEDIUM", options: [{ text: "O(n)", isCorrect: false }, { text: "O(log n)", isCorrect: false }, { text: "O(1)", isCorrect: true }, { text: "O(n^2)", isCorrect: false }] },
    { id: 7, questionText: "Which Garbage Collector is used as default in Java 17 LTS?", topic: "Java", difficultyLevel: "MEDIUM", options: [{ text: "G1 Garbage Collector", isCorrect: true }, { text: "ZGC", isCorrect: false }, { text: "Parallel GC", isCorrect: false }, { text: "CMS GC", isCorrect: false }] },
    { id: 8, questionText: "What happens if a thread calls Thread.sleep() inside a synchronized block?", topic: "Java", difficultyLevel: "MEDIUM", options: [{ text: "It releases the monitor lock", isCorrect: false }, { text: "It throws InterruptedException immediately", isCorrect: false }, { text: "It sleeps while keeping the monitor lock held", isCorrect: true }, { text: "JVM crashes", isCorrect: false }] },
    { id: 9, questionText: "Which interface does java.util.TreeSet implement to maintain sorted element order?", topic: "Java", difficultyLevel: "MEDIUM", options: [{ text: "java.util.List", isCorrect: false }, { text: "java.util.Queue", isCorrect: false }, { text: "java.util.RandomAccess", isCorrect: false }, { text: "java.util.NavigableSet", isCorrect: true }] },
    { id: 10, questionText: "What happens if an abstract class does not have any abstract methods?", topic: "Java", difficultyLevel: "MEDIUM", options: [{ text: "It will not compile", isCorrect: false }, { text: "The class can still be abstract", isCorrect: true }, { text: "Java auto-adds abstract method", isCorrect: false }, { text: "It becomes a final class", isCorrect: false }] },
    { id: 11, questionText: "Which class in java.util.concurrent provides atomic operations without lock overhead?", topic: "Java", difficultyLevel: "MEDIUM", options: [{ text: "AtomicInteger", isCorrect: true }, { text: "VolatileInt", isCorrect: false }, { text: "SynchronizedInteger", isCorrect: false }, { text: "LockedInt", isCorrect: false }] },
    { id: 12, questionText: "Which annotation in JPA is used to specify a primary key auto-generation strategy?", topic: "Java", difficultyLevel: "MEDIUM", options: [{ text: "Id", isCorrect: false }, { text: "Column", isCorrect: false }, { text: "GeneratedValue", isCorrect: true }, { text: "Entity", isCorrect: false }] },
    { id: 13, questionText: "In Java memory management, where are String Literals stored starting from Java 7/8?", topic: "Java", difficultyLevel: "HARD", options: [{ text: "PermGen Space", isCorrect: false }, { text: "Inside the Java Heap Space", isCorrect: true }, { text: "Native Stack Memory", isCorrect: false }, { text: "Code Cache", isCorrect: false }] },
    { id: 14, questionText: "Which Java 17 feature introduces immutable data-carrier classes with implicit constructors and getters?", topic: "Java", difficultyLevel: "HARD", options: [{ text: "Sealed Classes", isCorrect: false }, { text: "Pattern Matching", isCorrect: false }, { text: "Records", isCorrect: true }, { text: "Text Blocks", isCorrect: false }] },
    { id: 15, questionText: "What is the primary effect of the volatile keyword on a variable in Java multithreading?", topic: "Java", difficultyLevel: "HARD", options: [{ text: "Ensures atomic increments", isCorrect: false }, { text: "Synchronizes method access", isCorrect: false }, { text: "Prevents object mutation", isCorrect: false }, { text: "Guarantees main memory visibility and prevents instruction reordering", isCorrect: true }] },
    { id: 16, questionText: "What is the time complexity of Arrays.sort() for primitive arrays in Java (Dual-Pivot Quicksort)?", topic: "Java", difficultyLevel: "HARD", options: [{ text: "O(n log n)", isCorrect: true }, { text: "O(n^2)", isCorrect: false }, { text: "O(n)", isCorrect: false }, { text: "O(log n)", isCorrect: false }] },
    { id: 17, questionText: "Which functional interface in java.util.function takes one input argument and returns a boolean?", topic: "Java", difficultyLevel: "HARD", options: [{ text: "Function<T,R>", isCorrect: false }, { text: "Predicate<T>", isCorrect: true }, { text: "Consumer<T>", isCorrect: false }, { text: "Supplier<T>", isCorrect: false }] },
    { id: 18, questionText: "In Spring Framework, what is the default bean scope of a Spring-managed component?", topic: "Java", difficultyLevel: "HARD", options: [{ text: "Prototype", isCorrect: false }, { text: "Request", isCorrect: false }, { text: "Singleton", isCorrect: true }, { text: "Session", isCorrect: false }] },
    { id: 19, questionText: "Which Java feature introduced in Java 11 allows running a single-file source code without compiling?", topic: "Java", difficultyLevel: "HARD", options: [{ text: "JShell", isCorrect: false }, { text: "Var syntax", isCorrect: false }, { text: "Single-File Source-Code Execution", isCorrect: true }, { text: "Module System", isCorrect: false }] },
    { id: 20, questionText: "What exception is thrown when an application attempts to use null where an object is required?", topic: "Java", difficultyLevel: "HARD", options: [{ text: "IllegalArgumentException", isCorrect: false }, { text: "ClassCastException", isCorrect: false }, { text: "IllegalStateException", isCorrect: false }, { text: "NullPointerException", isCorrect: true }] },

    // .NET (20 Questions)
    { id: 21, questionText: "Which runtime engine executes compiled C# MSIL code in the .NET framework?", topic: ".NET", difficultyLevel: "EASY", options: [{ text: "JVM", isCorrect: false }, { text: "CLR (Common Language Runtime)", isCorrect: true }, { text: "V8 Engine", isCorrect: false }, { text: "KRE", isCorrect: false }] },
    { id: 22, questionText: "What keyword is used to declare an asynchronous method in C#?", topic: ".NET", difficultyLevel: "EASY", options: [{ text: "await", isCorrect: false }, { text: "thread", isCorrect: false }, { text: "async", isCorrect: true }, { text: "task", isCorrect: false }] },
    { id: 23, questionText: "Which memory management mechanism is used in .NET for automatic object cleanup?", topic: ".NET", difficultyLevel: "EASY", options: [{ text: "Garbage Collector (GC)", isCorrect: true }, { text: "Manual Free Memory", isCorrect: false }, { text: "Reference Counting", isCorrect: false }, { text: "RAII", isCorrect: false }] },
    { id: 24, questionText: "Which tool is used for managing dependencies and packages in .NET projects?", topic: ".NET", difficultyLevel: "EASY", options: [{ text: "npm", isCorrect: false }, { text: "pip", isCorrect: false }, { text: "Maven", isCorrect: false }, { text: "NuGet", isCorrect: true }] },
    { id: 25, questionText: "What is the extension of C# source code files?", topic: ".NET", difficultyLevel: "EASY", options: [{ text: ".cs", isCorrect: true }, { text: ".cpp", isCorrect: false }, { text: ".java", isCorrect: false }, { text: ".net", isCorrect: false }] },
    { id: 26, questionText: "What is the lifetime of a dependency registered with builder.Services.AddScoped<T>() in ASP.NET Core?", topic: ".NET", difficultyLevel: "MEDIUM", options: [{ text: "Single instance across application", isCorrect: false }, { text: "One instance created per HTTP Request", isCorrect: true }, { text: "New instance every time requested", isCorrect: false }, { text: "One instance per thread", isCorrect: false }] },
    { id: 27, questionText: "What is the primary difference between IEnumerable<T> and IQueryable<T> in Entity Framework Core?", topic: ".NET", difficultyLevel: "MEDIUM", options: [{ text: "IEnumerable executes queries on SQL Server", isCorrect: false }, { text: "IQueryable cannot filter data", isCorrect: false }, { text: "IQueryable executes SQL queries server-side on database while IEnumerable evaluates in-memory", isCorrect: true }, { text: "They behave identically", isCorrect: false }] },
    { id: 28, questionText: "What is the role of Middleware components in an ASP.NET Core HTTP request pipeline?", topic: ".NET", difficultyLevel: "MEDIUM", options: [{ text: "To inspect, process, or short-circuit HTTP requests and responses", isCorrect: true }, { text: "To compile C# code into IL", isCorrect: false }, { text: "To execute database schema migrations", isCorrect: false }, { text: "To compress static images", isCorrect: false }] },
    { id: 29, questionText: "Which syntax enables value types (structs) to represent null values in C#?", topic: ".NET", difficultyLevel: "MEDIUM", options: [{ text: "var keyword", isCorrect: false }, { text: "ref modifier", isCorrect: false }, { text: "out parameter", isCorrect: false }, { text: "Nullable<T> or T? operator", isCorrect: true }] },
    { id: 30, questionText: "Which Entity Framework Core command applies pending migrations to the configured database?", topic: ".NET", difficultyLevel: "MEDIUM", options: [{ text: "dotnet ef database update", isCorrect: true }, { text: "dotnet ef migrations add", isCorrect: false }, { text: "dotnet ef schema apply", isCorrect: false }, { text: "dotnet ef build", isCorrect: false }] },
    { id: 31, questionText: "What type of class member is shared across all instances of a class in C#?", topic: ".NET", difficultyLevel: "MEDIUM", options: [{ text: "virtual", isCorrect: false }, { text: "static", isCorrect: true }, { text: "override", isCorrect: false }, { text: "sealed", isCorrect: false }] },
    { id: 32, questionText: "Which data structure in C# represents a key-value collection with fast lookup capability?", topic: ".NET", difficultyLevel: "MEDIUM", options: [{ text: "List<T>", isCorrect: false }, { text: "Queue<T>", isCorrect: false }, { text: "Dictionary<TKey, TValue>", isCorrect: true }, { text: "HashSet<T>", isCorrect: false }] },
    { id: 33, questionText: "Which C# 10 feature allows defining namespaces for an entire file without enclosing curly braces?", topic: ".NET", difficultyLevel: "HARD", options: [{ text: "Global using directives", isCorrect: false }, { text: "Top-level statements", isCorrect: false }, { text: "Record structs", isCorrect: false }, { text: "File-scoped namespace declarations", isCorrect: true }] },
    { id: 34, questionText: "In .NET Garbage Collector heap layout, which segment handles object allocations larger than 85,000 bytes?", topic: ".NET", difficultyLevel: "HARD", options: [{ text: "Large Object Heap (LOH)", isCorrect: true }, { text: "Generation 0", isCorrect: false }, { text: "Generation 1", isCorrect: false }, { text: "Ephemeral Segment", isCorrect: false }] },
    { id: 35, questionText: "How does Kestrel web server function in a production ASP.NET Core application deployment?", topic: ".NET", difficultyLevel: "HARD", options: [{ text: "As an internal SQL optimizer", isCorrect: false }, { text: "As an in-process, cross-platform HTTP web server often reverse-proxied behind Nginx or IIS", isCorrect: true }, { text: "As a static asset bundler", isCorrect: false }, { text: "As a background worker", isCorrect: false }] },
    { id: 36, questionText: "What C# feature enables adding new methods to existing types without modifying their original source code?", topic: ".NET", difficultyLevel: "HARD", options: [{ text: "Partial classes", isCorrect: false }, { text: "Operator overloading", isCorrect: false }, { text: "Extension Methods", isCorrect: true }, { text: "Abstract methods", isCorrect: false }] },
    { id: 37, questionText: "Which pattern is built natively into ASP.NET Core for resolving component dependencies automatically?", topic: ".NET", difficultyLevel: "HARD", options: [{ text: "Singleton Pattern", isCorrect: false }, { text: "Factory Pattern", isCorrect: false }, { text: "Observer Pattern", isCorrect: false }, { text: "Dependency Injection (DI)", isCorrect: true }] },
    { id: 38, questionText: "In C#, what is the difference between Task.Run() and Task.Factory.StartNew()?", topic: ".NET", difficultyLevel: "HARD", options: [{ text: "Task.Run is a lightweight shortcut configured with default task scheduler settings", isCorrect: true }, { text: "Task.Run starts OS threads directly", isCorrect: false }, { text: "Task.Factory.StartNew cannot run async code", isCorrect: false }, { text: "They are exact aliases", isCorrect: false }] },
    { id: 39, questionText: "Which keyword in C# ensures that unmanaged resources are disposed of automatically when leaving scope?", topic: ".NET", difficultyLevel: "HARD", options: [{ text: "lock", isCorrect: false }, { text: "using", isCorrect: true }, { text: "fixed", isCorrect: false }, { text: "try-finally", isCorrect: false }] },
    { id: 40, questionText: "What is the C# keyword used to pass an argument by reference where the called method MUST assign a value?", topic: ".NET", difficultyLevel: "HARD", options: [{ text: "ref", isCorrect: false }, { text: "in", isCorrect: false }, { text: "out", isCorrect: true }, { text: "params", isCorrect: false }] },

    // DSA (20 Questions)
    { id: 41, questionText: "Which data structure operates strictly on a Last-In, First-Out (LIFO) access order?", topic: "DSA", difficultyLevel: "EASY", options: [{ text: "Queue", isCorrect: false }, { text: "Linked List", isCorrect: false }, { text: "Stack", isCorrect: true }, { text: "Heap", isCorrect: false }] },
    { id: 42, questionText: "What is the worst-case time complexity of Binary Search on a sorted array of size n?", topic: "DSA", difficultyLevel: "EASY", options: [{ text: "O(n)", isCorrect: false }, { text: "O(1)", isCorrect: false }, { text: "O(n log n)", isCorrect: false }, { text: "O(log n)", isCorrect: true }] },
    { id: 43, questionText: "Which tree traversal method visits nodes of a Binary Search Tree (BST) in ascending sorted order?", topic: "DSA", difficultyLevel: "EASY", options: [{ text: "In-order Traversal", isCorrect: true }, { text: "Pre-order Traversal", isCorrect: false }, { text: "Post-order Traversal", isCorrect: false }, { text: "Level-order Traversal", isCorrect: false }] },
    { id: 44, questionText: "Which data structure uses First-In, First-Out (FIFO) queueing discipline?", topic: "DSA", difficultyLevel: "EASY", options: [{ text: "Stack", isCorrect: false }, { text: "Queue", isCorrect: true }, { text: "Binary Tree", isCorrect: false }, { text: "Graph", isCorrect: false }] },
    { id: 45, questionText: "What is the time complexity of accessing an element in an array by index?", topic: "DSA", difficultyLevel: "EASY", options: [{ text: "O(n)", isCorrect: false }, { text: "O(log n)", isCorrect: false }, { text: "O(1)", isCorrect: true }, { text: "O(n^2)", isCorrect: false }] },
    { id: 46, questionText: "What is the worst-case time complexity of QuickSort algorithm?", topic: "DSA", difficultyLevel: "MEDIUM", options: [{ text: "O(n log n)", isCorrect: false }, { text: "O(n)", isCorrect: false }, { text: "O(log n)", isCorrect: false }, { text: "O(n^2)", isCorrect: true }] },
    { id: 47, questionText: "Which graph traversal algorithm utilizes a Queue data structure for node processing?", topic: "DSA", difficultyLevel: "MEDIUM", options: [{ text: "Breadth-First Search (BFS)", isCorrect: true }, { text: "Depth-First Search (DFS)", isCorrect: false }, { text: "Dijkstra's Algorithm", isCorrect: false }, { text: "Kruskal's Algorithm", isCorrect: false }] },
    { id: 48, questionText: "Which data structure provides efficient O(1) peek and O(log n) insertion for Priority Queue implementation?", topic: "DSA", difficultyLevel: "MEDIUM", options: [{ text: "Unsorted Array", isCorrect: false }, { text: "Binary Heap", isCorrect: true }, { text: "Singly Linked List", isCorrect: false }, { text: "Circular Queue", isCorrect: false }] },
    { id: 49, questionText: "What is the maximum number of nodes in a binary tree of height h (assuming root is at height 1)?", topic: "DSA", difficultyLevel: "MEDIUM", options: [{ text: "2^h", isCorrect: false }, { text: "2^(h-1)", isCorrect: false }, { text: "2^h - 1", isCorrect: true }, { text: "h^2", isCorrect: false }] },
    { id: 50, questionText: "Which data structure is best suited to check for balanced parentheses in an expression string?", topic: "DSA", difficultyLevel: "MEDIUM", options: [{ text: "Array", isCorrect: false }, { text: "Queue", isCorrect: false }, { text: "Hash Table", isCorrect: false }, { text: "Stack", isCorrect: true }] },
    { id: 51, questionText: "What is the best-case time complexity of Bubble Sort when applied to an already sorted array?", topic: "DSA", difficultyLevel: "MEDIUM", options: [{ text: "O(n)", isCorrect: true }, { text: "O(n^2)", isCorrect: false }, { text: "O(log n)", isCorrect: false }, { text: "O(n log n)", isCorrect: false }] },
    { id: 52, questionText: "In a circular linked list, what node does the last node's next pointer point to?", topic: "DSA", difficultyLevel: "MEDIUM", options: [{ text: "null", isCorrect: false }, { text: "First Node (Head)", isCorrect: true }, { text: "Previous Node", isCorrect: false }, { text: "Itself", isCorrect: false }] },
    { id: 53, questionText: "Which algorithm finds the shortest path from a single source vertex in a weighted graph with negative edge weights?", topic: "DSA", difficultyLevel: "HARD", options: [{ text: "Dijkstra's Algorithm", isCorrect: false }, { text: "Floyd-Warshall Algorithm", isCorrect: false }, { text: "Bellman-Ford Algorithm", isCorrect: true }, { text: "Prim's Algorithm", isCorrect: false }] },
    { id: 54, questionText: "How is the Balance Factor of a node calculated in an AVL Tree?", topic: "DSA", difficultyLevel: "HARD", options: [{ text: "Count(Left Nodes) - Count(Right Nodes)", isCorrect: false }, { text: "Depth(Node) - Height(Node)", isCorrect: false }, { text: "Max(Left, Right)", isCorrect: false }, { text: "Height(Left Subtree) - Height(Right Subtree)", isCorrect: true }] },
    { id: 55, questionText: "What two core conditions must a problem satisfy to be solvable using Dynamic Programming?", topic: "DSA", difficultyLevel: "HARD", options: [{ text: "Overlapping Subproblems and Optimal Substructure", isCorrect: true }, { text: "Greedy Choice Property and Sorting", isCorrect: false }, { text: "Divide and Conquer without Overlap", isCorrect: false }, { text: "Linear Time Complexity", isCorrect: false }] },
    { id: 56, questionText: "What is the average time complexity of searching for an element in a Red-Black Tree with n nodes?", topic: "DSA", difficultyLevel: "HARD", options: [{ text: "O(n)", isCorrect: false }, { text: "O(log n)", isCorrect: true }, { text: "O(1)", isCorrect: false }, { text: "O(n log n)", isCorrect: false }] },
    { id: 57, questionText: "Which algorithm is used to compute Minimum Spanning Tree (MST) by picking minimum weight edges?", topic: "DSA", difficultyLevel: "HARD", options: [{ text: "Dijkstra's Algorithm", isCorrect: false }, { text: "BFS Traversal", isCorrect: false }, { text: "Kruskal's Algorithm", isCorrect: true }, { text: "Topological Sort", isCorrect: false }] },
    { id: 58, questionText: "What is the amortized time complexity of dynamic array reallocation when resizing capacity?", topic: "DSA", difficultyLevel: "HARD", options: [{ text: "O(n)", isCorrect: false }, { text: "O(log n)", isCorrect: false }, { text: "O(n^2)", isCorrect: false }, { text: "O(1)", isCorrect: true }] },
    { id: 59, questionText: "In a Max-Heap, where is the largest element always located?", topic: "DSA", difficultyLevel: "HARD", options: [{ text: "At the Root Node", isCorrect: true }, { text: "At the Leaf Node", isCorrect: false }, { text: "In the Middle Node", isCorrect: false }, { text: "Random Position", isCorrect: false }] },
    { id: 60, questionText: "What data structure is used in Floyd-Warshall algorithm to store all-pairs shortest paths?", topic: "DSA", difficultyLevel: "HARD", options: [{ text: "Priority Queue", isCorrect: false }, { text: "2D Distance Matrix", isCorrect: true }, { text: "Adjacency List", isCorrect: false }, { text: "Disjoint Set", isCorrect: false }] },

    // Database (20 Questions)
    { id: 61, questionText: "Which SQL command is used to retrieve rows from a relational database table?", topic: "Database", difficultyLevel: "EASY", options: [{ text: "GET", isCorrect: false }, { text: "EXTRACT", isCorrect: false }, { text: "SELECT", isCorrect: true }, { text: "OPEN", isCorrect: false }] },
    { id: 62, questionText: "Which key constraint uniquely identifies each row record in a database table?", topic: "Database", difficultyLevel: "EASY", options: [{ text: "Foreign Key", isCorrect: false }, { text: "Composite Key", isCorrect: false }, { text: "Candidate Key", isCorrect: false }, { text: "Primary Key", isCorrect: true }] },
    { id: 63, questionText: "What does the ACID acronym guarantee in database management systems?", topic: "Database", difficultyLevel: "EASY", options: [{ text: "Atomicity, Consistency, Isolation, Durability", isCorrect: true }, { text: "Accuracy, Control, Integrity, Data", isCorrect: false }, { text: "Access, Concurrency, Index, Durability", isCorrect: false }, { text: "Aggregation, Constraint, Isolation, Data", isCorrect: false }] },
    { id: 64, questionText: "Which SQL statement is used to insert new records into a database table?", topic: "Database", difficultyLevel: "EASY", options: [{ text: "ADD ROW", isCorrect: false }, { text: "INSERT INTO", isCorrect: true }, { text: "PUSH DATA", isCorrect: false }, { text: "CREATE RECORD", isCorrect: false }] },
    { id: 65, questionText: "Which SQL clause is used to filter query results based on specified conditions?", topic: "Database", difficultyLevel: "EASY", options: [{ text: "GROUP BY", isCorrect: false }, { text: "ORDER BY", isCorrect: false }, { text: "WHERE", isCorrect: true }, { text: "HAVING", isCorrect: false }] },
    { id: 66, questionText: "Which SQL JOIN clause returns all records from the left table and matched records from the right table?", topic: "Database", difficultyLevel: "MEDIUM", options: [{ text: "RIGHT JOIN", isCorrect: false }, { text: "INNER JOIN", isCorrect: false }, { text: "FULL JOIN", isCorrect: false }, { text: "LEFT JOIN", isCorrect: true }] },
    { id: 67, questionText: "Which Normal Form specifically eliminates transitive functional dependencies in database design?", topic: "Database", difficultyLevel: "MEDIUM", options: [{ text: "Third Normal Form (3NF)", isCorrect: true }, { text: "First Normal Form (1NF)", isCorrect: false }, { text: "Second Normal Form (2NF)", isCorrect: false }, { text: "Boyce-Codd Normal Form (BCNF)", isCorrect: false }] },
    { id: 68, questionText: "What is the primary benefit of creating a B-Tree Index on a database table column?", topic: "Database", difficultyLevel: "MEDIUM", options: [{ text: "Reduces disk storage space", isCorrect: false }, { text: "Accelerates query search and data retrieval speed", isCorrect: true }, { text: "Prevents duplicate rows", isCorrect: false }, { text: "Encrypts column values", isCorrect: false }] },
    { id: 69, questionText: "What happens to uncommitted changes when a database transaction executes ROLLBACK?", topic: "Database", difficultyLevel: "MEDIUM", options: [{ text: "Changes are committed to disk", isCorrect: false }, { text: "Database tables are dropped", isCorrect: false }, { text: "All uncommitted modifications are undone", isCorrect: true }, { text: "Only primary keys reset", isCorrect: false }] },
    { id: 70, questionText: "Which SQL aggregate function calculates the total sum of numerical values in a column?", topic: "Database", difficultyLevel: "MEDIUM", options: [{ text: "COUNT()", isCorrect: false }, { text: "AVG()", isCorrect: false }, { text: "TOTAL()", isCorrect: false }, { text: "SUM()", isCorrect: true }] },
    { id: 71, questionText: "Which SQL clause is used to filter aggregated data generated by GROUP BY?", topic: "Database", difficultyLevel: "MEDIUM", options: [{ text: "HAVING", isCorrect: true }, { text: "WHERE", isCorrect: false }, { text: "LIMIT", isCorrect: false }, { text: "FILTER", isCorrect: false }] },
    { id: 72, questionText: "In Relational Database Design, what is a Foreign Key?", topic: "Database", difficultyLevel: "MEDIUM", options: [{ text: "A key that encrypts data", isCorrect: false }, { text: "A field in one table referencing Primary Key of another table", isCorrect: true }, { text: "A key used for web APIs", isCorrect: false }, { text: "A unique index constraint", isCorrect: false }] },
    { id: 73, questionText: "Which Transaction Isolation Level prevents Dirty Reads, Non-Repeatable Reads, and Phantom Reads?", topic: "Database", difficultyLevel: "HARD", options: [{ text: "READ COMMITTED", isCorrect: false }, { text: "REPEATABLE READ", isCorrect: false }, { text: "SERIALIZABLE", isCorrect: true }, { text: "READ UNCOMMITTED", isCorrect: false }] },
    { id: 74, questionText: "In SQL query optimization, what is a Covering Index?", topic: "Database", difficultyLevel: "HARD", options: [{ text: "An index created on Primary Key", isCorrect: false }, { text: "A clustered index spanning databases", isCorrect: false }, { text: "A full-text search index", isCorrect: false }, { text: "An index containing all queried columns without table lookup", isCorrect: true }] },
    { id: 75, questionText: "How does Write-Ahead Logging (WAL) ensure durability during database crash recovery?", topic: "Database", difficultyLevel: "HARD", options: [{ text: "Logs data modifications to disk before actual table pages are written", isCorrect: true }, { text: "Saves automated database dumps", isCorrect: false }, { text: "Encrypts transaction log files", isCorrect: false }, { text: "Clears buffer pool memory", isCorrect: false }] },
    { id: 76, questionText: "What database phenomenon occurs when a transaction reads uncommitted changes from another transaction?", topic: "Database", difficultyLevel: "HARD", options: [{ text: "Phantom Read", isCorrect: false }, { text: "Dirty Read", isCorrect: true }, { text: "Non-Repeatable Read", isCorrect: false }, { text: "Lost Update", isCorrect: false }] },
    { id: 77, questionText: "In MySQL InnoDB storage engine, what is the default primary key indexing structure?", topic: "Database", difficultyLevel: "HARD", options: [{ text: "Secondary Hash Index", isCorrect: false }, { text: "B+ Tree Non-Clustered Index", isCorrect: false }, { text: "Clustered B+ Tree Index", isCorrect: true }, { text: "Full-Text Index", isCorrect: false }] },
    { id: 78, questionText: "Which NOSQL database category does MongoDB belong to?", topic: "Database", difficultyLevel: "HARD", options: [{ text: "Column-Family Store", isCorrect: false }, { text: "Graph Database", isCorrect: false }, { text: "Key-Value Store", isCorrect: false }, { text: "Document-Oriented Database", isCorrect: true }] },
    { id: 79, questionText: "What is the purpose of database Sharding in large scale web architectures?", topic: "Database", difficultyLevel: "HARD", options: [{ text: "Horizontal partitioning of database rows across multiple server instances", isCorrect: true }, { text: "Creating read-only backups", isCorrect: false }, { text: "Encrypting database tables", isCorrect: false }, { text: "Compressing table indexes", isCorrect: false }] },
    { id: 80, questionText: "Which theorem states that a distributed data store can provide at most 2 out of 3: Consistency, Availability, Partition Tolerance?", topic: "Database", difficultyLevel: "HARD", options: [{ text: "ACID Theorem", isCorrect: false }, { text: "CAP Theorem", isCorrect: true }, { text: "BASE Theorem", isCorrect: false }, { text: "Two-Phase Commit Theorem", isCorrect: false }] },

    // Operating System (20 Questions)
    { id: 81, questionText: "Which core component of the Operating System manages hardware resources and CPU scheduling?", topic: "Operating System", difficultyLevel: "EASY", options: [{ text: "Shell", isCorrect: false }, { text: "Compiler", isCorrect: false }, { text: "Kernel", isCorrect: true }, { text: "Linker", isCorrect: false }] },
    { id: 82, questionText: "What state does a process transition into while waiting for disk I/O completion?", topic: "Operating System", difficultyLevel: "EASY", options: [{ text: "Running State", isCorrect: false }, { text: "Ready State", isCorrect: false }, { text: "Terminated State", isCorrect: false }, { text: "Waiting / Blocked State", isCorrect: true }] },
    { id: 83, questionText: "What defines a Deadlock condition in multi-processing operating systems?", topic: "Operating System", difficultyLevel: "EASY", options: [{ text: "Two or more processes are permanently blocked waiting for resources held by each other", isCorrect: true }, { text: "A process executing an infinite loop", isCorrect: false }, { text: "System memory leak", isCorrect: false }, { text: "Hard drive failure", isCorrect: false }] },
    { id: 84, questionText: "What is the main role of an Operating System Command Interpreter?", topic: "Operating System", difficultyLevel: "EASY", options: [{ text: "To compile C++ code", isCorrect: false }, { text: "To provide a Shell UI for user commands", isCorrect: true }, { text: "To manage CPU L1 cache", isCorrect: false }, { text: "To clean temp files", isCorrect: false }] },
    { id: 85, questionText: "Which system call is used in Unix-like operating systems to create a new child process?", topic: "Operating System", difficultyLevel: "EASY", options: [{ text: "exec()", isCorrect: false }, { text: "exit()", isCorrect: false }, { text: "fork()", isCorrect: true }, { text: "wait()", isCorrect: false }] },
    { id: 86, questionText: "Which CPU scheduling algorithm allocates equal CPU time slices to processes in a circular queue?", topic: "Operating System", difficultyLevel: "MEDIUM", options: [{ text: "FCFS Scheduling", isCorrect: false }, { text: "Shortest Job First", isCorrect: false }, { text: "Priority Scheduling", isCorrect: false }, { text: "Round Robin Scheduling", isCorrect: true }] },
    { id: 87, questionText: "What is the purpose of Virtual Memory in modern computer systems?", topic: "Operating System", difficultyLevel: "MEDIUM", options: [{ text: "Allows execution of processes exceeding physical RAM capacity using disk page files", isCorrect: true }, { text: "Increases CPU clock frequency", isCorrect: false }, { text: "Accelerates GPU rendering", isCorrect: false }, { text: "Scans memory for malware", isCorrect: false }] },
    { id: 88, questionText: "What is thrashing in operating system virtual memory management?", topic: "Operating System", difficultyLevel: "MEDIUM", options: [{ text: "Corrupted disk sectors", isCorrect: false }, { text: "Excessive page swapping between RAM and disk resulting in degraded performance", isCorrect: true }, { text: "High CPU utilization", isCorrect: false }, { text: "Process starvation in queue", isCorrect: false }] },
    { id: 89, questionText: "Which mechanism enables processes to exchange data and synchronize actions across address spaces?", topic: "Operating System", difficultyLevel: "MEDIUM", options: [{ text: "Symmetric Multiprocessing", isCorrect: false }, { text: "Direct Memory Access", isCorrect: false }, { text: "Inter-Process Communication (IPC)", isCorrect: true }, { text: "Interrupt Vector Table", isCorrect: false }] },
    { id: 90, questionText: "What hardware signal is sent to the CPU by peripheral devices when attention is required?", topic: "Operating System", difficultyLevel: "MEDIUM", options: [{ text: "Trap", isCorrect: false }, { text: "Semaphore", isCorrect: false }, { text: "System Call", isCorrect: false }, { text: "Interrupt", isCorrect: true }] },
    { id: 91, questionText: "Which page replacement algorithm replaces the page that has not been used for the longest period of time?", topic: "Operating System", difficultyLevel: "MEDIUM", options: [{ text: "Least Recently Used (LRU)", isCorrect: true }, { text: "First-In First-Out (FIFO)", isCorrect: false }, { text: "Optimal Page Replacement", isCorrect: false }, { text: "Most Recently Used (MRU)", isCorrect: false }] },
    { id: 92, questionText: "What synchronization primitive uses integer counter variables to control access to shared resources?", topic: "Operating System", difficultyLevel: "MEDIUM", options: [{ text: "Spinlock", isCorrect: false }, { text: "Semaphore", isCorrect: true }, { text: "Mutex", isCorrect: false }, { text: "Condition Variable", isCorrect: false }] },
    { id: 93, questionText: "Which algorithm is used by Operating Systems to avoid Deadlocks by testing resource allocation safety?", topic: "Operating System", difficultyLevel: "HARD", options: [{ text: "Peterson's Algorithm", isCorrect: false }, { text: "Snoopy Bus Protocol", isCorrect: false }, { text: "Banker's Algorithm", isCorrect: true }, { text: "LRU Page Replacement", isCorrect: false }] },
    { id: 94, questionText: "What is a key difference between Kernel-Level Threads and User-Level Threads?", topic: "Operating System", difficultyLevel: "HARD", options: [{ text: "User-Level Threads execute faster in kernel mode", isCorrect: false }, { text: "Kernel-Level Threads do not support multi-core CPUs", isCorrect: false }, { text: "They are identical", isCorrect: false }, { text: "Kernel-Level Threads are scheduled directly by OS Kernel while User-Level Threads are managed by thread libraries", isCorrect: true }] },
    { id: 95, questionText: "What is the Translation Lookaside Buffer (TLB) in CPU memory management architecture?", topic: "Operating System", difficultyLevel: "HARD", options: [{ text: "A high-speed hardware cache used to speed up virtual-to-physical address translation", isCorrect: true }, { text: "A disk cache for page files", isCorrect: false }, { text: "A register file inside ALU", isCorrect: false }, { text: "A backup page table in RAM", isCorrect: false }] },
    { id: 96, questionText: "What condition causes a Page Fault in virtual memory address translation?", topic: "Operating System", difficultyLevel: "HARD", options: [{ text: "CPU executes invalid instruction", isCorrect: false }, { text: "Requested page is not currently loaded in physical RAM", isCorrect: true }, { text: "Hard drive is full", isCorrect: false }, { text: "RAM capacity is exceeded", isCorrect: false }] },
    { id: 97, questionText: "In disk scheduling algorithms, how does SCAN (Elevator) algorithm move the disk arm?", topic: "Operating System", difficultyLevel: "HARD", options: [{ text: "Moves randomly across tracks", isCorrect: false }, { text: "Serves closest request first", isCorrect: false }, { text: "Moves from one end of disk to the other, servicing requests along the way", isCorrect: true }, { text: "Serves in order of arrival", isCorrect: false }] },
    { id: 98, questionText: "What is Context Switching in CPU process scheduling?", topic: "Operating System", difficultyLevel: "HARD", options: [{ text: "Changing CPU clock speed", isCorrect: false }, { text: "Swapping disk page files", isCorrect: false }, { text: "Compiling kernel code", isCorrect: false }, { text: "Saving current process state and loading state of another process", isCorrect: true }] },
    { id: 99, questionText: "Which mutual exclusion solution guarantees progress and bounded waiting for 2 concurrent processes?", topic: "Operating System", difficultyLevel: "HARD", options: [{ text: "Peterson's Algorithm", isCorrect: true }, { text: "Banker's Algorithm", isCorrect: false }, { text: "Dekker's Lock", isCorrect: false }, { text: "TestAndSet Instruction", isCorrect: false }] },
    { id: 100, questionText: "What is the purpose of Spooling (Simultaneous Peripheral Operations On-Line) in OS I/O systems?", topic: "Operating System", difficultyLevel: "HARD", options: [{ text: "Encrypting disk drives", isCorrect: false }, { text: "Buffering I/O job data on disk so slow devices process at their own pace", isCorrect: true }, { text: "Defragmenting RAM memory", isCorrect: false }, { text: "Cleaning temporary log files", isCorrect: false }] }
  ];

  const getFallbackQuestions = (topic, difficulty, limit) => {
    let eligible = questionPool;
    if (topic && topic !== 'ALL') {
      eligible = eligible.filter(q => (q.topic || '').toUpperCase() === topic.toUpperCase());
    }
    if (difficulty && difficulty !== 'ALL') {
      eligible = eligible.filter(q => (q.difficultyLevel || '').toUpperCase() === difficulty.toUpperCase());
    }
    if (eligible.length === 0) {
      eligible = questionPool;
    }
    const shuffled = [...eligible].sort(() => 0.5 - Math.random());
    return shuffled.slice(0, Number(limit || 10));
  };

  const enrichQuestionsWithOptions = (qList, quizTopic) => {
    if (!qList || !Array.isArray(qList)) return [];

    return qList.map((q, idx) => {
      if (q.options && Array.isArray(q.options) && q.options.length > 0) {
        return q;
      }
      const normTxt = (q.questionText || '').toLowerCase().replace(/[^a-z0-9]/g, '');
      const poolMatch = questionPool.find(pq => pq.questionText.toLowerCase().replace(/[^a-z0-9]/g, '') === normTxt);
      if (poolMatch && poolMatch.options) {
        return { ...q, options: poolMatch.options };
      }
      const topicMatches = questionPool.filter(pq => (pq.topic || '').toLowerCase() === (quizTopic || '').toLowerCase());
      if (topicMatches.length > 0) {
        const match = topicMatches[idx % topicMatches.length];
        return { ...q, options: match.options };
      }
      return q;
    });
  };

  useEffect(() => {
    const quizzes = JSON.parse(localStorage.getItem('lq_quizzes') || '[]');
    let foundQuiz = quizzes.find(q => String(q.id) === String(quizId) || q.title === quizId || (q.topic && q.topic.toLowerCase() === String(quizId).toLowerCase()));

    // Dedicated persistent storage check by unique Quiz ID
    const savedQForId = JSON.parse(localStorage.getItem(`lq_quiz_questions_${quizId}`) || 'null');

    if (foundQuiz) {
      setQuiz(foundQuiz);
      let qList = savedQForId || foundQuiz.questions;
      if (!qList || qList.length === 0) {
        qList = getFallbackQuestions(foundQuiz.topic, foundQuiz.difficulty, foundQuiz.questionsCount || foundQuiz.questionsLimit || 10);
      }
      qList = enrichQuestionsWithOptions(qList, foundQuiz.topic);

      // Lock questions permanently so retakes use the EXACT same questions
      localStorage.setItem(`lq_quiz_questions_${foundQuiz.id}`, JSON.stringify(qList));
      if (foundQuiz.title) localStorage.setItem(`lq_quiz_questions_${foundQuiz.title}`, JSON.stringify(qList));
      if (quizId) localStorage.setItem(`lq_quiz_questions_${quizId}`, JSON.stringify(qList));

      foundQuiz.questions = qList;
      const updatedQuizzes = quizzes.map(q => q.id === foundQuiz.id ? foundQuiz : q);
      localStorage.setItem('lq_quizzes', JSON.stringify(updatedQuizzes));

      setQuestions(qList);
      setTimeLeft((foundQuiz.durationMinutes || 30) * 60);
      setIsLoading(false);
    } else {
      // Try API fetch
      axiosClient.get(`/quizzes/${quizId}`).then(res => {
        if (res.data) {
          setQuiz(res.data);
          let qList = savedQForId || savedQForTitle || res.data.questions;
          if (!qList || qList.length === 0) {
            qList = getFallbackQuestions(res.data.topic, res.data.difficulty, res.data.questionsCount || 10);
          }
          qList = enrichQuestionsWithOptions(qList, res.data.topic);

          localStorage.setItem(`lq_quiz_questions_${quizId}`, JSON.stringify(qList));
          if (res.data.title) localStorage.setItem(`lq_quiz_questions_${res.data.title}`, JSON.stringify(qList));

          setQuestions(qList);
          setTimeLeft((res.data.durationMinutes || 30) * 60);
        }
      }).catch(err => {
        console.log('Quiz fetch error, generating question set');
        const fallbackTopic = (quizId && isNaN(quizId)) ? quizId : 'Java';
        let fallbackQ = savedQForId || savedQForTitle || getFallbackQuestions(fallbackTopic, 'ALL', 10);
        fallbackQ = enrichQuestionsWithOptions(fallbackQ, fallbackTopic);

        localStorage.setItem(`lq_quiz_questions_${quizId}`, JSON.stringify(fallbackQ));

        setQuiz({ id: quizId, title: `${fallbackTopic} Assessment`, topic: fallbackTopic, durationMinutes: 30, passScore: 70, questions: fallbackQ });
        setQuestions(fallbackQ);
      }).finally(() => {
        setIsLoading(false);
      });
    }

    // Check review mode
    const isReviewMode = window.location.search.includes('review=true');
    const userStr = localStorage.getItem('user');
    const user = userStr ? JSON.parse(userStr) : null;
    const studentKey = user?.username || user?.id || 'default_student';

    const storageKey = `lq_student_results_${studentKey}`;
    const savedResults = JSON.parse(localStorage.getItem(storageKey) || '{}');
    const prevResult = savedResults[quizId] || (foundQuiz ? savedResults[foundQuiz.id] || savedResults[foundQuiz.title] : null);

    if (isReviewMode && prevResult) {
      setSubmittedResult(prevResult);
      if (prevResult.savedAnswers) {
        setSelectedAnswers(prevResult.savedAnswers);
      }
    }
  }, [quizId]);

  // Countdown timer
  useEffect(() => {
    if (isLoading || submittedResult) return;

    if (timeLeft <= 0) {
      handleSubmit();
      return;
    }

    const timer = setInterval(() => setTimeLeft(prev => prev - 1), 1000);
    return () => clearInterval(timer);
  }, [timeLeft, isLoading, submittedResult]);

  const handleSelectOption = (questionIdx, optionIdx) => {
    setSelectedAnswers(prev => ({
      ...prev,
      [questionIdx]: optionIdx
    }));
  };

  const handleSubmit = () => {
    setIsSubmitting(true);

    // Calculate score
    let correctCount = 0;
    const totalCount = questions.length;

    questions.forEach((q, qIdx) => {
      const selectedOptIdx = selectedAnswers[qIdx];
      if (selectedOptIdx !== undefined && q.options && q.options[selectedOptIdx]) {
        const chosenOpt = q.options[selectedOptIdx];
        if (chosenOpt.isCorrect || chosenOpt.correct) {
          correctCount++;
        }
      }
    });

    const scorePercentage = totalCount > 0 ? Math.round((correctCount / totalCount) * 100) : 0;
    const passThreshold = quiz?.passScore || 70;
    const isPassed = scorePercentage >= passThreshold;

    // Save result to student results history in localStorage (scoped by logged in student)
    const userStr = localStorage.getItem('user');
    const user = userStr ? JSON.parse(userStr) : null;
    const studentKey = user?.username || user?.id || 'default_student';

    const storageKey = `lq_student_results_${studentKey}`;
    const savedResults = JSON.parse(localStorage.getItem(storageKey) || '{}');
    const prevResult = savedResults[quiz?.id || quizId] || savedResults[quiz?.title];
    const previousAttempts = prevResult?.attemptCount || 0;
    const currentAttemptCount = previousAttempts + 1;
    const maxAttempts = Number(quiz?.maxAttempts) || Number(prevResult?.maxAttempts) || 3;

    const resultData = {
      quizId: quiz?.id || quizId,
      quizTitle: quiz?.title || 'Assessment',
      topic: quiz?.topic || 'General',
      scorePercentage,
      correctCount,
      totalCount,
      wrongCount: totalCount - correctCount,
      passThreshold,
      isPassed,
      attemptCount: currentAttemptCount,
      maxAttempts: maxAttempts,
      submittedAt: new Date().toISOString(),
      savedAnswers: { ...selectedAnswers }
    };

    savedResults[quiz?.id || quizId] = resultData;
    if (quiz?.title) savedResults[quiz.title] = resultData;
    localStorage.setItem(storageKey, JSON.stringify(savedResults));

    // Save submission to teacher accessible repository (scoped by publishing teacher)
    const teacherKey = quiz?.createdByTeacher || 'default_teacher';
    const teacherSubmission = {
      id: Date.now(),
      quizId: quiz?.id || quizId,
      quizTitle: quiz?.title || 'Assessment',
      topic: quiz?.topic || 'General',
      studentUsername: studentKey,
      scorePercentage,
      correctCount,
      totalCount,
      wrongCount: totalCount - correctCount,
      passThreshold,
      isPassed,
      attemptCount: currentAttemptCount,
      maxAttempts: maxAttempts,
      createdByTeacher: teacherKey,
      submittedAt: new Date().toISOString()
    };

    const teacherSubmissions = JSON.parse(localStorage.getItem('lq_teacher_student_submissions') || '[]');
    const cleanTeacherSubmissions = teacherSubmissions.filter(s => 
      !(s.studentUsername === studentKey && (String(s.quizId) === String(quiz?.id || quizId) || s.quizTitle === quiz?.title))
    );
    cleanTeacherSubmissions.unshift(teacherSubmission);
    localStorage.setItem('lq_teacher_student_submissions', JSON.stringify(cleanTeacherSubmissions));

    // Sync to backend if possible
    axiosClient.post('/exams/submit', {
      quizId: quiz?.id,
      score: scorePercentage,
      isPassed
    }).catch(e => {});

    setTimeout(() => {
      setSubmittedResult(resultData);
      setIsSubmitting(false);
    }, 400);
  };

  const formatTime = (seconds) => {
    const mins = Math.floor(seconds / 60);
    const secs = seconds % 60;
    return `${mins.toString().padStart(2, '0')}:${secs.toString().padStart(2, '0')}`;
  };

  if (isLoading) {
    return (
      <div style={{ textAlign: 'center', padding: '60px', color: 'var(--text-secondary)' }}>
        Loading examination session...
      </div>
    );
  }

  // Helper to generate AI explanation for wrong/unanswered questions
  const getAIExplanation = (q, selectedOptIdx, correctOptIdx) => {
    const selectedText = (selectedOptIdx !== undefined && q.options && q.options[selectedOptIdx]) ? q.options[selectedOptIdx].text : 'No Answer Selected';
    const correctText = (correctOptIdx !== undefined && q.options && q.options[correctOptIdx]) ? q.options[correctOptIdx].text : '';

    const qText = (q.questionText || '').toLowerCase();
    const topic = (q.topic || '').toLowerCase();

    // 1. Unanswered Case
    if (selectedOptIdx === undefined) {
      return `❌ Unanswered Question. The correct answer is "${correctText}".\n\n✅ Explanation: Reviewing "${q.topic || 'Subject Concept'}" will help solidify this core topic for future retakes.`;
    }

    // 2. Operating System & Kernel / Shell
    if (qText.includes('kernel') || qText.includes('shell') || qText.includes('operating system') || topic.includes('operating system') || topic.includes('os')) {
      if (selectedText.toLowerCase().includes('shell')) {
        return `❌ Why "${selectedText}" is incorrect: The Shell is an outer command-line interpreter/user interface layer that passes commands to the OS, not the core OS engine.\n\n✅ Why "${correctText}" is correct: The Kernel is the central core component of an Operating System that directly manages hardware, CPU scheduling, system memory, process execution, and device drivers.`;
      }
      return `❌ Why "${selectedText}" is incorrect: "${selectedText}" provides higher-level user utility services rather than managing core hardware interfaces.\n\n✅ Why "${correctText}" is correct: The Kernel acts as the essential low-level bridge between application software and physical computer hardware.`;
    }

    // 3. Java - Controllers & Web
    if (qText.includes('controller') || qText.includes('restcontroller')) {
      return `❌ Why "${selectedText}" is incorrect: "${selectedText}" is designed for traditional Spring MVC applications that render HTML/JSP view templates.\n\n✅ Why "${correctText}" is correct: "${correctText}" combines @Controller and @ResponseBody to automatically serialize return objects into JSON or XML payloads for RESTful APIs.`;
    }

    // 4. Java - Collections & HashMap
    if (qText.includes('hashmap') || qText.includes('o(1)') || qText.includes('arraylist')) {
      return `❌ Why "${selectedText}" is incorrect: "${selectedText}" requires linear traversal across elements, resulting in O(n) search time.\n\n✅ Why "${correctText}" is correct: "${correctText}" computes array bucket indices directly via key hash codes, achieving constant O(1) average lookup performance.`;
    }

    // 5. Data Structures - Stack vs Queue
    if (qText.includes('stack') || qText.includes('lifo') || qText.includes('queue') || qText.includes('fifo')) {
      return `❌ Why "${selectedText}" is incorrect: "${selectedText}" operates on First-In, First-Out (FIFO) ordering where elements exit in arrival order.\n\n✅ Why "${correctText}" is correct: "${correctText}" follows Last-In, First-Out (LIFO) discipline, where the most recently added item is pushed and popped first.`;
    }

    // 6. Java - Garbage Collector
    if (qText.includes('garbage collector') || qText.includes('java 17') || qText.includes('g1')) {
      return `❌ Why "${selectedText}" is incorrect: "${selectedText}" is an older or experimental memory collector replaced in modern Java LTS releases.\n\n✅ Why "${correctText}" is correct: "${correctText}" is the default collector in Java 17 LTS, partitioning the heap into equal regions for high-throughput, low-latency garbage collection.`;
    }

    // 7. Java - Volatile & Concurrency
    if (qText.includes('volatile') || qText.includes('thread') || qText.includes('synchronized')) {
      return `❌ Why "${selectedText}" is incorrect: "${selectedText}" manages method locking or monitor access rather than direct CPU cache synchronization.\n\n✅ Why "${correctText}" is correct: "${correctText}" forces thread variable reads/writes directly to main memory, ensuring thread visibility and preventing compiler instruction reordering.`;
    }

    // 8. Databases & SQL
    if (qText.includes('sql') || qText.includes('select') || qText.includes('join') || qText.includes('where') || qText.includes('having') || topic.includes('database')) {
      return `❌ Why "${selectedText}" is incorrect: "${selectedText}" does not satisfy relational database filtering or joining rules.\n\n✅ Why "${correctText}" is correct: "${correctText}" strictly satisfies SQL query execution syntax, properly filtering and joining database table records.`;
    }

    // 9. General Fallback
    return `❌ Why "${selectedText}" is incorrect: "${selectedText}" is a distractor option that does not fulfill the specific conceptual requirements of this question.\n\n✅ Why "${correctText}" is correct: "${correctText}" is the authoritative standard answer for this ${q.topic || 'computer science'} concept.`;
  };

  // Helper to compute AI Focus Areas based on quiz performance
  const getAIFocusAreas = (questionsList, effectiveAnswers) => {
    const topicStats = {};
    questionsList.forEach((q, idx) => {
      const topic = q.topic || 'General Knowledge';
      if (!topicStats[topic]) {
        topicStats[topic] = { total: 0, correct: 0, wrongQuestions: [] };
      }
      topicStats[topic].total += 1;
      const correctIdx = (q.options || []).findIndex(opt => opt.isCorrect || opt.correct);
      const studentIdx = effectiveAnswers[idx];
      if (studentIdx !== undefined && studentIdx === correctIdx) {
        topicStats[topic].correct += 1;
      } else {
        topicStats[topic].wrongQuestions.push(q);
      }
    });

    const weakTopics = Object.entries(topicStats)
      .map(([topic, data]) => ({
        topic,
        accuracy: Math.round((data.correct / data.total) * 100),
        wrongCount: data.wrongQuestions.length
      }))
      .filter(t => t.wrongCount > 0)
      .sort((a, b) => a.accuracy - b.accuracy);

    return weakTopics;
  };

  if (submittedResult) {
    return (
      <div style={{ maxWidth: '780px', margin: '40px auto' }}>
        <div className="glass-panel" style={{ padding: '40px', textAlign: 'center' }}>
          {submittedResult.isPassed ? (
            <CheckCircle2 size={64} color="#10b981" style={{ margin: '0 auto 16px' }} />
          ) : (
            <XCircle size={64} color="#f43f5e" style={{ margin: '0 auto 16px' }} />
          )}

          <h2 style={{ fontSize: '1.8rem', fontWeight: 800, color: '#fff' }}>
            {submittedResult.isPassed ? 'Congratulations! Examination Passed' : 'Examination Completed'}
          </h2>
          <p style={{ color: 'var(--text-secondary)', marginTop: '6px', fontSize: '0.95rem' }}>
            {submittedResult.quizTitle} ({submittedResult.topic})
          </p>

          {/* Score Card */}
          <div style={{ 
            background: submittedResult.isPassed ? 'rgba(16, 185, 129, 0.1)' : 'rgba(244, 63, 94, 0.1)', 
            border: submittedResult.isPassed ? '1px solid rgba(16, 185, 129, 0.3)' : '1px solid rgba(244, 63, 94, 0.3)', 
            borderRadius: '16px', 
            padding: '28px', 
            margin: '28px 0' 
          }}>
            <div style={{ fontSize: '0.9rem', color: 'var(--text-muted)', fontWeight: 600 }}>FINAL SCORE</div>
            <div style={{ fontSize: '3.2rem', fontWeight: 900, color: submittedResult.isPassed ? '#10b981' : '#f43f5e', margin: '4px 0' }}>
              {submittedResult.scorePercentage}%
            </div>
            
            <div style={{ display: 'inline-block', marginTop: '4px' }}>
              <span className={`badge ${submittedResult.isPassed ? 'badge-success' : 'badge-danger'}`} style={{ fontSize: '0.9rem', padding: '6px 16px' }}>
                {submittedResult.isPassed ? 'PASSED (QUALIFIED)' : 'FAILED (BELOW PASS MARKS)'}
              </span>
            </div>

            <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr 1fr 1fr', gap: '16px', marginTop: '24px', paddingTop: '20px', borderTop: '1px solid rgba(255,255,255,0.08)' }}>
              <div>
                <div style={{ fontSize: '0.75rem', color: 'var(--text-muted)' }}>CORRECT</div>
                <div style={{ fontSize: '1.3rem', fontWeight: 800, color: '#10b981' }}>{submittedResult.correctCount} / {submittedResult.totalCount}</div>
              </div>
              <div>
                <div style={{ fontSize: '0.75rem', color: 'var(--text-muted)' }}>INCORRECT</div>
                <div style={{ fontSize: '1.3rem', fontWeight: 800, color: '#f43f5e' }}>{submittedResult.wrongCount}</div>
              </div>
              <div>
                <div style={{ fontSize: '0.75rem', color: 'var(--text-muted)' }}>PASS THRESHOLD</div>
                <div style={{ fontSize: '1.3rem', fontWeight: 800, color: '#a5b4fc' }}>{submittedResult.passThreshold}%</div>
              </div>
              <div>
                <div style={{ fontSize: '0.75rem', color: 'var(--text-muted)' }}>ATTEMPTS USED</div>
                <div style={{ fontSize: '1.3rem', fontWeight: 800, color: '#fbbf24' }}>
                  {submittedResult.attemptCount || 1} / {quiz?.maxAttempts || submittedResult.maxAttempts || 3}
                </div>
              </div>
            </div>
          </div>

          <div style={{ display: 'flex', gap: '12px', justifyContent: 'center', marginTop: '28px' }}>
            {(submittedResult.attemptCount || 1) < (Number(quiz?.maxAttempts) || Number(submittedResult.maxAttempts) || 3) ? (
              <button className="btn btn-secondary" onClick={() => { setSubmittedResult(null); setSelectedAnswers({}); setTimeLeft((quiz.durationMinutes || 30) * 60); }}>
                <RefreshCw size={16} /> Retake Exam ({(Number(quiz?.maxAttempts) || Number(submittedResult.maxAttempts) || 3) - (submittedResult.attemptCount || 1)} Left)
              </button>
            ) : (
              <button className="btn btn-secondary" disabled style={{ opacity: 0.5, cursor: 'not-allowed' }}>
                <XCircle size={16} /> Retake Limit Reached ({submittedResult.attemptCount}/{Number(quiz?.maxAttempts) || Number(submittedResult.maxAttempts) || 3})
              </button>
            )}
            <button className="btn btn-primary" onClick={() => navigate('/student/quizzes')}>
              <ArrowLeft size={16} /> Back to Quizzes & Exams
            </button>
          </div>

          {/* DETAILED QUESTION & ANSWER REVIEW SECTION */}
          <div style={{ marginTop: '40px', textAlign: 'left', borderTop: '1px solid rgba(255,255,255,0.1)', paddingTop: '32px' }}>
            <h3 style={{ fontSize: '1.4rem', fontWeight: 800, color: '#fff', marginBottom: '6px' }}>
              Detailed Question & Answer Review (Attempt #{submittedResult.attemptCount || 1})
            </h3>
            <p style={{ color: 'var(--text-secondary)', fontSize: '0.9rem', marginBottom: '24px' }}>
              Review your selected answers below. Correct answers are highlighted in <strong style={{ color: '#10b981' }}>Green</strong> and incorrect selections in <strong style={{ color: '#f43f5e' }}>Red</strong> with instant AI explanations.
            </p>

            <div style={{ display: 'flex', flexDirection: 'column', gap: '24px' }}>
              {questions.map((q, qIdx) => {
                const effectiveAnswers = (submittedResult && submittedResult.savedAnswers) ? submittedResult.savedAnswers : selectedAnswers;
                const selectedOptIdx = effectiveAnswers[qIdx] !== undefined ? effectiveAnswers[qIdx] : undefined;
                const correctOptIdx = (q.options || []).findIndex(opt => opt.isCorrect || opt.correct);
                const isStudentCorrect = (selectedOptIdx !== undefined && selectedOptIdx === correctOptIdx);

                return (
                  <div key={qIdx} className="glass-panel" style={{ padding: '24px', borderRadius: '12px', background: 'rgba(15, 23, 42, 0.65)' }}>
                    <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '12px' }}>
                      <span className="badge badge-info" style={{ fontWeight: 700 }}>
                        Question {qIdx + 1} of {questions.length}
                      </span>

                      {isStudentCorrect ? (
                        <span className="badge badge-success" style={{ display: 'inline-flex', alignItems: 'center', gap: '6px' }}>
                          <CheckCircle2 size={14} /> Correct (+1 Mark)
                        </span>
                      ) : selectedOptIdx !== undefined ? (
                        <span className="badge badge-danger" style={{ background: 'rgba(244, 63, 94, 0.15)', color: '#f43f5e', border: '1px solid rgba(244, 63, 94, 0.3)', display: 'inline-flex', alignItems: 'center', gap: '6px' }}>
                          <XCircle size={14} /> Incorrect (0 Marks)
                        </span>
                      ) : (
                        <span className="badge badge-warning" style={{ display: 'inline-flex', alignItems: 'center', gap: '6px' }}>
                          <AlertTriangle size={14} /> Unanswered
                        </span>
                      )}
                    </div>

                    <h4 style={{ fontSize: '1.05rem', fontWeight: 800, color: '#fff', marginBottom: '16px', lineHeight: 1.5 }}>
                      {q.questionText}
                    </h4>

                    {/* Options list */}
                    <div style={{ display: 'flex', flexDirection: 'column', gap: '10px' }}>
                      {(q.options || []).map((opt, optIdx) => {
                        const isCorrectOpt = (optIdx === correctOptIdx);
                        const isSelectedOpt = (optIdx === selectedOptIdx);

                        let optBg = 'rgba(255, 255, 255, 0.03)';
                        let optBorder = '1px solid rgba(255, 255, 255, 0.08)';
                        let optColor = 'var(--text-secondary)';

                        if (isCorrectOpt) {
                          optBg = 'rgba(16, 185, 129, 0.15)';
                          optBorder = '1px solid #10b981';
                          optColor = '#ffffff';
                        } else if (isSelectedOpt && !isCorrectOpt) {
                          optBg = 'rgba(244, 63, 94, 0.15)';
                          optBorder = '1px solid #f43f5e';
                          optColor = '#f43f5e';
                        }

                        return (
                          <div 
                            key={optIdx} 
                            style={{ 
                              padding: '12px 16px', 
                              borderRadius: '10px', 
                              background: optBg, 
                              border: optBorder, 
                              color: optColor,
                              display: 'flex', 
                              justifyContent: 'space-between', 
                              alignItems: 'center',
                              fontSize: '0.9rem',
                              fontWeight: (isCorrectOpt || isSelectedOpt) ? 700 : 400
                            }}
                          >
                            <div style={{ display: 'flex', alignItems: 'center', gap: '10px' }}>
                              <span style={{ fontWeight: 800, opacity: 0.8 }}>
                                {String.fromCharCode(65 + optIdx)}.
                              </span>
                              <span>{opt.text}</span>
                            </div>

                            <div>
                              {isCorrectOpt && isSelectedOpt && (
                                <span className="badge badge-success" style={{ fontSize: '0.75rem' }}>
                                  ✓ Your Selected Answer (Correct)
                                </span>
                              )}
                              {isCorrectOpt && !isSelectedOpt && (
                                <span className="badge badge-success" style={{ fontSize: '0.75rem' }}>
                                  ✓ Correct Answer
                                </span>
                              )}
                              {isSelectedOpt && !isCorrectOpt && (
                                <span className="badge badge-danger" style={{ background: 'rgba(244, 63, 94, 0.2)', color: '#f43f5e', border: '1px solid rgba(244, 63, 94, 0.4)', fontSize: '0.75rem' }}>
                                  ✗ Your Selected Answer (Incorrect)
                                </span>
                              )}
                            </div>
                          </div>
                        );
                      })}
                    </div>

                    {/* AI Explanation & Misconception Breakdown Card for Wrong/Unanswered */}
                    {!isStudentCorrect && (
                      <div style={{ marginTop: '16px', background: 'rgba(99, 102, 241, 0.12)', border: '1px solid rgba(99, 102, 241, 0.3)', borderRadius: '10px', padding: '16px', display: 'flex', alignItems: 'flex-start', gap: '12px' }}>
                        <Bot size={22} color="#818cf8" style={{ marginTop: '2px', flexShrink: 0 }} />
                        <div>
                          <div style={{ fontSize: '0.8rem', fontWeight: 800, color: '#818cf8', textTransform: 'uppercase', letterSpacing: '0.05em' }}>
                            🤖 AI Tutor Diagnostic Explanation
                          </div>
                          <p style={{ fontSize: '0.875rem', color: '#e2e8f0', marginTop: '6px', lineHeight: 1.6, margin: 0, whiteSpace: 'pre-line' }}>
                            {getAIExplanation(q, selectedOptIdx, correctOptIdx)}
                          </p>
                        </div>
                      </div>
                    )}
                  </div>
                );
              })}
            </div>
          </div>
        </div>
      </div>
    );
  }

  return (
    <div style={{ maxWidth: '820px', margin: '0 auto', display: 'flex', flexDirection: 'column', gap: '24px' }}>
      {/* Top Bar */}
      <div className="glass-panel" style={{ padding: '20px 28px', display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
        <div style={{ display: 'flex', alignItems: 'center', gap: '12px' }}>
          <ShieldCheck color="#6366f1" size={26} />
          <div>
            <h3 style={{ fontSize: '1.2rem', fontWeight: 800, color: '#fff' }}>{quiz?.title || 'Active Examination'}</h3>
            <span style={{ fontSize: '0.8rem', color: 'var(--text-secondary)' }}>Subject: <strong>{quiz?.topic}</strong> | {questions.length} Total Questions</span>
          </div>
        </div>

        <div style={{ display: 'flex', alignItems: 'center', gap: '8px', background: timeLeft < 300 ? 'rgba(244, 63, 94, 0.2)' : 'rgba(245, 158, 11, 0.15)', border: timeLeft < 300 ? '1px solid rgba(244, 63, 94, 0.4)' : '1px solid rgba(245, 158, 11, 0.3)', padding: '10px 18px', borderRadius: '10px', color: timeLeft < 300 ? '#f43f5e' : '#f59e0b', fontWeight: 800, fontSize: '1.1rem' }}>
          <Clock size={20} /> {formatTime(timeLeft)}
        </div>
      </div>

      {/* Questions List */}
      {questions.map((q, qIdx) => (
        <div key={qIdx} className="glass-panel" style={{ padding: '28px' }}>
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '12px' }}>
            <span className="badge badge-info" style={{ fontWeight: 800 }}>Question {qIdx + 1} of {questions.length}</span>
            <span className={`badge ${q.difficultyLevel === 'HARD' ? 'badge-danger' : q.difficultyLevel === 'MEDIUM' ? 'badge-warning' : 'badge-success'}`}>
              {q.difficultyLevel || 'EASY'}
            </span>
          </div>

          <h4 style={{ fontSize: '1.1rem', fontWeight: 700, color: '#fff', lineHeight: 1.5, marginBottom: '20px' }}>
            {q.questionText}
          </h4>

          <div style={{ display: 'flex', flexDirection: 'column', gap: '12px' }}>
            {q.options?.map((opt, oIdx) => {
              const isSelected = selectedAnswers[qIdx] === oIdx;
              const optionLabel = String.fromCharCode(65 + oIdx);

              return (
                <div
                  key={oIdx}
                  onClick={() => handleSelectOption(qIdx, oIdx)}
                  style={{
                    padding: '14px 18px',
                    borderRadius: '10px',
                    border: isSelected ? '2px solid #6366f1' : '1px solid rgba(255, 255, 255, 0.08)',
                    background: isSelected ? 'rgba(99, 102, 241, 0.18)' : 'rgba(255, 255, 255, 0.02)',
                    cursor: 'pointer',
                    display: 'flex',
                    alignItems: 'center',
                    justifyContent: 'space-between',
                    transition: 'all 0.15s ease'
                  }}
                >
                  <div style={{ display: 'flex', alignItems: 'center', gap: '12px' }}>
                    <span style={{ 
                      fontWeight: 800, 
                      color: isSelected ? '#fff' : 'var(--text-secondary)', 
                      background: isSelected ? '#6366f1' : 'rgba(255, 255, 255, 0.06)', 
                      width: '28px', 
                      height: '28px', 
                      borderRadius: '50%', 
                      display: 'flex', 
                      alignItems: 'center', 
                      justifyContent: 'center', 
                      fontSize: '0.8rem' 
                    }}>
                      {optionLabel}
                    </span>
                    <span style={{ color: isSelected ? '#fff' : 'var(--text-secondary)', fontWeight: isSelected ? 700 : 400, fontSize: '0.92rem' }}>
                      {opt.text || opt.optionText}
                    </span>
                  </div>

                  {isSelected && <CheckCircle2 size={18} color="#6366f1" />}
                </div>
              );
            })}
          </div>
        </div>
      ))}

      {/* Footer Submit Bar */}
      <div className="glass-panel" style={{ padding: '20px 28px', display: 'flex', justifyContent: 'space-between', alignItems: 'center', position: 'sticky', bottom: '20px', zIndex: 10 }}>
        <span style={{ fontSize: '0.9rem', color: 'var(--text-secondary)' }}>
          <strong style={{ color: '#fff' }}>{Object.keys(selectedAnswers).length}</strong> of <strong>{questions.length}</strong> Questions Answered
        </span>
        <button className="btn btn-primary" onClick={handleSubmit} disabled={isSubmitting} style={{ padding: '12px 28px', fontSize: '0.95rem' }}>
          {isSubmitting ? 'Evaluating Marks...' : 'Submit Examination'}
        </button>
      </div>
    </div>
  );
};

export default TakeQuizPage;
