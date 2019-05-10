# Graph planarization application
This application was made to simplify search of planar subgraphs.
## Getting Started
Start using this program isn't a problem. Just follow few instructions.
### Downloading
First of all, you need to get a copy of [program](https://drive.google.com/file/d/1bt8ghkTi2m-VGXnqwkM_4XT4SO8-Ybq2/view?usp=sharing).
Don't forget that you need to have JRE 8 or newer version to run this application. If you haven't JRE 8 or newer version you should follow this [link](https://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html).
### Input file preparation
You need to create file with adjacency matrix of your graph. The format of matrix is like that:
```
0 1 1 1 0 0 0
1 0 1 0 1 0 1
1 1 0 1 1 1 1
1 0 1 0 1 1 1
0 1 1 1 0 1 0
0 0 1 1 1 0 1
0 1 1 1 0 1 0
```
### Application launching
To launch this application move to directory with jar-file and file with adjacency matrix and put command in a command line:
```
java -jar Planarization.jar example
```
Where example is a file with adjacency matrix.
## What application print
Because algorithm has few parts, application prints all computation phases.
### Search of hamiltonian cycle
First action of application is search of hamiltonian cycle. It prints all steps and in the end of search it prints hamiltonian cycle that was found.
If graph hasn't hamiltonian cycle application will stop with relevant message!
### Matrix of crossing creation
After finding a hamiltonian cycle application forms matrix of crossing where each element is a rib.
### Family of sets "Psi" search
Application search all inner steady sets that is called "Psi".
### Maximum dicotyledonous subgraph
Looking through pairs of "Psi" sets application finds maximum dicotyledonous subgraph and it prints only first one.
After it application removes ribs that are contained in printed maximum dicotyledonous subgraph, remove empty sets and sets that are subsets of other existing sets.
New family of sets "Psi" is using to find new maximum dicotyledonous subgraph.
This procedure continues before the family of sets "Psi" doesn't become empty.
### Result
All printed pairs of maximum dicotyledonous subgraph create set of planar subgraphs.# Graph-planarization-application
