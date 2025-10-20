// In src/main/java/com/carrental/repository/RentalRepository.java

package com.carrental.repository;

import com.carrental.model.Rental;
import com.carrental.model.User; // Make sure this is imported
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List; // And this

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
    
    // This method will automatically generate the query to find all rentals for a given user
    List<Rental> findByUser(User user);
}