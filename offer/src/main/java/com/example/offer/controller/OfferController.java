package com.example.offer.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.offer.Utility.JsonResponseUtils;
import com.example.offer.Utility.JsonResponseUtils.STATUS;
import com.example.offer.Utility.OfferErroResponse;
import com.example.offer.Utility.OfferNotFoundException;
import com.example.offer.dao.OfferDao;
import com.example.offer.models.Offer;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/offer")
public class OfferController {

	@Autowired
	private OfferDao theOfferDao;

	List<Offer> offers;

	@PostMapping("/create")
	public String createOffer(@RequestBody Offer offer) throws Exception {
		offer.setStatus(true);
		theOfferDao.save(offer);
		return JsonResponseUtils.keyResponse(STATUS.Success);
	}

	@GetMapping("/getOffers")
	public ResponseEntity<String> getOffers() throws Exception {

		offers = new ArrayList<Offer>();

		Date date = new Date();
		offers = theOfferDao.findByOfferEndTimeAfterAndStatus(date, true);

		ObjectMapper mapper = new ObjectMapper();

		return ResponseEntity.accepted().header(HttpHeaders.CONTENT_TYPE, "application/json")
				.body(mapper.writeValueAsString(offers));

	}

	@GetMapping("/getAllOffers")
	public ResponseEntity<String> getAllOffers() throws Exception {

		offers = new ArrayList<Offer>();
		offers = theOfferDao.findAll();

		ObjectMapper mapper = new ObjectMapper();

		return ResponseEntity.accepted().header(HttpHeaders.CONTENT_TYPE, "application/json")
				.body(mapper.writeValueAsString(offers));

	}

	@GetMapping("/offerExpire/{id}")
	public String expireOffer(@PathVariable Long id) throws Exception {

		Offer offer = theOfferDao.findOfferById(id);

		offer.setStatus(false);
		theOfferDao.save(offer);
		return JsonResponseUtils.keyResponse(STATUS.Success);
	}

	@GetMapping("/getOffer/{id}")
	public ResponseEntity<String> getById(@PathVariable long id) throws Exception {

		Offer offer = theOfferDao.findOfferById(id);
		if (offer != null) {
			ObjectMapper mapper = new ObjectMapper();

			return ResponseEntity.accepted().header(HttpHeaders.CONTENT_TYPE, "application/json")
					.body(mapper.writeValueAsString(offer));
		}
		return ResponseEntity.badRequest().header(HttpHeaders.CONTENT_TYPE, "application/json")
				.body(JsonResponseUtils.errorResponse("OFFER_002", STATUS.Failure, "Failed to get the Offer by Id"));

	}

	@GetMapping("/query/{offerCode}")
	public ResponseEntity<String> queryOffer(@PathVariable String offerCode) throws Exception {
		if (offerCode != null) {
			Offer offer = theOfferDao.findOfferByOfferCodeAndStatus(offerCode, true);
			ObjectMapper mapper = new ObjectMapper();

			return ResponseEntity.accepted().header(HttpHeaders.CONTENT_TYPE, "application/json")
					.body(mapper.writeValueAsString(offer));
		}
		return ResponseEntity.badRequest().header(HttpHeaders.CONTENT_TYPE, "application/json")
				.body(JsonResponseUtils.errorResponse("OFFER_002", STATUS.Failure, "Failed to get the Offer by OfferCode"));

	}

	@GetMapping("/applyOfferCode/{offerCode}")
	public String applyOfferCode(@PathVariable String offerCode) throws Exception {
		if (offerCode != null) {
			Offer offer = theOfferDao.findByOfferEndTimeAfterAndStatusAndOfferCode(new Date(), true, offerCode);
			if(offer == null) {
				return JsonResponseUtils.keyResponse("message", "Invalid Offer code");
			}
			return JsonResponseUtils.keyResponse("message", "Offer code applied successfully");
		}

		return JsonResponseUtils.keyResponse(STATUS.Failure);

	}

	@ExceptionHandler
	public ResponseEntity<OfferErroResponse> handleException(OfferNotFoundException exc) {

		OfferErroResponse error = new OfferErroResponse();
		error.setStatus(HttpStatus.NOT_FOUND.value());
		error.setMessage(exc.getMessage());
		error.setTimeStamp(System.currentTimeMillis());
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler
	public ResponseEntity<OfferErroResponse> handleException(Exception ex) {
		OfferErroResponse error = new OfferErroResponse();
		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setMessage(ex.getMessage());
		error.setTimeStamp(System.currentTimeMillis());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

}
