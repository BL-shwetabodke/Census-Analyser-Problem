package censusanalyser;

import com.google.gson.Gson;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CensusAnalyser {
    Map<String, CensusDTO> censusMap;
    List<CensusDTO> censusDTOList;

    public int loadIndiaCensusData(String ...csvFilePath) throws CensusAnalyserException {
        censusMap = new CensusLoader().loadCensusData(IndiaCensusCSV.class, csvFilePath);
        censusDTOList = censusMap.values().stream().collect(Collectors.toList());
        return censusMap.size();

    }

//    public int loadIndianStateCodeData(String csvFilePath) throws CensusAnalyserException {
//        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
//            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
//            Iterator<IndiaStateCodeCSV> stateCodeCSVIterator = csvBuilder.getCSVFileIterator(reader, IndiaStateCodeCSV.class);
//            Iterable<IndiaStateCodeCSV> csvIterable = () -> stateCodeCSVIterator;
//            StreamSupport.stream(csvIterable.spliterator(), false)
//                    .filter(csvState -> censusMap.get(csvState.state) != null)
//                    .forEach(csvState -> censusMap.get(csvState.state).stateCode = csvState.stateCode);
//            censusDTOList = censusMap.values().stream().collect(Collectors.toList());
//
//            return censusMap.size();
//
//        } catch (IOException e) {
//            throw new CensusAnalyserException(e.getMessage(),
//                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
//        }
//    }

    public String getStateWiseSortedCensusData(String csvFilePath) {
        if(censusMap == null || censusMap.size()==0) {
            throw new CensusAnalyserException("No Census Data",
                    CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }

        Comparator<CensusDTO> censusComparator =Comparator.comparing(census -> census.state);
        censusDTOList =censusMap.values().stream().collect(Collectors.toList());
        this.sort(censusComparator);
        String sortedStateCensusJson=new Gson().toJson(censusDTOList);
        return sortedStateCensusJson;
    }

    public int loadUsCensusData(String csvFilePath) {
        return new CensusLoader().loadCensusData(UsCensusCSV.class, csvFilePath).size();
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
