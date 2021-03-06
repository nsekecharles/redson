import com.sidemash.redson.Json;
import com.sidemash.redson.JsonEntry;
import com.sidemash.redson.JsonObject;

import java.util.Optional;

/**
 * Created by Serge Martial on 09/06/2015.
 */
public class Person {

    /*
    static {
        Json.registerWriter(Person.class, (person, jsonValue) -> JsonObject.of(
                JsonEntry.of("name", person.name),
                JsonEntry.of("age", person.age),
                JsonEntry.of("height", person.height)
        ));
        Json.registerReader(Person.class, jsonValue ->
                        new Person(
                                jsonValue.get("name").asString(),
                                jsonValue.get("age").asInt(),
                                jsonValue.get("height").asFloat()
                        )
        );
    }

    */
    public String name;
    public Integer age;
    public String password = null; // "TopSecret";
    public Float height;
    public Optional<Integer> nb = Optional.of(2);

    public Person(){
        this("Serge", 24, 12F);
    }
    public Person(String name, Integer age, Float height) {
        this.name = name;
        this.age = age;
        this.height = height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (name != null ? !name.equals(person.name) : person.name != null) return false;
        if (age != null ? !age.equals(person.age) : person.age != null) return false;
        if (password != null ? !password.equals(person.password) : person.password != null)
            return false;
        return !(height != null ? !height.equals(person.height) : person.height != null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (age != null ? age.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (height != null ? height.hashCode() : 0);
        return result;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public String getPassword() {
        return password;
    }

    public Float getHeight() {
        return height;
    }

    public Optional<Integer> getNb() {
        return nb;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", password='" + password + '\'' +
                ", height=" + height +
                '}';
    }
}
