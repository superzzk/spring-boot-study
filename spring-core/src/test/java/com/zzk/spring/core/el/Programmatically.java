package com.zzk.spring.core.el;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author zhangzhongkun02
 * @date 2023/6/22 3:03 PM
 */
class Programmatically {
    @Test
    void parseExpression(){
        ExpressionParser expressionParser = new SpelExpressionParser();

        Expression expression = expressionParser.parseExpression("'Any string'");
        assertEquals("Any string", expression.getValue());

        // arithmetic
        expression = expressionParser.parseExpression("1 + 1");
        assertEquals(2, expression.getValue());

        // constructor
        expression = expressionParser.parseExpression("new String('Any string').length()");
        assertEquals(10, expression.getValue());

        expression = expressionParser.parseExpression("'Any string'.replace(\" \", \"\").length()");
        assertEquals(9, expression.getValue(Integer.class));
    }

    @Test
    void evaluation_context(){
        Car car = new Car();
        car.setMake("Good manufacturer");
        car.setModel("Model 3");
        car.setYearOfProduction(2020);

        ExpressionParser expressionParser = new SpelExpressionParser();
        Expression expression = expressionParser.parseExpression("model");

        EvaluationContext context = new StandardEvaluationContext(car);
        assertEquals("Model 3", expression.getValue(context));

        // build context is expensive, if it's often change, can use:
        assertEquals("Model 3", expression.getValue(car));

        expression = expressionParser.parseExpression("yearOfProduction > 2005");
        assertTrue(expression.getValue(car, Boolean.class));
    }

    @Test
    void set_value(){
        Car car = new Car();
        car.setMake("Good manufacturer");
        car.setModel("Model 3");
        car.setYearOfProduction(2014);

        CarPark carPark = new CarPark();
        carPark.getCars().add(car);

        StandardEvaluationContext context = new StandardEvaluationContext(carPark);
        ExpressionParser expressionParser = new SpelExpressionParser();
        expressionParser.parseExpression("cars[0].model").setValue(context, "Other model");
        assertEquals("Other model", car.getModel());
    }

    @Test
    void type_conversion_demo(){
        class Simple {
            public List<Boolean> booleanList = new ArrayList<Boolean>();
        }

        Simple simple = new Simple();
        simple.booleanList.add(true);

        StandardEvaluationContext simpleContext = new StandardEvaluationContext(simple);
        ExpressionParser parser = new SpelExpressionParser();
        // false is passed in here as a string.  SpEL and the conversion service will
        // correctly recognize that it needs to be a Boolean and convert it
        parser.parseExpression("booleanList[0]").setValue(simpleContext, "false");

        // b will be false
        assertFalse(simple.booleanList.get(0));
    }

    @Test
    void name(){
        Inventor tesla = new Inventor("tesla", new Date(), "China");
        tesla.setPlaceOfBirth(new PlaceOfBirth("Beijing"));
        tesla.setInventions(new String[]{"A","B","C","Induction motor"});

        StandardEvaluationContext context = new StandardEvaluationContext(tesla);
        ExpressionParser parser = new SpelExpressionParser();
        // evals to 1856
        int year = (Integer) parser.parseExpression("Birthdate.Year + 1900").getValue(context);
        System.out.println(year);

        String city = (String) parser.parseExpression("placeOfBirth.City").getValue(context);
        assertEquals("Beijing", city);

        // evaluates to "Induction motor"
        String invention = parser.parseExpression("inventions[3]").getValue(context, String.class);
        assertEquals("Induction motor", invention);
    }

    @Data
    public class Engine {
        private int capacity;
        private int horsePower;
        private int numberOfCylinders;

        // Getters and setters
    }

    @Data
    public class Car {
        private String make;
        private String model;
        private Engine engine;
        private int yearOfProduction;
        private int horsePower;

        // Getters and setters
    }

    @Data
    public class CarPark {
        private List<Car> cars = new ArrayList<>();

        // Getter and setter
    }

    @Data
    @NoArgsConstructor
    public class Inventor {

        private String name;
        private String nationality;
        private String[] inventions;
        private Date birthdate;
        private PlaceOfBirth placeOfBirth;

        public Inventor(String name, String nationality)
        {
            GregorianCalendar c= new GregorianCalendar();
            this.name = name;
            this.nationality = nationality;
            this.birthdate = c.getTime();
        }
        public Inventor(String name, Date birthdate, String nationality) {
            this.name = name;
            this.nationality = nationality;
            this.birthdate = birthdate;
        }
    }

    @Data
    public class PlaceOfBirth {

        private String city;
        private String country;

        public PlaceOfBirth(String city) {
            this.city=city;
        }
        public PlaceOfBirth(String city, String country)
        {
            this(city);
            this.country = country;
        }
    }

    @Data
    public static class Society {

        private String name;

        public static String Advisors = "advisors";
        public static String President = "president";

        private List<Inventor> members = new ArrayList<Inventor>();
        private Map officers = new HashMap();

        public boolean isMember(String name)
        {
            boolean found = false;
            for (Inventor inventor : members) {
                if (inventor.getName().equals(name))
                {
                    found = true;
                    break;
                }
            }
            return found;
        }
    }
}
