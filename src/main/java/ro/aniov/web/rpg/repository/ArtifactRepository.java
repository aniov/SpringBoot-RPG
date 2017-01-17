package ro.aniov.web.rpg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.aniov.web.rpg.model.characters.hero.artifact.Artifact;
import ro.aniov.web.rpg.model.characters.hero.artifact.ArtifactType;

/**
 * Created by Marius on 12/15/2016.
 */
@Repository
public interface ArtifactRepository extends JpaRepository<Artifact, Long>{

    Artifact findByArtifactType(ArtifactType artifactType);
}
