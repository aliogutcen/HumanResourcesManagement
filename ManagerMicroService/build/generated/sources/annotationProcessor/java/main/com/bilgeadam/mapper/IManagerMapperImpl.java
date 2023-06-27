package com.bilgeadam.mapper;

import com.bilgeadam.dto.response.GetAllInfoManager;
import com.bilgeadam.repository.entity.Manager;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-06-11T17:22:32+0300",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.1.jar, environment: Java 17.0.4.1 (Oracle Corporation)"
)
@Component
public class IManagerMapperImpl implements IManagerMapper {

    @Override
    public GetAllInfoManager froInfoManager(Manager manager) {
        if ( manager == null ) {
            return null;
        }

        GetAllInfoManager.GetAllInfoManagerBuilder getAllInfoManager = GetAllInfoManager.builder();

        getAllInfoManager.companyid( manager.getCompanyid() );
        getAllInfoManager.id( manager.getId() );
        getAllInfoManager.image( manager.getImage() );
        getAllInfoManager.authid( manager.getAuthid() );
        getAllInfoManager.firstName( manager.getFirstName() );
        getAllInfoManager.surname( manager.getSurname() );
        getAllInfoManager.email( manager.getEmail() );
        getAllInfoManager.dateOfEmployment( manager.getDateOfEmployment() );
        getAllInfoManager.role( manager.getRole() );
        getAllInfoManager.address( manager.getAddress() );
        getAllInfoManager.phone( manager.getPhone() );
        getAllInfoManager.identificationNumber( manager.getIdentificationNumber() );
        getAllInfoManager.birthdayPlace( manager.getBirthdayPlace() );
        getAllInfoManager.birthDate( manager.getBirthDate() );

        return getAllInfoManager.build();
    }
}
