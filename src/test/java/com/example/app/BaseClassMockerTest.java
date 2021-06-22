package com.example.app;

import org.mockito.Mockito;
import org.junit.jupiter.api.Test;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class BaseClassMockerTest {
    public class Parent {        
        String name = "Ghosh";

        public String getName() {
            return this.name;
        }
    }
    public class Child1 extends Parent {        
        String name = "Neil";        

        @Override
        public String getName() {
            String parentName = super.getName();
            return parentName +" "+ this.name;
        }
    }

    /**
     * Same as the Child1 but the super.getName() called is wrapped in a separate method
     * https://stackoverflow.com/a/43901723
     */
    public class Child2 extends Parent {        
        String name = "Neil";        
    
        @Override
        public String getName() {
            String parentName = getParentName();
            return parentName +" "+ this.name;
        }

        /**
         * Wrapper around the super call.
         * @return Name of the parent
         */
        String getParentName() {
            return super.getName();
        }        
    }

    /**
     * This test fails as the super.getName() could not be mocked
     * https://stackoverflow.com/a/3838664
     */
    @Test
    void shouldReturnFullName() {
        // Prevent/stub logic in super.save()
        Child1 child = Mockito.spy(new Child1());
        Mockito.doReturn("someParent").when((Parent)child).getName();

        assertThat(child.getName(), equalTo("someParent Neil"));        
    }

    /**
     * Stubs one method and calls the real method 
     */
    @Test
    void shouldReturnFullName_ThatWorks() {
        // Prevent/stub logic in super.save()
        Child2 child = Mockito.spy(new Child2());
        Mockito.doReturn("someParent").when(child).getParentName();
        Mockito.doCallRealMethod().when(child).getName();

        assertThat(child.getName(), equalTo("someParent Neil"));        
    }

}
