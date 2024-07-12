package ca.jrvs.insurance_api.controller;

import ca.jrvs.insurance_api.model.Person;
import ca.jrvs.insurance_api.service.PersonService;
import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/insurance_api")
public class PersonController {

  @Autowired
  private final PersonService service;

  public PersonController(PersonService service) {
    this.service = service;
  }

  @PostMapping("person")
  @ResponseStatus(HttpStatus.CREATED)
  public void postPerson(@RequestBody Person person) {
    service.save(person);
  }

  @PostMapping("people")
  @ResponseStatus(HttpStatus.CREATED)
  public void postPeople(@RequestBody List<Person> people) {
    service.saveAll(people);
  }

  @GetMapping("person/{id}")
  public ResponseEntity<Person> getPerson(@PathVariable ObjectId id) {
    Optional<Person> o = service.findOne(id);
    if (o.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    return ResponseEntity.ok(o.get());
  }

  @GetMapping("people/{ids}")
  public List<Person> getPeople(@PathVariable List<ObjectId> ids) {
    return service.findAll(ids);
  }

  @GetMapping("people")
  public List<Person> getPeople() {
    return service.findAll();
  }

  @DeleteMapping("person/{id}")
  public void deletePerson(@PathVariable ObjectId id) {
    service.delete(id);
  }

  @DeleteMapping("people/{ids}")
  public void deletePeople(@PathVariable List<ObjectId> ids) {
    service.delete(ids);
  }

  @DeleteMapping("people")
  public void deletePeople() {
    service.deleteAll();
  }

  @PatchMapping("person")
  public void patchPerson(@RequestBody Person person) {
    service.update(person);
  }

  @PatchMapping("people")
  public void patchPeople(@RequestBody List<Person> people) {
    service.update(people);
  }

  @GetMapping("count")
  public long getCount() {
    return service.count();
  }

  @GetMapping("avgAge")
  public double getAvgAge() {
    return service.getAverageAge();
  }

  @GetMapping("maxCars")
  public int getMaxCars() {
    return service.getMaxCars();
  }

  @GetMapping("canadianFordCustomers")
  public int getCanadianFordCustomerAmount() {
    return service.getCanadianFordCustomerAmount();
  }

  @GetMapping("uninsuredDrivers")
  public List<Person> getUninsuredDrivers() {
    return service.getUninsuredDrivers();
  }

  @GetMapping("mostPopularMake")
  public String getMostPopularMake() {
    return service.getMostPopularMake();
  }

  @GetMapping("numUninsuredMinors")
  public int getUninsuredMinorCount() {
    return service.getUninsuredMinorCount();
  }

  @GetMapping("numCarlessAdults")
  public int getCarlessAdults() {
    return service.getCarlessAdults();
  }

  @GetMapping("numUninsuredFastDrivers")
  public int getUninsuredFastDrivers() {
    return service.getUninsuredFastDrivers();
  }

}
