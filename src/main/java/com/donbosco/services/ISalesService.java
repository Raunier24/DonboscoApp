package com.donbosco.services;

import com.donbosco.dto.SalesDto;

public interface  ISalesService {
    public void verifyFlightAvailability(SalesDto salesDto);
    public void processReservation(SalesDto salesDto);
}
