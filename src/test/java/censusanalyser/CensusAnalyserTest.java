package censusanalyser;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CensusAnalyserTest {
    static CensusAnalyser censusAnalyser;
    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String INDIA_STATE_CSV_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";
    private static final String US_CENSUS_CSV_FILE_PATH = "./src/test/resources/USCensusData.csv";

    @BeforeClass
    public static void beforeClass() {
        censusAnalyser = new CensusAnalyser();
    }

    @Test
    public void givenIndianCensusCSVFileReturnsCorrectRecords() {
        try {
            int numOfRecords = censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CSV_FILE_PATH);
            Assert.assertEquals(29, numOfRecords);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        try {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnState_ShouldReturnSortedResult() {
        censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CSV_FILE_PATH);
        String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortField.STATE);
        CensusDTO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDTO[].class);
        Assert.assertEquals("Andhra Pradesh", censusCSV[censusCSV.length - 1].state);
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnStateCode_ShouldReturnSortedResult() {
        censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CSV_FILE_PATH);
        String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortField.STATECODE);
        CensusDTO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDTO[].class);
        Assert.assertEquals("AP", censusCSV[censusCSV.length - 1].stateCode);
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnPopulation_ShouldReturnSortedResult() {
        censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CSV_FILE_PATH);
        String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortField.POPULATION);
        CensusDTO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDTO[].class);
        Assert.assertEquals(199812341, censusCSV[0].population, 0.0);
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnPopulationDensity_ShouldReturnSortedResult() {
        censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CSV_FILE_PATH);
        String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortField.POPULATIONSDENSITY);
        CensusDTO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDTO[].class);
        Assert.assertEquals(1102, censusCSV[0].densityPerSqKm, 0.0);
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnTotalArea_ShouldReturnSortedResult() {
        censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CSV_FILE_PATH);
        String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortField.TOTALAREA);
        CensusDTO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDTO[].class);
        Assert.assertEquals(342239, censusCSV[0].totalArea, 0.0);
    }

    @Test
    public void givenUSCensusData_ShouldReturnCorrectRecord() {
        int censusDataCount = censusAnalyser.loadCensusData(CensusAnalyser.Country.US, US_CENSUS_CSV_FILE_PATH);
        Assert.assertEquals(51, censusDataCount);
    }

    @Test
    public void givenUSCensusData_WhenSortedOnState_ShouldReturnSortedResult() {
        censusAnalyser.loadCensusData(CensusAnalyser.Country.US, US_CENSUS_CSV_FILE_PATH);
        String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortField.STATE);
        CensusDTO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDTO[].class);
        Assert.assertEquals("Wyoming", censusCSV[0].state);
    }

    @Test
    public void givenUSCensusData_WhenSortedOnPopulation_ShouldReturnSortedResult() {
        censusAnalyser.loadCensusData(CensusAnalyser.Country.US, US_CENSUS_CSV_FILE_PATH);
        String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortField.POPULATION);
        CensusDTO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDTO[].class);
        Assert.assertEquals(3.7253956E7, censusCSV[0].population, 0.0);
    }

    @Test
    public void givenUSCensusData_WhenSortedOnPopulationDensity_ShouldReturnSortedResult() {
        censusAnalyser.loadCensusData(CensusAnalyser.Country.US, US_CENSUS_CSV_FILE_PATH);
        String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortField.POPULATIONSDENSITY);
        System.out.println(sortedCensusData);
        CensusDTO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDTO[].class);
        Assert.assertEquals(3805.61, censusCSV[0].densityPerSqKm, 0.00);
    }

    @Test
    public void givenUSCensusData_WhenSortedOnTotalArea_ShouldReturnSortedResult() {
        censusAnalyser.loadCensusData(CensusAnalyser.Country.US, US_CENSUS_CSV_FILE_PATH);
        String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortField.TOTALAREA);
        System.out.println(sortedCensusData);
        CensusDTO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDTO[].class);
        Assert.assertEquals(1723338.01, censusCSV[0].totalArea, 0.0);
    }

    @Test
    public void givenUSCensusData_WhenSortedOnStateCode_ShouldReturnSortedResult() {
        censusAnalyser.loadCensusData(CensusAnalyser.Country.US, US_CENSUS_CSV_FILE_PATH);
        String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortField.STATECODE);
        System.out.println(sortedCensusData);
        CensusDTO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDTO[].class);
        Assert.assertEquals("WY", censusCSV[0].stateCode);
    }

    @Test
    public void givenUSAndIndiaCensusData_WhenSortedOnPopulationDensity_ShouldReturnSortedResult() {
        censusAnalyser.loadCensusData(CensusAnalyser.Country.US, US_CENSUS_CSV_FILE_PATH);
        String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortField.POPULATIONSDENSITY);
        CensusDTO[] usCensusCSV = new Gson().fromJson(sortedCensusData, CensusDTO[].class);

        censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CSV_FILE_PATH);
        String sortedIndiaCensusData = censusAnalyser.getStateWiseSortedCensusData(SortField.POPULATIONSDENSITY);
        CensusDTO[] indiaCensusCSV = new Gson().fromJson(sortedIndiaCensusData, CensusDTO[].class);

        Assert.assertEquals(true, usCensusCSV[0].population < indiaCensusCSV[0].population);
    }
}