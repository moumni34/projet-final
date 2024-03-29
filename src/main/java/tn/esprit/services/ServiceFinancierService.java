package tn.esprit.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import tn.esprit.entities.Depenses;
import tn.esprit.entities.Prixinscriptions;
import tn.esprit.entities.Projet;
import tn .esprit.entities.Revenus;
import tn.esprit.entities.ServiceFinancier;
import tn.esprit.entities.User;
import tn.esprit.repositories.DepenseRepository ;
import tn.esprit.repositories.PrixinscriptionsRepository;
import tn.esprit.repositories.ProjetRepository;
import tn.esprit.repositories.RevenueRepository;
import tn.esprit.repositories.ServiceFinancierRepository;
import tn.esprit.repositories.UserRepository;

@Service
public class ServiceFinancierService {
	@Autowired
	ServiceFinancierRepository serviceFinancierRepository;
@Autowired
DepenseRepository depenseRepo;
@Autowired
RevenueRepository revenuRepo;

@Autowired
ProjetRepository projetRepo;

@Autowired
UserRepository userRepo;
@Autowired
PrixinscriptionsRepository prixRepository;

	public ServiceFinancier ajouterServiceFinancier(ServiceFinancier r) {
		return serviceFinancierRepository.save(r);
		
	}

	public List<ServiceFinancier> ListServiceFinancier(){
		return serviceFinancierRepository.findAll();
	}


	public void supprimerServiceFinancier(Long idServiceFinancier) {
		ServiceFinancier r = serviceFinancierRepository.findById(idServiceFinancier).orElse(null);
		
		serviceFinancierRepository.delete(r);
	}
	
	public void updateServiceFinancier(ServiceFinancier serviceFinancier, Long idServiceFinancier) {
		
		ServiceFinancier sf= serviceFinancierRepository.findById(idServiceFinancier).orElse(null);	
		
		sf.setIdF(idServiceFinancier);
		sf.setTotaleDepenses(serviceFinancier.getTotaleDepenses());
		sf.setTotaleRevenues(serviceFinancier.getTotaleRevenues());
		serviceFinancierRepository.save(sf);
		
	}	
	
	
	public ServiceFinancier getSFbyId(Long idServiceFinancier) {
		return serviceFinancierRepository.findById(idServiceFinancier).orElse(null);	
	}
	
	public float EtatFinanceByYear() {
		Date currentSqlDate = new Date(System.currentTimeMillis());
		List<Depenses> listdepenses = depenseRepo.findAll();
		float sommeDepense=0;
		float sommeRevenus=0;
		float sommeprojets=0;
		int nbrAbonnéRestau=0;
		int nbrAbonnéFoyer=0;
		List<User> listuser = userRepo.findAll();
		List<Prixinscriptions> prixinscriptions = prixRepository.findAll();
		List<Projet> projets = projetRepo.findAll();
		
		Prixinscriptions prix = new Prixinscriptions();
		
		for(Prixinscriptions p : prixinscriptions) {
			if(p.getDate().getYear()==currentSqlDate.getYear()) {
				prix = p;
			}
		}
		for (Projet p : projets) {
			sommeprojets+= p.getPrixProjet();
			
			}
		System.out.println("/////////"+sommeprojets);
		for (User u : listuser) {
			if(u.getAbonneRestaurant().booleanValue()) {
				nbrAbonnéRestau++;
			}
			if(u.getAbonneFoyer().booleanValue()) {
				nbrAbonnéFoyer++;
			}
		}
			sommeRevenus+=listuser.size()*prix.getPrixScolarité()+nbrAbonnéFoyer*prix.getPrixAbonnementFoyer()+
					nbrAbonnéRestau*prix.getPrixAbonnementRestaurant() + sommeprojets;
	
		for(Depenses d :listdepenses) {
			sommeDepense+= d.getConsommationeau()+d.getConsommationelectricite()+d.getMaintenanceRestaurant()+d.getSalaireProf();
	}
	
	
	return sommeRevenus-sommeDepense;
	
}
	
	
	

	//@Scheduled(fixedRate = 10000)
	//@Scheduled(cron = "* * * * */12 *")
	/*
	private void AugmenterPrixScolarité() {
		Date currentSqlDate = new Date(System.currentTimeMillis());
		Prixinscriptions prix = new Prixinscriptions();
		List<Prixinscriptions> prixinscriptions = prixRepository.findAll();

		for(Prixinscriptions p : prixinscriptions) {
			if(p.getDate().getYear()==currentSqlDate.getYear()) {
				prix = p;
			}
		}
		float nvprix = (float) (prix.getPrixScolarité()+prix.getPrixScolarité()*0.05);
		
		prix.setPrixScolarité(nvprix);
		
		prixRepository.save(prix);

	}
	
*/
	
	
}