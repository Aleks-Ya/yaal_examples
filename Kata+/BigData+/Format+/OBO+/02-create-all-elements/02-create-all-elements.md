# 02-create-all-elements

NOT FINISHED!!!

## Task
Create all possible elements in an OBO file:
1. Class
2. Axioms
	1. SubClassOf
	2. EquivalentClasses
	3. DisjointClasses
	4. DisjointUnion
3. Property
	1. Object Property
	2. Data Property
	3. Annotation Property
4. Datatype


## Steps
Use Protege OWL editor
1. Create new ontology:
	1. Ontology IRI: `http://www.kata.org/ontologies/courses.obo`
2. Create terms: `Course`, `ComputerScienceCourse`, `BiologyCourse`, `Program`, `Curriculum`
3. Create axioms: 
	1. SubClassOf: `ComputerScienceCourse` and `BiologyCourse` are sub-classes of `Course`
	2. EquivalentClasses: `Program` is equal to `Course`
	3. DisjointClasses: `Curriculum` is not equal to `Course` and `Program`
	4. DisjointUnion: sub-classes of `Course` should be disjointed
3. Save in file `courses_all.obo` in `OBO Format`

## Cleanup
1. Delete `courses_all.obo` file
