# Fair Housing Assignment
Repo for fair housing assignment for social computing course.

## Running the code
###Using Gradle
Gradle is included in this repo and can be used to run the algorithm against a provided test file
or against another test file provided in a source file of the correct format.

To run using the default test file and the Fair Housing Allocation Algorithm run the following command:

```
./gradlew runApp
```
To pass in a different test file or to run with a different experimental algorithm, 
you can pass in a `fileName` or `testName` 
(1, 2, 3, 4 are supported test names. See below for more information.):

```
./gradlew runApp -PfileName=test1.txt -PtestName=2
```

###Using intelliJ or Jar
Run the code with two arguments to exercise a selected initialization and matching strategy 
 against a test file.
 
The first argument should be a test input file path. For example "src/test/resources/test1.txt"

The second argument should be a string number that represents the combination of 
initialization and matching that you want to use to solve the housing assignment.

The following options are supported:
- **"1"** - ___Default___ - No Owners and Default Matching
- **"2"** - ___Fair Housing___ - OwnersInitialization and FairOwnerMatching
- **"3"** - ___ Poorest Housing___  - No Owners and PoorestAgentWinsMatching
- **"4"** - ___ Agent Priority Housing___  - No Owners and Default Matching with Agent Allocation
Option 4 is the one selected by default and used in the paper.

See the research paper associated with this repo for further explanation of each option.