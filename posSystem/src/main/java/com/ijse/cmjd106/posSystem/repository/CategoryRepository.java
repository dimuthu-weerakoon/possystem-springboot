package com.ijse.cmjd106.posSystem.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ijse.cmjd106.posSystem.model.Category;
@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
