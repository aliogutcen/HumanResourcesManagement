package com.bilgeadam.service;

import com.bilgeadam.dto.request.AddManagerRequestDto;
import com.bilgeadam.dto.request.BaseRequestDto;
import com.bilgeadam.dto.response.GetAllInfoManager;
import com.bilgeadam.dto.response.SumamryInfoManager;
import com.bilgeadam.dto.response.SummarForCompany;
import com.bilgeadam.exception.EErrorType;
import com.bilgeadam.exception.ManagerException;
import com.bilgeadam.mapper.IManagerMapper;
import com.bilgeadam.rabbitmq.model.CompanyName;
import com.bilgeadam.rabbitmq.model.CreateManager;
import com.bilgeadam.rabbitmq.producer.ManagerProducer;
import com.bilgeadam.repository.IManagerRepository;
import com.bilgeadam.repository.entity.Manager;
import com.bilgeadam.utility.JwtTokenManager;
import com.bilgeadam.utility.ServiceManager;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


@Service
public class ManagerService extends ServiceManager<Manager, String> {

    private final IManagerRepository managerRepository;
    private final ManagerProducer managerProducer;

    private final JwtTokenManager jwtTokenManager;

    public ManagerService(IManagerRepository managerRepository, ManagerProducer managerProducer, JwtTokenManager jwtTokenManager) {
        super(managerRepository);
        this.managerRepository = managerRepository;
        this.managerProducer = managerProducer;
        this.jwtTokenManager = jwtTokenManager;
    }
    public boolean addNewManager(AddManagerRequestDto addManagerRequestDto)  {
        String companyId = managerProducer.companyIdForManager(CompanyName.builder()
                .companyName(addManagerRequestDto.getCompanyName())
                .build());
        if (managerRepository.findOptionalByCompanyid(companyId).isPresent())
            throw new ManagerException(EErrorType.MANAGER_HAS_BEEN_WITH_COMPANY);
        else{
        Optional<Manager> managerOptional = managerRepository.findOptionalByIdentificationNumber(addManagerRequestDto.getIdentificationNumber());
        if (managerOptional.isPresent()) throw new ManagerException(EErrorType.MANAGER_HAS_BEEN);
        Long result = managerProducer.createAuthFromManager(CreateManager.builder()
                .email(addManagerRequestDto.getEmail())
                .build());
        if (result == 0L) throw new ManagerException(EErrorType.AUTH_EMAIL_ERROR);
        else {

            String url = imageUpload(addManagerRequestDto.getImage());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Manager manager = Manager.builder()
                    .address(addManagerRequestDto.getAddress())
                    .email(addManagerRequestDto.getEmail())
                    .firstName(addManagerRequestDto.getFirstName())
                    .image(url)
                    .phone(addManagerRequestDto.getPhone())
                    .identificationNumber(addManagerRequestDto.getIdentificationNumber())
                    .dateOfEmployment(addManagerRequestDto.getDateOfEmployment())
                    .surname(addManagerRequestDto.getSurname())
                    .companyid(companyId)
                    .authid(result)
                    .birthdayPlace(addManagerRequestDto.getBirthdayPlace())
                    .birthDate(addManagerRequestDto.getBirthDate())
                    .build();

            save(manager);
            return true;
        }
        }
    }
    public List<SumamryInfoManager> getAllManagerSummaryInfo() {
        List<SumamryInfoManager> sumamryInfoManagers = new ArrayList<>();
        managerRepository.findAll().forEach(x -> {
            sumamryInfoManagers.add(SumamryInfoManager.builder()
                    .address(x.getAddress())
                    .id(x.getId())
                    .surname(x.getSurname())
                    .firstName(x.getFirstName())
                    .image(x.getImage())
                    .email(x.getEmail())
                    .phone(x.getPhone())
                    .build());
        });
        return sumamryInfoManagers;
    }
    public GetAllInfoManager getAllInfo(String id) {
        Optional<Manager> managerOptional = managerRepository.findById(id);
        return IManagerMapper.INSTANCE.froInfoManager(managerOptional.get());

    }
    public List<SumamryInfoManager> getTop5Manager() {
        List<SumamryInfoManager> sumamryInfoManagers = new ArrayList<>();
        managerRepository.findTop5ByOrderByCreatedateDesc().forEach(x -> {
            sumamryInfoManagers.add(SumamryInfoManager.builder()
                    .id(x.getId())
                    .image(x.getImage())
                    .firstName(x.getFirstName())
                    .surname(x.getSurname())
                    .email(x.getEmail())
                    .build());
        });
        return sumamryInfoManagers;
    }
    public String imageUpload(MultipartFile file) {
        // Configure
        Map config = new HashMap();
        config.put("cloud_name", "doa04qdhh");
        config.put("api_key", "261194321947226");
        config.put("api_secret", "K5_9m33MSDBvu4MZuHhHWeFxNeA");
        Cloudinary cloudinary = new Cloudinary(config);
        try {
            Map<?, ?> result = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String url = (String) result.get("url");
            System.out.println(url + " --------------------------");
            return url;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    public String getImageForManager(BaseRequestDto baseRequestDto) {
        Optional<Long> authId = jwtTokenManager.getIdFromToken(baseRequestDto.getToken());
        if (authId.isEmpty()) throw new ManagerException(EErrorType.INVALID_TOKEN);
        Optional<Manager> manager = managerRepository.findOptionalByAuthid(authId.get());
        return manager.get().getImage();
    }
    public GetAllInfoManager getInfoForManager(BaseRequestDto baseRequestDto) {
        Optional<Long> authId = jwtTokenManager.getIdFromToken(baseRequestDto.getToken());
        if (authId.isEmpty()) throw new ManagerException(EErrorType.INVALID_TOKEN);
        Optional<Manager> manager = managerRepository.findOptionalByAuthid(authId.get());
        return IManagerMapper.INSTANCE.froInfoManager(manager.get());
    }
    public SummarForCompany summaryForCompany(String companyId) {
        Optional<Manager> manager = managerRepository.findOptionalByCompanyid(companyId);
        SummarForCompany summarForCompany = SummarForCompany.builder()
                .email(manager.get().getEmail())
                .firstName(manager.get().getFirstName())
                .phone(manager.get().getPhone())
                .surname(manager.get().getSurname())
                .image(manager.get().getImage())
                .build();
        return summarForCompany;
    }
}
