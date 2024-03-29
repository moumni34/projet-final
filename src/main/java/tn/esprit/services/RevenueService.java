package tn.esprit.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.entities.Depenses;
import tn.esprit.entities.Revenus;
import tn.esprit.repositories.RevenueRepository;

@Service
public class RevenueService {
@Autowired
RevenueRepository revenueRepository;
	
public Revenus ajouterRevenus(Revenus r) {
	
	return revenueRepository.save(r);
	
}

public List<Revenus> ListRevenus(){
	return revenueRepository.findAll();
}


public void supprimerRevenus(Long idRevenus) {
	Revenus r = revenueRepository.findById(idRevenus).orElse(null);
	
	revenueRepository.delete(r);
}
	
public void updateRevenus(Revenus Revenus, Long idRevenus) {
	
	Revenus rev= revenueRepository.findById(idRevenus).orElse(null);	
	
	rev.setIdRevenue(idRevenus);
	rev.setAbonnementFoyer(Revenus.getAbonnementFoyer());
	rev.setAbonnementRestaurant(Revenus.getAbonnementRestaurant());
	rev.setProjets(Revenus.getProjets());
	rev.setProjets(Revenus.getProjets());
	rev.setScolariteEtud(Revenus.getScolariteEtud());
	revenueRepository.save(rev);
	
}	


public Revenus getrevenusServiceById(Long idRevenus) {
	return revenueRepository.findById(idRevenus).orElse(null);
}

	
}
