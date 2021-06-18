package com.example.app;

import static java.time.LocalDateTime.now;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

/**
 * https://stackoverflow.com/a/29793460
 * https://www.petrikainulainen.net/programming/testing/junit-5-tutorial-writing-assertions-with-hamcrest/
 * https://www.vogella.com/tutorials/Mockito/article.html
 * https://www.baeldung.com/hamcrest-bean-matchers
 */
class ReflectionMatchersTest {

    public class Person {
        LocalDateTime timestamp;
        String name;
        Integer age;

        public Person(String name, Integer age) {
            this.timestamp = now();
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return this.name;
        }
    }

    @Test
    void shouldContainSameElementsInAnyOrder() {
        List<String> expectedFields = List.of("StringA", "StringB");
        List<String> actualList = List.of("StringB", "StringA");

        // Compare the size so that the test does not pass if actualList is a subset of
        // expected list
        assertThat(expectedFields.size(), equalTo(actualList.size()));
        assertThat(expectedFields, containsInAnyOrder(actualList.toArray()));
    }

    @Test
    void shouldContainSameFieldsValuesIgnoreTimeStamp() {
        Person expected = new Person("Neil", 20);
        Person actual = new Person("Neil", 20);

        // Timestamp does not match
        assertFalse(new ReflectionEquals(expected).matches(actual));

        // Ignores timestamp
        assertTrue(new ReflectionEquals(expected, "timestamp").matches(actual));

        // This needs public constructors and accesor methods
        assertThat(actual, samePropertyValuesAs(expected));
    }

    @Test
    void shouldContainFields() {
        Person person = new Person("Neil", 20);

        // You need a getter in POJO
        // Needs a public class/constructor even for inner class
        assertThat(person, hasProperty("name", equalTo("Neil")));

    }
}