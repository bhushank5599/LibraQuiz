package com.lq.questionbank.config;

import com.lq.questionbank.dto.QuestionRequest;
import com.lq.questionbank.entity.Question;
import com.lq.questionbank.repository.QuestionRepository;
import com.lq.questionbank.service.QuestionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SeedDataConfig implements CommandLineRunner {

    private final QuestionRepository questionRepository;
    private final QuestionService questionService;

    public SeedDataConfig(QuestionRepository questionRepository, QuestionService questionService) {
        this.questionRepository = questionRepository;
        this.questionService = questionService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (questionRepository.count() < 100) {
            questionRepository.deleteAll(); // Re-seed complete 100 questions dataset across 5 topics

            // ==========================================
            // TOPIC 1: JAVA (20 Questions)
            // ==========================================
            addQ("What is the default value of a boolean primitive variable in Java?", "Java", "EASY",
                    List.of("true", "false", "null", "0"), 1); // B

            addQ("Which keyword is used to prevent a class from being subclassed in Java?", "Java", "EASY",
                    List.of("static", "abstract", "final", "private"), 2); // C

            addQ("Which package is automatically imported in every Java program?", "Java", "EASY",
                    List.of("java.util", "java.io", "java.net", "java.lang"), 3); // D

            addQ("What is the size of an int variable in Java?", "Java", "EASY",
                    List.of("4 Bytes (32 bits)", "2 Bytes", "8 Bytes", "1 Byte"), 0); // A

            addQ("Which method is the entry point for execution in any standalone Java application?", "Java", "EASY",
                    List.of("public void start()", "public static void main(String[] args)", "public void main()", "static void run()"), 1); // B

            addQ("What is the average time complexity of HashMap.get(key) operation in Java?", "Java", "MEDIUM",
                    List.of("O(n)", "O(log n)", "O(1)", "O(n^2)"), 2); // C

            addQ("Which Garbage Collector is used as default in Java 17 LTS?", "Java", "MEDIUM",
                    List.of("G1 Garbage Collector", "ZGC", "Parallel GC", "CMS GC"), 0); // A

            addQ("What happens if a thread calls Thread.sleep() inside a synchronized block?", "Java", "MEDIUM",
                    List.of("It releases the monitor lock", "It throws InterruptedException immediately", "It sleeps while keeping the monitor lock held", "JVM crashes"), 2); // C

            addQ("Which interface does java.util.TreeSet implement to maintain sorted element order?", "Java", "MEDIUM",
                    List.of("java.util.List", "java.util.Queue", "java.util.RandomAccess", "java.util.NavigableSet"), 3); // D

            addQ("What happens if an abstract class does not have any abstract methods?", "Java", "MEDIUM",
                    List.of("It will not compile", "The class can still be abstract", "Java auto-adds abstract method", "It becomes a final class"), 1); // B

            addQ("Which class in java.util.concurrent provides atomic operations without lock overhead?", "Java", "MEDIUM",
                    List.of("AtomicInteger", "VolatileInt", "SynchronizedInteger", "LockedInt"), 0); // A

            addQ("Which annotation in JPA is used to specify a primary key auto-generation strategy?", "Java", "MEDIUM",
                    List.of("Id", "Column", "GeneratedValue", "Entity"), 2); // C

            addQ("In Java memory management, where are String Literals stored starting from Java 7/8?", "Java", "HARD",
                    List.of("PermGen Space", "Inside the Java Heap Space", "Native Stack Memory", "Code Cache"), 1); // B

            addQ("Which Java 17 feature introduces immutable data-carrier classes with implicit constructors and getters?", "Java", "HARD",
                    List.of("Sealed Classes", "Pattern Matching", "Records", "Text Blocks"), 2); // C

            addQ("What is the primary effect of the volatile keyword on a variable in Java multithreading?", "Java", "HARD",
                    List.of("Ensures atomic increments", "Synchronizes method access", "Prevents object mutation", "Guarantees main memory visibility and prevents instruction reordering"), 3); // D

            addQ("What is the time complexity of Arrays.sort() for primitive arrays in Java (Dual-Pivot Quicksort)?", "Java", "HARD",
                    List.of("O(n log n)", "O(n^2)", "O(n)", "O(log n)"), 0); // A

            addQ("Which functional interface in java.util.function takes one input argument and returns a boolean?", "Java", "HARD",
                    List.of("Function<T,R>", "Predicate<T>", "Consumer<T>", "Supplier<T>"), 1); // B

            addQ("In Spring Framework, what is the default bean scope of a Spring-managed component?", "Java", "HARD",
                    List.of("Prototype", "Request", "Singleton", "Session"), 2); // C

            addQ("Which Java feature introduced in Java 11 allows running a single-file source code without compiling?", "Java", "HARD",
                    List.of("JShell", "Var syntax", "Single-File Source-Code Execution", "Module System"), 2); // C

            addQ("What exception is thrown when an application attempts to use null where an object is required?", "Java", "HARD",
                    List.of("IllegalArgumentException", "ClassCastException", "IllegalStateException", "NullPointerException"), 3); // D

            // ==========================================
            // TOPIC 2: .NET (20 Questions)
            // ==========================================
            addQ("Which runtime engine executes compiled C# MSIL code in the .NET framework?", ".NET", "EASY",
                    List.of("JVM", "CLR (Common Language Runtime)", "V8 Engine", "KRE"), 1); // B

            addQ("What keyword is used to declare an asynchronous method in C#?", ".NET", "EASY",
                    List.of("await", "thread", "async", "task"), 2); // C

            addQ("Which memory management mechanism is used in .NET for automatic object cleanup?", ".NET", "EASY",
                    List.of("Garbage Collector (GC)", "Manual Free Memory", "Reference Counting", "RAII"), 0); // A

            addQ("Which tool is used for managing dependencies and packages in .NET projects?", ".NET", "EASY",
                    List.of("npm", "pip", "Maven", "NuGet"), 3); // D

            addQ("What is the extension of C# source code files?", ".NET", "EASY",
                    List.of(".cs", ".cpp", ".java", ".net"), 0); // A

            addQ("What is the lifetime of a dependency registered with builder.Services.AddScoped<T>() in ASP.NET Core?", ".NET", "MEDIUM",
                    List.of("Single instance across application", "One instance created per HTTP Request", "New instance every time requested", "One instance per thread"), 1); // B

            addQ("What is the primary difference between IEnumerable<T> and IQueryable<T> in Entity Framework Core?", ".NET", "MEDIUM",
                    List.of("IEnumerable executes queries on SQL Server", "IQueryable cannot filter data", "IQueryable executes SQL queries server-side on database while IEnumerable evaluates in-memory", "They behave identically"), 2); // C

            addQ("What is the role of Middleware components in an ASP.NET Core HTTP request pipeline?", ".NET", "MEDIUM",
                    List.of("To inspect, process, or short-circuit HTTP requests and responses", "To compile C# code into IL", "To execute database schema migrations", "To compress static images"), 0); // A

            addQ("Which syntax enables value types (structs) to represent null values in C#?", ".NET", "MEDIUM",
                    List.of("var keyword", "ref modifier", "out parameter", "Nullable<T> or T? operator"), 3); // D

            addQ("Which Entity Framework Core command applies pending migrations to the configured database?", ".NET", "MEDIUM",
                    List.of("dotnet ef database update", "dotnet ef migrations add", "dotnet ef schema apply", "dotnet ef build"), 0); // A

            addQ("What type of class member is shared across all instances of a class in C#?", ".NET", "MEDIUM",
                    List.of("virtual", "static", "override", "sealed"), 1); // B

            addQ("Which data structure in C# represents a key-value collection with fast lookup capability?", ".NET", "MEDIUM",
                    List.of("List<T>", "Queue<T>", "Dictionary<TKey, TValue>", "HashSet<T>"), 2); // C

            addQ("Which C# 10 feature allows defining namespaces for an entire file without enclosing curly braces?", ".NET", "HARD",
                    List.of("Global using directives", "Top-level statements", "Record structs", "File-scoped namespace declarations"), 3); // D

            addQ("In .NET Garbage Collector heap layout, which segment handles object allocations larger than 85,000 bytes?", ".NET", "HARD",
                    List.of("Large Object Heap (LOH)", "Generation 0", "Generation 1", "Ephemeral Segment"), 0); // A

            addQ("How does Kestrel web server function in a production ASP.NET Core application deployment?", ".NET", "HARD",
                    List.of("As an internal SQL optimizer", "As an in-process, cross-platform HTTP web server often reverse-proxied behind Nginx or IIS", "As a static asset bundler", "As a background worker"), 1); // B

            addQ("What C# feature enables adding new methods to existing types without modifying their original source code?", ".NET", "HARD",
                    List.of("Partial classes", "Operator overloading", "Extension Methods", "Abstract methods"), 2); // C

            addQ("Which pattern is built natively into ASP.NET Core for resolving component dependencies automatically?", ".NET", "HARD",
                    List.of("Singleton Pattern", "Factory Pattern", "Observer Pattern", "Dependency Injection (DI)"), 3); // D

            addQ("In C#, what is the difference between Task.Run() and Task.Factory.StartNew()?", ".NET", "HARD",
                    List.of("Task.Run is a lightweight shortcut configured with default task scheduler settings", "Task.Run starts OS threads directly", "Task.Factory.StartNew cannot run async code", "They are exact aliases"), 0); // A

            addQ("Which keyword in C# ensures that unmanaged resources are disposed of automatically when leaving scope?", ".NET", "HARD",
                    List.of("lock", "using", "fixed", "try-finally"), 1); // B

            addQ("What is the C# keyword used to pass an argument by reference where the called method MUST assign a value?", ".NET", "HARD",
                    List.of("ref", "in", "out", "params"), 2); // C

            // ==========================================
            // TOPIC 3: DSA (20 Questions)
            // ==========================================
            addQ("Which data structure operates strictly on a Last-In, First-Out (LIFO) access order?", "DSA", "EASY",
                    List.of("Queue", "Linked List", "Stack", "Heap"), 2); // C

            addQ("What is the worst-case time complexity of Binary Search on a sorted array of size n?", "DSA", "EASY",
                    List.of("O(n)", "O(1)", "O(n log n)", "O(log n)"), 3); // D

            addQ("Which tree traversal method visits nodes of a Binary Search Tree (BST) in ascending sorted order?", "DSA", "EASY",
                    List.of("In-order Traversal", "Pre-order Traversal", "Post-order Traversal", "Level-order Traversal"), 0); // A

            addQ("Which data structure uses First-In, First-Out (FIFO) queueing discipline?", "DSA", "EASY",
                    List.of("Stack", "Queue", "Binary Tree", "Graph"), 1); // B

            addQ("What is the time complexity of accessing an element in an array by index?", "DSA", "EASY",
                    List.of("O(n)", "O(log n)", "O(1)", "O(n^2)"), 2); // C

            addQ("What is the worst-case time complexity of QuickSort algorithm?", "DSA", "MEDIUM",
                    List.of("O(n log n)", "O(n)", "O(log n)", "O(n^2)"), 3); // D

            addQ("Which graph traversal algorithm utilizes a Queue data structure for node processing?", "DSA", "MEDIUM",
                    List.of("Breadth-First Search (BFS)", "Depth-First Search (DFS)", "Dijkstra's Algorithm", "Kruskal's Algorithm"), 0); // A

            addQ("Which data structure provides efficient O(1) peek and O(log n) insertion for Priority Queue implementation?", "DSA", "MEDIUM",
                    List.of("Unsorted Array", "Binary Heap", "Singly Linked List", "Circular Queue"), 1); // B

            addQ("What is the maximum number of nodes in a binary tree of height h (assuming root is at height 1)?", "DSA", "MEDIUM",
                    List.of("2^h", "2^(h-1)", "2^h - 1", "h^2"), 2); // C

            addQ("Which data structure is best suited to check for balanced parentheses in an expression string?", "DSA", "MEDIUM",
                    List.of("Array", "Queue", "Hash Table", "Stack"), 3); // D

            addQ("What is the best-case time complexity of Bubble Sort when applied to an already sorted array?", "DSA", "MEDIUM",
                    List.of("O(n)", "O(n^2)", "O(log n)", "O(n log n)"), 0); // A

            addQ("In a circular linked list, what node does the last node's next pointer point to?", "DSA", "MEDIUM",
                    List.of("null", "First Node (Head)", "Previous Node", "Itself"), 1); // B

            addQ("Which algorithm finds the shortest path from a single source vertex in a weighted graph with negative edge weights?", "DSA", "HARD",
                    List.of("Dijkstra's Algorithm", "Floyd-Warshall Algorithm", "Bellman-Ford Algorithm", "Prim's Algorithm"), 2); // C

            addQ("How is the Balance Factor of a node calculated in an AVL Tree?", "DSA", "HARD",
                    List.of("Count(Left Nodes) - Count(Right Nodes)", "Depth(Node) - Height(Node)", "Max(Left, Right)", "Height(Left Subtree) - Height(Right Subtree)"), 3); // D

            addQ("What two core conditions must a problem satisfy to be solvable using Dynamic Programming?", "DSA", "HARD",
                    List.of("Overlapping Subproblems and Optimal Substructure", "Greedy Choice Property and Sorting", "Divide and Conquer without Overlap", "Linear Time Complexity"), 0); // A

            addQ("What is the average time complexity of searching for an element in a Red-Black Tree with n nodes?", "DSA", "HARD",
                    List.of("O(n)", "O(log n)", "O(1)", "O(n log n)"), 1); // B

            addQ("Which algorithm is used to compute Minimum Spanning Tree (MST) by picking minimum weight edges?", "DSA", "HARD",
                    List.of("Dijkstra's Algorithm", "BFS Traversal", "Kruskal's Algorithm", "Topological Sort"), 2); // C

            addQ("What is the amortized time complexity of dynamic array reallocation when resizing capacity?", "DSA", "HARD",
                    List.of("O(n)", "O(log n)", "O(n^2)", "O(1)"), 3); // D

            addQ("In a Max-Heap, where is the largest element always located?", "DSA", "HARD",
                    List.of("At the Root Node", "At the Leaf Node", "In the Middle Node", "Random Position"), 0); // A

            addQ("What data structure is used in Floyd-Warshall algorithm to store all-pairs shortest paths?", "DSA", "HARD",
                    List.of("Priority Queue", "2D Distance Matrix", "Adjacency List", "Disjoint Set"), 1); // B

            // ==========================================
            // TOPIC 4: DATABASE (20 Questions)
            // ==========================================
            addQ("Which SQL command is used to retrieve rows from a relational database table?", "Database", "EASY",
                    List.of("GET", "EXTRACT", "SELECT", "OPEN"), 2); // C

            addQ("Which key constraint uniquely identifies each row record in a database table?", "Database", "EASY",
                    List.of("Foreign Key", "Composite Key", "Candidate Key", "Primary Key"), 3); // D

            addQ("What does the ACID acronym guarantee in database management systems?", "Database", "EASY",
                    List.of("Atomicity, Consistency, Isolation, Durability", "Accuracy, Control, Integrity, Data", "Access, Concurrency, Index, Durability", "Aggregation, Constraint, Isolation, Data"), 0); // A

            addQ("Which SQL statement is used to insert new records into a database table?", "Database", "EASY",
                    List.of("ADD ROW", "INSERT INTO", "PUSH DATA", "CREATE RECORD"), 1); // B

            addQ("Which SQL clause is used to filter query results based on specified conditions?", "Database", "EASY",
                    List.of("GROUP BY", "ORDER BY", "WHERE", "HAVING"), 2); // C

            addQ("Which SQL JOIN clause returns all records from the left table and matched records from the right table?", "Database", "MEDIUM",
                    List.of("RIGHT JOIN", "INNER JOIN", "FULL JOIN", "LEFT JOIN"), 3); // D

            addQ("Which Normal Form specifically eliminates transitive functional dependencies in database design?", "Database", "MEDIUM",
                    List.of("Third Normal Form (3NF)", "First Normal Form (1NF)", "Second Normal Form (2NF)", "Boyce-Codd Normal Form (BCNF)"), 0); // A

            addQ("What is the primary benefit of creating a B-Tree Index on a database table column?", "Database", "MEDIUM",
                    List.of("Reduces disk storage space", "Accelerates query search and data retrieval speed", "Prevents duplicate rows", "Encrypts column values"), 1); // B

            addQ("What happens to uncommitted changes when a database transaction executes ROLLBACK?", "Database", "MEDIUM",
                    List.of("Changes are committed to disk", "Database tables are dropped", "All uncommitted modifications are undone", "Only primary keys reset"), 2); // C

            addQ("Which SQL aggregate function calculates the total sum of numerical values in a column?", "Database", "MEDIUM",
                    List.of("COUNT()", "AVG()", "TOTAL()", "SUM()"), 3); // D

            addQ("Which SQL clause is used to filter aggregated data generated by GROUP BY?", "Database", "MEDIUM",
                    List.of("HAVING", "WHERE", "LIMIT", "FILTER"), 0); // A

            addQ("In Relational Database Design, what is a Foreign Key?", "Database", "MEDIUM",
                    List.of("A key that encrypts data", "A field in one table referencing Primary Key of another table", "A key used for web APIs", "A unique index constraint"), 1); // B

            addQ("Which Transaction Isolation Level prevents Dirty Reads, Non-Repeatable Reads, and Phantom Reads?", "Database", "HARD",
                    List.of("READ COMMITTED", "REPEATABLE READ", "SERIALIZABLE", "READ UNCOMMITTED"), 2); // C

            addQ("In SQL query optimization, what is a Covering Index?", "Database", "HARD",
                    List.of("An index created on Primary Key", "A clustered index spanning databases", "A full-text search index", "An index containing all queried columns without table lookup"), 3); // D

            addQ("How does Write-Ahead Logging (WAL) ensure durability during database crash recovery?", "Database", "HARD",
                    List.of("Logs data modifications to disk before actual table pages are written", "Saves automated database dumps", "Encrypts transaction log files", "Clears buffer pool memory"), 0); // A

            addQ("What database phenomenon occurs when a transaction reads uncommitted changes from another transaction?", "Database", "HARD",
                    List.of("Phantom Read", "Dirty Read", "Non-Repeatable Read", "Lost Update"), 1); // B

            addQ("In MySQL InnoDB storage engine, what is the default primary key indexing structure?", "Database", "HARD",
                    List.of("Secondary Hash Index", "B+ Tree Non-Clustered Index", "Clustered B+ Tree Index", "Full-Text Index"), 2); // C

            addQ("Which NOSQL database category does MongoDB belong to?", "Database", "HARD",
                    List.of("Column-Family Store", "Graph Database", "Key-Value Store", "Document-Oriented Database"), 3); // D

            addQ("What is the purpose of database Sharding in large scale web architectures?", "Database", "HARD",
                    List.of("Horizontal partitioning of database rows across multiple server instances", "Creating read-only backups", "Encrypting database tables", "Compressing table indexes"), 0); // A

            addQ("Which theorem states that a distributed data store can provide at most 2 out of 3: Consistency, Availability, Partition Tolerance?", "Database", "HARD",
                    List.of("ACID Theorem", "CAP Theorem", "BASE Theorem", "Two-Phase Commit Theorem"), 1); // B

            // ==========================================
            // TOPIC 5: OPERATING SYSTEM (20 Questions)
            // ==========================================
            addQ("Which core component of the Operating System manages hardware resources and CPU scheduling?", "Operating System", "EASY",
                    List.of("Shell", "Compiler", "Kernel", "Linker"), 2); // C

            addQ("What state does a process transition into while waiting for disk I/O completion?", "Operating System", "EASY",
                    List.of("Running State", "Ready State", "Terminated State", "Waiting / Blocked State"), 3); // D

            addQ("What defines a Deadlock condition in multi-processing operating systems?", "Operating System", "EASY",
                    List.of("Two or more processes are permanently blocked waiting for resources held by each other", "A process executing an infinite loop", "System memory leak", "Hard drive failure"), 0); // A

            addQ("What is the main role of an Operating System Command Interpreter?", "Operating System", "EASY",
                    List.of("To compile C++ code", "To provide a Shell UI for user commands", "To manage CPU L1 cache", "To clean temp files"), 1); // B

            addQ("Which system call is used in Unix-like operating systems to create a new child process?", "Operating System", "EASY",
                    List.of("exec()", "exit()", "fork()", "wait()"), 2); // C

            addQ("Which CPU scheduling algorithm allocates equal CPU time slices to processes in a circular queue?", "Operating System", "MEDIUM",
                    List.of("FCFS Scheduling", "Shortest Job First", "Priority Scheduling", "Round Robin Scheduling"), 3); // D

            addQ("What is the purpose of Virtual Memory in modern computer systems?", "Operating System", "MEDIUM",
                    List.of("Allows execution of processes exceeding physical RAM capacity using disk page files", "Increases CPU clock frequency", "Accelerates GPU rendering", "Scans memory for malware"), 0); // A

            addQ("What is thrashing in operating system virtual memory management?", "Operating System", "MEDIUM",
                    List.of("Corrupted disk sectors", "Excessive page swapping between RAM and disk resulting in degraded performance", "High CPU utilization", "Process starvation in queue"), 1); // B

            addQ("Which mechanism enables processes to exchange data and synchronize actions across address spaces?", "Operating System", "MEDIUM",
                    List.of("Symmetric Multiprocessing", "Direct Memory Access", "Inter-Process Communication (IPC)", "Interrupt Vector Table"), 2); // C

            addQ("What hardware signal is sent to the CPU by peripheral devices when attention is required?", "Operating System", "MEDIUM",
                    List.of("Trap", "Semaphore", "System Call", "Interrupt"), 3); // D

            addQ("Which page replacement algorithm replaces the page that has not been used for the longest period of time?", "Operating System", "MEDIUM",
                    List.of("Least Recently Used (LRU)", "First-In First-Out (FIFO)", "Optimal Page Replacement", "Most Recently Used (MRU)"), 0); // A

            addQ("What synchronization primitive uses integer counter variables to control access to shared resources?", "Operating System", "MEDIUM",
                    List.of("Spinlock", "Semaphore", "Mutex", "Condition Variable"), 1); // B

            addQ("Which algorithm is used by Operating Systems to avoid Deadlocks by testing resource allocation safety?", "Operating System", "HARD",
                    List.of("Peterson's Algorithm", "Snoopy Bus Protocol", "Banker's Algorithm", "LRU Page Replacement"), 2); // C

            addQ("What is a key difference between Kernel-Level Threads and User-Level Threads?", "Operating System", "HARD",
                    List.of("User-Level Threads execute faster in kernel mode", "Kernel-Level Threads do not support multi-core CPUs", "They are identical", "Kernel-Level Threads are scheduled directly by OS Kernel while User-Level Threads are managed by thread libraries"), 3); // D

            addQ("What is the Translation Lookaside Buffer (TLB) in CPU memory management architecture?", "Operating System", "HARD",
                    List.of("A high-speed hardware cache used to speed up virtual-to-physical address translation", "A disk cache for page files", "A register file inside ALU", "A backup page table in RAM"), 0); // A

            addQ("What condition causes a Page Fault in virtual memory address translation?", "Operating System", "HARD",
                    List.of("CPU executes invalid instruction", "Requested page is not currently loaded in physical RAM", "Hard drive is full", "RAM capacity is exceeded"), 1); // B

            addQ("In disk scheduling algorithms, how does SCAN (Elevator) algorithm move the disk arm?", "Operating System", "HARD",
                    List.of("Moves randomly across tracks", "Serves closest request first", "Moves from one end of disk to the other, servicing requests along the way", "Serves in order of arrival"), 2); // C

            addQ("What is Context Switching in CPU process scheduling?", "Operating System", "HARD",
                    List.of("Changing CPU clock speed", "Swapping disk page files", "Compiling kernel code", "Saving current process state and loading state of another process"), 3); // D

            addQ("Which mutual exclusion solution guarantees progress and bounded waiting for 2 concurrent processes?", "Operating System", "HARD",
                    List.of("Peterson's Algorithm", "Banker's Algorithm", "Dekker's Lock", "TestAndSet Instruction"), 0); // A

            addQ("What is the purpose of Spooling (Simultaneous Peripheral Operations On-Line) in OS I/O systems?", "Operating System", "HARD",
                    List.of("Encrypting disk drives", "Buffering I/O job data on disk so slow devices process at their own pace", "Defragmenting RAM memory", "Cleaning temporary log files"), 1); // B
        }
    }

    private void addQ(String questionText, String topic, String difficulty, List<String> options, int correctIndex) {
        List<QuestionRequest.OptionDto> optionDtos = List.of(
                new QuestionRequest.OptionDto(options.get(0), correctIndex == 0),
                new QuestionRequest.OptionDto(options.get(1), correctIndex == 1),
                new QuestionRequest.OptionDto(options.get(2), correctIndex == 2),
                new QuestionRequest.OptionDto(options.get(3), correctIndex == 3)
        );

        questionService.createQuestion(QuestionRequest.builder()
                .questionText(questionText)
                .questionType(Question.QuestionType.SINGLE_CHOICE)
                .difficultyLevel(difficulty)
                .marks(1.0)
                .topic(topic)
                .categoryId(1L)
                .createdByTeacherId(9L)
                .options(optionDtos)
                .build());
    }
}
