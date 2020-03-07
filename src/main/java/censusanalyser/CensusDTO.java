package censusanalyser;

public class CensusDTO {
    public String state;
    public String stateCode;
    public double population;
    public double totalArea;
    public double densityPerSqKm;

    public CensusDTO(IndiaCensusCSV indiaCensusCSV) {

        state = indiaCensusCSV.state;
        population = indiaCensusCSV.population;
        totalArea = indiaCensusCSV.totalArea;
        densityPerSqKm = indiaCensusCSV.densityPerSqKm;
    }

    public CensusDTO(UsCensusCSV UsCensusCSV) {
        state = UsCensusCSV.state;
        stateCode = UsCensusCSV.stateCode;
        population = UsCensusCSV.population;
        totalArea = UsCensusCSV.totalArea;
        densityPerSqKm = UsCensusCSV.densityPerSqKm;
    }
}
