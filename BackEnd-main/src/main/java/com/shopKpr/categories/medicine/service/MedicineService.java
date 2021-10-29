package com.shopKpr.categories.medicine.service;

import com.shopKpr.categories.medicine.model.Medicine;
import com.shopKpr.categories.medicine.repository.elasticRepo.ElasticRepository;
import com.shopKpr.categories.medicine.repository.mongoRepo.MongoRepository;
import com.shopKpr.exceptions.AlreadyExists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
public class MedicineService {

    private final MongoRepository medicineMongoRepo;
    private final ElasticRepository medicineElasticRepo;

    public MedicineService( MongoRepository medicineMongoRepo, ElasticRepository medicineElasticRepo ) {
        this.medicineMongoRepo = medicineMongoRepo;
        this.medicineElasticRepo = medicineElasticRepo;
    }


    @Transactional
    public void addNewMedicine( Medicine medicine ) {

        Optional<Medicine> medicineOptional = medicineElasticRepo.findByBrandName(medicine.getBrandName());

        if(medicineOptional.isPresent()){
            log.info("Medicine already exists, medicine name is: " + medicine.getBrandName());
            throw new AlreadyExists("Medicine already exists");
        }else{
            medicine.setCreationDate(new Date(System.currentTimeMillis()));
            medicineMongoRepo.save(medicine);
            medicineElasticRepo.save(medicine);
        }
    }
}
