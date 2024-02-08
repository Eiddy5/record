package application;

import annotation.Autowried;
import processor.AutowriedProcessor;
import service.PersonService;

public class Main {
    @Autowried
    private PersonService personService;

    public static void main(String[] args) {
        Main main = new Main();
        AutowriedProcessor.processAnnotations(main.getClass());
        main.personService.getPersonList();
    }
}
