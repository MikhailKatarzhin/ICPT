package volodov.cursework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import volodov.cursework.model.Personality;
import volodov.cursework.repo.PersonalityRepository;

@Service
public class PersonalityService{

    PersonalityRepository personalityRepository;

    @Autowired
    public PersonalityService(PersonalityRepository personalityRepository) {
        this.personalityRepository = personalityRepository;
    }

    public Personality getBySeriesAndNumber(String seriesAndNumber){
        if (personalityRepository.countBySeriesAndNumber(seriesAndNumber) == 0)
            return null;
        return personalityRepository.getBySeriesAndNumber(seriesAndNumber);
    }

    public Personality save(Personality personality){
        if (personality == null)
            return null;
        return personalityRepository.save(personality);
    }
}
