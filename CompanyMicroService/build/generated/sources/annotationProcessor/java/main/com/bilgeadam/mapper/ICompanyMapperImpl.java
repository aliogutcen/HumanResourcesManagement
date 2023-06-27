package com.bilgeadam.mapper;

import com.bilgeadam.dto.response.GetAllInfoCompany;
import com.bilgeadam.repository.entity.Company;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-06-11T17:22:10+0300",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.1.jar, environment: Java 17.0.4.1 (Oracle Corporation)"
)
@Component
public class ICompanyMapperImpl implements ICompanyMapper {

    @Override
    public GetAllInfoCompany fromInfoCompany(Company company) {
        if ( company == null ) {
            return null;
        }

        GetAllInfoCompany.GetAllInfoCompanyBuilder getAllInfoCompany = GetAllInfoCompany.builder();

        getAllInfoCompany.id( company.getId() );
        getAllInfoCompany.name( company.getName() );
        getAllInfoCompany.title( company.getTitle() );
        getAllInfoCompany.centralRegistrySystem( company.getCentralRegistrySystem() );
        getAllInfoCompany.taxNumber( company.getTaxNumber() );
        getAllInfoCompany.taxOffice( company.getTaxOffice() );
        getAllInfoCompany.image( company.getImage() );
        getAllInfoCompany.phone( company.getPhone() );
        getAllInfoCompany.address( company.getAddress() );
        getAllInfoCompany.email( company.getEmail() );
        getAllInfoCompany.numberOfWorkers( company.getNumberOfWorkers() );
        getAllInfoCompany.yearOfEstablishment( company.getYearOfEstablishment() );
        getAllInfoCompany.contractStartYear( company.getContractStartYear() );
        getAllInfoCompany.contractEndYear( company.getContractEndYear() );
        getAllInfoCompany.status( company.getStatus() );

        return getAllInfoCompany.build();
    }
}
