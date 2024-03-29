package tn.esprit.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.entities.Reclamation;
import tn.esprit.entities.Response;
import tn.esprit.repositories.ReclamationRepository;
import tn.esprit.repositories.ResponseRepository;
@Service
public class ResponseService {
@Autowired
ResponseRepository responseRepository;
@Autowired 
ReclamationRepository reclamationRepository;




public void addResponse(Response res,Long idReclamation) {
Reclamation r = reclamationRepository.findById(idReclamation).orElse(null);

res.setReclamation(r);
r.setEtat("traité");
responseRepository.save(res);	
reclamationRepository.save(r);
}
public void supprimerResponse(Long idResponse) {
	Response r = responseRepository.findById(idResponse).orElse(null);
	
	responseRepository.delete(r);
}


public List<Response> getAllResponses(){
	return responseRepository.findAll();
}

public List<Response> getResponsesByReclamation(Long idReclamation){
	Reclamation rec = reclamationRepository.findById(idReclamation).orElse(null);
	return rec.getResponses();
}

public void updateResponse(Response response, Long idResponse) {
	
	Response res= responseRepository.findById(idResponse).orElse(null);	
	res.setReclamation(res.getReclamation());
	res.setIdResponse(idResponse);
	res.setDescription(response.getDescription());
	
	
	responseRepository.save(res);
	
}	


}
