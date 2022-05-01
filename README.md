# hashset
Ex3 of the Java OOP course


## File description
- ClosedHashSet.java - a hash-set based on closed-hashing with quadratic probing.
- OpenHashSet.java - a hash-set based on chaining. Extends SimpleHashSet.
- LinkedStringList.java - a wrapper-class for openHashSet that has-a LinkedList<String> 
- SimpleHashSet.java - an abstract class implementing SimpleSet
- CollectionFacadeSet.java - Wraps an underlying Collection
- SimpleSetPerformanceAnalyzer.java - Measures the run-times requested in the Performance Analysis section.
____________________



## Design
OpenHashSet and ClosedHashSet extend SimpleHashSet which implements SimpleSet  
CollectionFacadeSet implements SimpleSet  
LinkedStringList is a wrapper-class for OpenHashSet table  
SimpleSetPerformanceAnalyzer has a main method which analyzes run times of data structures  
____________________



## Implementation details
- How you implemented OpenHashSet’s table?  
Defined a wrapper-class that has-a LinkedList<String> and delegates methods to it, and have an array of that class instead.  
I chose this because i had no idea at the begenning how to write the code of the CollectionFacadeSet in order to extend it.  
  
- How you implemented the deletion mechanism in ClosedHashSet?  
I put an empty string ("") instead of the deleted value.  
