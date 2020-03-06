package censusanalyser;

import java.util.Map;

public class CensusAdapterFactory {
    public static Map<String, CensusDTO> getCensesAdapter(CensusAnalyser.Country country, String... csvFilePath) {
        if (country.equals(CensusAnalyser.Country.INDIA))
            return new IndianCensusAdapter().loadCensusData(csvFilePath);
        else if (country.equals(CensusAnalyser.Country.US))
            return new UsCensusAdapter().loadCensusData(csvFilePath);
        throw new CensusAnalyserException("Invalid Country",
                CensusAnalyserException.ExceptionType.INVALID_COUNTRY);
    }
}
