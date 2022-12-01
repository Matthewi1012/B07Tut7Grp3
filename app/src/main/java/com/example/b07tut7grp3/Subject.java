package com.example.b07tut7grp3;

@SuppressWarnings("unused")
/**
 * An enumeration of all possible subjects at UTSC
 * @author Kevin Li
 * @since 0.1
 */
public enum Subject{
    AFRICAN_STUDIES("African Studies"),
    ANTHROPOLOGY("Anthropology"),
    APPLIED_MICROBIOLOGY("Applied Microbiology"),
    ARTS_CULTURE_MEDIA("Arts, Culture, and Media"),
    ARTS_MANAGEMENT("Arts Management"),
    ART_HISTORY("Art History"),
    ASTRONOMY("Astronomy"),
    BIOLOGICAL_SCIENCES("Biological Sciences"),
    CHEMISTRY("Chemistry"),
    CITY_STUDIES("City Studies"),
    CLASSICAL_STUDIES("Classical Studies"),
    COGNITIVE_SCIENCE("Cognitive Science"),
    COMPUTER_SCIENCE("Computer Science"),
    CONCURRENT_TEACHER_EDUCATION("Concurrent Teacher Education"),
    DIASPORA_TRANSNATIONAL_STUDIES("Diaspora and Transnational Studies"),
    ECONOMICS_FOR_MANAGEMENT_STUDIES("Economics for Management Studies"),
    ENGLISH("English"),
    ENVIRONMENTAL_SCIENCE("Environmental Science"),
    ENVIRONMENTAL_SCIENCE_AND_TECHNOLOGY("Environmental Science and Technology"),
    ENVIRONMENTAL_STUDIES("Environmental Studies"),
    FRENCH("French"),
    GEOGRAPHY("Geography"),
    GLOBAL_ASIA_STUDIES("Global Asia Studies"),
    HEALTH_STUDIES("Health Studies"),
    HISTORICAL_CULTURAL_STUDIES("Historical and Cultural Studies"),
    HISTORY("History"),
    INTERNATIONAL_DEVELOPMENT_STUDIES("International Development Studies"),
    INTERNATIONAL_STUDIES("International Studies"),
    INTERSECTIONS_EXCHANGES_ENCOUNTERS("Intersections, Exchanges, and Encounters"),
    JOURNALISM("Journalism"),
    LANGUAGES("Languages"),
    LINGUISTICS("Linguistics"),
    MANAGEMENT("Management"),
    MATHEMATICS("Mathematics"),
    MEDIA_STUDIES("Media Studies"),
    MUSIC_AND_CULTURE("Music and Culture"),
    NEUROSCIENCE("Neuroscience"),
    NEW_MEDIA_STUDIES("New Media Studies"),
    PARAMEDICINE("Paramedicine"),
    PHYSICAL_SCIENCES("Physical Sciences"),
    PHYSICS_AND_ASTROPHYSICS("Physics and Astrophysics"),
    POLITICAL_SCIENCE("Political Science"),
    PSYCHOLOGY("Psychology"),
    RELIGION("Religion"),
    SOCIETY_AND_ENVIRONMENT("Society and Environment"),
    SOCIOLOGY("Sociology"),
    STATISTICS("Statistics"),
    STUDIO("Studio"),
    TEACHING_AND_LEARNING("Teaching and Learning"),
    THEATRE_AND_PERFORMANCE_STUDIES("Theatre and Performance Studies"),
    VISUAL_AND_PERFORMING_ARTS("Visual and Performing Arts"),
    WOMENS_AND_GENDER_STUDIES("Women's and Gender Studies");
    private final String subject;
    Subject(String subject){
        this.subject = subject;
    }

    @Override
    public String toString(){
        return this.subject;
    }
}
