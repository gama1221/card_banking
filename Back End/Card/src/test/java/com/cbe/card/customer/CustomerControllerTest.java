package com.cbe.card.customer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerControllerTest {
    Calculator calculator = new Calculator();

    @Test
    @DisplayName("Test Two Numbers")
   void testAddTwoNumbers(){
       assertThat(calculator.addTwoNumbers(210,30)).isEqualTo(240);
   }
   @Test
   @DisplayName("Test Two Numbers using assert equal")
   void testAddTwoNumbersA(){
       assertEquals(calculator.addTwoNumbers(12,12), 24);
   }
}
class Calculator{
    int addTwoNumbers(int a, int b){
        return a + b;
    }
}