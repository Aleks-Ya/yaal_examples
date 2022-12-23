/**
 * Solving error when running EclipseLink tests from different packages.<br/>
 * Approach 1: "manytests1" package (use @EnableAutoConfiguration).<br/>
 * Approach 2: "manytests2" package (use @EntityScan and @EnableJpaRepositories).<br/>
 * Run entire package of tests ("manytests1" or "manytests2").
 */
package jpa.eclipselink.test_hierarchy;