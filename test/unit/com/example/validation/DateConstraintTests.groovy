package com.example.validation

import org.junit.Test

/**
 * Created by naga on 2014/02/23.
 */
class DateConstraintTests extends AbstractConstraintTests {

    @Override
    protected Class<?> getConstraintClass() {
        return DateConstraint.class;
    }

    public void testValidation() {

        testConstraintMessageCodes(
                getConstraint("testString", Boolean.TRUE),
                "wrong_date",
                ["testClass.testString.date.error","testClass.testString.date.invalid"] as String[] ,
                ["testString",TestClass.class,"wrong_date"] as Object[]);

        testConstraintMessageCodes(
                getConstraint("testString", Boolean.TRUE),
                "2014/02/29",
                ["testClass.testString.date.error","testClass.testString.date.invalid"] as String[] ,
                ["testString",TestClass.class,"2014/02/29"] as Object[]);

        testConstraintPassed(
                getConstraint("testString", Boolean.TRUE),
                "20140103");

        testConstraintPassed(
                getConstraint("testString", Boolean.TRUE),
                "2014/01/03");

        testConstraintPassed(
                getConstraint("testString", Boolean.TRUE),
                "2014/1/3");

        testConstraintFailed(
                getConstraint("testString", Boolean.TRUE),
                "201401003");

        testConstraintFailed(
                getConstraint("testString", Boolean.TRUE),
                "2014/001/003");

        testConstraintFailed(
                getConstraint("testString", Boolean.TRUE),
                "2014//");

        testConstraintFailed(
                getConstraint("testString", Boolean.TRUE),
                "2014-12-12");

        testConstraintFailed(
                getConstraint("testString", Boolean.TRUE),
                "2014/13/01");

    }

    public void testNullValue() {
        // must always pass for null value
        testConstraintPassed(
                getConstraint("testString", Boolean.TRUE),
                null);
    }

    public void testBlankString() {
        // must always pass for blank value
        testConstraintPassed(
                getConstraint("testString", Boolean.TRUE),
                "");
    }

    public void testCreation() {
        DateConstraint constraint = new DateConstraint();
        assertEquals(DateConstraint.DATE_CONSTRAINT, constraint.getName());
        assertTrue(constraint.supports(String.class));
        assertFalse(constraint.supports(null));
        assertFalse(constraint.supports(Long.class));

        try {
            getConstraint("testString", "wrong");
            fail("DateConstraint must throw an exception for non-boolean parameters.");
        } catch (IllegalArgumentException iae) {
            // Great
        }
    }
}
