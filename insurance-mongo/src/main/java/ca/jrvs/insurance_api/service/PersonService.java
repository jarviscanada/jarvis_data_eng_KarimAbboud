package ca.jrvs.insurance_api.service;

import ca.jrvs.insurance_api.model.Address;
import ca.jrvs.insurance_api.model.Car;
import ca.jrvs.insurance_api.model.Person;
import ca.jrvs.insurance_api.repository.PersonRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

  @Autowired
  private final PersonRepository repo;

  public PersonService(PersonRepository repo) {
    this.repo = repo;
  }

  public void save(Person person) {
    repo.save(person);
  }

  public void saveAll(List<Person> people) {
    repo.saveAll(people);
  }

  public Optional<Person> findOne(ObjectId id) {
    return repo.findById(id);
  }

  public List<Person> findAll(List<ObjectId> ids) {
    return repo.findAllById(ids);
  }

  public List<Person> findAll() {
    return repo.findAll();
  }

  public void delete(ObjectId id) {
    repo.deleteById(id);
  }

  public void delete(List<ObjectId> ids) {
    repo.deleteAllById(ids);
  }

  public void deleteAll() {
    repo.deleteAll();
  }

  public void update(Person person) {
    ObjectId id = person.getId();
    Person updatedPerson = new Person(id, null, null, 0,
        null, null, null);
    Optional<Person> previousData = repo.findById(id);

    // The basic save() operation can be used to quickly perform update operations, but it
    // relies on proper user input to the update API call. This approach, while lengthy works
    // regardless of whether user input follows proper formatting or not.
    if (previousData.isPresent()) {
      Person previousPerson = previousData.get();
      String previousFirstName = previousPerson.getFirstName();
      String previousLastName = previousPerson.getLastName();
      int previousAge = previousPerson.getAge();
      Address previousAddress = previousPerson.getAddressEntity();
      Date previousCreatedAt = previousPerson.getCreatedAt();
      Boolean previousInsurance = previousPerson.getInsurance();
      List<Car> previousCars = previousPerson.getCarEntities();

      if (person.getFirstName() == null) {
        updatedPerson.setFirstName(previousFirstName);
      } else {
        updatedPerson.setFirstName(person.getFirstName());
      }

      if (person.getLastName() == null) {
        updatedPerson.setLastName(previousLastName);
      } else {
        updatedPerson.setLastName(person.getLastName());
      }

      if (person.getAge() == 0) {
        updatedPerson.setAge(previousAge);
      } else {
        updatedPerson.setAge(person.getAge());
      }

      if (person.getAddressEntity() == null) {
        updatedPerson.setAddressEntity(previousAddress);
      } else {
        updatedPerson.setAddressEntity(person.getAddressEntity());
      }

      updatedPerson.setCreatedAt(previousCreatedAt);

      if (person.getInsurance() == null) {
        updatedPerson.setInsurance(previousInsurance);
      } else {
        updatedPerson.setInsurance(person.getInsurance());
      }

      if (person.getCarEntities() == null) {
        updatedPerson.setCarEntities(previousCars);
      } else {
        updatedPerson.setCarEntities(person.getCarEntities());
      }
    }
    repo.save(updatedPerson);
  }

  public void update(List<Person> people) {
    for (Person person : people) {
      update(person);
    }
  }

  public long count() {
    return repo.count();
  }

  public double getAverageAge() {
    return repo.getAverageAge();
  }

  public int getMaxCars() {
    return repo.getMaxCars();
  }

  public int getCanadianFordCustomerAmount() {
    Integer fordCustomers = repo.getCanadianFordCustomerAmount();

    if (fordCustomers == null) {
      return 0;
    }
    return fordCustomers;
  }

  public List<Person> getUninsuredDrivers() {
    return repo.getUninsuredDrivers();
  }

  public String getMostPopularMake() {
    String mostPopularMake = repo.getMostPopularMake();

    if (mostPopularMake == null) {
      return "No user in the database owns a car.";
    }
    return mostPopularMake;
  }

  public int getUninsuredMinorCount() {
    Integer numUninsuredMinors = repo.getUninsuredMinorCount();

    if (numUninsuredMinors == null) {
      return 0;
    }
    return numUninsuredMinors;
  }

  public int getCarlessAdults() {
    Integer numCarlessAdults = repo.getCarlessAdults();

    if (numCarlessAdults == null) {
      return 0;
    }
    return numCarlessAdults;
  }

  public int getUninsuredFastDrivers() {
    Integer numDrivers = repo.getUninsuredFastDrivers();

    if (numDrivers == null) {
      return 0;
    }
    return numDrivers;
  }

}

