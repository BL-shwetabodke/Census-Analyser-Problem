package censusanalyser;

import com.google.gson.Gson;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CensusAnalyser {
    public enum Country {INDIA, US}

    Map<String, CensusDTO> censusMap;
    List<CensusDTO> censusDTOList;

    public int loadCensusData(Country country, String... csvFilePath) throws CensusAnalyserException {
        censusMap = CensusAdapterFactory.getCensesAdapter(country, csvFilePath);
        return censusMap.size();
    }

    public String getStateWiseSortedCensusData() {
        if (censusMap == null || censusMap.size() == 0) {
            throw new CensusAnalyserException("No Census Data",
                    CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDTO> censusComparator = Comparator.comparing(census -> census.state);
        censusDTOList = censusMap.values().stream().collect(Collectors.toList());
        this.sort(censusComparator);

        String sortedStateCensusJson = new Gson().toJson(censusDTOList);
        return sortedStateCensusJson;
    }


    private void sort(Comparator<CensusDTO> censusCSVComparator) {
        for (int i = 0; i < censusDTOList.size() - 1; i++) {
            for (int j = 0; j < censusDTOList.size() - i - 1; j++) {
                CensusDTO census1 = censusDTOList.get(j);
                CensusDTO census2 = censusDTOList.get(j + 1);
                if (censusCSVComparator.compare(census1, census2) > 0) {
                    censusDTOList.set(j, census2);
                    censusDTOList.set(j + 1, census1);
                }
            }
        }
    }

}
