import React, { useState, useEffect } from 'react';
import { HelpCircle, Plus, CheckCircle2, Clock, Award, Trash2, Eye, Shuffle, BookOpen, AlertCircle, Sparkles, Users } from 'lucide-react';
import axiosClient from '../../api/axiosClient';

const TeacherQuizBuilder = () => {
  const [quizzes, setQuizzes] = useState([]);
  const [isLoading, setIsLoading] = useState(true);

  // Build Quiz Modal State
  const [showModal, setShowModal] = useState(false);
  const [viewingQuiz, setViewingQuiz] = useState(null);
  const [viewingResultsQuiz, setViewingResultsQuiz] = useState(null);
  const [teacherSubmissions, setTeacherSubmissions] = useState([]);

  const [newQuiz, setNewQuiz] = useState({
    title: '',
    topic: 'Java',
    durationMinutes: 30,
    questionsLimit: 10,
    difficulty: 'ALL',
    passScore: 70,
    maxAttempts: 3
  });

  // Complete 100 MCQ Question Pool (20 per topic with options included)
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

  const getCurrentTeacherKey = () => {
    try {
      const userStr = localStorage.getItem('user');
      const u = userStr ? JSON.parse(userStr) : null;
      return u?.username || u?.email || u?.id || 'default_teacher';
    } catch (e) {
      return 'default_teacher';
    }
  };

  // Fetch quizzes from storage & API
  const fetchQuizzes = async () => {
    setIsLoading(true);
    try {
      const currentTeacherKey = getCurrentTeacherKey();
      const deletedList = JSON.parse(localStorage.getItem('lq_deleted_quizzes') || '[]');
      const isDeleted = (q) => deletedList.includes(q.id) || deletedList.includes(q.title);

      const stored = JSON.parse(localStorage.getItem('lq_quizzes') || '[]');
      const cleanStored = stored.filter(q => 
        !q.title?.includes('Spring Boot') && 
        !q.title?.includes('Microservices') &&
        !q.title?.includes('AVL Trees') &&
        !q.title?.includes('Advanced JPA') &&
        !isDeleted(q) &&
        q.createdByTeacher === currentTeacherKey
      );

      let apiQuizzes = [];
      try {
        const res = await axiosClient.get('/quizzes');
        if (res.data && Array.isArray(res.data)) {
          apiQuizzes = res.data.filter(q => 
            !q.title?.includes('Spring Boot') && 
            !q.title?.includes('Microservices') &&
            !q.title?.includes('AVL Trees') &&
            !q.title?.includes('Advanced JPA') &&
            !isDeleted(q) &&
            (q.createdByTeacher === currentTeacherKey || cleanStored.some(cs => cs.title === q.title || cs.id === q.id))
          );
        }
      } catch (e) {
        console.log('QuizService fetch fallback');
      }

      const combined = [...cleanStored, ...apiQuizzes];

      const uniqueMap = new Map();
      combined.forEach(q => {
        if (q.title && !uniqueMap.has(q.title) && !isDeleted(q)) {
          uniqueMap.set(q.title, { ...q, createdByTeacher: q.createdByTeacher || currentTeacherKey });
        }
      });

      setQuizzes(Array.from(uniqueMap.values()));

      // Load student submissions for this teacher profile
      const allSubmissions = JSON.parse(localStorage.getItem('lq_teacher_student_submissions') || '[]');
      const mySubmissions = allSubmissions.filter(s => !s.createdByTeacher || s.createdByTeacher === currentTeacherKey);
      setTeacherSubmissions(mySubmissions);
    } catch (err) {
      console.error('Error fetching quizzes:', err);
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    fetchQuizzes();
  }, []);

  // Helper: Select random questions from pool based on selected Topic & Difficulty
  const pickRandomQuestions = (topic, difficulty, limit) => {
    let eligible = questionPool.filter(q => (q.topic || '').toUpperCase() === topic.toUpperCase());

    if (difficulty !== 'ALL') {
      eligible = eligible.filter(q => (q.difficultyLevel || '').toUpperCase() === difficulty.toUpperCase());
    }

    if (eligible.length === 0) {
      eligible = questionPool.filter(q => (q.topic || '').toUpperCase() === topic.toUpperCase());
    }

    const shuffled = [...eligible].sort(() => 0.5 - Math.random());
    return shuffled.slice(0, Number(limit));
  };

  // Helper: Clear past student results and locked questions when a quiz is deleted or recreated
  const clearQuizHistoryAndQuestions = (quizId, quizTitle) => {
    if (quizId) localStorage.removeItem(`lq_quiz_questions_${quizId}`);
    if (quizTitle) localStorage.removeItem(`lq_quiz_questions_${quizTitle}`);

    Object.keys(localStorage).forEach(key => {
      if (key.startsWith('lq_student_results_')) {
        try {
          const resMap = JSON.parse(localStorage.getItem(key) || '{}');
          let modified = false;
          if (quizId && resMap[quizId]) {
            delete resMap[quizId];
            modified = true;
          }
          if (quizTitle && resMap[quizTitle]) {
            delete resMap[quizTitle];
            modified = true;
          }
          if (modified) {
            localStorage.setItem(key, JSON.stringify(resMap));
          }
        } catch (e) {}
      }
    });
  };

  // Handle Create New Quiz
  const handleCreateQuiz = async (e) => {
    e.preventDefault();

    const quizTitleClean = newQuiz.title.trim();
    const currentTeacherKey = getCurrentTeacherKey();

    // Clear any previous attempts or questions from an old quiz with the same title
    clearQuizHistoryAndQuestions(null, quizTitleClean);

    const selectedQuestions = pickRandomQuestions(newQuiz.topic, newQuiz.difficulty, newQuiz.questionsLimit);

    const quizRecord = {
      id: Date.now(),
      title: quizTitleClean,
      topic: newQuiz.topic,
      durationMinutes: Number(newQuiz.durationMinutes) || 30,
      questionsLimit: selectedQuestions.length,
      questionsCount: selectedQuestions.length,
      difficulty: newQuiz.difficulty,
      passScore: Number(newQuiz.passScore) || 70,
      maxAttempts: Number(newQuiz.maxAttempts) || 3,
      isPublished: false,
      createdByTeacher: currentTeacherKey,
      questions: selectedQuestions,
      createdAt: new Date().toISOString()
    };

    // Persist questions dedicatedly by quiz ID and title so retakes ALWAYS match
    localStorage.setItem(`lq_quiz_questions_${quizRecord.id}`, JSON.stringify(selectedQuestions));
    localStorage.setItem(`lq_quiz_questions_${quizRecord.title}`, JSON.stringify(selectedQuestions));

    // Clean deleted list if re-creating quiz with same name
    const deletedList = JSON.parse(localStorage.getItem('lq_deleted_quizzes') || '[]');
    const cleanedDeleted = deletedList.filter(item => item !== quizRecord.id && item !== quizRecord.title);
    localStorage.setItem('lq_deleted_quizzes', JSON.stringify(cleanedDeleted));

    // Save in persistent localStorage (preserves all teachers' quizzes globally)
    const existing = JSON.parse(localStorage.getItem('lq_quizzes') || '[]');
    const globalUpdated = [quizRecord, ...existing.filter(q => q.id !== quizRecord.id && !(q.title === quizRecord.title && q.createdByTeacher === currentTeacherKey))];
    localStorage.setItem('lq_quizzes', JSON.stringify(globalUpdated));

    try {
      const payload = {
        title: quizTitleClean,
        topic: newQuiz.topic,
        durationMinutes: Number(newQuiz.durationMinutes),
        passScore: Number(newQuiz.passScore),
        isPublished: false,
        createdByTeacher: currentTeacherKey,
        questionsCount: selectedQuestions.length
      };
      await axiosClient.post('/quizzes', payload);
    } catch (e) {
      console.log('QuizService creation sync fallback');
    }

    setQuizzes(globalUpdated.filter(q => !q.createdByTeacher || q.createdByTeacher === currentTeacherKey));
    setShowModal(false);
    setNewQuiz({ title: '', topic: 'Java', durationMinutes: 30, questionsLimit: 10, difficulty: 'ALL', passScore: 70, maxAttempts: 3 });
  };

  // Toggle Publish Status
  const togglePublish = async (quizId) => {
    const currentTeacherKey = getCurrentTeacherKey();
    const existing = JSON.parse(localStorage.getItem('lq_quizzes') || '[]');
    const globalUpdated = existing.map(q => (String(q.id) === String(quizId) || q.title === quizId) ? { ...q, isPublished: !q.isPublished } : q);
    localStorage.setItem('lq_quizzes', JSON.stringify(globalUpdated));

    setQuizzes(prev => prev.map(q => (String(q.id) === String(quizId) || q.title === quizId) ? { ...q, isPublished: !q.isPublished } : q));

    try {
      await axiosClient.post(`/quizzes/${quizId}/publish`);
    } catch (e) {}
  };

  // Delete Quiz
  const handleDeleteQuiz = async (quizTarget) => {
    const targetId = quizTarget.id;
    const targetTitle = quizTarget.title;

    clearQuizHistoryAndQuestions(targetId, targetTitle);

    const existing = JSON.parse(localStorage.getItem('lq_quizzes') || '[]');
    const globalUpdated = existing.filter(q => q.id !== targetId && q.title !== targetTitle);
    localStorage.setItem('lq_quizzes', JSON.stringify(globalUpdated));

    setQuizzes(prev => prev.filter(q => q.id !== targetId && q.title !== targetTitle));

    const deletedList = JSON.parse(localStorage.getItem('lq_deleted_quizzes') || '[]');
    if (targetId && !deletedList.includes(targetId)) deletedList.push(targetId);
    if (targetTitle && !deletedList.includes(targetTitle)) deletedList.push(targetTitle);
    localStorage.setItem('lq_deleted_quizzes', JSON.stringify(deletedList));

    try {
      await axiosClient.delete(`/quizzes/${targetId}`);
    } catch (e) {
      console.log('Backend deletion fallback');
    }
  };

  return (
    <div>
      {/* Header */}
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '32px' }}>
        <div>
          <div className="badge badge-warning" style={{ marginBottom: '8px' }}>
            👨‍🏫 Faculty Assessment Studio
          </div>
          <h1 style={{ fontSize: '2rem', fontWeight: 800 }}>Quiz & Examination Builder</h1>
          <p style={{ color: 'var(--text-secondary)', fontSize: '0.9rem', marginTop: '4px' }}>
            Configure topic, duration, difficulty, passing thresholds, and automatically select random questions for student exams.
          </p>
        </div>
        <button className="btn btn-primary" onClick={() => setShowModal(true)}>
          <Plus size={18} /> Build New Quiz
        </button>
      </div>

      {/* Quiz Cards List */}
      {isLoading ? (
        <div style={{ textAlign: 'center', padding: '40px', color: 'var(--text-secondary)' }}>
          Loading created quizzes...
        </div>
      ) : quizzes.length === 0 ? (
        <div className="glass-panel" style={{ padding: '48px', textAlign: 'center', color: 'var(--text-secondary)' }}>
          <HelpCircle size={52} style={{ margin: '0 auto 16px', opacity: 0.4, color: 'var(--amber-500)' }} />
          <h4 style={{ fontSize: '1.2rem', fontWeight: 800, color: '#fff', marginBottom: '6px' }}>No Quizzes Created Yet</h4>
          <p style={{ fontSize: '0.9rem', color: 'var(--text-muted)', maxWidth: '440px', margin: '0 auto 20px' }}>
            Click <strong>Build New Quiz</strong> to select a topic, set duration & passing score, and auto-generate random MCQ questions.
          </p>
          <button className="btn btn-primary" onClick={() => setShowModal(true)}>
            <Plus size={18} /> Build First Quiz
          </button>
        </div>
      ) : (
        <div style={{ display: 'flex', flexDirection: 'column', gap: '20px' }}>
          {quizzes.map((q) => {
            const quizSubmissions = teacherSubmissions.filter(s => String(s.quizId) === String(q.id) || s.quizTitle === q.title);
            return (
              <div key={q.id} className="glass-panel" style={{ padding: '24px', display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                <div>
                  <div style={{ display: 'flex', alignItems: 'center', gap: '10px', marginBottom: '10px' }}>
                    <span className="badge badge-info" style={{ fontWeight: 800 }}>{q.topic || 'Java'}</span>
                    {q.isPublished ? (
                      <span className="badge badge-success"><CheckCircle2 size={12} /> PUBLISHED</span>
                    ) : (
                      <span className="badge badge-warning">DRAFT / UNPUBLISHED</span>
                    )}
                    <span className="badge badge-secondary" style={{ background: 'rgba(255,255,255,0.06)' }}>
                      {(q.questions && q.questions.length > 0) ? q.questions.length : (q.questionsCount || q.questionsLimit || 10)} Questions Selected
                    </span>
                    <span className={`badge ${q.difficulty === 'HARD' ? 'badge-danger' : q.difficulty === 'MEDIUM' ? 'badge-warning' : 'badge-success'}`}>
                      {q.difficulty || 'ALL DIFFICULTIES'}
                    </span>
                  </div>

                  <h3 style={{ fontSize: '1.25rem', fontWeight: 800, color: '#fff', marginBottom: '8px' }}>{q.title}</h3>
                  
                  <div style={{ display: 'flex', gap: '24px', fontSize: '0.85rem', color: 'var(--text-secondary)' }}>
                    <span>⏱️ Duration: <strong style={{ color: '#fff' }}>{q.durationMinutes} Mins</strong></span>
                    <span>🎯 Passing Score: <strong style={{ color: '#10b981' }}>{q.passScore}%</strong></span>
                    <span>🔁 Retake Limit: <strong style={{ color: '#fbbf24' }}>{q.maxAttempts || 3} {Number(q.maxAttempts) === 1 ? 'Attempt' : 'Attempts'}</strong></span>
                  </div>
                </div>

                <div style={{ display: 'flex', gap: '10px', alignItems: 'center' }}>
                  <button 
                    className="btn btn-secondary"
                    style={{ padding: '8px 14px', fontSize: '0.85rem', color: '#a5b4fc', border: '1px solid rgba(165, 180, 252, 0.3)' }}
                    onClick={() => setViewingResultsQuiz(q)}
                  >
                    <Users size={16} /> Student Results ({quizSubmissions.length})
                  </button>

                  <button 
                    className="btn btn-secondary"
                    style={{ padding: '8px 14px', fontSize: '0.85rem' }}
                    onClick={() => setViewingQuiz(q)}
                  >
                    <Eye size={16} /> View Questions
                  </button>

                  <button 
                    className={`btn ${q.isPublished ? 'btn-secondary' : 'btn-primary'}`}
                    style={{ padding: '8px 14px', fontSize: '0.85rem' }}
                    onClick={() => togglePublish(q.id)}
                  >
                    {q.isPublished ? 'Unpublish' : 'Publish Quiz'}
                  </button>

                  <button 
                    className="btn btn-secondary"
                    style={{ padding: '8px 10px', color: '#f43f5e', border: '1px solid rgba(244,63,94,0.3)' }}
                    onClick={() => handleDeleteQuiz(q)}
                    title="Delete Quiz"
                  >
                    <Trash2 size={16} />
                  </button>
                </div>
              </div>
            );
          })}
        </div>
      )}

      {/* BUILD NEW QUIZ MODAL */}
      {showModal && (
        <div style={{ position: 'fixed', inset: 0, background: 'rgba(0,0,0,0.75)', backdropFilter: 'blur(8px)', zIndex: 1000, display: 'flex', alignItems: 'center', justifyContent: 'center', padding: '20px' }}>
          <div className="glass-panel" style={{ width: '100%', maxWidth: '540px', padding: '32px' }}>
            <div style={{ display: 'flex', alignItems: 'center', gap: '10px', marginBottom: '6px' }}>
              <div className="badge badge-warning">⚡ Quiz Auto-Builder</div>
            </div>
            <h3 style={{ fontSize: '1.4rem', fontWeight: 800, marginBottom: '20px' }}>Create & Configure Quiz</h3>
            
            <form onSubmit={handleCreateQuiz}>
              <div className="form-group">
                <label className="form-label">Quiz Title</label>
                <input 
                  type="text" 
                  className="form-input" 
                  placeholder="e.g. Java Core & Multithreading Final Assessment" 
                  value={newQuiz.title}
                  onChange={(e) => setNewQuiz({ ...newQuiz, title: e.target.value })}
                  required 
                />
              </div>

              <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '16px' }}>
                <div className="form-group">
                  <label className="form-label">Subject Topic (1 of 5)</label>
                  <select 
                    className="form-select" 
                    value={newQuiz.topic} 
                    onChange={(e) => setNewQuiz({ ...newQuiz, topic: e.target.value })}
                  >
                    <option value="Java">Java</option>
                    <option value=".NET">.NET</option>
                    <option value="DSA">Data Structures & Algorithms (DSA)</option>
                    <option value="Database">Database Systems</option>
                    <option value="Operating System">Operating System</option>
                  </select>
                </div>

                <div className="form-group">
                  <label className="form-label">Question Difficulty</label>
                  <select 
                    className="form-select" 
                    value={newQuiz.difficulty} 
                    onChange={(e) => setNewQuiz({ ...newQuiz, difficulty: e.target.value })}
                  >
                    <option value="ALL">All Difficulties</option>
                    <option value="EASY">Easy</option>
                    <option value="MEDIUM">Medium</option>
                    <option value="HARD">Hard</option>
                  </select>
                </div>
              </div>

              <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '16px' }}>
                <div className="form-group">
                  <label className="form-label">Duration (Minutes)</label>
                  <select 
                    className="form-select"
                    value={newQuiz.durationMinutes}
                    onChange={(e) => setNewQuiz({ ...newQuiz, durationMinutes: e.target.value })}
                  >
                    <option value={15}>15 Minutes</option>
                    <option value={30}>30 Minutes</option>
                    <option value={45}>45 Minutes</option>
                    <option value={60}>60 Minutes (1 Hour)</option>
                  </select>
                </div>

                <div className="form-group">
                  <label className="form-label">Questions Limit</label>
                  <select 
                    className="form-select"
                    value={newQuiz.questionsLimit}
                    onChange={(e) => setNewQuiz({ ...newQuiz, questionsLimit: e.target.value })}
                  >
                    <option value={5}>5 Random Questions</option>
                    <option value={10}>10 Random Questions</option>
                    <option value={15}>15 Random Questions</option>
                    <option value={20}>20 Random Questions</option>
                  </select>
                </div>
              </div>

              <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '16px', marginBottom: '24px' }}>
                <div className="form-group" style={{ marginBottom: 0 }}>
                  <label className="form-label">Passing Marks Score (%)</label>
                  <select 
                    className="form-select"
                    value={newQuiz.passScore}
                    onChange={(e) => setNewQuiz({ ...newQuiz, passScore: e.target.value })}
                  >
                    <option value={50}>50% Passing Marks</option>
                    <option value={60}>60% Passing Marks</option>
                    <option value={70}>70% Passing Marks (Standard)</option>
                    <option value={80}>80% Passing Marks (Advanced)</option>
                  </select>
                </div>

                <div className="form-group" style={{ marginBottom: 0 }}>
                  <label className="form-label">Retake Attempts Limit</label>
                  <select 
                    className="form-select"
                    value={newQuiz.maxAttempts}
                    onChange={(e) => setNewQuiz({ ...newQuiz, maxAttempts: e.target.value })}
                  >
                    <option value={1}>1 Attempt (No Retakes)</option>
                    <option value={2}>2 Attempts Allowed</option>
                    <option value={3}>3 Attempts Allowed</option>
                  </select>
                </div>
              </div>

              <div style={{ background: 'rgba(99, 102, 241, 0.1)', border: '1px solid rgba(99, 102, 241, 0.25)', borderRadius: '8px', padding: '12px', fontSize: '0.8rem', color: '#a5b4fc', marginBottom: '24px', display: 'flex', alignItems: 'center', gap: '8px' }}>
                <Shuffle size={16} /> Random selection engine will pick {newQuiz.questionsLimit} MCQ questions from the {newQuiz.topic} question pool.
              </div>

              <div style={{ display: 'flex', gap: '12px', justifyContent: 'flex-end' }}>
                <button type="button" className="btn btn-secondary" onClick={() => setShowModal(false)}>Cancel</button>
                <button type="submit" className="btn btn-primary">
                  <Sparkles size={16} /> Generate Quiz
                </button>
              </div>
            </form>
          </div>
        </div>
      )}

      {/* VIEW QUIZ RANDOMLY SELECTED QUESTIONS MODAL */}
      {viewingQuiz && (
        <div style={{ position: 'fixed', inset: 0, background: 'rgba(0,0,0,0.75)', backdropFilter: 'blur(8px)', zIndex: 1000, display: 'flex', alignItems: 'center', justifyContent: 'center', padding: '20px' }}>
          <div className="glass-panel" style={{ width: '100%', maxWidth: '640px', maxHeight: '85vh', overflowY: 'auto', padding: '32px' }}>
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start', marginBottom: '20px' }}>
              <div>
                <span className="badge badge-info" style={{ fontWeight: 800, marginBottom: '6px' }}>{viewingQuiz.topic}</span>
                <h3 style={{ fontSize: '1.4rem', fontWeight: 800, color: '#fff' }}>{viewingQuiz.title}</h3>
                <p style={{ fontSize: '0.85rem', color: 'var(--text-secondary)', marginTop: '4px' }}>
                  {viewingQuiz.questions ? viewingQuiz.questions.length : viewingQuiz.questionsCount} Randomly Selected Questions Pool
                </p>
              </div>
              <button className="btn btn-secondary" onClick={() => setViewingQuiz(null)}>Close</button>
            </div>

            <div style={{ display: 'flex', flexDirection: 'column', gap: '12px' }}>
              {viewingQuiz.questions && viewingQuiz.questions.length > 0 ? (
                viewingQuiz.questions.map((q, idx) => (
                  <div key={idx} style={{ background: 'rgba(255,255,255,0.03)', borderRadius: '10px', padding: '14px 18px', border: '1px solid rgba(255,255,255,0.06)' }}>
                    <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '6px' }}>
                      <span className="badge badge-secondary" style={{ fontSize: '0.75rem' }}>Question {idx + 1}</span>
                      <span className={`badge ${q.difficultyLevel === 'HARD' ? 'badge-danger' : q.difficultyLevel === 'MEDIUM' ? 'badge-warning' : 'badge-success'}`} style={{ fontSize: '0.7rem' }}>
                        {q.difficultyLevel || 'EASY'}
                      </span>
                    </div>
                    <p style={{ fontSize: '0.95rem', fontWeight: 600, color: '#fff' }}>{q.questionText}</p>
                  </div>
                ))
              ) : (
                <div style={{ textAlign: 'center', padding: '24px', color: 'var(--text-secondary)' }}>
                  Questions automatically selected upon student test initialization.
                </div>
              )}
            </div>
          </div>
        </div>
      )}

      {/* VIEW STUDENT EXAM RESULTS MODAL */}
      {viewingResultsQuiz && (() => {
        const quizSubmissions = teacherSubmissions.filter(s => String(s.quizId) === String(viewingResultsQuiz.id) || s.quizTitle === viewingResultsQuiz.title);

        return (
          <div style={{ position: 'fixed', inset: 0, background: 'rgba(0,0,0,0.75)', backdropFilter: 'blur(8px)', zIndex: 1000, display: 'flex', alignItems: 'center', justifyContent: 'center', padding: '20px' }}>
            <div className="glass-panel" style={{ width: '100%', maxWidth: '720px', maxHeight: '85vh', overflowY: 'auto', padding: '32px' }}>
              <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start', marginBottom: '20px' }}>
                <div>
                  <div className="badge badge-info" style={{ fontWeight: 800, marginBottom: '6px' }}>Student Performance Dashboard</div>
                  <h3 style={{ fontSize: '1.4rem', fontWeight: 800, color: '#fff' }}>{viewingResultsQuiz.title}</h3>
                  <p style={{ fontSize: '0.85rem', color: 'var(--text-secondary)', marginTop: '4px' }}>
                    {quizSubmissions.length} Student Exam {quizSubmissions.length === 1 ? 'Submission' : 'Submissions'}
                  </p>
                </div>
                <button className="btn btn-secondary" onClick={() => setViewingResultsQuiz(null)}>Close</button>
              </div>

              {quizSubmissions.length === 0 ? (
                <div style={{ textAlign: 'center', padding: '40px', color: 'var(--text-secondary)' }}>
                  <Users size={44} style={{ margin: '0 auto 12px', opacity: 0.4, color: 'var(--primary-500)' }} />
                  <h4 style={{ color: '#fff', fontSize: '1.1rem', fontWeight: 700 }}>No Student Submissions Yet</h4>
                  <p style={{ fontSize: '0.85rem', marginTop: '4px' }}>When a student attempts this published quiz, their scores and results will appear here.</p>
                </div>
              ) : (
                <div style={{ display: 'flex', flexDirection: 'column', gap: '12px' }}>
                  {quizSubmissions.map((sub, idx) => (
                    <div key={idx} style={{ background: 'rgba(255,255,255,0.03)', borderRadius: '12px', padding: '16px 20px', border: '1px solid rgba(255,255,255,0.08)', display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                      <div>
                        <div style={{ display: 'flex', alignItems: 'center', gap: '10px', marginBottom: '6px' }}>
                          <span style={{ fontSize: '1rem', fontWeight: 800, color: '#fff' }}>👤 {sub.studentUsername}</span>
                          <span className={`badge ${sub.isPassed ? 'badge-success' : 'badge-danger'}`}>
                            {sub.isPassed ? 'PASSED' : 'FAILED'}
                          </span>
                          <span className="badge badge-secondary" style={{ fontSize: '0.75rem' }}>
                            Attempt {sub.attemptCount} / {sub.maxAttempts || 3}
                          </span>
                        </div>
                        <div style={{ fontSize: '0.8rem', color: 'var(--text-secondary)' }}>
                          Submitted on: {new Date(sub.submittedAt).toLocaleString()}
                        </div>
                      </div>

                      <div style={{ textAlign: 'right' }}>
                        <div style={{ fontSize: '1.5rem', fontWeight: 900, color: sub.isPassed ? '#10b981' : '#f43f5e' }}>
                          {sub.scorePercentage}%
                        </div>
                        <div style={{ fontSize: '0.78rem', color: 'var(--text-secondary)' }}>
                          {sub.correctCount} / {sub.totalCount} Correct
                        </div>
                      </div>
                    </div>
                  ))}
                </div>
              )}
            </div>
          </div>
        );
      })()}
    </div>
  );
};

export default TeacherQuizBuilder;
