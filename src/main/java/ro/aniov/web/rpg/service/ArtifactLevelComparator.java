package ro.aniov.web.rpg.service;

import ro.aniov.web.rpg.model.characters.hero.artifact.Artifact;

import java.util.Comparator;

/**
 * Created by Marius on 1/4/2017.
 */
public class ArtifactLevelComparator implements Comparator<Artifact> {

    /** We want artifacts to be displayed in order by level*/
    @Override
    public int compare(Artifact artifact1, Artifact artifact2) {
        return artifact1.getLevel() - artifact2.getLevel();
    }
}
