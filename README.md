Once you clone the project do:

1. From the root folder run "sbt"
2. (Optional) In sbt shell run "eclipse" to create an eclipse project that can be imported into eclipse
3. In sbt shell run "compile"
4. In sbt shell run "run" . 

Functionalities of various classes from the project are briefly described below

Main.scala -             Main entry point to the project
Shingle.scala -          Created hashed k-shingles of all documents present under src/main/resources/documents  directory.  k=10 defined in Main.scala
CompareSet.scala -       Computes Jaccard similarity of two given sets
MinHashing.scala -       Computes MinHash signature of a given size (n) for a given set of hashed shingles. n=8 used in Main.scala
				         Minhash signature is computed by applying randomnly genererated n hash functions on each element of the set and then selecting the minimum hash value.
				         Thus, the output MinHash Signature contains n minhash values corresponding to n hash functions.
CompareSignature.scala - Compares to given minHash signature set and returns the similarity measure (ratio of matching minhash values and total minhash values)

All spark logs are going into the spark.log.
# ID2222-DataMining

