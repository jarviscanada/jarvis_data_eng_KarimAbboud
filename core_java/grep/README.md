# Java Grep App
This application mimics the functionality of Linux's command-line utility, grep, by recursively searching through a provided starting directory to locate files that contain a given regex expression and output the specific lines in the files that match the expression. This app is built using Core Java and is available through a Docker image available on Docker Hub (See Deployment section for more information). Application components follow Maven's standard directory layout for Java applications. The application outputs the result of its search into a user-provided file.

Technologies: Core Java, Maven, Docker, IntelliJ IDEA, Git, GitHub

# Quick Start
It is recommended that this application be run through a Docker container. Once you have Docker installed on your system, please run the following commands in your terminal.
```
# Dependencies: Docker
# Retrieve the Docker image from Docker Hub
docker pull karimabboud/grep

# Run the Docker container
docker run --rm -v `pwd`/data:/data -v `pwd`/log:/log karimabboud/grep \
[Your regex expression] [Path of your starting directory] [Path to output file]

# Verify the output of your search through your output file
```

# Implementation
## Pseudocode
```
matchedLines = []
for file in listFilesRecursively(rootDir)
  for line in readLines(file)
   if containsPattern(line)
     matchedLines.add(line)
writeToFile(matchedLines)
```

## Performance Issue
As demonstrated in the pseudocode above, this application's base implementation relies on compiling a complete list of all files originating from a specific directory and storing that list in memory for iteration. For small file systems, this is sufficient, but large file systems could cause memory issues for the Java Virtual Machine (JVM). Increasing heap memory allocated to the JVM is a potential solution, but may not scale as file systems continue to grow.
A potential solution to this issue includes reconstructing the application using Java streams and lambda functions rather than an iterative approach to take advantage of stream APIs that deal with files like Files.list() implementing a lazy approach to populating the stream. A potential implementation of this approach is available as an alternative in this repository (JavaGrepLambdaImp.java).

# Testing
Testing was performed manually by comparing the application output file to the results of Linux's native grep command given the same starting point and search parameters.

# Deployment
The application is available either through this GitHub repository or alternatively through a Docker image located on Docker Hub.
The image is located at the following URL:
```
https://hub.docker.com/r/karimabboud/grep
```

Please see the Quick Start section above for instructions on retrieving this image from Docker Hub for use on your local machine.

# Improvement
- The stream approach we have taken to resolving the performance issue listed above can be improved, because while it populates the stream lazily, it still eventually requires the final output of the stream to be stored in a list structure, which has implications on memory performance. Ideally, the application could work entirely through lazily populated streams from start to finish to avoid using excessive memory until the application returns its final output. This would require modifying some of the base interfaces that our application implements.
- The basic linux grep command offers recursive searching through directories using an option to the command. This application implements recursive search by default, which may not always be desirable if users know that the information they are seeking is in the current directory.
- This documentation could be improved to provide an alternative deployment method to Docker for users that prefer running Java applications directly through a JAR file.
