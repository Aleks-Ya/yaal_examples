# 02-refer-another-obo-file

## Task
Refer to an OBO file from another OBO file.

## Setup
1. Create 1st OBO file:
	1. Create new ontology:
		1. Ontology IRI: `http://www.kata.org/ontologies/animals.obo`
	2. Creat terms:
		1. `Kind`
		2. `Code`
2. Create 2nd OBO file:
	1. Create new ontology:
		1. Ontology IRI: `http://www.kata.org/ontologies/cats.obo`
	2. Creat terms:
		1. `Cat` 
		1. `Domestic`, SubClass of `Kind`
		2. `Wild`, SubClass of `Kind`
		3. `EuropeanCode`, SubClass of `Code`
		4. `AmericanCode`, SubClass of `Code`

NOT FINISHED!!!!
