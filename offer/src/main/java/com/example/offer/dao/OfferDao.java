package com.example.offer.dao;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.TemporalType;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.offer.models.Offer;

@Repository
@Transactional
public interface OfferDao extends JpaRepository<Offer, Long> {

	Offer findOfferById(Long id);

	@Query("from Offer where status=:status and offerEndTime=:offerEndTime")
	List<Offer> findOfferByStatusAndOfferEndTime(@Param("status") boolean status, @Temporal(TemporalType.DATE) Date offerEndTime);

	Offer findOfferByOfferCodeAndStatus(String offerCode, boolean status);
	
	List<Offer> findByOfferEndTimeAfterAndStatus(Date offerEndTime, boolean status);
	
	Offer findByOfferEndTimeAfterAndStatusAndOfferCode(Date offerEndTime, boolean status, String offerCode);
	
}
