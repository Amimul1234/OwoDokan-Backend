package com.shopKpr.categories.medicine.repository.mongoRepo;

import com.shopKpr.categories.medicine.model.Medicine;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoRepository extends
        org.springframework.data.mongodb.repository.MongoRepository<Medicine, String> {
}
