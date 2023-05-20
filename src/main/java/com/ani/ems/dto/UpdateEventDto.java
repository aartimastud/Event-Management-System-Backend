package com.ani.ems.dto;

import java.time.LocalDate;

import javax.persistence.OrderBy;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventDto {

    private Long id;

    @NotBlank(message = "title is mandatory")
    @NotNull(message = "title should not be null")
    private String title;

    @NotBlank(message = "description is mandatory")
    @NotNull(message = "description should not be null")
    private String description;

    @Future(message = "Date should not be from past")
    private LocalDate startdate;

    @Future(message = "Date should not be from past")
    private LocalDate enddate;

    @NotBlank(message = "location is mandatory")
    @NotNull(message = "location should not be null")
    private String location;

    @NotBlank(message = "time is mandatory")
    @NotNull(message = "time should not be null")
    private String time;
}