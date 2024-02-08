package service;

import entry.Person;

import java.util.List;

public class PersonService {

    private List<Person> personList;

    public PersonService() {
        this.personList = List.of(
                new Person("张三", "男", 18),
                new Person("李四", "男", 19),
                new Person("王五", "女", 20),
                new Person("赵六", "女", 21));
    }

    public void addPerson(Person person) {
        this.personList.add(person);
    }

    public Person deletePersonByName(String name) {
        for (Person p : personList) {
            if (p.getName().equals(name)) {
                personList.remove(p);
                return p;
            }
        }
        return null;
    }

    public List<Person> getPersonList() {
        return personList;
    }

}
