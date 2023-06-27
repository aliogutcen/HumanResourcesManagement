package com.bilgeadam.mapper;

import com.bilgeadam.dto.response.GetAllWorker;
import com.bilgeadam.repository.entity.Worker;
import java.time.format.DateTimeFormatter;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-06-11T17:22:47+0300",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.1.jar, environment: Java 17.0.4.1 (Oracle Corporation)"
)
@Component
public class IWorkerMapperImpl implements IWorkerMapper {

    @Override
    public GetAllWorker fromInfoWorker(Worker worker) {
        if ( worker == null ) {
            return null;
        }

        GetAllWorker.GetAllWorkerBuilder getAllWorker = GetAllWorker.builder();

        getAllWorker.id( worker.getId() );
        getAllWorker.companyid( worker.getCompanyid() );
        getAllWorker.managerid( worker.getManagerid() );
        getAllWorker.name( worker.getName() );
        getAllWorker.secondname( worker.getSecondname() );
        getAllWorker.surname( worker.getSurname() );
        getAllWorker.secondSurname( worker.getSecondSurname() );
        getAllWorker.birthDate( worker.getBirthDate() );
        getAllWorker.birthPlace( worker.getBirthPlace() );
        getAllWorker.identificationNumber( worker.getIdentificationNumber() );
        if ( worker.getDateOfEmployment() != null ) {
            getAllWorker.dateOfEmployment( DateTimeFormatter.ISO_LOCAL_DATE.format( worker.getDateOfEmployment() ) );
        }
        getAllWorker.terminationDate( worker.getTerminationDate() );
        getAllWorker.activity( worker.getActivity() );
        getAllWorker.occupation( worker.getOccupation() );
        getAllWorker.email( worker.getEmail() );
        getAllWorker.address( worker.getAddress() );
        getAllWorker.companyPhone( worker.getCompanyPhone() );
        getAllWorker.image( worker.getImage() );
        getAllWorker.salary( worker.getSalary() );

        return getAllWorker.build();
    }
}
