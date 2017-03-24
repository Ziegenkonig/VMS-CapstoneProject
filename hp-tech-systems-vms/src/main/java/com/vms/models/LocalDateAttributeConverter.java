package com.vms.models;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
//import java.util.Date;
import java.sql.Date;


import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LocalDateAttributeConverter implements AttributeConverter<LocalDate, Date> {
	
	public static final ZoneId ZONE_AMERICA_CENTRAL = ZoneId.of("America/Chicago");
	
    @Override
    public Date convertToDatabaseColumn(LocalDate locDate) {
    	if (locDate == null) {
            return null;
        }

        // Untested, probably something like this

        ZonedDateTime zonedDateTime = locDate.atStartOfDay(ZONE_AMERICA_CENTRAL);
        LocalDate producerLocalDate = zonedDateTime.toLocalDate();
        Date date = Date.valueOf(producerLocalDate);

        return date;
    	//return (locDate == null ? null : Date.from(locDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
    }

    @Override
    public LocalDate convertToEntityAttribute(Date sqlDate) {
    	if (sqlDate == null) {
            return null;
        }

        // Fixed implementation considering server timezone

        Instant instant = Instant.ofEpochMilli(sqlDate.getTime());
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZONE_AMERICA_CENTRAL);
        LocalDate localDate = localDateTime.toLocalDate();

        return localDate;
    	//return (sqlDate == null ? null : sqlDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    }
}