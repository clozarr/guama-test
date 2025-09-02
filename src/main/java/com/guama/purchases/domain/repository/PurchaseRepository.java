package com.guama.purchases.domain.repository;

import com.guama.purchases.domain.entity.Purchase;
import com.guama.purchases.domain.enums.PurchaseStatus;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Purchase entities.
 */
@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, UUID> {

    /**
     * Finds all purchases with the specified status, ordered by creation date in ascending order.
     *
     * @param status the status of the purchases to find
     * @return a list of purchases with the specified status, ordered by creation date
     */
    List<Purchase> findAllByStatusOrderByCreatedAscCreatedAtAsc(PurchaseStatus status);
    /* Finds all purchases with the specified status and customer ID, ordered by creation date in ascending order.
     *
     * @param status the status of the purchases to find
     * @param customerId the ID of the customer whose purchases to find
     * @return a list of purchases with the specified status and customer ID, ordered by creation date
     */
    List<Purchase> findAllByStatusAndCustomerIdOrderByCreatedAscCreatedAtAsc(PurchaseStatus status, String customerId);
    /**
     * Finds all purchases for the specified customer ID, ordered by creation date in ascending order.
     *
     * @param customerId the ID of the customer whose purchases to find
     * @return a list of purchases for the specified customer ID, ordered by creation date
     */
    List<Purchase> findAllByCustomerIdOrderByCreatedAscCreatedAtAsc(String customerId);



    /* * Finds purchases with descriptions containing the specified string, case-insensitive.
     *
     * @param description the string to search for in purchase descriptions
     * @return a list of purchases with descriptions containing the specified string
     */

    @Query("SELECT p FROM Purchase p WHERE LOWER(p.description) LIKE LOWER(CONCAT('%', :description, '%'))")
    List<Purchase> findByDescriptionContainingIgnoreCase(@Param("description") String description);


    /**
     * Finds purchases created on the specified date, ordered by creation date in ascending order.
     *
     * @param date the date to search for purchases
     * @return a list of purchases created on the specified date, ordered by creation date
     */
    List<Purchase> findAllByCreatedOrderByCreatedAsc(LocalDate date);

}
