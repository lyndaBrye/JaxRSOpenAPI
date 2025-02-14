package fr.istic.taa.jaxrs.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import fr.istic.taa.jaxrs.domain.Artiste;
import fr.istic.taa.jaxrs.domain.Organisateur;
import fr.istic.taa.jaxrs.domain.Ticket;
import jakarta.persistence.*;

import java.io.Serializable;

import java.time.LocalDateTime;
import java.util.List;


public class ConcertDTO {

    private Long id;

    private LocalDateTime date;

    private String lieu;
    private int capacity;
    private Long artiste_id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Long getArtiste_id() {
        return artiste_id;
    }

    public void setArtiste_id(Long artiste_id) {
        this.artiste_id = artiste_id;
    }
}
