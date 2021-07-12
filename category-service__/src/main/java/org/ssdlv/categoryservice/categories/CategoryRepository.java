package org.ssdlv.categoryservice.categories;

//import org.springframework.data.jpa.repository.JpaRepository;


import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository<Category, Long> {
}
