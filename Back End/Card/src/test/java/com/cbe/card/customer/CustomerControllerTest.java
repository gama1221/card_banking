package com.cbe.card.customer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CustomerControllerTest {
    Calculator calculator = new Calculator();
    @Test
    @DisplayName("Test Two Numbers")
   void testAddTwoNumbers(){
       assertThat(calculator.addTwoNumbers(210,30)).isEqualTo(240);
   }
}
class Calculator{
    int addTwoNumbers(int a, int b){
        return a + b;
    }
}