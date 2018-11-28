# Fair Housing Assignment
Repo for fair housing assignment for social computing course.

## Running the code
Run the code with two arguments to exercise a selected initialization and matching strategy 
 against a test file.
 
The first argument should be a test input file path. For example "src/test/resources/test1.txt"

The second argument should be a string number that represents the combination of 
initialization and matching that you want to use to solve the housing assignment.

The following options are supported:

- **"1"** - ___Fair Housing___ - OwnersInitialization and FairOwnerMatching
- **"2"** - ___Default___ - No Owners and Default Matching
- **"3"** - ___ Poorest Housing___  - No Owners and PoorestAgentWinsMatching
- **"4"** - ___ Agent Priority Housing___  - No Owners and Default Matching with Agent Allocation

See the research paper associated with this repo for further explanation of each option.