package io.github.nisarg1184.spring.rest.actuator;

import io.github.nisarg1184.spring.rest.client.PersonClient;
import io.github.nisarg1184.spring.rest.model.Person;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class PersonAppHealthIndicator implements HealthIndicator {

    private final PersonClient personClient;

    public PersonAppHealthIndicator(PersonClient personClient) {
        this.personClient = personClient;
    }

    @Override
    public Health health() {
        try {
            ResponseEntity<Person[]> personResponseEntity = personClient.fetchPeople();
            if(personResponseEntity.getStatusCode().is2xxSuccessful()) {
                return Health.up().build();
            } else {
                return Health.down().build();
            }
        } catch (Exception e) {
            return Health.down(e).build();
        }
    }
}
