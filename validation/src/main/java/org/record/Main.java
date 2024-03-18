package org.record;

import org.record.bean.Person;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class Main {
    public static void main(String[] args) {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Person person = new Person();
        person.setAge(25);

        Set<ConstraintViolation<Person>> validate = validator.validate(person);
        for (ConstraintViolation<Person> constraintViolation : validate) {
            System.out.println(constraintViolation.getMessage());
        }
        factory.close();
    }
}
