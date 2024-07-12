package ca.jrvs.insurance_api.repository;

import ca.jrvs.insurance_api.model.Person;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends MongoRepository<Person, ObjectId> {

  @Aggregation({ "{ $group: { _id: null, avgAge: { $avg: '$age' }}}",
                 "{ $project: { _id: 0 }}" })
  double getAverageAge();

  @Aggregation({ "{ $group: { _id: null, maxCars: { $max: { $size: '$carEntities' }}}}",
                 "{ $project: { _id: 0 }}" })
  int getMaxCars();

  @Aggregation({ "{ $match: { 'addressEntity.country': 'Canada', 'carEntities.make': 'Ford' }}",
                 "{ $count: '# of Ford Customers in Canada' }" })
  Integer getCanadianFordCustomerAmount();

  @Aggregation({ "{ $addFields: { carAmount: { $size: '$carEntities' }}}",
                 "{ $match: { carAmount: { $gt: 0 }, insurance: false }}" })
  List<Person> getUninsuredDrivers();

  @Aggregation({ "{ $unwind: { path: '$carEntities', preserveNullAndEmptyArrays: false }}",
                 "{ $group: { _id: '$carEntities.make', makeCount: { $sum: 1 }}}",
                 "{ $sort: { makeCount: -1 }}",
                 "{ $limit: 1 }",
                 "{ $project: { _id: 0, make: '$_id'}}" })
  String getMostPopularMake();

  @Aggregation({ "{ $addFields: { carAmount: { $size: '$carEntities' }}}",
                 "{ $match: { age: { $lt: 18 }, carAmount: { $gt: 0 }, insurance: false }}",
                 "{ $count: '# of minor car owners without insurance' }" })
  Integer getUninsuredMinorCount();

  @Aggregation({ "{ $addFields: { carAmount: { $size: '$carEntities' }}}",
                 "{ $match: { age: { $gte: 18 }, carAmount: 0 }}",
                 "{ $count: '# of Adults that do not own a car' }" })
  Integer getCarlessAdults();

  @Aggregation({ "{ $match: { insurance: false, 'carEntities.maxSpeed': { $gte: 200 }}}",
                 "{ $count: '# of uninsured drivers with fast cars (200 mph)' }" })
  Integer getUninsuredFastDrivers();

}
