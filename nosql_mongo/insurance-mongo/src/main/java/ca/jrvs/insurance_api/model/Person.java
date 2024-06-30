package ca.jrvs.insurance_api.model;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("people")
public class Person {

  private ObjectId id;
  private String firstName;
  private String lastName;
  private int age;
  private Address addressEntity;
  private Date createdAt = new Date();
  private Boolean insurance;
  private List<Car> carEntities;

  public Person(ObjectId id, String firstName, String lastName, int age, Address addressEntity,
      Boolean insurance, List<Car> carEntities) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.age = age;
    this.addressEntity = addressEntity;
    this.insurance = insurance;
    this.carEntities = carEntities;
  }

  public ObjectId getId() {
    return id;
  }

  public void setId(ObjectId id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public Address getAddressEntity() {
    return addressEntity;
  }

  public void setAddressEntity(Address addressEntity) {
    this.addressEntity = addressEntity;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Boolean getInsurance() {
    return insurance;
  }

  public void setInsurance(Boolean insurance) {
    this.insurance = insurance;
  }

  public List<Car> getCarEntities() {
    return carEntities;
  }

  public void setCarEntities(List<Car> carEntities) {
    this.carEntities = carEntities;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Person person = (Person) o;
    return age == person.age && Objects.equals(id, person.id) && Objects.equals(
        firstName, person.firstName) && Objects.equals(lastName, person.lastName)
        && Objects.equals(addressEntity, person.addressEntity) && Objects.equals(
        createdAt, person.createdAt) && Objects.equals(insurance, person.insurance)
        && Objects.equals(carEntities, person.carEntities);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, firstName, lastName, age, addressEntity, createdAt, insurance,
        carEntities);
  }

  @Override
  public String toString() {
    return firstName + " " + lastName + ", ID= " + id;
  }

}