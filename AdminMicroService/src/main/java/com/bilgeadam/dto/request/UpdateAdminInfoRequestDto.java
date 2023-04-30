package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UpdateAdminInfoRequestDto {

    String token;
    String firstName;
    String surname;
    LocalDate dateOfEmployment;
    String email;

    String image;



}
