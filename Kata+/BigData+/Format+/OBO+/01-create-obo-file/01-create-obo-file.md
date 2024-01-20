# 01-create-obo-file

## Task
Create an ontology in an OBO file:
1. Terms: 
	1. `Course`
	2. `ComputerScienceCourse` (extends `Course`)
	3. `BiologyCourse` (extends `Course`)

## Setup
Use Protege OWL editor
1. Create new ontology:
	1. Ontology IRI: `http://www.kata.org/ontologies/courses.obo`
2. Creat terms:
	1. `Course`
	2. `ComputerScienceCourse`, SubClass of `Course`
	3. `BiologyCourse`, SubClass of `Course`
3. Save in file `courses.obo` in `OBO Format`

## Cleanup
1. Delete `courses.obo`
