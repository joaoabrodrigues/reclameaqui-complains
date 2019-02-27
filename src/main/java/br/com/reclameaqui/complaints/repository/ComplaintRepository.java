package br.com.reclameaqui.complaints.repository;

import br.com.reclameaqui.complaints.model.Complaint;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(path = "/complaint", collectionResourceRel = "complaints")
public interface ComplaintRepository extends MongoRepository<Complaint, String> {

    @RestResource(rel = "company-city", path = "company-city")
    List<Complaint> findByLocaleCityAndCompanyName(String city, String companyName);

    @RestResource(rel = "company-state", path = "company-state")
    List<Complaint> findByLocaleStateAndCompanyName(String state, String companyName);

    @RestResource(rel = "city", path = "city")
    List<Complaint> findByLocaleCity(String city);

    @RestResource(rel = "company", path = "company")
    List<Complaint> findByCompanyName(String companyName);
}
