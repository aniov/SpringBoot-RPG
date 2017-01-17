package ro.aniov.web.rpg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.aniov.web.rpg.model.characters.hero.Hero;
import ro.aniov.web.rpg.model.characters.hero.artifact.Artifact;
import ro.aniov.web.rpg.model.characters.hero.artifact.ArtifactType;
import ro.aniov.web.rpg.repository.ArtifactRepository;

/**
 * Created by Marius on 12/15/2016.
 */
@Service
public class ArtifactService {

    @Autowired
    ArtifactRepository artifactRepository;


    public void addArtifactToHero(Hero hero, Artifact artifact) {
        artifact.setHero(hero);
        artifactRepository.save(artifact);
    }

    public void deleteArtifactFromHero(Long artifactID){
        artifactRepository.delete(artifactID);
        System.out.println("\nIt should be deleted: " + artifactID);
        artifactRepository.flush();


    }

    public Artifact geBytArtifactType(ArtifactType artifactType){
        return artifactRepository.findByArtifactType(artifactType);
    }
}
