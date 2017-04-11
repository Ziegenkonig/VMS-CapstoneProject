package com.vms.models;

//import java.util.Date;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

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
    	return sqlDate.toLocalDate();
    	/*
        Instant instant = Instant.ofEpochMilli(sqlDate.getTime());
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZONE_AMERICA_CENTRAL);
        LocalDate localDate = localDateTime.toLocalDate();
<<<<<<< HEAD
=======

>>>>>>> 23d765e63d376df8ec1e9c2726082e3b390ecc12
        return localDate;
        */
    	//return (sqlDate == null ? null : sqlDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    }
}