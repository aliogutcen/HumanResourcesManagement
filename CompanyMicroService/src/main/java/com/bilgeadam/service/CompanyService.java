package com.bilgeadam.service;

import com.bilgeadam.dto.request.AddCompanyRequestDto;
import com.bilgeadam.dto.response.GetAllInfoCompany;
import com.bilgeadam.dto.response.SummaryInfoCompany;
import com.bilgeadam.exception.CompanyException;
import com.bilgeadam.exception.EErrorType;
import com.bilgeadam.mapper.ICompanyMapper;
import com.bilgeadam.repository.ICompanyRepository;
import com.bilgeadam.repository.entity.Company;
import com.bilgeadam.utility.FileService;
import com.bilgeadam.utility.JwtTokenManager;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyService extends ServiceManager<Company,String > {

    private final ICompanyRepository companyRepository;

    private final JwtTokenManager jwtTokenManager;

    private final FileService fileService;

    public CompanyService(ICompanyRepository companyRepository, JwtTokenManager jwtTokenManager,FileService fileService) {
        super(companyRepository);
        this.companyRepository = companyRepository;
        this.jwtTokenManager = jwtTokenManager;
        this.fileService = fileService;
    }

    public boolean  addCompany(AddCompanyRequestDto addCompanyRequestDto) throws IOException {
        Optional<Company> companyOptional = companyRepository.findOptionalByCentralRegistrySystem(addCompanyRequestDto.getCentralRegistrySystem());
        if (companyOptional.isPresent()) throw new CompanyException(EErrorType.COMPANY_HAS_BEEN);
        Optional<Long> authid = jwtTokenManager.getIdFromToken(addCompanyRequestDto.getToken());
        if (authid.isEmpty()) throw new CompanyException(EErrorType.INVALID_TOKEN);
        else {
            String fileName = fileService.decodeBase64(addCompanyRequestDto.getImage());
            Company company = Company.builder()
                    .centralRegistrySystem(addCompanyRequestDto.getCentralRegistrySystem())
                    .name(addCompanyRequestDto.getName())
                    .contractEndYear(addCompanyRequestDto.getContractEndYear())
                    .phone(addCompanyRequestDto.getPhone())
                    .image(fileName)
                    .address(addCompanyRequestDto.getAddress())
                    .taxNumber(addCompanyRequestDto.getTaxNumber())
                    .taxOffice(addCompanyRequestDto.getTaxOffice())
                    .contractStartYear(addCompanyRequestDto.getContractStartYear())
                    .numberOfWorkers(addCompanyRequestDto.getNumberOfWorkers())
                    .status(addCompanyRequestDto.getStatus())
                    .title(addCompanyRequestDto.getTitle())
                    .email(addCompanyRequestDto.getEmail())
                    .yearOfEstablishment(addCompanyRequestDto.getYearOfEstablishment())
                    .build();
            save(company);
            return true;
        }
    }


    public List<SummaryInfoCompany> getAllCompanySummaryInfo() {
        List<SummaryInfoCompany> summaryInfoCompanies = new ArrayList<>();
        companyRepository.findAll().forEach(x->{
            summaryInfoCompanies.add(SummaryInfoCompany.builder()
                            .id(x.getId())
                            .address(x.getAddress())
                            .title(x.getTitle())
                            .phone(x.getPhone())
                            .name(x.getName())
                            .email(x.getEmail())
                    .build());
        });
        return summaryInfoCompanies;
    }


    public GetAllInfoCompany getAllInfo(String id) {
        Optional<Company>companyOptional = companyRepository.findById(id);
        return ICompanyMapper.INSTANCE.fromInfoCompany(companyOptional.get());
    }
}